package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;

import java.util.List;

import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Models.Entry;
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
    DatabaseHelper _db;
    static Context _context;

    public static void init(Context context){
        _context = context;
        ourInstance = new EntryManager();
    }

    public EntryManager() {
        _db = new DatabaseHelper(_context);
        _entries = _db.getAllEntries();
    }

    public void addEntry(Entry entry) {
        _db.addEntry(entry);
        _entries.add(entry);
    }

    public List<Entry> get_entries() {
        if (_entries == null) {
            User user = UserManager.getInstance().get_currentUser();
            _entries = _db.getAllEntries();
        }
        return _entries;
    }

}
