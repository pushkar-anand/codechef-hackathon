package hackthon.codechef.chefonphone.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestLongListLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.AlarmHelpers;
import hackthon.codechef.chefonphone.utils.Helpers;

public class ContestListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Pair<ArrayList<Contest>, ArrayList<Contest>>> {

    private ProgressBar contestLoaderProgress;
    private LinearLayout presentLinear, futureLinear;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        contestLoaderProgress = findViewById(R.id.contestListProgress);
        presentLinear = findViewById(R.id.presentContestList);
        futureLinear = findViewById(R.id.futureContestList);
        scrollView = findViewById(R.id.contestScroll);
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

        Helpers.handleDrawerNavigation(ContestListActivity.this, id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("InflateParams")
    private void displayList(Pair<ArrayList<Contest>, ArrayList<Contest>> contestArrayList) {

        ArrayList<Contest> presentList = contestArrayList.first;
        ArrayList<Contest> futureList = contestArrayList.second;

        LayoutInflater inflater =
                (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (final Contest contest : presentList) {
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
                        Intent intent = new Intent(ContestListActivity.this, ContestActivity.class);
                        intent.putExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY, contest.getContestCode());
                        startActivity(intent);
                    }
                });

                presentLinear.addView(view,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        for (final Contest contest : futureList) {
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
                        Intent intent = new Intent(ContestListActivity.this, ContestActivity.class);
                        intent.putExtra(StringKeys.CONTEST_ACTVITY_INTENT_KEY, contest.getContestCode());
                        startActivity(intent);
                    }
                });

                final ImageView notifyMeImage = view.findViewById(R.id.notify);

                if (AlarmHelpers.alarmIsSet(ContestListActivity.this, contest.getContestCode())) {
                    notifyMeImage.setImageResource(R.drawable.ic_action_notify_on);
                    notifyMeImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            doNotNotifyMe(notifyMeImage, contest);
                        }
                    });
                } else {
                    notifyMeImage.setImageResource(R.drawable.ic_action_notify);
                    notifyMeImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notifyMe(notifyMeImage, contest);
                        }
                    });
                }

                futureLinear.addView(view,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        contestLoaderProgress.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

    }

    private void notifyMe(final ImageView imgView, final Contest contest) {
        AlarmHelpers.setAlarm(ContestListActivity.this,
                contest.getContestCode(),
                contest.getContestStartDate());

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doNotNotifyMe(imgView, contest);
            }
        });
    }

    private void doNotNotifyMe(final ImageView imgView, final Contest contest) {
        AlarmHelpers.deleteAlarm(ContestListActivity.this, contest.getContestCode());

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyMe(imgView, contest);
            }
        });

    }

    @NonNull
    @Override
    public Loader<Pair<ArrayList<Contest>, ArrayList<Contest>>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ContestLongListLoader(ContestListActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Pair<ArrayList<Contest>, ArrayList<Contest>>> loader,
                               Pair<ArrayList<Contest>, ArrayList<Contest>> data) {
        if (data != null) {
            displayList(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Pair<ArrayList<Contest>, ArrayList<Contest>>> loader) {

    }
}
