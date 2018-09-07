package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class ContestDetailsLoader extends AsyncTaskLoader<Contest> {

    private String contest_code;

    public ContestDetailsLoader(@NonNull Context context, String contest_code) {
        super(context);
        this.contest_code = contest_code;
    }

    @Nullable
    @Override
    public Contest loadInBackground() {

        String url = Helpers.buildContestDetailsUrl(getContext(), Urls.CONTEST_DETAILS_URL, contest_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            //TODO parse json
            //{"code":"XXX","name":"XXX","startDate":"2018-09-07","endDate":"2018-09-17","bannerFile":"https:\/\/www.codechef.com\/download\/small-banner\/SEPT18A\/1536305099.jpg","freezingTime":0,"announcements":"&lt;p&gt;14:30, 15th October 2017: The exam is extended by 10 minutes.&lt;br \/&gt;&lt;b&gt;&lt;br \/&gt;The problem weightages are given below in rules section.&lt;\/b&gt;&lt;\/p&gt;\r\n&lt;p&gt;The content of Recent Activity block from exam page has been made inaccessible. In case if you try to access it, you will get an error stating&nbsp;&lt;b&gt;&quot;You are not allowed to check this contest. Please reload&quot;&lt;\/b&gt;. Please ignore the error and continue with your exam.&lt;\/p&gt;\r\n&lt;p&gt;14:44, 15th October 2017: Problem accuracy will not be displayed. It has been restricted for this exam.&lt;\/p&gt;\r\n&lt;p&gt;Also, the score shown on the exam page is not final. It is subject to change after final verification.&lt;\/p&gt;\r\n&lt;p&gt;Additionally, you cannot leave the exam hall before 3:30 pm.&lt;\/p&gt;","problemsList":[{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"PHOTOCOM","contestCode":"SEPT18A","successfulSubmissions":3,"accuracy":6.1224489795918},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"MAGICHF","contestCode":"SEPT18A","successfulSubmissions":21,"accuracy":84},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"CHEFLST","contestCode":"SEPT18A","successfulSubmissions":0,"accuracy":0},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"XORIER","contestCode":"SEPT18A","successfulSubmissions":9,"accuracy":27.027027027027},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"CHEFZERO","contestCode":"SEPT18A","successfulSubmissions":7,"accuracy":69.230769230769},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"FCTR","contestCode":"SEPT18A","successfulSubmissions":1,"accuracy":1.9607843137255},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"TABGAME","contestCode":"SEPT18A","successfulSubmissions":8,"accuracy":17.021276595745},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"CHEFADV","contestCode":"SEPT18A","successfulSubmissions":10,"accuracy":66.666666666667},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"BSHUFFLE","contestCode":"SEPT18A","successfulSubmissions":33,"accuracy":11.458333333333},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"ANDSQR","contestCode":"SEPT18A","successfulSubmissions":7,"accuracy":14}]}

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
