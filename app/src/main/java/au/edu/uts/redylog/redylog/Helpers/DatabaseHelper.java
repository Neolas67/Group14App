package au.edu.uts.redylog.redylog.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.ThemedSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.Models.User;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "redyLog";

    // Table Definitions
    private static final String TABLE_USERS = "users";
    private static final String TABLE_JOURNALS = "journals";
    private static final String TABLE_ENTRIES = "entries";

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
    private static final String ENTRY_ORIGINALID = "originalentryid";
    private static final String ENTRY_LATITUDE = "latitude";
    private static final String ENTRY_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createUserTable(sqLiteDatabase);
        createJournalTable(sqLiteDatabase);
        createEntryTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNALS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);

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
                + JOURNAL_STARTDATE + " NUMERIC, "
                + JOURNAL_ENDDATE + " NUMERIC, "
                + JOURNAL_STATUS + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_JOURNAL_TABLE);
    }

    public void addJournal(Journal journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(JOURNAL_USERID, journal.get_userId());
        values.put(JOURNAL_TITLE, journal.get_title());
        values.put(JOURNAL_STARTDATE, HelperMethods.dateToLong(journal.get_startDate()));
        values.put(JOURNAL_ENDDATE, HelperMethods.dateToLong(journal.get_endDate()));
        values.put(JOURNAL_STATUS, journal.get_status());

        long newId = db.insert(TABLE_USERS, null, values);
        journal.set_journalId(newId);
        db.close();
    }

    public List<Journal> getAllJournals() {
        List<Journal> journalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_JOURNALS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Journal journal = new Journal(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        HelperMethods.longToDate(cursor.getLong(3)),
                        HelperMethods.longToDate(cursor.getLong(4)),
                        cursor.getInt(5)
                );
                journalList.add(journal);
            } while (cursor.moveToNext());
        }

        return journalList;
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
                + ENTRY_ORIGINALID + " INTEGER, "
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
        values.put(ENTRY_STATUS, entry.get_status());
        values.put(ENTRY_ORIGINALID, entry.get_originalEntryId());
        values.put(ENTRY_LATITUDE, entry.get_latitude());
        values.put(ENTRY_LONGITUDE, entry.get_longitude());

        long newId = db.insert(TABLE_USERS, null, values);
        entry.set_entryId(newId);
        db.close();
    }

    public List<Entry> getAllEntries() {
        List<Entry> entryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ENTRIES;
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
                        cursor.getInt(5),
                        cursor.getLong(6),
                        cursor.getDouble(7),
                        cursor.getDouble(8)
                );
                entryList.add(entry);
            } while (cursor.moveToNext());
        }

        return entryList;
    }
}
