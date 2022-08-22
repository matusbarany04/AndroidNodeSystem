package com.msvastudios.trick_builder.popups;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;


import com.msvastudios.trick_builder.R;


public class YesNoDialog {

        Dialog dialog;
        YesNoDialog.Popup callback;

        public YesNoDialog(Context context, String title, String description, int dialogImage) {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_yes_no);

            Button yes = dialog.findViewById(R.id.btn_yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onYesClicked();
                }
            });

            Button no = dialog.findViewById(R.id.btn_no);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onNoClicked();
                }
            });


            TextView titleView = dialog.findViewById(R.id.txttite);
            titleView.setText(title);

            TextView descriptionView = dialog.findViewById(R.id.txtDesc);
            descriptionView.setText(description);
        }

        public void show(){
            dialog.show();
        }

        public void hide(){
            dialog.dismiss();
        }


        public void setOnItemClickListener(YesNoDialog.Popup callback) {
            this.callback = callback;
        }

        public interface Popup {
            void onYesClicked();
            void onNoClicked();
        }

    }

