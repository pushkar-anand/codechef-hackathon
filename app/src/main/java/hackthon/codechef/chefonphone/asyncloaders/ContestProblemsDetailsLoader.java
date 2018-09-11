package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import hackthon.codechef.chefonphone.data.Problem;

public class ContestProblemsDetailsLoader extends AsyncTaskLoader<Problem> {

    public ContestProblemsDetailsLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Problem loadInBackground() {


        return null;
    }


}
