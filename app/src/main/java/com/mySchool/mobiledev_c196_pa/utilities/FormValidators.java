package com.mySchool.mobiledev_c196_pa.utilities;

import android.widget.EditText;
import android.widget.RadioGroup;

import java.time.ZonedDateTime;

public abstract class FormValidators {

    private FormValidators() {
    }

    /**
     * Valid if name is not empty.
     * @param editText field to check
     * @return true if valid.
     */
    public static boolean nameValidation(EditText editText) {
        String name = editText.getText().toString().trim();
        if (!name.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Valid if Phone Number contains 10 characters.
     * @param editText text to check.
     * @return true if check is valid.
     */
    public static boolean phoneValidation(EditText editText) {
        String phone = editText.getText().toString().trim();
        if (phone.length() < 10) {
            return false;
        }
        return true;
    }

    /**
     * Valid if email is not empty.
     * @param editText email field to check.
     * @return true if valid.
     */
    public static boolean emailValidation(EditText editText) {
        String email = editText.getText().toString().trim();
        return !email.isEmpty();
    }

    /**
     * Valid if Start date is not empty.
     * @param start Start Date EditText.
     * @return true if valid, false if empty.
     */
    public static boolean startDateValidation(EditText start) {
        return !start.getText().toString().isEmpty();
    }

    /**
     * Valid if End Date is this same or after Start date and both fields are not empty.
     * @param start EditText start date.
     * @param end EditText end date.
     * @return true if valid.
     */
    public static boolean endDateValidation(EditText start, EditText end) {
        if (start.getText().toString().isEmpty() || end.getText().toString().isEmpty()) {
            return false;
        } else {
            ZonedDateTime startDate = DateTimeConv.stringToDateLocalWithoutTime(start.getText().toString());
            ZonedDateTime endDate = DateTimeConv.stringToDateLocalWithoutTime(end.getText().toString());
            return endDate.isAfter(startDate) || endDate.isEqual(startDate);
        }
    }

    /**
     * Valid if RadioGroup has an option selected.
     * @param group RadioGroup to check.
     * @return true if valid.
     */
    public static boolean radioGroupValidation(RadioGroup group) {
        return group.getCheckedRadioButtonId() != -1;
    }
}
