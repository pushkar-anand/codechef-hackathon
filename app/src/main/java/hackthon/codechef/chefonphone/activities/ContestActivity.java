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

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.adapters.ProblemListAdapter;
import hackthon.codechef.chefonphone.asyncloaders.ContestDetailsLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Contest;

public class ContestActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Contest> {

    private ProgressBar contest_problemLoaderProgress;
    private RecyclerView contestProblemRecycler;
    private ProblemListAdapter contestProblemListAdapter;
    private TextView contestTitleTV, contestCodeTV;

    private String contestToLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        Intent intent = getIntent();
        if (intent.hasExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY)) {
            contestToLoad = intent.getStringExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY);
        } else {
            finish();
        }

        getSupportLoaderManager().initLoader(IDs.CONTEST_DETAILS_LOADER, null, this).forceLoad();

        contest_problemLoaderProgress = findViewById(R.id.contestProblemLoader);
        contestProblemRecycler = findViewById(R.id.contestProblemRecyclerView);
        contestProblemListAdapter = new ProblemListAdapter(false);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        contestProblemRecycler.setLayoutManager(layoutManager);
        contestProblemRecycler.setItemAnimator(new DefaultItemAnimator());
        contestProblemRecycler.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        contestProblemRecycler.setAdapter(contestProblemListAdapter);
        contestProblemRecycler.setHasFixedSize(true);


        contestTitleTV = findViewById(R.id.contest_title);
        contestCodeTV = findViewById(R.id.contest_code);

    }

    public void populateViewWithContestProblems(Contest contest) {

        contestProblemListAdapter.populateProblemList(contest.getContestProblemsList());
        contest_problemLoaderProgress.setVisibility(View.GONE);
        contestProblemRecycler.setVisibility(View.VISIBLE);
        contestProblemListAdapter.notifyDataSetChanged();

        contestTitleTV.setText(contest.getContestName());
        contestCodeTV.setText(contest.getContestCode());

        setTitle(contest.getContestCode());
    }

    @NonNull
    @Override
    public Loader<Contest> onCreateLoader(int id, @Nullable Bundle args) {
        return new ContestDetailsLoader(ContestActivity.this, contestToLoad);
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
