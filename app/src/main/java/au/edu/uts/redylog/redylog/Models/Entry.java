package au.edu.uts.redylog.redylog.Models;

import java.util.Date;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class Entry {

    long _entryId;
    long _journalId;
    String _title;
    Date _createdDate;
    String _contents;
    int _status;
    long _originalEntryId;
    double _latitude;
    double _longitude;

    public Entry(long _entryId, long _journalId, String _title, Date _createdDate, String _contents, int _status, long _originalEntryId, double _latitude, double _longitude) {
        this._entryId = _entryId;
        this._journalId = _journalId;
        this._title = _title;
        this._createdDate = _createdDate;
        this._contents = _contents;
        this._status = _status;
        this._originalEntryId = _originalEntryId;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public Entry(long _journalId, String _title, Date _createdDate, String _contents, int _status, long _originalEntryId, double _latitude, double _longitude) {
        this._journalId = _journalId;
        this._title = _title;
        this._createdDate = _createdDate;
        this._contents = _contents;
        this._status = _status;
        this._originalEntryId = _originalEntryId;
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public Entry( String _title, String _contents) {
        Journal journal= JournalManager.getInstance().get_currentJournal();
        this._journalId=journal.get_journalId();
        this._title = _title;
        this._createdDate = new Date();
        this._contents = _contents;
        this._status = StatusEnum.Open.ordinal();
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

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public long get_originalEntryId() {
        return _originalEntryId;
    }

    public void set_originalEntryId(long _originalEntryId) {
        this._originalEntryId = _originalEntryId;
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
}
