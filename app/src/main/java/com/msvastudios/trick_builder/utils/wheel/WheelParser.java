package com.msvastudios.trick_builder.utils.wheel;

import android.content.Context;

import com.msvastudios.trick_builder.R;

import java.util.ArrayList;

public class WheelParser {
    private ArrayList<Wheel> wheelList;
    Context context;
    public WheelParser(Context context) {
        wheelList = new ArrayList<>();
        this.context = context;
        loadWheelsFromMyServer();
    }

    private void loadWheelsFromMyServer() {
        try {
            throw new Exception("Just for testing");
        } catch (Exception e) {
            executeFallbackWheels();
        }
    }

    private void executeFallbackWheels() {

        wheelList.add(new Wheel("1", "basic", R.drawable.basic,context));
        wheelList.add(new Wheel("2", "basic", R.drawable.melon_trans,context));
        wheelList.add(new Wheel("3", "basic", R.drawable.red_wheel,context));
        wheelList.add(new Wheel("4", "basic", R.drawable.donut_wheel,context));;

    }

    public ArrayList<Wheel> getWheelList() {
        return wheelList;
    }

    public Wheel getWheel(int position) {
        return wheelList.get(position);
    }

    public Wheel getWheelById(String id) {
        for (Wheel wheel: wheelList) {
            if (id.equals(wheel.id)){
                return wheel;
            }
        }
        return null;
    }


}
