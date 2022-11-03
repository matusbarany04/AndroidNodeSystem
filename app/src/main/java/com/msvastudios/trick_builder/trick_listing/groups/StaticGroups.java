package com.msvastudios.trick_builder.trick_listing.groups;

import android.service.controls.templates.StatelessTemplate;

public enum StaticGroups {
    LEARNED_TRICKS("learned_tricks"),
    ALL_TRICKS("all_tricks");
    String title;
    StaticGroups(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Might return null
     * @param title
     * @return
     */
    public static StaticGroups getByTitle(String title){
        for (StaticGroups group: StaticGroups.values()) {
            if(group.title.equals(title))
                return group;
        }
        return null;
    }
}
