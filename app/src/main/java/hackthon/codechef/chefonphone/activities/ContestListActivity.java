package hackthon.codechef.chefonphone.activities;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.asyncloaders.ContestLongListLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Helpers;

public class ContestListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Pair<ArrayList<Contest>, ArrayList<Contest>>> {

    private ProgressBar contestLoaderProgress;
    private LinearLayout presentList, futureList;


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

        Helpers.handleDrawerNavigation(ContestListActivity.this, id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayList(Pair<ArrayList<Contest>, ArrayList<Contest>> contestArrayList) {

        ArrayList<Contest> presentList = contestArrayList.first;
        ArrayList<Contest> futureList = contestArrayList.second;

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
