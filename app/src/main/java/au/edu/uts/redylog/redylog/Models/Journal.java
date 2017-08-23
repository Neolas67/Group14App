package au.edu.uts.redylog.redylog.Models;

import java.util.Date;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class Journal {

    long _journalId;
    long _userId;
    String _title;
    Date _startDate;
    Date _endDate;
    int _status;

    public Journal(long _journalId, long _userId, String _title, Date _startDate, Date _endDate, int _status) {
        this._journalId = _journalId;
        this._userId = _userId;
        this._title = _title;
        this._startDate = _startDate;
        this._endDate = _endDate;
        this._status = _status;
    }

    public Journal(long _userId, String _title, Date _startDate, Date _endDate, int _status) {
        this._userId = _userId;
        this._title = _title;
        this._startDate = _startDate;
        this._endDate = _endDate;
        this._status = _status;
    }

    public long get_journalId() {
        return _journalId;
    }

    public void set_journalId(long _journalId) {
        this._journalId = _journalId;
    }

    public long get_userId() {
        return _userId;
    }

    public void set_userId(long _userId) {
        this._userId = _userId;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
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

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }
}
