package com.msvastudios.trick_builder.trick_listing.tricks;

import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.ArrayList;
import java.util.UUID;
public class Trick {
    String name;
    String description;
    String uuid;
    boolean learned;
    ArrayList<String> groupUuid;

    public Trick(String name, String description, ArrayList<String> groupUuid,boolean learned){
        this.name = name;
        this.description = description;
        this.groupUuid = groupUuid;
        uuid = UUID.randomUUID().toString();
        this.learned = learned;
    }

    public Trick(TrickEntity entity){
        this.name = entity.name;
        this.groupUuid = entity.groupIds;
        this.description = entity.description;
        uuid = entity.trickUUID;
        this.learned = entity.learned;
    }

    public String getName() {
        return name;
    }
}
