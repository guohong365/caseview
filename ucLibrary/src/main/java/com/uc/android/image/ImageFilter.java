package com.uc.android.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.uc.android.model.TitledObject;
import com.uc.android.model.WithId;

public interface ImageFilter extends WithId, TitledObject {
    String getCategory();
    Bitmap apply(Bitmap input);
}
