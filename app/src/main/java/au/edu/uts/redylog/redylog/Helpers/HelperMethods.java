package au.edu.uts.redylog.redylog.Helpers;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
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

        return name.matches("^[A-Za-z]{3,15}$");
        /* Match characters and symbols in the list, a-z & A-Z, and it Length at least 3 characters and maximum length of 15*/
    }

    public static boolean validEmail(String email) {
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        /* The Magic line of code */
    }

    public static boolean validPassword(String password) {
        return password.matches("^[A-Za-z]{4,8}$");
        /* Match characters and symbols in the list, a-z & A-Z, and it Length at least 4 characters and maximum length of 8*/

        //return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");//
        /*Minimum eight characters, at least one letter and one number:*/

        //return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");//
        /*Minimum eight characters, at least one letter, one number and one special character:*/

        //return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");//
        /*Minimum eight characters, at least one uppercase letter, one lowercase letter and one number*/

        //return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}");//
        /*Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character*/

    }




    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }
}
