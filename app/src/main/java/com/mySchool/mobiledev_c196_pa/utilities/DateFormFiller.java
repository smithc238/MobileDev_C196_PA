package com.mySchool.mobiledev_c196_pa.utilities;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateFormFiller {
    private ZonedDateTime date = ZonedDateTime.now();
    private EditText editText;

    public DateFormFiller(EditText editText) {
        this.editText = editText;
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(),onDateSetListener,
                date.getYear(),date.getMonthValue(),date.getDayOfMonth());
        dialog.show();
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ZonedDateTime setDate = ZonedDateTime.of(year,month,dayOfMonth,8,0,0,0, ZoneId.systemDefault());
            updateField(setDate);
        }
    };

    private void updateField(ZonedDateTime dateTime) {
        editText.setText(DateTimeConv.dateToStringLocal(dateTime));
    }
}
