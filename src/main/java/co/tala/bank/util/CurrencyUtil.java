package co.tala.bank.util;

import java.text.DecimalFormat;

/**
 * Utility class for formatting amounts.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017 author
 * <a href="tonyafula@gmail.com">Tony Afula</a>
 */
public class CurrencyUtil {

    /**
     * Round off to 2dp and add comma separator.
     *
     * @param wholeNumber
     * @return formatted number with comma separator and currency.
     */
    public static String formatNumber(Double wholeNumber) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        final String formattedNumber = "$"+formatter.format(wholeNumber);//add commas

        return formattedNumber;
    }
}
