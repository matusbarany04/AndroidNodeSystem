package com.msvastudios.trick_builder.node_editor.node.item.line;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line {
    public static int lineCounter = 0;

    boolean connected = false;
    LinePoint startPoint;
    LinePoint endPoint;
    private String id;

    public Line(LinePoint startPoint,LinePoint endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        calculateId();
    }

    public Line drawSelf(Canvas canvas, Paint paint){
        canvas.drawLine(startPoint.x, startPoint.y , endPoint.x , endPoint.y, paint);
        return this;
    }

    public void updateEndPoint(LinePoint endPoint){
        this.endPoint.x = endPoint.x;
        this.endPoint.y = endPoint.y;
    }

    private void calculateId(){
        this.id = String.valueOf(lineCounter + 1);
        lineCounter++;
    }
    public void updateStartPoint(LinePoint startPoint){
        this.startPoint.x = startPoint.x;
        this.startPoint.y = startPoint.y;
    }

    public String getId() {
        return id;
    }

    public LinePoint getEndPoint() {
        return endPoint;
    }

    public LinePoint getStartPoint() {
        return startPoint;
    }
}
