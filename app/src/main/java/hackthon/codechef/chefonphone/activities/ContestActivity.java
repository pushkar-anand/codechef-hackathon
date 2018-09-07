package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.adapters.ProblemListAdapter;
import hackthon.codechef.chefonphone.asyncloaders.ContestDetailsLoader;
import hackthon.codechef.chefonphone.asyncloaders.PracticeProblemLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.data.Problem;

public class ContestActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Contest> {

    private String contestCode,contestDetails;
    private ProgressBar contest_problemLoaderProgress;
    private RecyclerView contest_problemRecycler;
    private ProblemListAdapter contest_problemListAdapter;

    private String contest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        Intent intent = getIntent();
        if (intent.hasExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY)) {
            contest = intent.getStringExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY);
        } else {
            finish();
        }

        getSupportLoaderManager().initLoader(IDs.CONTEST_DETAILS_LOADER, null, this).forceLoad();

        contest_problemLoaderProgress = findViewById(R.id.contestProblemLoader);
        contest_problemRecycler = findViewById(R.id.contestProblemRecyclerView);
        contest_problemListAdapter = new ProblemListAdapter();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        contest_problemRecycler.setLayoutManager(layoutManager);
        contest_problemRecycler.setItemAnimator(new DefaultItemAnimator());
        contest_problemRecycler.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        contest_problemRecycler.setAdapter(contest_problemListAdapter);
        contest_problemRecycler.setHasFixedSize(true);

        TextView typeTV = findViewById(R.id.contest_code);
        String str = "CODE: " + contestCode;

    }

    public void populateViewWithContestProblems(Contest contest_problems){
        //TODO
        contest_problemListAdapter.populateProblemList(contest_problems);
        contest_problemLoaderProgress.setVisibility(View.GONE);
        contest_problemRecycler.setVisibility(View.VISIBLE);
        contest_problemListAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Loader<Contest> onCreateLoader(int id, @Nullable Bundle args) {
        return new ContestDetailsLoader(ContestActivity.this, contest);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Contest> loader, Contest data) {
        if (data != null) {
            populateViewWithContestProblems(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Contest> loader) {

    }
}
