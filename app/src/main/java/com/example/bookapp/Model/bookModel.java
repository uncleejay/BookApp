package com.example.bookapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class bookModel implements Parcelable {
    private String title;
    private String author;
    private String infoUrl;
    private String imageUrl;
    private String publisher;


    protected bookModel(Parcel in) {
        title = in.readString();
        author = in.readString();
        infoUrl = in.readString();
        imageUrl = in.readString();
        publisher = in.readString();
    }

    public bookModel(String title, String author, String infoUrl, String imageUrl, String publisher) {
        this.title = title;
        this.author = author;
        this.infoUrl = infoUrl;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
    }

    public static final Creator<bookModel> CREATOR = new Creator<bookModel>() {
        @Override
        public bookModel createFromParcel(Parcel in) {
            return new bookModel(in);
        }

        @Override
        public bookModel[] newArray(int size) {
            return new bookModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(infoUrl);
        dest.writeString(imageUrl);
        dest.writeString(publisher);
    }
}
