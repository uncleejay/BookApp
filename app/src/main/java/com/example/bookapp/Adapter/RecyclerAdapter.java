package com.example.bookapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookapp.Model.bookModel;
import com.example.bookapp.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.vHolder> {
    private Context mContext;
    private List<bookModel> bookList;

    public RecyclerAdapter(Context mContext, List<bookModel> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookcard,parent,false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.title.setText(bookList.get(position).getTitle());
        holder.author.setText(bookList.get(position).getAuthor());
        // loading Book cover using Glide library
        Glide.with(mContext).load(bookList.get(position).getImageUrl()).into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{
        public TextView title, author;
        public ImageView thumbnail;

        public vHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.count);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

        }
    }
}
