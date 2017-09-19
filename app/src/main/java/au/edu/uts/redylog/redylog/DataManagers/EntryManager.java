package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
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

    public void updateEntry(Entry entry) {
        _db.updateEntry(entry);
    }


    public List<Entry> get_entries(Journal journal, String query) {
        if (_entries.size() == 0) {
            _entries.addAll(_db.getAllEntries());
        }

        List<Entry> filteredList = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            for (Entry e: _entries) {
                if (e.get_journalId() == journal.get_journalId())
                    filteredList.add(e);
            }
        } else {
            for (Entry e: _entries) {
                if (e.get_journalId() == journal.get_journalId() &&
                        (e.get_title().toLowerCase().contains(query.toLowerCase()) ||
                                e.get_contents().toLowerCase().contains(query.toLowerCase())))
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
