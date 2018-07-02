package com.uc.android.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;

public abstract class AbstractImageFilter implements ImageFilter {
    private Long id;
    private String title;
    private String category;
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getCategory() {
        return category;
    }


    public AbstractImageFilter(String category, Long id, String title){
        this.id=id;
        this.category=category;
        this.title=title;
    }
}
