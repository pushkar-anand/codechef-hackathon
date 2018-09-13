package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.data.CompilationLog;
import hackthon.codechef.chefonphone.databases.AppDatabase;

public class CompilationLogLoader extends AsyncTaskLoader<ArrayList<CompilationLog>> {

    public CompilationLogLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public ArrayList<CompilationLog> loadInBackground() {

        ArrayList<CompilationLog> compilationLogs = new ArrayList<>();

        AppDatabase appDatabase = AppDatabase.getInstance(getContext());

        ArrayList<Integer> logIDs = appDatabase.listCompilationLogIDs();

        for (Integer i = 0; i < logIDs.size(); i++) {
            CompilationLog log = new CompilationLog();

            Cursor cursor = appDatabase.getLogData(logIDs.get(i));

            log.setId(i);

            log.setProblem(cursor
                    .getString(cursor
                            .getColumnIndex(AppDatabase.COMPILE_LOG_TABLE_COLUMN_PROBLEM)));
            log.setLang(cursor
                    .getString(cursor
                            .getColumnIndex(AppDatabase.COMPILE_LOG_TABLE_COLUMN_LANG)));

            log.setStatus(cursor
                    .getString(cursor
                            .getColumnIndex(AppDatabase.COMPILE_LOG_TABLE_COLUMN_STATUS)));

            log.setTimeStamp(cursor
                    .getString(cursor
                            .getColumnIndex(AppDatabase.COMPILE_LOG_TABLE_COLUMN_TIMESTAMP)));

            compilationLogs.add(log);
        }

        return compilationLogs;
    }
}