package com.msvastudios.trick_builder.utils.wheel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Wheel {
    Bitmap image;
    String id;
    String label;

    public Wheel(String id, String label, Bitmap image) {
        this.id = id;
        this.label = label;
        this.image = image;
    }

    public Wheel(String id, String label, int image, Context context) {
        this.id = id;
        this.label = label;
        this.image = BitmapFactory.decodeResource(context.getResources(), image);
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Bitmap getImage() {
        return image;
    }
}
