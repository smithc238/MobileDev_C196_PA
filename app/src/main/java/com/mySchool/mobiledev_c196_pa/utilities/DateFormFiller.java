package com.mySchool.mobiledev_c196_pa.utilities;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Class to create a DatePickerDialog when an EditText field is clicked.
 */
public class DateFormFiller {
    private ZonedDateTime date;
    private EditText editText;

    /**
     * Made constructor private to ensure static method was only version to instantiate this class.
     * @param editText editText to set DatePickerDialog.
     * @param date date to set starting point, if null starts with current date.
     */
    private DateFormFiller(EditText editText, ZonedDateTime date) {
        this.editText = editText;
        if (date == null) {
            this.date = ZonedDateTime.now();
        } else {
            this.date = date;
        }
    }

    /**
     * Static method to get instance to set DatePickerDialog on EditText field.
     * @param editText editText to add DatePickerDialog.
     * @param date date to set starting point, if null starts with current date.
     */
    public static void dateOnClickDatePicker(EditText editText, ZonedDateTime date) {
        editText.setOnClickListener(v -> {
            DateFormFiller formFiller = new DateFormFiller(editText, date);
            formFiller.showDatePickerDialog(v);
        });
    }

    /**
     * Creates DatePickerDialog with current date as starting point.
     * @param view view.
     */
    private void showDatePickerDialog(View view) {
        //Had to subtract 1 from month as DatePicker was set to work with Calendar.MONTH (0-11)
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(),onDateSetListener,
                date.getYear(),date.getMonthValue()-1,date.getDayOfMonth());
        dialog.show();
    }

    /**
     * Dialog Listener which gets the date from dialog.
     */
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //Added 1 to month as Calendar.MONTH is 0-11 to correct for when using ZonedDateTime.getMonthValue
            ZonedDateTime setDate = ZonedDateTime.of(year,month+1,dayOfMonth,8,0,0,0, ZoneId.systemDefault());
            updateField(setDate);
        }
    };

    /**
     * Method help set the EditText field after Dialog is complete.
     * @param dateTime date created from listener.
     */
    private void updateField(ZonedDateTime dateTime) {
        this.date = dateTime;
        editText.setText(DateTimeConv.dateToStringLocal(dateTime));
    }


}
