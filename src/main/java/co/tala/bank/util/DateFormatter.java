package co.tala.bank.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Utility class for formatting and parsing dates.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017 author
 * <a href="tonyafula@gmail.com">Tony Afula</a>
 */
public class DateFormatter {

    /**
     * Format date to yyyy-M-dd.
     *
     * @param date -date to format
     * @return formatted date.
     */
    public static Date formatDate(Date date) {
        Date newDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
            String todayStr = dateFormat.format(date);
            newDate = dateFormat.parse(todayStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return newDate;
    }

}
