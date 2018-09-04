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

import hackthon.codechef.chefonphone.R;
import hackthon.codechef.chefonphone.constants.SharedPrefKeys;
import hackthon.codechef.chefonphone.constants.Urls;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        CookieManager.getInstance().setAcceptCookie(true);
        loginWebView.addJavascriptInterface(new WebAppInterface(this), "Register");
        loginWebView.loadUrl(Urls.LOGIN_URL);
    }

}
