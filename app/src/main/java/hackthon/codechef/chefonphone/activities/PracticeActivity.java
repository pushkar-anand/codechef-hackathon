package hackthon.codechef.chefonphone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hackthon.codechef.chefonphone.R;

public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Intent practice_activity = getIntent();

        try {
            String value = practice_activity.getStringExtra("level");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
