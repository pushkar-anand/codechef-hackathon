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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.adapters.ProblemListAdapter;
import hackthon.codechef.chefonphone.asyncloaders.PracticeProblemLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Helpers;

import static org.apache.commons.text.WordUtils.capitalizeFully;

public class PracticeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<ArrayList<Problem>> {

    private String level;
    private ProgressBar problemLoaderProgress;
    private RecyclerView problemRecycler;
    private ProblemListAdapter problemListAdapter;

    private Integer offset = 0;

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

        problemLoaderProgress = findViewById(R.id.practiceProblemLoader);
        problemRecycler = findViewById(R.id.problemRecyclerView);
        problemListAdapter = new ProblemListAdapter();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        problemRecycler.setLayoutManager(layoutManager);
        problemRecycler.setItemAnimator(new DefaultItemAnimator());
        problemRecycler.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        problemRecycler.setAdapter(problemListAdapter);

        TextView typeTV = findViewById(R.id.practiceProblemType);
        String str = "LEVEL :  " + capitalizeFully(level);
        typeTV.setText(str);
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

        Helpers.handleDrawerNavigation(PracticeActivity.this, id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void populateViewWithProblems(ArrayList<Problem> problems) {

        Log.d(getClass().getSimpleName(), "populateViewWithProblems: length " + problems.size());
        problemListAdapter.populateProblemList(problems);
        problemLoaderProgress.setVisibility(View.GONE);
        problemRecycler.setVisibility(View.VISIBLE);
        problemListAdapter.notifyDataSetChanged();

        problemListAdapter.setLoadMoreClickListener(new ProblemListAdapter.LoadMoreClickListener() {
            @Override
            public void onLoadMoreClicked() {
                offset += 20;
                Bundle bundle = new Bundle();
                bundle.putBoolean("offset", true);
                problemLoaderProgress.setVisibility(View.VISIBLE);
                problemRecycler.setVisibility(View.GONE);
                getSupportLoaderManager().restartLoader(IDs.PRACTICE_LOADER, bundle, PracticeActivity.this).forceLoad();
            }
        });

    }

    @NonNull
    @Override
    public Loader<ArrayList<Problem>> onCreateLoader(int id, @Nullable Bundle args) {
        if (args == null) {
            return new PracticeProblemLoader(PracticeActivity.this, level, null);
        } else {
            return new PracticeProblemLoader(PracticeActivity.this, level, offset);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Problem>> loader, ArrayList<Problem> data) {
        if (data != null) {
            populateViewWithProblems(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Problem>> loader) {

    }
}
