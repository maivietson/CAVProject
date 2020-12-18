package com.galaxygame.cavenjoy.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.galaxygame.cavenjoy.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class CustomDialog extends Dialog {

    Context context;
    CustomDialog dialog;

    private RadioGroup rbGroup;
    private RadioButton rbMale, rbFamale;
    private TextInputEditText edtAge;
    private AppCompatButton btnOK;

    int age;
    String gender;
    String[] arrayAge  = new String[100];

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        dialog = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);

        rbGroup = findViewById(R.id.rb_group);
        rbMale = findViewById(R.id.rb_male);
        rbFamale = findViewById(R.id.rb_famale);
        edtAge = findViewById(R.id.edtAge);
        btnOK = findViewById(R.id.btn_ok);

        rbMale.setChecked(true);

        rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                doOnGenderChanged(compoundButton, b);
            }
        });

        rbFamale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                doOnGenderChanged(compoundButton, b);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ageString = edtAge.getText().toString();

                if(ageString.isEmpty()) {
                    edtAge.setError("Please enter your age");
                } else {
                    dialog.dismiss();
                    age = Integer.parseInt(edtAge.getText().toString());
                    String message;
                    if (age >= 18) {
                        message = "Code to unlock 18+ mode: xxx2020";
                    } else {
                        message = "Thank you!!";
                    }

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setMessage(message);
                    alertBuilder.setCancelable(true);

                    alertBuilder.setPositiveButton("Ok", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    private void doOnGenderChanged(CompoundButton buttonView, boolean isChecked) {
        RadioButton radio = (RadioButton)buttonView;
        gender = radio.getText().toString();
    }
}
