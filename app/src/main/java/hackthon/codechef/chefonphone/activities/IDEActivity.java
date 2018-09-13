package hackthon.codechef.chefonphone.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.adapters.CompilationLogsAdapter;
import hackthon.codechef.chefonphone.asyncloaders.CompilationLogLoader;
import hackthon.codechef.chefonphone.constants.IDs;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.StringKeys;
import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.CompilationLog;
import hackthon.codechef.chefonphone.databases.AppDatabase;
import hackthon.codechef.chefonphone.utils.Cache;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class IDEActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks {

    private ProgressBar ideLoaderProgress;
    private WebView ideWebView;
    private String problem = null, contest = null;
    private SharedPreferences preferences;
    private View problemView, statusView;
    private Boolean isLogsLoaderInitiated = false;
    private RecyclerView logsRecycler;
    private CompilationLogsAdapter compilationLogsAdapter;

    private Boolean isLoadViewShowing = false;

    //TODO find a way to manage user run queue status

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ide);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        Helpers.updateDrawerNavHeader(this, navHeaderView);

        Intent intent = getIntent();

        preferences = getSharedPreferences(SharedPrefKeys.LOGIN_PREF, MODE_PRIVATE);

        if (intent.hasExtra(StringKeys.IDE_ACTIVITY_INTENT_KEY)) {
            problem = intent.getStringExtra(StringKeys.IDE_ACTIVITY_INTENT_KEY);
        }

        ideLoaderProgress = findViewById(R.id.ideLoaderProgress);
        ideWebView = findViewById(R.id.ideWebView);
        problemView = findViewById(R.id.problemViewInclude);

        class WebAppInterface {
            private Context mContext;

            /**
             * Instantiate the interface and set the context
             */
            private WebAppInterface(Context c) {
                mContext = c;
            }

            @JavascriptInterface
            public void logToLogcat(String msg) {
                Log.d("Js console: ", msg);
            }

            @JavascriptInterface
            public void sendRunRequest(final String lang, final String code, final String input) {

                try {
                    String url = Urls.IDE_RUN_URL;

                    JSONObject object = new JSONObject();

                    object.put("sourceCode", code);
                    object.put("lang", lang);
                    object.put("input", input);
                    object.put("user", preferences.getString(SharedPrefKeys.CODECHEF_HANDLE, ""));
                    object.put("token", preferences.getString(SharedPrefKeys.LOGIN_KEY, ""));

                    String json = object.toString();

                    String result = Internet.getHTTPSPostJSONRequestResponse(url, json);
                    Log.d("IDE_RUN_RESPONSE", result);

                    JSONObject response = new JSONObject(result);
                    String statusCode = response.getString("code");

                    AppDatabase appDatabase = AppDatabase.getInstance(IDEActivity.this);
                    appDatabase.newCompilationRequest(problem, lang, statusCode);

                    ideWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            ideWebView.evaluateJavascript("responseReceived()", null);
                        }
                    });

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(IDEActivity.this,
                                    "Request added to queue. Check status from menu.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @JavascriptInterface
            public void saveIdeCode(String code, String lang) {

                try {
                    Cache.addToCache(IDEActivity.this, lang, code);
                    preferences.edit().putString(SharedPrefKeys.IDE_AUTOSAVE, lang).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @JavascriptInterface
            public void updateProblemCode(String code) {
                problem = code;
            }
        }

        WebSettings webSettings = ideWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        ideWebView.addJavascriptInterface(new WebAppInterface(this), "IDE");

        ideWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ideLoaderProgress.setVisibility(View.VISIBLE);
                ideWebView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ideLoaderProgress.setVisibility(View.GONE);
                ideWebView.setVisibility(View.VISIBLE);
                initIDE();
            }
        });

        ideWebView.loadUrl("file:///android_asset/ide/ide.html");

        logsRecycler = findViewById(R.id.compileLogRecycler);
        statusView = findViewById(R.id.statusViewInclude);
        compilationLogsAdapter = new CompilationLogsAdapter();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        logsRecycler.setLayoutManager(layoutManager);
        logsRecycler.setItemAnimator(new DefaultItemAnimator());
        logsRecycler.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        logsRecycler.setAdapter(compilationLogsAdapter);

    }

    private void initIDE() {
        try {
            JSONObject obj = new JSONObject();
            if (problem != null) {
                obj.put("problemCode", problem);
            }
            if (preferences.contains(SharedPrefKeys.IDE_AUTOSAVE)) {
                String lang = preferences.getString(SharedPrefKeys.IDE_AUTOSAVE, "");
                if (Cache.isInCache(this, lang)) {
                    String code = Cache.getFromCache(this, lang);
                    obj.put("autoSaveLang", lang);
                    obj.put("autoSaveCode", code);
                }
            }
            String strObj = obj.toString();
            Log.d("initIDE", strObj);
            String jsCall = "initEditor(" + strObj + ")";

            ideWebView.evaluateJavascript(jsCall, null);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else if (isLoadViewShowing) {

            ideWebView.setVisibility(View.VISIBLE);
            statusView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ide_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        Helpers.handleMenuCLicks(this, id);

        if (id == R.id.action_info) {
            viewInfo();
        } else if (id == R.id.action_download) {
            downloadCode();
        } else if (id == R.id.action_status) {
            viewCompilationLogs();
        }

        return super.onOptionsItemSelected(item);
    }

    private void viewCompilationLogs() {

        LoaderManager loaderManager = getSupportLoaderManager();

        if (!isLogsLoaderInitiated) {
            loaderManager.initLoader(IDs.COMPILATION_LOGS_LOADER,
                    null, this).forceLoad();
        } else {
            loaderManager.restartLoader(IDs.COMPILATION_LOGS_LOADER,
                    null, this).forceLoad();
        }

        isLogsLoaderInitiated = true;
        ideLoaderProgress.setVisibility(View.VISIBLE);
    }

    private void viewInfo() {

        if (problem != null && contest != null) {
            //TODO finish this to show problem

        } else {
            Toast.makeText(this, "No problem loaded. Enter problem code to load.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadCode() {

        ideWebView.evaluateJavascript("downloadCode()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                // value is the result returned by the Javascript as JSON
                Log.d("JS Response", value);
                //TODO finish this.
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Helpers.handleDrawerNavigation(this, id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void populateAndShowCompilationLogs(ArrayList<CompilationLog> compilationLogs) {
        compilationLogsAdapter.updateLogsData(compilationLogs);
        compilationLogsAdapter.notifyDataSetChanged();
        ideWebView.setVisibility(View.GONE);
        ideLoaderProgress.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
        isLoadViewShowing = false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        String lang = preferences.getString(SharedPrefKeys.IDE_AUTOSAVE, "");
        Cache.removeFromCache(this, lang);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        if (id == IDs.COMPILATION_LOGS_LOADER) {
            return new CompilationLogLoader(this);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {

        if (data != null) {
            if (loader.getId() == IDs.COMPILATION_LOGS_LOADER) {
                ArrayList<CompilationLog> compilationLogs = (ArrayList<CompilationLog>) data;
                populateAndShowCompilationLogs(compilationLogs);
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
