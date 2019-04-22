package com.example.bookapp.Adapter.pojo;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.example.bookapp.Model.ApiCalls;
import com.example.bookapp.Model.bookModel;

import java.util.List;

public class webviewLoader extends AsyncTaskLoader<List<bookModel>> {

    String url;
    public static List<bookModel> arrayList = null;

    public webviewLoader(Context context, String url) {
        super(context);
        if(url == null){
            return;
        }
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<bookModel> loadInBackground() {
        if(url == null) {
            return null;
        }
        arrayList = ApiCalls.fetchBooksData(url);
        return arrayList;
    }
}
