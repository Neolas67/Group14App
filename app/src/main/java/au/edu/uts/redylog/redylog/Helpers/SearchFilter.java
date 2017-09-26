package au.edu.uts.redylog.redylog.Helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hayden on 19-Sep-17.
 */

public class SearchFilter implements Serializable {

    FragmentEnum _searchType;
    String _query;
    Date _startDate;
    Date _endDate;
    StatusEnum _status;

    //Empty constructor for serialization
    public SearchFilter() {

    }

    public SearchFilter(FragmentEnum searchType) {
        _searchType = searchType;
    }

    public FragmentEnum get_searchType() {
        return _searchType;
    }

    public void set_searchType(FragmentEnum _searchType) {
        this._searchType = _searchType;
    }

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

    public List<StatusEnum> getStatusEnums() {
        if (_searchType == FragmentEnum.EntryListFragment) {
            return StatusEnum.getEntryEnums();
        } else if (_searchType == FragmentEnum.JournalListFragment) {
            return StatusEnum.getJournalEnums();
        } else {
            return new ArrayList<StatusEnum>();
        }
    }

    @Override
    public String toString() {
        return "SearchFilter [" + "_searchType = " + _searchType
                + ",_startDate = " + _startDate
                + ", _endDate = " + _endDate
                + ", _status = " + _status
                + "]";
    }
}
