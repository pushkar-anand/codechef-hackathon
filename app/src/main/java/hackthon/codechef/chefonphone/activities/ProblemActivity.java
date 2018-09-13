package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;
import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestProblemsDetailsLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Problem;

public class ProblemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Problem> {

    private String contestCode = null, problemCode;
    private ProgressBar problemDetailsLoader;
    private View includeProblemView;

    private TextView problemName, problemCode_contest, dateAdded, sourceSizeLimit, maxTimeLimit, challengeType;
    private TextView successfulSubmissions, author;
    private MarkdownView problemBody;

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

        problemName = findViewById(R.id.problem_name);
        problemCode_contest = findViewById(R.id.problem_code);
        dateAdded = findViewById(R.id.date_added);
        sourceSizeLimit = findViewById(R.id.source_size_limit);
        maxTimeLimit = findViewById(R.id.time_limit);
        challengeType = findViewById(R.id.challenge_type);
        successfulSubmissions = findViewById(R.id.successful_submissions);
        author = findViewById(R.id.author);
        problemBody = findViewById(R.id.body);

    }

    private void updateViewWithProblemData(Problem problem) {

        String problemNameStr = "Problem Name : " + problem.getProblemName();
        String problemCode_ContestStr = "<br>Problem Code:</br> " + problem.getProblemCode();
        String dateAddedStr = "<br>Date Added:<br> " + problem.getDateAdded();
        String sourceSizeLimitStr = "<br>Source Size Limit:<br> " + problem.getSourceSizeLimit();
        String maxTimeLimitStr = "<br>Max Time Limit:<br> " + problem.getMaxTimeLimit();
        String challengeTypeStr = "<br>Challenge Type:<br> " + problem.getChallengeType();
        String successfulSubmissionsStr = "<br>Successful Submissions:<br> " + problem.getSuccessfulSubmissions();
        String authorStr = "<br>Author:<br> " + problem.getAuthor();

        problemName.setText(Html.fromHtml(problemNameStr));
        problemCode_contest.setText(Html.fromHtml(problemCode_ContestStr));
        dateAdded.setText(Html.fromHtml(dateAddedStr));
        sourceSizeLimit.setText(Html.fromHtml(sourceSizeLimitStr));
        maxTimeLimit.setText(Html.fromHtml(maxTimeLimitStr));
        challengeType.setText(Html.fromHtml(challengeTypeStr));
        successfulSubmissions.setText(Html.fromHtml(successfulSubmissionsStr));
        author.setText(Html.fromHtml(authorStr));

        problemBody.addStyleSheet(new Github());
        problemBody.loadMarkdown(Html.fromHtml(problem.getBody()).toString());

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
