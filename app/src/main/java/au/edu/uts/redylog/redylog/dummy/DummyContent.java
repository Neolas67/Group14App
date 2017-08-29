package au.edu.uts.redylog.redylog.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.Journal;

public class DummyContent {

    public static final List<Journal> ITEMS = new ArrayList<>();
    public static final List<Entry> ENTRY_ITEMS= new ArrayList<>();
    public static final Map<String, Journal> ITEM_MAP = new HashMap<>();
    public static final Map<String, Entry> ENTRY_ITEM_MAP = new HashMap<>();


    static {
        for (Journal journal : JournalManager.getInstance().get_journals()) {
            addItem(journal);
        }

        for (Entry entry : EntryManager.getInstance().get_entries()){
            addItem(entry);
        }
    }

    private static void addItem(Journal journal) {
        ITEMS.add(journal);
        ITEM_MAP.put(Long.toString(journal.get_journalId()), journal);
    }

    private static void addItem(Entry entry) {
        ENTRY_ITEMS.add(entry);
        ENTRY_ITEM_MAP.put(Long.toString(entry.get_entryId()),entry);
    }

}
