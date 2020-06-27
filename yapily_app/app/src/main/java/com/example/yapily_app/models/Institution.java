package com.example.yapily_app.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class Institution {

    private String id;

    private String name;

    private String imageUrl;

    public Institution(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @BindingAdapter({"imageUrl"})
    public static void loadImageUrl(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .fit()
                .centerInside()
                .into(view);
    }
}
