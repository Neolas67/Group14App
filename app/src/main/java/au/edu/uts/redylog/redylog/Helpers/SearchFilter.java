package au.edu.uts.redylog.redylog.Helpers;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hayden on 19-Sep-17.
 */

public class SearchFilter implements Serializable {

    String _query;
    Date _startDate;
    Date _endDate;
    StatusEnum _status;

    public String get_query() {
        return _query;
    }

    public void set_query(String _query) {
        this._query = _query;
    }

    public Date get_startDate() {
        return _startDate;
    }

    public void set_startDate(Date _startDate) {
        this._startDate = _startDate;
    }

    public Date get_endDate() {
        return _endDate;
    }

    public void set_endDate(Date _endDate) {
        this._endDate = _endDate;
    }

    public StatusEnum get_status() {
        return _status;
    }

    public void set_status(StatusEnum _status) {
        this._status = _status;
    }

    @Override
    public String toString() {
        return "SearchFilter [_startDate = " + _startDate
                + ", _endDate = " + _endDate
                + ", _status = " + _status
                + "]";
    }
}
