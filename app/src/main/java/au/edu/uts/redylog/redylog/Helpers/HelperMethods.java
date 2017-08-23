package au.edu.uts.redylog.redylog.Helpers;

import java.util.Date;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class HelperMethods {

    public  static  long dateToLong(Date date) {
        return date != null ? date.getTime() : null;
    }

    public  static  Date longToDate(long ticks) {
        return new Date(ticks);
    }

}
