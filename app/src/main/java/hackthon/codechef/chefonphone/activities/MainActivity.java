package hackthon.codechef.chefonphone.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.Urls;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);

        if(preferences.contains(SharedPrefKeys.LOGIN_KEY))
        {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        class WebAppInterface {
            private Context mContext;

            /** Instantiate the interface and set the context */
            private WebAppInterface(Context c) {
                mContext = c;
            }

            @JavascriptInterface
            public void saveDetails(String name, String key, String handle) {
                SharedPreferences preferences = mContext.getSharedPreferences(SharedPrefKeys.LOGIN_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString(SharedPrefKeys.LOGIN_KEY, key);
                edit.putString(SharedPrefKeys.FULLNAME, name);
                edit.putString(SharedPrefKeys.CODECHEF_HANDLE, handle);
                edit.apply();
            }

            @JavascriptInterface
            public void startNextActivity() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        WebView loginWebView = findViewById(R.id.loginWebview);
        WebSettings webSettings = loginWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
        CookieManager.getInstance().setAcceptCookie(true);
        loginWebView.addJavascriptInterface(new WebAppInterface(this), "Register");
        loginWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        loginWebView.loadUrl(Urls.LOGIN_URL);
    }

}
