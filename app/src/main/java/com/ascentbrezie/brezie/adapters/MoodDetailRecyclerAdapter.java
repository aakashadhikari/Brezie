package com.ascentbrezie.brezie.adapters;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.activities.LoginOrRegisterActivity;
import com.ascentbrezie.brezie.custom.CustomButton;
import com.ascentbrezie.brezie.custom.CustomEditText;
import com.ascentbrezie.brezie.custom.CustomTextView;
import com.ascentbrezie.brezie.data.CommentsData;
import com.ascentbrezie.brezie.data.MoodDetailData;
import com.ascentbrezie.brezie.imageloader.ImageLoader;
import com.ascentbrezie.brezie.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class MoodDetailRecyclerAdapter extends RecyclerView.Adapter<MoodDetailRecyclerAdapter.ViewHolder>{

    private Context context;
    private int width,height;

    private ImageView moodImage,like,share,download;

//    private LinearLayout abc;
    private Button abc;
    private LinearLayout rowLayout,displayComments;
    private CustomEditText enterComments;
    private CustomButton addComment;
    private CustomTextView commentsCount;

    private ArrayList<MoodDetailData> moodDetailData;
    private ImageLoader imageLoader;
    private String route;
    private SharedPreferences sharedPreferences;
    private ViewHolder myViewHolder;
    private String moodId;
    private String quoteId,comment;
    private File cacheDir;
    public static int focussedOn;


    /**
     * This constructor will be called whenever we coming through
     * the comment cycle
     * **/
    public MoodDetailRecyclerAdapter(Context context,String quoteId,String comment,int width, int height, ArrayList<MoodDetailData> moodDetailData) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.moodDetailData = moodDetailData;
        this.quoteId = quoteId;
        this.comment = comment;
        imageLoader = new ImageLoader(context);

        addToJson(quoteId,"2","1",comment);

        Log.d(Constants.LOG_TAG,Constants.MOOD_DETAIL_RECYCLER_ADAPTER);
    }

    /**
     * This construtor will be called normally whenever we are calling the mood
     * detail activity
     * **/
    public MoodDetailRecyclerAdapter(Context context,String moodId,int width, int height, ArrayList<MoodDetailData> moodDetailData) {
        this.context = context;
        this.moodId = moodId;
        this.width = width;
        this.height = height;
        this.moodDetailData = moodDetailData;
        imageLoader = new ImageLoader(context);

        Log.d(Constants.LOG_TAG,Constants.MOOD_DETAIL_RECYCLER_ADAPTER);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_mood_detail,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        findViews(viewHolder);
        setCardView();
        setViews(i);

        focussedOn = i;


//        if((i%5) == 0){
//
//            fetchMoreData();
//
//        }

    }

    public void findViews(ViewHolder holder){

//        abc = (Button)holder.v.findViewById(R.id.abc);
//        abc = (LinearLayout) holder.v.findViewById(R.id.abc);
        rowLayout = (LinearLayout) holder.v.findViewById(R.id.row_layout_mood_detail_holder_fragment);
        moodImage = (ImageView) holder.v.findViewById(R.id.quote_image_mood_detail_holder_fragment);

        like = (ImageView) holder.v.findViewById(R.id.like_included);
        share = (ImageView) holder.v.findViewById(R.id.share_included);
        download = (ImageView) holder.v.findViewById(R.id.download_included);


//        commentsCount = (CustomTextView) holder.v.findViewById(R.id.comments_count_text_mood_detail_activity);
//        displayComments = (LinearLayout) holder.v.findViewById(R.id.display_comments_layout_mood_detail_activity);
//        enterComments = (CustomEditText) holder.v.findViewById(R.id.add_comment_edit_included);
//        addComment = (CustomButton) holder.v.findViewById(R.id.add_comment_button_included);
    }

    public void setCardView(){
//
//        int cardWidth = width-20;
//        int cardHeight = height-20;

//        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams1.setMargins(10, 0, 10,20);
//        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        abc.setLayoutParams(layoutParams1);

//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(cardWidth, ViewGroup.LayoutParams.FILL_PARENT);
//        layoutParams.setMargins(10, 10, 0, 0);
//        rowLayout.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width,width);
        moodImage.setLayoutParams(layoutParams);

    }

    public void setViews(final int position){


        moodImage.setLayoutParams(new LinearLayout.LayoutParams(width, width));

//        commentsCount.setText(moodDetailData.get(position).getCommentsCount() + " Comments");

        imageLoader.DisplayImage(moodDetailData.get(position).getBackgroundUrl(), moodImage);

//        enterComments.setTag("extract_" + position);
//        displayComments.setTag("layout_"+position);

//        addCommentsToLayout(position);

        if(moodDetailData.get(position).isLiked()){

            like.setImageResource(R.drawable.icon_selected_like);
            moodDetailData.get(position).isLiked();
        }
        else{

            like.setImageResource(R.drawable.icon_unselected_like);
        }

        like.setTag("like_" + position);
        like.setOnClickListener(listener);

        if(moodDetailData.get(position).isShared()){

            share.setImageResource(R.drawable.icon_selected_share);
            moodDetailData.get(position).setIsShared(true);

        }
        else{

            share.setImageResource(R.drawable.icon_unselected_share);
        }

        share.setTag("share_" + position);
        share.setOnClickListener(listener);

        download.setImageResource(R.drawable.icon_download);
        download.setTag("download_" + position);
        download.setOnClickListener(listener);

//        addComment.setTag("comment_" + position);
//        addComment.setOnClickListener(listener);

    }

    public void fetchMoreData(){

        String url = Constants.moodDetailUrl;
        String moodId;
        String quoteId;
        String direction;

//
//        new FetchMoodDetailAsyncTask(context, new FetchMoodDetailAsyncTask.FetchMoodDetailCallback() {
//            @Override
//            public void onStart(boolean status) {
//
//            }
//            @Override
//            public void onResult(boolean result) {
//
//            }
//        }).execute(url,moodId,quoteId,direction);


    }


    public void addCommentsToLayout(int position){


        List<CommentsData> commentsData = moodDetailData.get(position).getCommentsData();

        displayComments.removeAllViews();
        RelativeLayout tempRootView = (RelativeLayout) displayComments.getRootView();
        LinearLayout tempView = (LinearLayout) tempRootView.findViewWithTag("layout_" + position);

        for(int i=0;i<commentsData.size();i++)
        {
            TextView tv = new TextView(context);
            tv.setText(commentsData.get(i).getNickName()+" : "+commentsData.get(i).getComment());
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutParams1);
            tv.setTextSize(18F);
            tempView.addView(tv);

        }


    }


    @Override
    public int getItemCount() {

        return moodDetailData.size();
    }

    public void like(int position){

        String id = moodDetailData.get(position).getQuoteId();
        like.setImageResource(R.drawable.icon_selected_like);
        moodDetailData.get(position).setIsLiked(true);
        notifyDataSetChanged();
        addToJson(id, "1", "1", null);

    }

    public void share(int position){

        String id = moodDetailData.get(position).getQuoteId();
        share.setImageResource(R.drawable.icon_selected_share);
        moodDetailData.get(position).setIsShared(true);
        notifyDataSetChanged();
        addToJson(id,"4","1",null);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        else
            cacheDir=context.getCacheDir();

        String fileName  = String.valueOf(moodDetailData.get(position).getBackgroundUrl().hashCode());
        File f = new File(cacheDir, fileName);

        Bitmap b = BitmapFactory.decodeFile(f.getPath());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                b, "BrezieImages", "Check out this quote");
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        share.putExtra(Intent.EXTRA_TEXT, "Get many more amazing quotes like this only on Brezie. Free download now at http://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(Intent.createChooser(share, "Select"));


    }

    public void download(int position){

        String id = moodDetailData.get(position).getQuoteId();
//        addToJson(id,"4","1",null);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        else
            cacheDir=context.getCacheDir();

        String fileName  = String.valueOf(moodDetailData.get(position).getBackgroundUrl().hashCode());
        File f = new File(cacheDir, fileName);

        Bitmap b = BitmapFactory.decodeFile(f.getPath());
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                b, "BrezieImages", "Check out this quote");
        Toast.makeText(context,"Image downloaded",Toast.LENGTH_SHORT).show();

    }

    public void setAsWallpaper(int position){

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        else
            cacheDir=context.getCacheDir();

        String fileName  = String.valueOf(moodDetailData.get(position).getBackgroundUrl().hashCode());
        File f = new File(cacheDir, fileName);

        Bitmap b = BitmapFactory.decodeFile(f.getPath());

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try{

            wallpaperManager.setBitmap(b);
        }
        catch (Exception e){

            e.printStackTrace();
        }


    }

    public void addComment(ViewParent v,int position){

        String quoteId = moodDetailData.get(position).getQuoteId();

        // This line will give us the edit text of that particular mood Image
        CustomEditText et  = (CustomEditText)((RelativeLayout) v.getParent()).findViewWithTag("extract_" + position);
        LinearLayout displayCommentHere = (LinearLayout)((RelativeLayout) v.getParent()).findViewWithTag("layout_" + position);


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname", "null");


        if(nickname.equalsIgnoreCase("null")){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("route", "comment");
            editor.putString("quoteId",quoteId);
            editor.putString("comment",et.getText().toString());
            editor.putString("moodId",moodId);
            editor.commit();

            Intent i = new Intent(context, LoginOrRegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
        else{


            TextView tv = new TextView(context);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutParams1);
            tv.setText(nickname + " : " + et.getText().toString());
            tv.setTextSize(18F);
            displayCommentHere.addView(tv);


            (moodDetailData.get(position).getCommentsData()).add(new CommentsData(et.getText().toString(),nickname));
            notifyDataSetChanged();

            // First we will add the value to the json and then clear the edittext
            // If we clear the edittext first and then the set the json then the json will
            // not be able to receive the data
            addToJson(quoteId, "2", "1", et.getText().toString());
            et.setText("");


        }

    }

    public void addToJson(String quoteId,String action,String actionFlag,String comment){

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
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

        String tagDetails[] = v.getTag().toString().split("_");
        int position = Integer.parseInt(tagDetails[1]);
        ViewParent parent = v.getParent();

        switch (v.getId()){

            case R.id.like_included: like(position);
                break;
            case R.id.share_included: share(position);
                break;
            case R.id.download_included: download(position);
                break;
//            case R.id.set_as_wallpaper_included: setAsWallpaper(position);
//                break;
            case R.id.add_comment_button_included:
                addComment(parent,position);
                break;


        }

    }
};

}
