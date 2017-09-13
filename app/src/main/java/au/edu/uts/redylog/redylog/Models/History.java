package au.edu.uts.redylog.redylog.Models;

import java.util.Date;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class History {

    long _historyId;
    long _entryId;
    String _title;
    String _content;
    Date _changedDate;

    public History(long _historyId, long _entryId, String _title, String _content, Date _changedDate) {
        this._historyId = _historyId;
        this._entryId = _entryId;
        this._title = _title;
        this._content = _content;
        this._changedDate = _changedDate;
    }

    public History(long _entryId, String _title, String _content, Date _changedDate) {
        this._entryId = _entryId;
        this._title = _title;
        this._content = _content;
        this._changedDate = _changedDate;
    }

    public long get_historyId() {
        return _historyId;
    }

    public void set_historyId(long _historyId) {
        this._historyId = _historyId;
    }

    public long get_entryId() {
        return _entryId;
    }

    public void set_entryId(long _entryId) {
        this._entryId = _entryId;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public Date get_changedDate() {
        return _changedDate;
    }

    public void set_changedDate(Date _changedDate) {
        this._changedDate = _changedDate;
    }
}
