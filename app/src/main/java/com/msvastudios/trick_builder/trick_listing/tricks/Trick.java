package com.msvastudios.trick_builder.trick_listing.tricks;

import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.ArrayList;
import java.util.UUID;
public class Trick {
    String text;
    ArrayList<String> groupUuid;
    String uuid;

    public Trick(String text, ArrayList<String> groupUuid){
        this.text = text;
        this.groupUuid = groupUuid;
        uuid = UUID.randomUUID().toString();
    }

    public Trick(TrickEntity entity){
        this.text = entity.name;
        this.groupUuid = entity.groupIds;
        uuid = entity.trickUUID;
    }

    public String getText() {
        return text;
    }
}
