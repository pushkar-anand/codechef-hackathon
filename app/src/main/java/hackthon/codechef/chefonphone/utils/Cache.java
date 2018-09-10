package hackthon.codechef.chefonphone.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cache {

    public static void
    addToCache(Context context, String key, String data)
            throws IOException {

        try {

            File file;
            FileOutputStream outputStream;

            file = new File(context.getCacheDir(), key);

            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public static Boolean isInCache(Context context, String key) {

        try {
            File file;
            file = new File(context.getCacheDir(), key);

            return file.exists();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String
    getFromCache(Context context, String key)
            throws IOException {

        try {


            File file;
            BufferedReader inputStream;

            file = new File(context.getCacheDir(), key);

            inputStream = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = inputStream.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void removeFromCache(Context context, String key) {
        File file;
        try {
            if (isInCache(context, key)) {
                file = new File(context.getCacheDir(), key);
                boolean x = file.delete();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
