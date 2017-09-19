package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public Journal get_journal(long id) {
        for (Journal j: _journals) {
            if (j.get_journalId() == id) { return j; }
        }

        return null;
    }

    public List<Journal> get_journals(String query) {
        if (_journals.size() == 0) {
            User user = UserManager.getInstance().get_currentUser();
            _journals.addAll(_db.getAllJournals(user.get_userId()));
        }

        List<Journal> filteredList = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(_journals);
        } else {
            for (Journal j : _journals) {
                if (j.get_title().toLowerCase().contains(query.toLowerCase()) ||
                        j.get_description().toLowerCase().contains(query.toLowerCase()))
                    filteredList.add(j);
            }
        }

        JournalComparator journalComparator = new JournalComparator();
        Collections.sort(filteredList, journalComparator);
        return filteredList;
    }

    public class JournalComparator implements Comparator<Journal> {
        public int compare(Journal journal1, Journal journal2) {
            return journal2.get_startDate().compareTo(journal1.get_startDate());
        }
    }

    public List<Journal> get_journals() {
        return get_journals(null);
    }

    public void closeJournal(Journal journal) {
        journal.set_status(StatusEnum.Closed);
        _db.updateJournal(journal);
    }

    public void updateJournal(Journal journal) {
        _db.updateJournal(journal);
    }

}
