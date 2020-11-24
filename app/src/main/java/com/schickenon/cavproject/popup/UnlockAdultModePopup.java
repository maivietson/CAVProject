package com.schickenon.cavproject.popup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.schickenon.cavproject.MainActivity;
import com.schickenon.cavproject.R;

public class UnlockAdultModePopup {

    EditText editTextCode;
    AppCompatButton btnUnlock, btnClose;

    Context context;

    public UnlockAdultModePopup(Context context) {
        this.context = context;
    }

    public void showPopupWindow(final View view) {

        LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup_unlock_adult_mode, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        editTextCode = popupView.findViewById(R.id.edtCode);
        btnUnlock = popupView.findViewById(R.id.btnUnlock);
        btnClose = popupView.findViewById(R.id.btn_Close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextCode.getText().toString().toUpperCase();
                if(code.equals("XXX2020")) {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }
        });
    }
}
