package com.hf.eventstats.utils;

import com.hf.eventstats.exception.InvalidTimeStampException;
import com.hf.eventstats.exception.InvalidXException;
import com.hf.eventstats.exception.InvalidYException;

import java.sql.Timestamp;

public class PayloadUtility {
    private static String TIMESTAMP_EXCEPTION = "TIMESTAMP_EXCEPTION";
    private static String INVALID_X_EXCEPTION = "INVALID_X_EXCEPTION";
    private static String INVALID_Y_EXCEPTION = "INVALID_Y_EXCEPTION";
    private static int Y_MIN_RANGE = 1073741823;
    private static int Y_MAX_RANGE = 2147483647;

    public static boolean isValid(String x, String y, Timestamp ts) throws InvalidXException, InvalidYException {
        return validX(x) && validY(y) && (ts.getClass() == Timestamp.class);
    }

    public static Timestamp createTimestampFromString(String stringUnixMs) throws InvalidTimeStampException {
        try {
            long unixTimestamp = Long.parseLong(stringUnixMs);
            return new Timestamp(unixTimestamp);
        } catch (Exception e) {
            throw new InvalidTimeStampException(TIMESTAMP_EXCEPTION, "Couldn't create a Timestamp for input: " + stringUnixMs);
        }
    }

    private static boolean validX(String x) throws InvalidXException {
        int integerPlaces = x.indexOf('.');
        int decimalPlaces = x.length() - integerPlaces - 1;
        if (decimalPlaces > 10) {
            throw new InvalidXException(INVALID_X_EXCEPTION, "The fractional part or x : " + x + "  is more than 10 digits ");
        }
        return true;
    }

    private static boolean validY(String stringY) throws InvalidYException {
        int y = Integer.parseInt(stringY);
        if (y < Y_MIN_RANGE || y > Y_MAX_RANGE) {
            throw new InvalidYException(INVALID_Y_EXCEPTION, "Value " + y + " of y is out of range");
        }
        return true;
    }
}
