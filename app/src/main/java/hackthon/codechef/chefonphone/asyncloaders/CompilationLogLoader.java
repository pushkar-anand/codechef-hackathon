package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
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

        AppDatabase appDatabase = AppDatabase.getInstance(getContext());

        return appDatabase.getAllLogs();
    }
}
