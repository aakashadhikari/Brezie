package com.ascentbrezie.brezie.utils;

import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.KeyValuePairData;
import com.ascentbrezie.brezie.data.MoodDetailData;
import com.ascentbrezie.brezie.data.ProfileData;
import com.ascentbrezie.brezie.data.QuotesData;
import com.ascentbrezie.brezie.data.TimelineData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class Constants {

    public static final String APP_NAME = "Brezie";
    public static final String LOG_TAG = "Brezie";

    // Landing Screen Variables

    //Splash Screen Variables
    public static String userId;
    public static String referenceCode;

    //
    public static String nickName;

    public static JSONObject transactionGrandParentJsonObject;
    public static JSONArray transactionParentJsonArray;
    public static JSONObject transactionParentJsonObject;
    public static JSONArray transactionChildJsonArray;
    public static JSONObject transactionChildJsonObject;



    // all the classes
    public static final String ABOUT_US_ACTIVITY = " ABOUT US ACTIVITY ";
    public static final String LANDING_ACTIVITY = " LANDING ACTIVITY ";
    public static final String LOGIN_ACTIVITY = " LOGIN ACTIVITY ";
    public static final String LOGIN_OR_REGISTER_ACTIVITY = " LOGIN OR REGISTER ACTIVITY ";
    public static final String MOBILE_VERIFICATION_ACTIVITY = " MOBILE VERIFICATION ACTIVITY ";
    public static final String MOOD_DETAIL_ACTIVITY = " MOOD DETAIL ACTIVITY ";
    public static final String PROFILE_ACTIVITY = " PROFILE ACTIVITY ";
    public static final String REGISTER_ACTIVITY = " REGISTER ACTIVITY ";
    public static final String SETTINGS_ACTIVITY = " SETTINGS ACTIVITY ";
    public static final String SPLASH_SCREEN_ACTIVITY = " SPLASH SCREEN ACTIVITY ";


    // all the fragments

    // all the adapters
    public static final String MOOD_DETAIL_RECYCLER_ADAPTER =" MOODS DETAIL RECYCLER ADAPTER";
    public static final String TIMELINE_RECYCLER_ADAPTER =" TIMELINE RECYCLER ADAPTER";

    // all the async tasks
    public static final String FETCH_MOOD_DETAIL_ASYNC_TASK=" FETCH MOOD DETAIL ASYNC TASK ";
    public static final String FETCH_PROFILE_DETAILS_ASYNC_TASK=" FETCH_PROFILE_DETAILS_ASYNC_TASK ";
    public static final String FETCH_QUOTES_FOR_DAY_ASYNC_TASK=" FETCH QUOTES FOR DAY ASYNC TASK ";
    public static final String FETCH_USER_ID_ASYNC_TASK=" FETCH USER ID ASYNC TASK ";
    public static final String LOGIN_ASYNC_TASK=" LOGIN ASYNC TASK ";
    public static final String MOBILE_VERIFICATION_ASYNC_TASK=" MOBILE VERIFICATION ASYNC TASK ";
    public static final String REGISTER_ASYNC_TASK=" REGISTER ASYNC TASK ";
    public static final String SEND_TRANSACTION_ASYNC_TASK=" SEND TRANSACTION ASYNC TASK ";


    // all the data
    public static ArrayList<CommentsData> commentsData;
    public static ArrayList<KeyValuePairData> keyValuePairData;
    public static ArrayList<MoodDetailData> moodDetailData;
    public static ArrayList<ProfileData> profileData;
    public static ArrayList<QuotesData> quotesData;
    public static ArrayList<TimelineData> timelineData;

    // all the developer links
    public static final String splashUrl = "http://brezie.in/dev/Brezie/splash.php";
    public static final String landingUrl = "http://brezie.in/dev/Brezie/landing.php";
    public static final String moodDetailUrl = "http://brezie.in/dev/Brezie/mood_detail.php";
    public static final String profileUrl = "http://brezie.in/dev/Brezie/profile.php";
    public static final String registerUrl = "http://brezie.in/dev/Brezie/register.php";
    public static final String verifyUrl = "http://brezie.in/dev/Brezie/verify.php";
    public static final String loginUrl = "http://brezie.in/dev/Brezie/login.php";

    // all the prod links
//    public static final String splashUrl = "http://brezie.com/brezieprod/splash.php";
//    public static final String landingUrl = "http://brezie.com/brezieprod/landing.php";
//    public static final String moodDetailUrl = "http://brezie.com/brezieprod/mood_detail.php";
//    public static final String profileUrl = "http://brezie.com/brezieprod/profile.php";
//    public static final String registerUrl = "http://brezie.com/brezieprod/register.php";
//    public static final String verifyUrl = "http://brezie.com/brezieprod/verify.php";

}
