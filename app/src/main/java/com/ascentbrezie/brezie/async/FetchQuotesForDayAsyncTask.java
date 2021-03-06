package com.ascentbrezie.brezie.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.KeyValuePairData;
import com.ascentbrezie.brezie.data.QuotesData;
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
public class FetchQuotesForDayAsyncTask extends AsyncTask<String,Void,Boolean> {

    private Context context;
    private FetchQuotesForDayCallback callback;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private URL url;
    private HttpURLConnection httpURLConnection;

    public interface FetchQuotesForDayCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);
    }

    public FetchQuotesForDayAsyncTask(Context context, FetchQuotesForDayCallback callback) {
        this.context = context;
        this.callback = callback;
        if(Constants.quotesData != null){

            Constants.quotesData.clear();
        }
        else{

            Constants.quotesData = new ArrayList<QuotesData>();
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... params) {

        Log.d(Constants.LOG_TAG,Constants.FETCH_QUOTES_FOR_DAY_ASYNC_TASK);
        Log.d(Constants.LOG_TAG," The url to be fetched is "+params[0]);

        try{

            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            List<KeyValuePairData> keyValuePairData = new ArrayList<KeyValuePairData>();
            keyValuePairData.add(new KeyValuePairData("user_id",params[1]));

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
                JSONArray jsonArray = jsonObject.getJSONArray("quotes");
                for(int i=0;i<jsonArray.length();i++){

                    String quote = jsonArray.getString(i);
                    Constants.quotesData.add(new QuotesData(quote));
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
