package com.ascentbrezie.brezie.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.KeyValuePairData;
import com.ascentbrezie.brezie.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aakash on 04-12-2015.
 */
public class FetchProfileDetailsAsyncTask extends AsyncTask<String,Void,Boolean> {
    private Context context;
    private FetchProfileDetailsAsyncTask.FetchProfileDetailsAsyncTaskCallback callback;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private Context mContext;

    public interface FetchProfileDetailsAsyncTaskCallback {
        public void onStart(boolean status);

        public void onResult(boolean result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }
    public FetchProfileDetailsAsyncTask(Context context, FetchProfileDetailsAsyncTaskCallback callback) {
        this.context = context;
        this.callback = callback;

    }


    @Override
    protected Boolean doInBackground(String... params) {

        Log.d(Constants.LOG_TAG, Constants.FETCH_PROFILE_DETAILS_ASYNC_TASK);
        Log.d(Constants.LOG_TAG," The url to be fetched is "+params[0]);
        //SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME);
        //sharedPreferences.getString("device_id", null);
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        prefs.getString("user_id", null);


        try{

            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            List<KeyValuePairData> keyValuePairData = new ArrayList<KeyValuePairData>();
            keyValuePairData.add(new KeyValuePairData("user_id",params[1]));
            keyValuePairData.add(new KeyValuePairData("first_name", params[2]));
            keyValuePairData.add(new KeyValuePairData("last_name", params[3]));
            keyValuePairData.add(new KeyValuePairData("nickname",params[4]));
            keyValuePairData.add(new KeyValuePairData("phone",params[5]));
            keyValuePairData.add(new KeyValuePairData("gender",params[6]));
            keyValuePairData.add(new KeyValuePairData("date_of_birth",params[7]));
            keyValuePairData.add(new KeyValuePairData("email",params[8]));

            outputStream = httpURLConnection.getOutputStream();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(constructPostParameters(keyValuePairData));
            bufferedWriter.flush();
            

            int statusCode = httpURLConnection.getResponseCode();

            if(statusCode == 200){


                inputStream = httpURLConnection.getInputStream();
                String response = convertInputStreamToString(inputStream);

                Log.d(Constants.LOG_TAG," The response is "+response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("listing");
                for(int i=0;i<jsonArray.length();i++){
                    jsonArray.getJSONObject(i);

                    int user_id = Integer.parseInt(jsonObject.optString("user_id").toString());
                    String first_name = (jsonObject.optString("first_name").toString());
                    String last_name = (jsonObject.optString("last_name").toString());
                    String nickname = (jsonObject.optString("nickname").toString());
                    int phone = Integer.parseInt(jsonObject.optString("phone").toString());
                    String gender = (jsonObject.optString("gender").toString());
                    int date_of_birth = Integer.parseInt(jsonObject.optString("date_of_birth").toString());
                    String email = (jsonObject.optString("email").toString());

                    SharedPreferences.Editor editor;
                    editor = prefs.edit();
                    editor.putInt("user_id", user_id);
                    editor.putString("first_name", first_name);
                    editor.putString("last_name", last_name);
                    editor.putString("nickname", nickname);
                    editor.putInt("phone", phone);
                    editor.putString("gender", gender);
                    editor.putLong("date_of_birth",date_of_birth);
                    editor.putString("email", email);
                    editor.apply();



                    Constants.commentsData = new ArrayList<CommentsData>();
//                    for(int j=0;j<;j++){
//
//
//                    }


                }

                return true;
            }
            return true;
//            return false;

        }
        catch(Exception e){


        }
        finally{


        }

        return true;
//        return false;
    }


    public String convertInputStreamToString(InputStream inputStream) throws IOException {

        String line="";
        String result="";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        while((line = bufferedReader.readLine())!=null) {

            result += line;

        }

             /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }

    public String constructPostParameters(List<KeyValuePairData> keyValuePairData){

        String result="";
        boolean firstTime=true;

        for(KeyValuePairData data : keyValuePairData){

            if (firstTime) {
                firstTime = false;
            } else {

                result += "&";
            }

            result += data.getKey();
            result += "=";
            result += data.getValue();
        }
        return result;

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," The value returned is "+result);
        callback.onResult(result);
    }



}
