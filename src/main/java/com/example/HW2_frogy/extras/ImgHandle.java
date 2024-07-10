package com.example.HW2_frogy.extras;

import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

public class ImgHandle {

    private AppCompatImageView img;
    private Type type;

    public ImgHandle() {
        // Default constructor
    }

    public ImageView getImg() {
        return img;
    }

    public Type getType() {
        return type;
    }

    public ImgHandle setImg(AppCompatImageView img) {
        this.img = img;
        return this;
    }

    public ImgHandle setType(Type type) {
        this.type = type;
        return this;
    }
}
