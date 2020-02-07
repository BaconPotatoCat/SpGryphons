package edu.sp.spgryphons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.Key;

public class WordListOpenHelper extends SQLiteOpenHelper {
    // It's a good idea to always define a log tag like this.
    private static final String TAG = WordListOpenHelper.class.getSimpleName();
    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String WORD_LIST_TABLE = "word_entries";
    private static final String DATABASE_NAME = "wordlist";
    // Column names...
    public static final String KEY_ID = "id";
    public static final String KEY_WORD = "word";
    public static final String KEY_DATE = "date";
    // ... and a string array of columns.
    private static final String[] COLUMNS ={KEY_ID, KEY_WORD, KEY_DATE};

    // Build the SQL query that creates the table.
    private static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
// id will auto-increment if no value passed
                    KEY_WORD + " TEXT, " + KEY_DATE + " TEXT);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WORD_LIST_TABLE_CREATE);
        fillDatabaseWithData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WordListOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(db);
    }
    public WordListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private void fillDatabaseWithData(SQLiteDatabase db){
        String[] words = {"Secure Coding", "EHD", "Mobile App", "SMW",
                "CLI", "CPF", "CPR"};

        String[] date = {"11-11-20", "21-1-20", "1-11-20", "11-11-11",
                "11-11-11", "11-11-11", "31-11-20"};

        // Create a container for the data.
        ContentValues values = new ContentValues();

        Log.d("t","Word length: "+words.length);
        for (int i=0; i < words.length; i++) {
// Put column/value pairs into the container.
// put() overrides existing values.
            Log.d("f" , "HERE: " + words);
            Log.d("f" , "THERE: " + date);
            values.put(KEY_WORD, words[i]);
            values.put(KEY_DATE, date[i]);
            db.insert(WORD_LIST_TABLE, null, values);
        }
    }
    public WordItem query(int position) {
        String query = "SELECT * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + KEY_WORD + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        WordItem entry = new WordItem();

        try {
            if (mReadableDB == null) {
                Log.d("kk","fff" + mReadableDB);
                mReadableDB = getReadableDatabase();
                Log.d("kk","ddd" + mReadableDB);
            }
            cursor = mReadableDB.rawQuery(query, null);


            boolean result = cursor.moveToFirst();
            Log.d("Here","result " + (cursor.getColumnIndex(KEY_DATE)));
            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD)));
            entry.setmDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
        }

        catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);

        }

        finally {
            cursor.close();
            return entry;
        }

    }
    public long insert(String word,String date) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word);
        values.put(KEY_DATE,date);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }
    public long count(){
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE);
    }
    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(WORD_LIST_TABLE,
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
        return deleted;
    }
    public int update(int id, String word, String date){
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_WORD,word);
            values.put(KEY_DATE,date);
            mNumberOfRowsUpdated = mWritableDB.update(WORD_LIST_TABLE, values, // new values to insert
                    // selection criteria for row (the _id column)
                    KEY_ID + " = ?",
//selection args; value of id
                    new String[]{String.valueOf(id)});
        }catch(Exception e){
            Log.d (TAG, "UPDATE EXCEPTION: " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }
}