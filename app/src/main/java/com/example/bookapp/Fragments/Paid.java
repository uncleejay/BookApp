package com.example.bookapp.Fragments;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.example.bookapp.Adapter.RecyclerAdapter;
import com.example.bookapp.Adapter.pojo.webviewLoader;
import com.example.bookapp.Model.ApiCalls;
import com.example.bookapp.Model.bookModel;
import com.example.bookapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class Paid extends Fragment implements LoaderManager.LoaderCallbacks<List<bookModel>>, View.OnClickListener {
    private static final String bookFetchUrl = "https://www.googleapis.com/books/v1/volumes";
    private RecyclerView recyclerView;
    public RecyclerAdapter adapter;

    public static ArrayList<bookModel> bookList = null;
    private static final int BOOKS_LOADER_ID = 1;
    private TextInputEditText searchBox;
    private ProgressBar books_progressBar;
    private TextView empty_state;
    Button searchbtn;
    public static final String TAG = "MyActivity";

    public Paid() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_free, container, false);
        searchBox=view.findViewById(R.id.search);
        books_progressBar = view.findViewById(R.id.books_progressBar);
//        books_progressBar.setIndeterminate(true);
        books_progressBar.setVisibility(View.GONE);
        searchbtn=view.findViewById(R.id.searchbtn);
        empty_state = (TextView) view.findViewById(R.id.empty_state);
        searchbtn.setOnClickListener(this);

        //Checking the Network State
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            empty_state.setText("NO INTERNET");
            empty_state.setVisibility(View.VISIBLE);
            searchbtn.setEnabled(false);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        if(savedInstanceState == null || !savedInstanceState.containsKey("booksList")){
            bookList = new ArrayList<>();
            adapter = new RecyclerAdapter(getContext(), bookList);

            //log Statement
            Log.i(TAG, "onCreate: " + bookList);
        }else {
            bookList.addAll(savedInstanceState.<bookModel>getParcelableArrayList("booksList"));

            //log statement
            Log.i(TAG, "onCreate: under else" + bookList );
            adapter = new RecyclerAdapter(getContext(), bookList);
            //this will reLoad the adapter
            adapter.notifyDataSetChanged();

        }


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //getLoaderManager().initLoader(BOOKS_LOADER_ID, null, this);

        return view;
    }

    @NonNull
    @Override
    public Loader<List<bookModel>> onCreateLoader(int id, @Nullable Bundle args) {
        String query = searchBox.getText().toString();
        if(query.isEmpty() || query.length() == 0){
            searchBox.setError("Please Enter Any Book");
            return new webviewLoader(getContext(), null);
        }

        //WITH URI
        Uri baseUri = Uri.parse(bookFetchUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);

        //when we click om searchButton keyboard will hide
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        searchBox.setText("");

        //Returning a new Loader Object
        return new webviewLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<bookModel>> loader, List<bookModel> data) {
        books_progressBar.setVisibility(View.GONE);
        if(data !=null && !data.isEmpty()){
            empty_state.setText("");
            prepareBooks(data);
            Log.i(TAG, "onLoadFinished: ");
        }
        else{
            empty_state.setText("NO DATA");
            empty_state.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<bookModel>> loader) {
        Log.i(ApiCalls.TAG, "onLoaderReset: ");
        if(adapter == null){
            return;
        }
        bookList.clear();
        adapter.notifyDataSetChanged();
        Log.i(TAG, "onLoaderReset: " + bookList);
    }






    private void prepareBooks(List<bookModel> booksList) {

        bookList.addAll(booksList);
        Log.i(TAG, "prepareBooks: " + bookList);

        //notifiying the recycleradapter that data has been changed
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.searchbtn:
                books_progressBar.setVisibility(View.VISIBLE);
                bookList.clear();
                adapter.notifyDataSetChanged();
                getLoaderManager().restartLoader(BOOKS_LOADER_ID, null, this);
                getLoaderManager().initLoader(BOOKS_LOADER_ID, null, this);
                Log.i(TAG, "searchButton: "  + bookList);
                break;
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
