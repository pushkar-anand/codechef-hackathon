package hackthon.codechef.chefonphone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Internet {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static String getHTTPSGetRequestResponse(@NonNull String Url) throws IOException
    {
       URL url = new URL(Url);
       HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        InputStream in = urlConnection.getInputStream();

        InputStreamReader inputStreamReader = new InputStreamReader(in);

        StringBuilder builder = new StringBuilder();


        int data = inputStreamReader.read();
        while (data != -1) {
            char current = (char) data;
            data = inputStreamReader.read();
            builder.append(current);
        }

        return builder.toString();
    }

    public static String getHTTPSPostRequestResponse(@NonNull String Url, @Nullable String data)
            throws IOException {

        URL url = new URL(Url);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);

        if (data != null) {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
        }

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }

    public static String getSecret()
    {
        return "a9e73b17ztNMIvl13H7wrNNb5YFza9e73b17";
    }
}
