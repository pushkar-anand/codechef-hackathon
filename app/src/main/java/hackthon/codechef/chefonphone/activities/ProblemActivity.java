package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestProblemsDetailsLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Problem;

public class ProblemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Problem> {

    private String contestCode = null, problemCode;
    private ProgressBar problemDetailsLoader;
    private View includeProblemView;
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

        problemDetailsLoader = findViewById(R.id.problemDetailsLoader);
        includeProblemView = findViewById(R.id.problemViewInclude);

        getSupportLoaderManager().initLoader(IDs.CONTEST_PROBLEMS_DETAILS_LOADER,
                null, this).forceLoad();

    }

    private void updateViewWithProblemData(Problem problem) {


        problemDetailsLoader.setVisibility(View.GONE);
        includeProblemView.setVisibility(View.VISIBLE);
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
