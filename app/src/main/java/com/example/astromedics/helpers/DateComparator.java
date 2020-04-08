package com.example.astromedics.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date> {
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public boolean equals(Date d1, Date d2){
        return DATE_FORMAT.format(d1).equals(DATE_FORMAT.format(d2));
    }

    public boolean between(Date date, Date startDate, Date endDate){
        boolean returnable = false;

        if(startDate.before(date) && endDate.after(date)){
            returnable = true;
        }
        if(equals(startDate, date) || equals(endDate, date)){
            returnable = true;
        }

        return returnable;
    }

    public int compare(Date d1, Date d2) {
        return DATE_FORMAT.format(d1)
                          .compareTo(DATE_FORMAT.format(d2));
    }
}
