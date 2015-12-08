package com.ascentbrezie.brezie.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by SAGAR on 12/7/2015.
 */
public class SendTransactionAsyncTask extends AsyncTask<JSONObject,Void,Boolean> {

    Context context;
    SendTransactionCallback callback;

    HttpURLConnection httpURLConnection;
    InputStream inputStream;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    URL url;


    public interface SendTransactionCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    public SendTransactionAsyncTask(Context context, SendTransactionCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(JSONObject... params) {

        Log.d(Constants.LOG_TAG, Constants.SEND_TRANSACTION_ASYNC_TASK);
        Log.d(Constants.LOG_TAG," The object to be sent is "+params[0]);

        try{

            url = new URL("hello");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            outputStream = httpURLConnection.getOutputStream();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(String.valueOf(params[0]));
            bufferedWriter.flush();

            int statusCode = httpURLConnection.getResponseCode();

            if(statusCode == 200){


                inputStream = httpURLConnection.getInputStream();
                String response = convertInputStreamToString(inputStream);

                Log.d(Constants.LOG_TAG," The response is "+response);

                return true;
            }

            return false;


        }
        catch(Exception e){

            e.printStackTrace();

        }
        finally {

            try{

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

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        clearJson();
        callback.onResult(result);

    }

    private void clearJson() {

        Constants.transactionGrandParentJsonObject = new JSONObject();
        Constants.transactionParentJsonObject = new JSONObject();
        Constants.transactionParentJsonArray = new JSONArray();
        Constants.transactionChildJsonObject = new JSONObject();
        Constants.transactionChildJsonArray = new JSONArray();


    }
}
