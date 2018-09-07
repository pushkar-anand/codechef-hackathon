package hackthon.codechef.chefonphone.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestListLoader;
import hackthon.codechef.chefonphone.asyncloaders.RecommendationLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Helpers;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks {

    private ProgressBar contestLoaderProgress;
    private ProgressBar recommendLoaderProgress;
    private LinearLayout contestLinear;
    private LinearLayout recommendLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        String welcomeTxt = "Welcome " + preferences.getString(SharedPrefKeys.FULLNAME, "") + "!!";

        if (!preferences.contains(SharedPrefKeys.LOGIN_KEY)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        contestLoaderProgress = findViewById(R.id.contestProgressBar);
        recommendLoaderProgress = findViewById(R.id.recommendProgressBar);
        contestLinear = findViewById(R.id.contestChildOfChild);
        recommendLinear = findViewById(R.id.recommendChildOfChild);

        TextView welcomeView = findViewById(R.id.welcomeTextView);
        welcomeView.setText(welcomeTxt);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportLoaderManager().initLoader(IDs.CONTEST_SHORT_LIST_LOADER, null, this).forceLoad();
        getSupportLoaderManager().initLoader(IDs.RECOMMENDATION_LOADER, null, this).forceLoad();

        View navHeaderView = navigationView.getHeaderView(0);
        Helpers.updateDrawerNavHeader(this, navHeaderView);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Helpers.handleDrawerNavigation(HomeActivity.this, id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("InflateParams")
    private void updateContestDetails(ArrayList<Contest> contestList) {

        LayoutInflater inflater =
                (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (final Contest contest : contestList) {

            if (inflater != null) {
                View view = inflater.inflate(R.layout.contest_card, null);

                TextView titleTV = view.findViewById(R.id.contest_title);
                titleTV.setText(contest.getContestName());

                TextView codeTV = view.findViewById(R.id.contest_code);
                codeTV.setText(contest.getContestCode());

                TextView startDateTV = view.findViewById(R.id.start_date);
                startDateTV.setText(contest.getContestStartDate());

                TextView endDateTV = view.findViewById(R.id.end_date);
                endDateTV.setText(contest.getContestEndDate());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, ContestActivity.class);
                        intent.putExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY, contest.getContestCode());
                        startActivity(intent);
                    }
                });

                contestLinear.addView(view,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }

        }
        contestLinear.setVisibility(View.VISIBLE);
        contestLoaderProgress.setVisibility(View.GONE);
    }

    private void updateRecommendation(Problem problem) {
        //TODO finish this.

    }


    @NonNull
    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        if (id == IDs.CONTEST_SHORT_LIST_LOADER) {
            return new ContestListLoader(HomeActivity.this, "short");
        } else {
            return new RecommendationLoader(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onLoadFinished(@NonNull Loader loader, Object object) {
        Integer id = loader.getId();
        if (object != null) {

            if (id.equals(IDs.CONTEST_SHORT_LIST_LOADER)) {
                updateContestDetails((ArrayList<Contest>) object);
            } else if (id.equals(IDs.RECOMMENDATION_LOADER)) {
                updateRecommendation((Problem) object);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }


}
