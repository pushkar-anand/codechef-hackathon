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
import android.text.Html;
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
import hackthon.codechef.chefonphone.asyncloaders.ContestShortListLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Helpers;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<ArrayList<Contest>> {

    private ProgressBar contestLoaderProgress;
    private LinearLayout contestLinear;

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
        contestLinear = findViewById(R.id.contestChildOfChild);

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

        Helpers.handleMenuCLicks(this, id);

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
                String contest_start = "<b>Started:</b> " + contest.getContestStartDate();
                startDateTV.setText(Html.fromHtml(contest_start));

                TextView endDateTV = view.findViewById(R.id.end_date);
                String contest_end = "<b>Ending:</b> " + contest.getContestEndDate();
                endDateTV.setText(Html.fromHtml(contest_end));

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


    @NonNull
    @Override
    public Loader<ArrayList<Contest>> onCreateLoader(int id, Bundle bundle) {
        return new ContestShortListLoader(HomeActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Contest>> loader, ArrayList<Contest> object) {
        if (object != null) {

            updateContestDetails(object);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }


}
