package au.edu.uts.redylog.redylog.Helpers;

import android.text.TextUtils;

import java.util.Date;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class HelperMethods {

    public  static  long dateToLong(Date date) {
        return date != null ? date.getTime() : 0;
    }

    public  static  Date longToDate(long ticks) {
        return new Date(ticks);
    }

    public static boolean validName(String name) {
        /*return name.matches("/^[a-z ,.'-]+$/i");*/
        return !TextUtils.isEmpty(name);
    }

    public static boolean validEmail(String email) {
        /*return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");*/
        return !TextUtils.isEmpty(email);
    }

    public static boolean validPassword(String password) {
        /*return password.matches("\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"");*/
        return !TextUtils.isEmpty(password);
    }
}
