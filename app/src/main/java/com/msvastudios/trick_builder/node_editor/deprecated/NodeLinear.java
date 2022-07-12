package com.msvastudios.trick_builder.node_editor.deprecated;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.msvastudios.trick_builder.R;
@Deprecated
public class NodeLinear extends ConstraintLayout {

    private int xDelta, yDelta;
    Context context;

    public NodeLinear(Context context) {
        super(context);
        init(null, 0);
        this.context = context;
    }

    public NodeLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NodeLinear(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
//        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.drawable.ic_launcher_background);





//        invalidate();

//        Button button = new Button(getContext());
//        button.setWidth(50);
//        button.setHeight(50);
//        button.setText("HEYY");

//        addView(button);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Node, defStyle, 0);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int exactWidth = 500; //do something to calculate the view widht
        int exactHeight = 500;//do something to calculate the view height

        setMeasuredDimension(exactWidth, exactHeight);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(exactWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(exactHeight, MeasureSpec.EXACTLY)
        );
    }
}