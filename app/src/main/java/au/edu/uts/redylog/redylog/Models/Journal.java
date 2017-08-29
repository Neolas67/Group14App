package au.edu.uts.redylog.redylog.Models;

import java.util.Date;

import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class Journal {

    long _journalId;
    long _userId;
    String _title;
    String _description;
    Date _startDate;
    Date _endDate;
    StatusEnum _status;

    public Journal(long _journalId, long _userId, String _title, String _description, Date _startDate, Date _endDate, StatusEnum _status) {
        this._journalId = _journalId;
        this._userId = _userId;
        this._title = _title;
        this._description = _description;
        this._startDate = _startDate;
        this._endDate = _endDate;
        this._status = _status;
    }

    public Journal(String _title, String _description) {
        User user = UserManager.getInstance().get_currentUser();
        this._userId = user.get_userId();
        this._title = _title;
        this._description = _description;
        this._startDate = new Date();
        this._endDate = null;
        this._status = StatusEnum.Open;
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

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
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
}
