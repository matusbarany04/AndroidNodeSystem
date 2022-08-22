package com.msvastudios.trick_builder.trick_listing.groups;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;

public class GroupItem extends RelativeLayout {
    Context context;
    String title;

    public GroupItem(Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;
    }


    public GroupItem(Context context)
    {
        super(context);
        this.context = context;
    }

    public GroupItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public GroupItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
    }


    public RelativeLayout getView(){
        return this;
    }

    public String getTitle() {
        return title;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }
}
