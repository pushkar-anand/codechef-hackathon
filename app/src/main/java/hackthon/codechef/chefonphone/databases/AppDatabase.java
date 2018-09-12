package hackthon.codechef.chefonphone.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppDatabase extends SQLiteOpenHelper {

    public static final String COMPILE_LOG_TABLE_COLUMN_LANG = "language";
    public static final String COMPILE_LOG_TABLE_COLUMN_PROBLEM = "problem";
    public static final String COMPILE_LOG_TABLE_COLUMN_STATUS = "status";
    public static final String COMPILE_LOG_TABLE_COLUMN_TIMESTAMP = "time";
    private static final String ALARMS_TABLE_COLUMN_ID = "id";
    public static final String ALARMS_TABLE_COLUMN_CONTEST = "contest";
    public static final String ALARMS_TABLE_COLUMN_TIME = "remind";
    private static final String DATABASE_NAME = "codechef.db";
    private static final Integer DATABASE_VERSION = 1;
    private static final String COMPILE_LOG_TABLE = "compile_log";
    private static final String COMPILE_LOG_TABLE_COLUMN_ID = "id";
    private static final String ALARMS_TABLE = "alarms";
    private static AppDatabase sInstance;


    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized AppDatabase getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new AppDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + COMPILE_LOG_TABLE + "(";
        createTableQuery += COMPILE_LOG_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        createTableQuery += COMPILE_LOG_TABLE_COLUMN_PROBLEM + " TEXT NOT NULL";
        createTableQuery += COMPILE_LOG_TABLE_COLUMN_LANG + " TEXT NOT NULL";
        createTableQuery += COMPILE_LOG_TABLE_COLUMN_STATUS + " TEXT NOT NULL";
        createTableQuery += COMPILE_LOG_TABLE_COLUMN_TIMESTAMP + " TEXT NOT NULL";
        createTableQuery += ")";

        Log.d("SQL_CREATE: ", createTableQuery);
        sqLiteDatabase.execSQL(createTableQuery);

        createTableQuery = "CREATE TABLE IF NOT EXISTS " + ALARMS_TABLE + "(";
        createTableQuery += ALARMS_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        createTableQuery += ALARMS_TABLE_COLUMN_CONTEST + " TEXT NOT NULL";
        createTableQuery += ALARMS_TABLE_COLUMN_TIME + " TEXT NOT NULL";
        createTableQuery += ")";

        Log.d("SQL_CREATE: ", createTableQuery);
        sqLiteDatabase.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String deleteQuery = "DROP TABLE IF EXISTS " + COMPILE_LOG_TABLE;
        sqLiteDatabase.execSQL(deleteQuery);

        deleteQuery = "DROP TABLE IF EXISTS " + ALARMS_TABLE;
        sqLiteDatabase.execSQL(deleteQuery);

        onCreate(sqLiteDatabase);
    }

    public void newCompilationRequest(String problem, String lang, String status) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COMPILE_LOG_TABLE_COLUMN_PROBLEM, problem);
        contentValues.put(COMPILE_LOG_TABLE_COLUMN_LANG, lang);
        contentValues.put(COMPILE_LOG_TABLE_COLUMN_STATUS, status);
        contentValues.put(COMPILE_LOG_TABLE_COLUMN_TIMESTAMP, SimpleDateFormat.getDateTimeInstance().format(new Date()));

        database.insert(COMPILE_LOG_TABLE, null, contentValues);

    }

    public ArrayList<Integer> listCompilationLogIDs() {

        ArrayList<Integer> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COMPILE_LOG_TABLE;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getInt(res.getColumnIndex(COMPILE_LOG_TABLE_COLUMN_ID)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public Cursor getLogData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COMPILE_LOG_TABLE + " WHERE " + COMPILE_LOG_TABLE_COLUMN_ID + "=" + id + "";

        return db.rawQuery(query, null);
    }

    public void clearCompilationLogs() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(COMPILE_LOG_TABLE, null, null);
    }

    public Long newAlarm(String contest, String time) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ALARMS_TABLE_COLUMN_CONTEST, contest);
        contentValues.put(ALARMS_TABLE_COLUMN_TIME, time);

        return database.insert(COMPILE_LOG_TABLE, null, contentValues);
    }

    public ArrayList<Integer> getAlarmsIDs() {

        ArrayList<Integer> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ALARMS_TABLE;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getInt(res.getColumnIndex(ALARMS_TABLE_COLUMN_ID)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public Cursor getAlarmData(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ALARMS_TABLE + " WHERE " + ALARMS_TABLE_COLUMN_ID + "=" + id + "";

        return db.rawQuery(query, null);
    }

    public boolean isAlarmSet(String contest) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ALARMS_TABLE;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            String dbContest = res.getString(res.getColumnIndex(ALARMS_TABLE_COLUMN_CONTEST));
            if (dbContest.equals(contest)) {
                return true;
            }
            res.moveToNext();
        }
        res.close();
        return false;
    }

    public Integer getAlarmID(String contest) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ALARMS_TABLE;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            String dbContest = res.getString(res.getColumnIndex(ALARMS_TABLE_COLUMN_CONTEST));
            Integer id = res.getInt(res.getColumnIndex(ALARMS_TABLE_COLUMN_ID));
            if (dbContest.equals(contest)) {
                return id;
            }
            res.moveToNext();
        }
        res.close();
        return -1;
    }

    public void removeAlarm(String contest) {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ALARMS_TABLE, ALARMS_TABLE_COLUMN_CONTEST + " = ?", new String[]{contest});
    }

    public void removeAllAlarms() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(ALARMS_TABLE, null, null);
    }
}
