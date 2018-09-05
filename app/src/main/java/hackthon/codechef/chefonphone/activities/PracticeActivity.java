package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.constants.StringKeys;

public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Intent practice_activity = getIntent();

        try {
            String value = practice_activity.getStringExtra(StringKeys.PRACTICE_ACTVITY_INTENT_KEY);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
