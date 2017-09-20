package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.SearchFilter;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.History;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.Models.User;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class EntryManager {

    private static EntryManager ourInstance;
    public static EntryManager getInstance() {
        return ourInstance;
    }

    List<Entry> _entries;
    List<History> _history;
    DatabaseHelper _db;
    static Context _context;

    public static void init(Context context){
        _context = context;
        ourInstance = new EntryManager();
    }

    public EntryManager() {
        _db = new DatabaseHelper(_context);
        _entries = new ArrayList<>();
        _history = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        _db.addEntry(entry);
        _entries.add(entry);
    }

    public void addHistory(History history) {
        _db.addHistory(history);
        _history.add(history);
    }

    public void hideEntry(Entry entry) {
        entry.set_status(StatusEnum.Hidden);
        _db.updateEntry(entry);
    }

    public void unhideEntry(Entry entry) {
        entry.set_status(StatusEnum.Open);
        _db.updateEntry(entry);
    }

    public void updateEntry(Entry entry) {
        _db.updateEntry(entry);
    }

    public List<Entry> get_entries(Journal journal, SearchFilter searchFilter) {
        if (_entries.size() == 0) {
            _entries.addAll(_db.getAllEntries());
        }

        List<Entry> filteredList = new ArrayList<>();

        for (Entry e: _entries) {

            if (e.get_journalId() != journal.get_journalId()) { continue; }

            if (searchFilter == null) {
                filteredList.add(e);
            } else {
                String query = searchFilter.get_query();
                Date startDate = searchFilter.get_startDate();
                Date endDate = searchFilter.get_endDate();
                StatusEnum status = searchFilter.get_status();

                if (!TextUtils.isEmpty(query) && !HelperMethods.searchString(e.get_contents(), query)) {
                    continue;
                }

                if (startDate != null && e.get_createdDate().before(startDate)) {
                    continue;
                }

                if (endDate != null && e.get_createdDate().after(endDate)) {
                    continue;
                }

                //Deleted and Hidden entries should not be shown unless they are explicitly searched for
                if (status == null || status == StatusEnum.None) {
                    if (e.get_status() == StatusEnum.Deleted || e.get_status() == StatusEnum.Hidden) { continue; }
                } else  {
                    if (e.get_status() != status) { continue; }
                }

                filteredList.add(e);
            }
        }

        EntryComparator entryComparator = new EntryComparator();
        Collections.sort(filteredList, entryComparator);
        return filteredList;
    }

    public class EntryComparator implements Comparator<Entry> {
        public int compare(Entry entry1, Entry entry2) {
            return entry2.get_createdDate().compareTo(entry1.get_createdDate());
        }
    }

    public List<Entry> get_entries(Journal journal) {
        return get_entries(journal, null);
    }

//    public List<History> get_history(Entry entry) {
//
//    }

}
