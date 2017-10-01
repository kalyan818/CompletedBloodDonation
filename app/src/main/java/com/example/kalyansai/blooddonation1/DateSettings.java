package com.example.kalyansai.blooddonation1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by kalyan sai on 23-Sep-17.
 */

public class DateSettings extends SignUp implements DatePickerDialog.OnDateSetListener {
    Context context;
    public DateSettings(Context context)
    {
        this.context = context;
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        SignUp.Abc(i,i1,i2,context);
    }
}

