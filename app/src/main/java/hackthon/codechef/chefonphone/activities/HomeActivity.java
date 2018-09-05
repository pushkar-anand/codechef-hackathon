package hackthon.codechef.chefonphone.activities;

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
import android.view.Menu;
import android.view.MenuItem;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestListLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        if(!preferences.contains(SharedPrefKeys.LOGIN_KEY))
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        String name = preferences.getString(SharedPrefKeys.FULLNAME, "");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportLoaderManager().initLoader(IDs.CONTEST_LIST_LOADER, null, this).forceLoad();
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

    public void startPracticeActivity(String level) {

        Intent practice_intent = new Intent(this, PracticeActivity.class);
        practice_intent.putExtra(StringKeys.PRACTICE_ACTVITY_INTENT_KEY, level);
        startActivity(practice_intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.beginner) {
            startPracticeActivity(StringKeys.PRACTICE_LEVEL_BEGINNER);
        } else if (id == R.id.easy) {
            startPracticeActivity(StringKeys.PRACTICE_LEVEL_EASY);
        } else if (id == R.id.medium) {
            startPracticeActivity(StringKeys.PRACTICE_LEVEL_MEDIUM);
        } else if (id == R.id.hard) {
            startPracticeActivity(StringKeys.PRACTICE_LEVEL_HARD);
        } else if (id == R.id.challenge) {
            startPracticeActivity(StringKeys.PRACTICE_LEVEL_CHALLENGE);
        } else if (id == R.id.peer) {
            startPracticeActivity(StringKeys.PRACTICE_LEVEL_PEER);
        } else if (id == R.id.ide) {
            Intent ide = new Intent(this,IDE_Activity.class);
            startActivity(ide);
        } else if (id == R.id.contest) {
            Intent contest = new Intent(this,ContestActivity.class);
            startActivity(contest);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @NonNull
    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        if (id == IDs.CONTEST_LIST_LOADER) {
            return new ContestListLoader(HomeActivity.this, "short");
        }else {
            return new Loader(this);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }


}
