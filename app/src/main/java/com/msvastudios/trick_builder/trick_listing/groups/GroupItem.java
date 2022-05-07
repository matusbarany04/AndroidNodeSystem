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
//        initYourStuff();

    }

    public GroupItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
//        initYourStuff();
    }

    public GroupItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
//        initYourStuff();
    }

//    private View getView(ViewGroup ){
//        LayoutInflater inflater = LayoutInflater.from(context);
//        inflater.inflate(R.layout.grid_item,context,false);
//        TextView text = findViewById(R.id.grid_item_text);
//        text.setText(title);
//        setBackgroundColor(Color.RED);
////        setElevation(6 * context.getResources().getDisplayMetrics().density);
////        setBackgroundResource(R.drawable.nav);
//        return this;
//    }

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
