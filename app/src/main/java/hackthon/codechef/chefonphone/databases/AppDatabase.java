package hackthon.codechef.chefonphone.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "codechef.db";
    private static final int DATABASE_VERSION = 1;
    public static String COMPILE_LOG_TABLE_COLUMN_LANG = "language";
    public static String COMPILE_LOG_TABLE_COLUMN_PROBLEM = "problem";
    public static String COMPILE_LOG_TABLE_COLUMN_STATUS = "status";
    public static String COMPILE_LOG_TABLE_COLUMN_TIMESTAMP = "time";
    public static String ALARMS_TABLE_COLUMN_ID = "id";
    public static String ALARMS_TABLE_COLUMN_CONTEST = "contest";
    public static String ALARMS_TABLE_COLUMN_TIME = "remind";
    private static AppDatabase sInstance;
    private static String COMPILE_LOG_TABLE = "compile_logs";
    private static String COMPILE_LOG_TABLE_COLUMN_ID = "id";
    private static String ALARMS_TABLE = "compile_logs";


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

    public void newAlarm(String contest, String time) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ALARMS_TABLE_COLUMN_CONTEST, contest);
        contentValues.put(ALARMS_TABLE_COLUMN_TIME, time);

        database.insert(COMPILE_LOG_TABLE, null, contentValues);
    }

    public ArrayList<Pair<String, String>> getAlarmsData() {
        ArrayList<Pair<String, String>> pairs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ALARMS_TABLE;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            pairs.add(Pair
                    .create(res.getString(res.getColumnIndex(ALARMS_TABLE_COLUMN_CONTEST)),
                            res.getString(res.getColumnIndex(ALARMS_TABLE_COLUMN_TIME))
                    )
            );
            res.moveToNext();
        }
        res.close();
        return pairs;
    }

    public Integer removeAlarm(String contest) {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ALARMS_TABLE, ALARMS_TABLE_COLUMN_CONTEST + " = ?", new String[]{contest});
    }

    public void removeAllAlarms() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(ALARMS_TABLE, null, null);
    }
}
