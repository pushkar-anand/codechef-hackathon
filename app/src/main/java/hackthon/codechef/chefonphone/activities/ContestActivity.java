package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestDetailsLoader;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Contest;

public class ContestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Contest> {

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

    }

    @NonNull
    @Override
    public Loader<Contest> onCreateLoader(int id, @Nullable Bundle args) {
        return new ContestDetailsLoader(ContestActivity.this, contest);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Contest> loader, Contest data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Contest> loader) {

    }
}
