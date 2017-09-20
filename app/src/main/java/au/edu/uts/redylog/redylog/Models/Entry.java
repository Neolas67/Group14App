package au.edu.uts.redylog.redylog.Models;

import java.io.Serializable;
import java.util.Date;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class Entry implements Serializable {

    long _entryId;
    long _journalId;
    String _title;
    Date _createdDate;
    String _contents;
    StatusEnum _status;
    double _latitude;
    double _longitude;

    public Entry(long _entryId, long _journalId, String _title, Date _createdDate, String _contents, StatusEnum _status, double _latitude, double _longitude) {
        this._entryId = _entryId;
        this._journalId = _journalId;
        this._title = _title;
        this._createdDate = _createdDate;
        this._contents = _contents;
        this._status = _status;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public Entry(long _journalId, String _title, Date _createdDate, String _contents, StatusEnum _status, double _latitude, double _longitude) {
        this._journalId = _journalId;
        this._title = _title;
        this._createdDate = _createdDate;
        this._contents = _contents;
        this._status = _status;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public Entry( String _title, String _contents, long _journalId) {
        this._journalId = _journalId;
        this._title = _title;
        this._createdDate = new Date();
        this._contents = _contents;
        this._status = StatusEnum.Open;
        this._latitude = 0;
        this._longitude = 0;
    }


    public long get_entryId() {
        return _entryId;
    }

    public void set_entryId(long _entryId) {
        this._entryId = _entryId;
    }

    public long get_journalId() {
        return _journalId;
    }

    public void set_journalId(long _journalId) {
        this._journalId = _journalId;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public Date get_createdDate() {
        return _createdDate;
    }

    public void set_createdDate(Date _createdDate) {
        this._createdDate = _createdDate;
    }

    public String get_contents() {
        return _contents;
    }

    public void set_contents(String _contents) {
        this._contents = _contents;
    }

    public StatusEnum get_status() {
        return _status;
    }

    public void set_status(StatusEnum _status) {
        this._status = _status;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    @Override
    public String toString() {
        return "Entry [_entryId = " + _entryId
                + ", _journalId = " + _journalId
                + ", _title = " + _title
                + ", _createdDate = " + _createdDate
                + ", _contents = " + _contents
                + ", _status = " + _status
                + ", _latitude = " + _latitude
                + ", _longitude = " + _longitude
                + "]";
    }
}
