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

public class CompileLogDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "compile_log.db";
    private static final int DATABASE_VERSION = 1;
    private static CompileLogDatabase sInstance;

    private static String COMPILE_LOG_TABLE = "compile_logs";

    private static String COMPILE_LOG_TABLE_COLUMN_ID = "id";
    private static String COMPILE_LOG_TABLE_COLUMN_LANG = "language";
    private static String COMPILE_LOG_TABLE_COLUMN_PROBLEM = "problem";
    private static String COMPILE_LOG_TABLE_COLUMN_STATUS = "status";
    private static String COMPILE_LOG_TABLE_COLUMN_TIMESTAMP = "time";


    private CompileLogDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized CompileLogDatabase getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new CompileLogDatabase(context.getApplicationContext());
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String deleteQuery = "DROP TABLE IF EXISTS " + COMPILE_LOG_TABLE;
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

    Cursor getLogData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COMPILE_LOG_TABLE + " WHERE " + COMPILE_LOG_TABLE_COLUMN_ID + "=" + id + "";

        return db.rawQuery(query, null);
    }

    public void clearCompilationLogs() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(COMPILE_LOG_TABLE, null, null);
    }
}