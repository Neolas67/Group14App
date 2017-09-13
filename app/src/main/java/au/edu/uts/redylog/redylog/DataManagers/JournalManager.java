package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.Models.User;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class JournalManager {

    private static JournalManager ourInstance;
    public static JournalManager getInstance() {
        return ourInstance;
    }

    List<Journal> _journals;
    DatabaseHelper _db;
    static Context _context;

    public static void init(Context context){
        _context = context;
         ourInstance = new JournalManager();
    }

    public JournalManager() {
        _db = new DatabaseHelper(_context);
        _journals = new ArrayList<>();
    }

    public void addJournal(Journal journal) {
        _db.addJournal(journal);
        _journals.add(journal);
    }

    public List<Journal> get_journals() {
        if (_journals.size() == 0) {
            User user = UserManager.getInstance().get_currentUser();
            _journals.addAll(_db.getAllJournals(user.get_userId()));
        }

        return _journals;
    }

    public void closeJournal(Journal journal) {
        journal.set_status(StatusEnum.Closed);
        _db.updateJournalStatus(journal);
    }

}
