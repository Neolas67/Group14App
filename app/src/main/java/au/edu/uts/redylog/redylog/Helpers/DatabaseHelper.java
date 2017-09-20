package au.edu.uts.redylog.redylog.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.Models.User;
import au.edu.uts.redylog.redylog.Models.History;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "redyLog";

    // Table Definitions
    private static final String TABLE_USERS = "users";
    private static final String TABLE_JOURNALS = "journals";
    private static final String TABLE_ENTRIES = "entries";
    private static final String TABLE_HISTORY = "history";

    // User Table - Column Definitions
    private static final String USER_ID = "userid";
    private static final String USER_FIRSTNAME = "firstname";
    private static final String USER_SURNAME = "surname";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";

    // Journal Table - Column Definitions
    private static final String JOURNAL_ID = "journalid";
    private static final String JOURNAL_USERID = "userid";
    private static final String JOURNAL_TITLE = "title";
    private static final String JOURNAL_DESCRIPTION = "description";
    private static final String JOURNAL_STARTDATE = "startdate";
    private static final String JOURNAL_ENDDATE = "enddate";
    private static final String JOURNAL_STATUS = "status";

    // Entries Table - Column Definitions
    private static final String ENTRY_ID = "entryid";
    private static final String ENTRY_JOURNALID = "journalid";
    private static final String ENTRY_TITLE = "title";
    private static final String ENTRY_CREATEDDATE = "createddate";
    private static final String ENTRY_CONTENTS = "contents";
    private static final String ENTRY_STATUS = "status";
    private static final String ENTRY_LATITUDE = "latitude";
    private static final String ENTRY_LONGITUDE = "longitude";

    // History Table - Column Definitions
    private static final String HISTORY_ID = "historyid";
    private static final String HISTORY_ENTRYID = "entryid";
    private static final String HISTORY_TITLE = "title";
    private static final String HISTORY_CONTENT = "contents";
    private static final String HISTORY_CHANGEDDATE = "changeddate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createUserTable(sqLiteDatabase);
        createJournalTable(sqLiteDatabase);
        createEntryTable(sqLiteDatabase);
        createHistoryTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNALS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        onCreate(sqLiteDatabase);
    }

    // User Queries
    private void createUserTable(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY, "
                + USER_FIRSTNAME + " TEXT, "
                + USER_SURNAME + " TEXT, "
                + USER_EMAIL + " TEXT, "
                + USER_PASSWORD + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_FIRSTNAME, user.get_firstName());
        values.put(USER_SURNAME, user.get_surname());
        values.put(USER_EMAIL, user.get_email());
        values.put(USER_PASSWORD, user.get_password());

        long newId = db.insert(TABLE_USERS, null, values);
        user.set_userId(newId);
        db.close();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    // Journal Queries
    private void createJournalTable(SQLiteDatabase sqLiteDatabase) {
        String CREATE_JOURNAL_TABLE = "CREATE TABLE " + TABLE_JOURNALS + "("
                + JOURNAL_ID + " INTEGER PRIMARY KEY, "
                + JOURNAL_USERID + " INTEGER, "
                + JOURNAL_TITLE + " TEXT, "
                + JOURNAL_DESCRIPTION + " TEXT, "
                + JOURNAL_STARTDATE + " NUMERIC, "
                + JOURNAL_ENDDATE + " NUMERIC, "
                + JOURNAL_STATUS + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_JOURNAL_TABLE);
    }

    public void addJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(JOURNAL_USERID, journal.get_userId());
        values.put(JOURNAL_TITLE, journal.get_title());
        values.put(JOURNAL_DESCRIPTION, journal.get_description());
        values.put(JOURNAL_STARTDATE, HelperMethods.dateToLong(journal.get_startDate()));
        values.put(JOURNAL_ENDDATE, HelperMethods.dateToLong(journal.get_endDate()));
        values.put(JOURNAL_STATUS, journal.get_status().toString());

        long newId = db.insert(TABLE_JOURNALS, null, values);
        journal.set_journalId(newId);
        db.close();
    }

    public List<Journal> getAllJournals(long userId) {
        return getFilteredJournals(userId, null);
    }

    public List<Journal> getFilteredJournals(long userId, String query) {
        String selectQuery = "SELECT * FROM " + TABLE_JOURNALS
                + " WHERE " + JOURNAL_USERID + " = " + Long.toString(userId);

        if (!TextUtils.isEmpty(query)) {
            selectQuery += " AND " + JOURNAL_TITLE + " LIKE '%" + query + "%'";
        }

        List<Journal> journalList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Journal journal = new Journal(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        HelperMethods.longToDate(cursor.getLong(4)),
                        HelperMethods.longToDate(cursor.getLong(5)),
                        StatusEnum.valueOf(cursor.getString(6))
                );
                journalList.add(journal);
            } while (cursor.moveToNext());
        }

        return journalList;
    }

    public void updateJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JOURNAL_STATUS, journal.get_status().toString());
        values.put(JOURNAL_TITLE, journal.get_title().toString());
        values.put(JOURNAL_DESCRIPTION, journal.get_description().toString());

        db.update(
                TABLE_JOURNALS,
                values,
                JOURNAL_ID + " = " + journal.get_journalId(),
                null);
    }

    // Entry Queries
    private void createEntryTable(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ENTRY_TABLE = "CREATE TABLE " + TABLE_ENTRIES + "("
                + ENTRY_ID + " INTEGER PRIMARY KEY, "
                + ENTRY_JOURNALID + " INTEGER,"
                + ENTRY_TITLE + " TEXT, "
                + ENTRY_CREATEDDATE + " NUMERIC, "
                + ENTRY_CONTENTS + " TEXT, "
                + ENTRY_STATUS + " INTEGER, "
                + ENTRY_LATITUDE + " NUMERIC, "
                + ENTRY_LONGITUDE + " NUMERIC"
                + ")";
        sqLiteDatabase.execSQL(CREATE_ENTRY_TABLE);
    }

    public void addEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ENTRY_JOURNALID, entry.get_journalId());
        values.put(ENTRY_TITLE, entry.get_title());
        values.put(ENTRY_CREATEDDATE, HelperMethods.dateToLong(entry.get_createdDate()));
        values.put(ENTRY_CONTENTS, entry.get_contents());
        values.put(ENTRY_STATUS, entry.get_status().toString());
        values.put(ENTRY_LATITUDE, entry.get_latitude());
        values.put(ENTRY_LONGITUDE, entry.get_longitude());

        long newId = db.insert(TABLE_ENTRIES, null, values);
        entry.set_entryId(newId);
        db.close();
    }

    public List<Entry> getAllEntries() {
        return getFilteredEntries(null);
    }

    public List<Entry> getFilteredEntries(String query) {
        String selectQuery = "SELECT * FROM " + TABLE_ENTRIES;
        if (!TextUtils.isEmpty(query)) {
            selectQuery += " WHERE " + ENTRY_CONTENTS + " LIKE '%" + query + "%'";
        }

        List<Entry> entryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Entry entry = new Entry(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        HelperMethods.longToDate(cursor.getLong(3)),
                        cursor.getString(4),
                        StatusEnum.valueOf(cursor.getString(5)),
                        cursor.getDouble(6),
                        cursor.getDouble(7)
                );
                entryList.add(entry);
            } while (cursor.moveToNext());
        }

        return entryList;
    }

    public void updateEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ENTRY_TITLE, entry.get_title().toString());
        values.put(ENTRY_CONTENTS, entry.get_contents().toString());

        db.update(
                TABLE_ENTRIES,
                values,
                ENTRY_ID + " = " + entry.get_entryId(),
                null);
    }

    // History Queries
    private void createHistoryTable(SQLiteDatabase sqLiteDatabase) {
        String CREATE_JOURNAL_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + HISTORY_ID + " INTEGER PRIMARY KEY, "
                + HISTORY_ENTRYID + " INTEGER, "
                + HISTORY_TITLE + " TEXT, "
                + HISTORY_CONTENT + " TEXT, "
                + HISTORY_CHANGEDDATE + " NUMERIC "
                + ")";
        sqLiteDatabase.execSQL(CREATE_JOURNAL_TABLE);
    }

    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HISTORY_ENTRYID, history.get_entryId());
        values.put(HISTORY_TITLE, history.get_title());
        values.put(HISTORY_CONTENT, history.get_content());
        values.put(HISTORY_CHANGEDDATE, HelperMethods.dateToLong(history.get_changedDate()));

        long newId = db.insert(TABLE_HISTORY, null, values);
        history.set_historyId(newId);
        db.close();
    }

    public List<History> getHistory(Entry entry) {
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY + " WHERE " + HISTORY_ENTRYID + " = " + entry.get_entryId();

        List<History> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                History history = new History(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        HelperMethods.longToDate(cursor.getLong(4))
                );
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        return historyList;
    }

    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 0);
    }
}
