package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.constants.StringKeys;

public class ProblemActivity extends AppCompatActivity {

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
}
