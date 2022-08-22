package com.msvastudios.trick_builder.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;


public class AddGroupPopup {

    Dialog dialog;
    Popup callback;

    public AddGroupPopup(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_group);

        EditText groupName = dialog.findViewById(R.id.groupName);

        Button addButton = dialog.findViewById(R.id.groupAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!groupName.getText().toString().replaceAll(" ", "").isEmpty()) {
                    System.out.println("calling back" + groupName.getText().toString());
                    callback.newGroupShouldBeAdded(groupName.getText().toString());
                }
            }
        });
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.dismiss();
    }

    public void setOnNewGroupShouldBeAdded(Popup callback) {
        this.callback = callback;
    }

    public interface Popup {
        void newGroupShouldBeAdded(String entity);
    }

}

