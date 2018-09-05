package hackthon.codechef.chefonphone.activities;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.PracticeProblemLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Helpers;

public class PracticeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<ArrayList<Problem>> {

    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();

        level = intent.getStringExtra(StringKeys.PRACTICE_ACTVITY_INTENT_KEY);

        getSupportLoaderManager().initLoader(IDs.PRACTICE_LOADER, null, this).forceLoad();

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

        Helpers.handleDrawerNavigation(PracticeActivity.this, id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Problem>> onCreateLoader(int id, @Nullable Bundle args) {
        return new PracticeProblemLoader(PracticeActivity.this, level, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Problem>> loader, ArrayList<Problem> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Problem>> loader) {

    }
}
