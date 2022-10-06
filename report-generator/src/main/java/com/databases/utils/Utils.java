package com.databases.utils;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service("ReportUtils")
public class Utils {

    public String formatDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = day < 10 ? "0" + day : day + "";
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthStr = month < 10 ? "0" + month : month + "";
        int year = calendar.get(Calendar.YEAR);
        return dayStr + '.' + monthStr + '.' + year;
    }

    public Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

}
