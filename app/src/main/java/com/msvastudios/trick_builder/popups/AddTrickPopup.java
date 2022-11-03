package com.msvastudios.trick_builder.popups;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.popups.AddGroupPopup;


public class AddTrickPopup {


    Dialog dialog;
    Popup callback;
    EditText groupName;

    public AddTrickPopup(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_group);

        groupName = dialog.findViewById(R.id.groupName);

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
        groupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains("\n")){
                    groupName.setText(charSequence.toString().replaceAll("\n",""));
                    callback.newGroupShouldBeAdded(groupName.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public void show() {
        dialog.show();
    }

    public void refresh(){
        groupName.setText("");
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
