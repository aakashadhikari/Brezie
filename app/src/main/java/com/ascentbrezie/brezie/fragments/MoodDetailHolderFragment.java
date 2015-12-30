package com.ascentbrezie.brezie.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.activities.LoginOrRegisterActivity;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.custom.CustomEditText;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.imageloader.ImageLoader;
import com.ascentbrezie.brezie.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by SAGAR on 12/28/2015.
 */
public class MoodDetailHolderFragment extends Fragment {

    private LinearLayout commentsLayout,footer;
    private int position;
    private ImageLoader imageLoader;
    private File cacheDir;

    private ScrollView scrollView;
    private CustomTextView commentsCounter;
    private CustomEditText editComment;
    private CustomButton addComment;
    private ImageView moodImage,download,share,like;
    private String moodId;


    public static MoodDetailHolderFragment newInstance(int position,String moodId) {

        Log.d(Constants.LOG_TAG, Constants.MOOD_DETAIL_HOLDER_FRAGMENT);

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("moodId", moodId);

        MoodDetailHolderFragment fragment = new MoodDetailHolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.row_mood_detail,container,false);

        imageLoader = new ImageLoader(getActivity().getApplicationContext());

        getExtras();
        findViews(v);
        setLayout();
        setViews();

        return v;
    }

    public void getExtras(){

        position = this.getArguments().getInt("position");
        moodId = this.getArguments().getString("moodId");
    }

    public void findViews(View v){



        scrollView = (ScrollView) v.findViewById(R.id.scroll_view_mood_detail_holder_fragment);
        moodImage = (ImageView) v.findViewById(R.id.quote_image_mood_detail_holder_fragment);

        download = (ImageView) v.findViewById(R.id.download_included);
        share = (ImageView) v.findViewById(R.id.share_included);
        like = (ImageView) v.findViewById(R.id.like_included);

        commentsCounter = (CustomTextView) v.findViewById(R.id.comments_count_text_mood_detail_holder_fragment);
        commentsLayout = (LinearLayout) v.findViewById(R.id.display_comments_layout_mood_detail_holder_fragment);

        footer = (LinearLayout) v.findViewById(R.id.footer_layout_mood_detail_holder_fragment);

        editComment = (CustomEditText) v.findViewById(R.id.add_comment_edit_included);
        addComment = (CustomButton) v.findViewById(R.id.add_comment_button_included);


    }

    public void setLayout(){


        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.footer_layout_mood_detail_holder_fragment);
        scrollView.setLayoutParams(layoutParams);

    }

    public void setViews(){

        imageLoader.DisplayImage(Constants.moodDetailData.get(position).getBackgroundUrl(), moodImage);

        commentsCounter.setText(Constants.moodDetailData.get(position).getCommentsCount()+" Comments ");

        if(Constants.moodDetailData.get(position).isLiked()){
            like.setImageResource(R.drawable.icon_selected_like);
        }
        else{
            like.setImageResource(R.drawable.icon_unselected_like);
        }

        if(Constants.moodDetailData.get(position).isShared()){
            share.setImageResource(R.drawable.icon_selected_share);
        }
        else{
            share.setImageResource(R.drawable.icon_unselected_share);
        }

        List<CommentsData> commentsData = Constants.moodDetailData.get(position).getCommentsData();

        for(int i =0 ;i<commentsData.size();i++){

            CustomTextView customTextView = new CustomTextView(getActivity().getApplicationContext());
            customTextView.setTextColor(getResources().getColor(R.color.black));
            customTextView.setTextSize(20);
            customTextView.setText(commentsData.get(i).getNickName()+" : "+commentsData.get(i).getComment());


            commentsLayout.addView(customTextView);

        }


        like.setOnClickListener(listener);
        share.setOnClickListener(listener);
        download.setOnClickListener(listener);
        addComment.setOnClickListener(listener);

    }

    public void like(){

        String id = Constants.moodDetailData.get(position).getQuoteId();
        like.setImageResource(R.drawable.icon_selected_like);
        Constants.moodDetailData.get(position).setIsLiked(true);
        addToJson(id, "1", "1", null);

    }

    public void share(){

        String id = Constants.moodDetailData.get(position).getQuoteId();
        share.setImageResource(R.drawable.icon_selected_share);
        Constants.moodDetailData.get(position).setIsShared(true);
        addToJson(id, "4", "1",null);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        else
            cacheDir= getActivity().getApplicationContext().getCacheDir();

        String fileName  = String.valueOf(Constants.moodDetailData.get(position).getBackgroundUrl().hashCode());
        File f = new File(cacheDir, fileName);

        Bitmap b = BitmapFactory.decodeFile(f.getPath());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                b, "BrezieImages", "Check out this quote");
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
//        share.putExtra(Intent.EXTRA_TEXT, "Get many more amazing quotes like this only on Brezie. Free download now at http://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
        share.putExtra(Intent.EXTRA_TEXT, "Get many more amazing quotes like this only on Brezie. Free download now at https://goo.gl/18pjef");
                getActivity().startActivity(Intent.createChooser(share, "Select"));


    }

    public void download() {

//        String id = moodDetailData.get(position).getQuoteId();
//        addToJson(id,"4","1",null);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        else
            cacheDir=getActivity().getCacheDir();

        String fileName  = String.valueOf(Constants.moodDetailData.get(position).getBackgroundUrl().hashCode());
        File f = new File(cacheDir, fileName);

        Bitmap b = BitmapFactory.decodeFile(f.getPath());
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                b, "BrezieImages", "Check out this quote");

        Toast.makeText(getActivity(), "Image downloaded", Toast.LENGTH_SHORT).show();

    }

    public void addCommentToLayout(){

        String quoteId = Constants.moodDetailData.get(position).getQuoteId();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname", "null");

        if(nickname.equalsIgnoreCase("null")){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("route", "comment");
            editor.putString("quoteId",quoteId);
            editor.putString("comment",editComment.getText().toString());
            editor.putString("moodId",moodId);
            editor.commit();

            Intent i = new Intent(getActivity(), LoginOrRegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(i);

        }
        else{


            CustomTextView tv = new CustomTextView(getActivity());
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutParams1);
            tv.setText(nickname + " : " + editComment.getText().toString());
            tv.setTextSize(20);
            tv.setTextColor(getResources().getColor(R.color.black));
            commentsLayout.addView(tv);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0,scrollView.getBottom());
                }
            });

            (Constants.moodDetailData.get(position).getCommentsData()).add(new CommentsData(editComment.getText().toString(), nickname));

            // First we will add the value to the json and then clear the edittext
            // If we clear the edittext first and then the set the json then the json will
            // not be able to receive the data
            addToJson(quoteId, "2", "1", editComment.getText().toString());
            editComment.setText("");


        }
    }

    public void addToJson(String quoteId,String action,String actionFlag,String comment){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "null");
        String nickname = sharedPreferences.getString("nickname","null");

        try{

            Constants.transactionParentJsonObject = new JSONObject();
            Constants.transactionParentJsonObject.put("quote_id",Integer.parseInt(quoteId));
            Constants.transactionParentJsonObject.put("action",Integer.parseInt(action));
            Constants.transactionParentJsonObject.put("action_flag",Integer.parseInt(actionFlag));


            if(action.equalsIgnoreCase("2")){

                Constants.transactionChildJsonObject = new JSONObject();
                Constants.transactionChildJsonObject.put("nickname",nickname);
                Constants.transactionChildJsonObject.put("comment",comment);

                // This array will hold the comments array
                Constants.transactionChildJsonArray.put(Constants.transactionChildJsonObject);
                Constants.transactionParentJsonObject.put("comments",Constants.transactionChildJsonArray);
                Constants.transactionChildJsonArray = new JSONArray();

            }

            Constants.transactionParentJsonArray.put(Constants.transactionParentJsonObject);
            Constants.transactionGrandParentJsonObject.put("user_id",Integer.parseInt(userId));
            Constants.transactionGrandParentJsonObject.put("transaction",Constants.transactionParentJsonArray);

        }
        catch (Exception e){

            e.printStackTrace();
        }

    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch(v.getId()){

                case R.id.like_included: like();
                    break;
                case R.id.share_included: share();
                    break;
                case R.id.download_included: download();
                    break;
                case R.id.add_comment_button_included: addCommentToLayout();
                    break;

            }

        }
    };



}
