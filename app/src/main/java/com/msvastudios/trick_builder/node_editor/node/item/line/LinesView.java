package com.msvastudios.trick_builder.node_editor.node.item.line;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.msvastudios.trick_builder.R;

import java.util.*;

public class LinesView extends View {
    public int iterator = 0;
    // below we are creating variables for our paint

    ArrayList<Line> lines;
    Paint otherPaint, outerPaint, textPaint;

    // and a floating variable for our left arc.
    float arcLeft;


    @SuppressLint("ResourceAsColor")
    public LinesView(Context context) {
        super(context);

        // on below line we are initializing our paint variable for our text
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        // on below line we are setting color to it.
        textPaint.setColor(Color.WHITE);

        // on below line we are setting text size to it.
        // In Paint we have to add text size using px so
        // we have created a method where we are converting dp to pixels.
        textPaint.setTextSize(pxFromDp(context, 24));

        // on below line we are initializing our outer paint
        outerPaint = new Paint();

        // on below line we are setting style to our paint.
//        outerPaint.setStyle(Paint.Style.FILL);

        // on below line we are setting color to it.
//        outerPaint.setColor(getResources().getColor(R.color.purple_200));

        // on below line we are creating a display metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();

        // on below line we are getting display metrics.
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        // on below line we are assigning
        // the value to the arc left.
        arcLeft = pxFromDp(context, 20);

        // on below line we are creating
        // a new variable for our paint
        otherPaint = new Paint();

        lines = new ArrayList<>();
    }

    // below method is use to generate px from DP.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // below four lines of code is use to add
        // back color to our screen which is green
        // canvas.drawPaint(outerPaint);

        // on below line we are setting color to our paint.
        otherPaint.setColor(Color.WHITE);

        // on below line we are setting style to out paint.
        otherPaint.setStyle(Paint.Style.FILL);
        otherPaint.setStrokeWidth(25);

        for (Line line : lines) {
            line.drawSelf(canvas, otherPaint);
        }

        otherPaint.setColor(getResources().getColor(R.color.purple_200));

        canvas.drawText("Updates: " + iterator, (float) (getWidth() * 0.3), (float) (getHeight() * 0.8), textPaint);
        iterator++;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
        invalidate();
    }


    public void addLine(Line line) {
        checkForMatch(line);
        lines.add(line);
        invalidate();
    }

    private void checkForMatch(Line line) {
        for (int i = 0 ; i < lines.size();i++) {
            if (line.endPoint == lines.get(i).endPoint) { // cant connect multiple outputs to single input
                removeLine(i);
            }
        }
    }

    public ArrayList<Line> getLinesContaining(LinePoint point){
        ArrayList<Line> output = new ArrayList<>();
        for (Line line : lines) {
            if (line.endPoint.equals(point) || line.startPoint.equals(point)){
                output.add(line);
            }
        }

        return output;
    }

    public void removeLine(int index) {
        lines.remove(index);
        invalidate();
    }

    public void removeLine(String id) { // TODO too slow refractor later!, (later comment) maybe I wanted to use hashmap?
        ArrayList<Line> outputLines = new ArrayList<>();
        for (Line line : lines) {
            if (!line.getId().equals(id)){
                outputLines.add(line);
            }
        }

        lines = outputLines;

        invalidate();
    }

    public void removeAllLinesWith(ArrayList<String> pointIds) { // TODO too slow refractor later!
        HashSet<Line> outputLines = new HashSet<Line>(lines);

        if(pointIds.size() > 0){
            for (String id : pointIds) {
                System.out.println("id + "  + id);
                for (Line line : lines) {
                    System.out.println("start " + line.getStartPoint().getId());
                    if (line.getStartPoint().getId().equals(id) || line.getEndPoint().getId().equals(id) ){
                        outputLines.remove(line);
                        System.out.println("found .. :)");
                    }
//                System.out.println("not found... :(");
                }
            }
            lines = new ArrayList<Line>(outputLines);
        }
        invalidate();
    }

    public void removeAllLinesWith(String pointid) {
        ArrayList<Line> outputLines = new ArrayList<>();
        for (Line line : lines) {
            if (!line.getStartPoint().getId().equals(pointid) &&
                    !line.getEndPoint().getId().equals(pointid) ){
                outputLines.add(line);
            }
        }
        lines = outputLines;
        invalidate();
    }

}
