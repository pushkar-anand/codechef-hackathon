package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestProblemsDetailsLoader;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Problem;

public class ProblemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Problem> {

    private String contestCode = null, problemCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        Intent intent = getIntent();
        if (!intent.hasExtra(StringKeys.PROBLEM_ACTIVITY_PROBLEM_CODE)) {
            finish();
        }
        problemCode = intent.getStringExtra(StringKeys.PROBLEM_ACTIVITY_PROBLEM_CODE);
        if (intent.hasExtra(StringKeys.PROBLEM_ACTIVITY_CONTEST_CODE)) {
            contestCode = intent.getStringExtra(StringKeys.PROBLEM_ACTIVITY_CONTEST_CODE);
        }

    }

    private void updateViewWithProblemData(Problem problem) {

    }

    @NonNull
    @Override
    public Loader<Problem> onCreateLoader(int id, @Nullable Bundle args) {
        return new ContestProblemsDetailsLoader(this, contestCode, problemCode);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Problem> loader, Problem data) {

        if (data != null) {
            updateViewWithProblemData(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Problem> loader) {

    }
}
