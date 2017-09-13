package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;

import java.util.ArrayList;
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

    public List<Entry> get_entries(Journal journal) {
        if (_entries.size() == 0) {
            User user = UserManager.getInstance().get_currentUser();
            _entries.addAll(_db.getAllEntries());
        }

        List<Entry> entriesToReturn = new ArrayList<>();

        for (Entry e: _entries) {
            if (e.get_journalId() == journal.get_journalId())
                entriesToReturn.add(e);
        }

        return entriesToReturn;
    }

//    public List<History> get_history(Entry entry) {
//
//    }

}
