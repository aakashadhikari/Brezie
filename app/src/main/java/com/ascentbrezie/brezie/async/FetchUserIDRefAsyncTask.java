package com.ascentbrezie.brezie.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.KeyValuePairData;
import com.ascentbrezie.brezie.data.MoodDetailData;
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
 * Created by Aakash on 03-12-2015.
 */
public class FetchUserIDRefAsyncTask extends AsyncTask<String,Void,Boolean>{

    private Context context;
    private FetchUserIDCallback callback;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private Context mContext;

    public interface FetchUserIDCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }
    public FetchUserIDRefAsyncTask(Context context, FetchUserIDCallback callback) {
        this.context = context;
        this.callback = callback;

    }


    @Override
    protected Boolean doInBackground(String... params) {

        Log.d(Constants.LOG_TAG,Constants.FETCH_USERIDREF_ASYNC_TASK);
        Log.d(Constants.LOG_TAG," The url to be fetched is "+params[0]);
        //SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_NAME);
        //sharedPreferences.getString("device_id", null);
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        prefs.getString("device_id", null);


        try{

            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            List<KeyValuePairData> keyValuePairData = new ArrayList<KeyValuePairData>();
            keyValuePairData.add(new KeyValuePairData("device_id",params[1]));

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
                    int ref_code = Integer.parseInt(jsonObject.optString("ref_code").toString());

                    SharedPreferences.Editor editor;
                    editor = prefs.edit();
                    editor.putInt("user_id", user_id);
                    editor.putInt("ref_code", ref_code);
                    editor.commit();



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

        BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

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
