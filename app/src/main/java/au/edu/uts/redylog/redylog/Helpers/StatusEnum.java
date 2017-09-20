package au.edu.uts.redylog.redylog.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hayden on 28-Aug-17.
 */

public enum StatusEnum {
    None, //Used for searching
    Open, //Default state of Journals & Entries
    Closed, //Used ONLY for Journals
    Hidden, //Used ONLY for Entries
    Deleted //Used for both Journals & Entries
}
