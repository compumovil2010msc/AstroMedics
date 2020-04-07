package com.example.astromedics.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ApplicationDateFormat {

    public Date Now() {
        return new Date();
    }

    public Date createDate(int year, int month, int date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,
                year);
        cal.set(Calendar.MONTH,
                month);
        cal.set(Calendar.DAY_OF_MONTH,
                date);
        return cal.getTime();
    }

    public Date createDate(int year, int month, int date, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,
                year);
        cal.set(Calendar.MONTH,
                month);
        cal.set(Calendar.DAY_OF_MONTH,
                date);
        cal.set(Calendar.HOUR,
                hour);
        cal.set(Calendar.MINUTE,
                minute);
        return cal.getTime();
    }

    public String toString(Date date){
        Locale spanishLocale = new Locale("es",
                                          "ES");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy", spanishLocale);
        return simpleDateFormat.format(date);
    }

    public String getHoursAndMinutes(Date date){
        Locale spanishLocale = new Locale("es",
                                          "ES");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", spanishLocale);
        return simpleDateFormat.format(date);
    }
}
