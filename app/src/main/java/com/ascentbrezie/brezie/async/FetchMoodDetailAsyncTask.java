package com.ascentbrezie.brezie.async;

import android.content.Context;
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
 * Created by ADMIN on 09-11-2015.
 */
public class FetchMoodDetailAsyncTask extends AsyncTask<String,Void,Boolean> {

    private Context context;
    private FetchMoodDetailCallback callback;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private URL url;
    private HttpURLConnection httpURLConnection;

    public interface FetchMoodDetailCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchMoodDetailAsyncTask(Context context, FetchMoodDetailCallback callback) {
        this.context = context;
        this.callback = callback;
        if(Constants.moodDetailData != null){
            Constants.moodDetailData.clear();
        }
        else{
            Constants.moodDetailData = new ArrayList<MoodDetailData>();
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... params) {

        Log.d(Constants.LOG_TAG,Constants.FETCH_MOOD_DETAIL_ASYNC_TASK);
        Log.d(Constants.LOG_TAG," The url to be fetched is "+params[0]);

        try{

            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            List<KeyValuePairData> keyValuePairData = new ArrayList<KeyValuePairData>();
            keyValuePairData.add(new KeyValuePairData("user_id",params[1]));
            keyValuePairData.add(new KeyValuePairData("mood_id",params[2]));
            keyValuePairData.add(new KeyValuePairData("latitude","1"));
            keyValuePairData.add(new KeyValuePairData("longitude","1"));
//            keyValuePairData.add(new KeyValuePairData("latitude",params[3]));
//            keyValuePairData.add(new KeyValuePairData("longitude",params[4]));

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
                String quoteId = jsonObject.getString("quote_id");
                String commentCounter = jsonObject.getString("comment_counter");
                String likeCounter = jsonObject.getString("like_counter");
                String shareCounter = jsonObject.getString("share_counter");
                String usedAsCounter = jsonObject.getString("usedas_counter");

                JSONArray jsonArray = jsonObject.getJSONArray("comments");
                for(int i=0;i<jsonArray.length();i++){

                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);
                    String nickName = nestedJsonObject.getString("nickname");
                    String comment = nestedJsonObject.getString("comment_text");

                    List<CommentsData>commentsData = new ArrayList<CommentsData>();
                    commentsData.add(new CommentsData(comment,nickName));

                }


                return true;
            }

            return false;

        }
        catch(Exception e){

            e.printStackTrace();

        }
        finally{

            try {

                if(inputStream != null){

                    inputStream.close();
                }
            }
            catch (Exception e){

                e.printStackTrace();
            }

        }

        return false;
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
        Log.d(Constants.LOG_TAG," the sent parameters "+result);
        return result;

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

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," The value returned is "+result);
        callback.onResult(result);
    }
}
