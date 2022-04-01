package com.msvastudios.trick_builder.node.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.NavigationRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;


public class NodeNav extends LinearLayout {
    Context context;

    int width, height;

    public NodeNav(Context context) {
        super(context);
        this.context = context;

        width = NodeDimensionsCalculator.getInnerNodeWidth();
        height = NodeDimensionsCalculator.nodeItemHeight();

        init(null, 0);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i, i1, i2, i3);
    }

    private void init(AttributeSet attrs, int defStyle) {

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(1);
        setBackgroundColor(Color.parseColor("#212321"));

        View.inflate(context, R.layout.view_nodenav, this);
        setElevation(6 * context.getResources().getDisplayMetrics().density);
        setBackgroundResource(R.drawable.nav);

    }

    public NodeNav setColor(@ColorRes int color){
        setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
        return this;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int exactWidth = width; //do something to calculate the view width
        int exactHeight = height;//do something to calculate the view height

        setMeasuredDimension(exactWidth, exactHeight);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(exactWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(exactHeight, MeasureSpec.EXACTLY)
        );
    }


}