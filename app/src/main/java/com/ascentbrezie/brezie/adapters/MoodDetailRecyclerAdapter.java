package com.ascentbrezie.brezie.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 25-09-2015.
 */
public class MoodDetailRecyclerAdapter extends RecyclerView.Adapter<MoodDetailRecyclerAdapter.ViewHolder> {

    private Context context;
    private int width,height;

    private ImageView moodImage,like,share;
    private int images[] = {R.drawable.q1,R.drawable.q2,R.drawable.q3,R.drawable.q4,R.drawable.q5};

    private LinearLayout rowLayout,displayComments;
    private CustomEditText enterComments;
    private CustomButton addComment;
    private CustomTextView commentsCount;

    private ArrayList<MoodDetailData> moodDetailData;
    private ImageLoader imageLoader;
    private String route;
    private SharedPreferences sharedPreferences;

    public MoodDetailRecyclerAdapter(Context context, int width, int height, ArrayList<MoodDetailData> moodDetailData) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.moodDetailData = moodDetailData;
        imageLoader = new ImageLoader(context);

        checkRoute();

        Log.d(Constants.LOG_TAG,Constants.MOOD_DETAIL_RECYCLER_ADAPTER);
    }

    public void checkRoute(){

        sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        route = sharedPreferences.getString("route","null");


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
    }

    public void findViews(ViewHolder holder){

        rowLayout = (LinearLayout) holder.v.findViewById(R.id.row_layout);
        moodImage = (ImageView) holder.v.findViewById(R.id.quote_image_mood_detail_activity);

        like = (ImageView) holder.v.findViewById(R.id.like_included);
        share = (ImageView) holder.v.findViewById(R.id.share_included);


        commentsCount = (CustomTextView) holder.v.findViewById(R.id.comments_count_text_mood_detail_activity);
        displayComments = (LinearLayout) holder.v.findViewById(R.id.display_comments_layout_mood_detail_activity);
        enterComments = (CustomEditText) holder.v.findViewById(R.id.add_comment_edit_included);
        addComment = (CustomButton) holder.v.findViewById(R.id.add_comment_button_included);
    }

    public void setCardView(){

        int cardWidth = width-20;
        int cardHeight = height-20;

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(cardWidth,cardHeight);
        layoutParams.setMargins(10,10,0,0);
        rowLayout.setLayoutParams(layoutParams);

    }

    public void setViews(int position){

        commentsCount.setText("Total Comments : "+moodDetailData.get(position).getCommentsCount());
        addCommentsToLayout(position);

        imageLoader.DisplayImage(moodDetailData.get(position).getBackgroundUrl(),moodImage);

        like.setTag("like_" + position);
        like.setOnClickListener(listener);

        share.setTag("share_" + position);
        share.setOnClickListener(listener);

        addComment.setTag("comment_" + position);
        addComment.setOnClickListener(listener);

//        moodImage.setImageResource(images[position]);
//        moodImage.setImageResource(images[position]);

    }


    public void addCommentsToLayout(int position){

        List<CommentsData> commentsData = Constants.moodDetailData.get(position).getCommentsData();

        displayComments.removeAllViews();
        for(int i=0;i<commentsData.size();i++)
        {
            TextView tv = new TextView(context);
            tv.setText(commentsData.get(i).getNickName()+" : "+commentsData.get(i).getComment());
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutParams1);
            tv.setTextSize(18F);
            displayComments.addView(tv);

        }
        /**
         * This case will only take place when the user has not logged in to the system
         * and trying to add a comment
         * That time he has to follow a cycle of LoginRegister -> Login / Register-> Return here
         * **/
        if(route.equalsIgnoreCase("comment")){

            sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
            String temp = sharedPreferences.getString("id_comment","null");
            String id_comment[] = temp.split("_");
            String nickname = sharedPreferences.getString("nickname","null");

            int id = Integer.parseInt(id_comment[0]);
            String comment = id_comment[1];

            TextView tv = new TextView(context);
            tv.setText(nickname+" : "+comment);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutParams1);
            tv.setTextSize(18F);
            displayComments.addView(tv);

            String temporary = String.valueOf(id);
            addToJson(temporary,"2","1");

        }


    }


    @Override
    public int getItemCount() {

        return moodDetailData.size();
    }

    public void like(int position){

        String id = moodDetailData.get(position).getQuoteId();
        like.setImageResource(R.drawable.icon_selected_like);
        notifyDataSetChanged();
        addToJson(id,"1","1");

    }

    public void share(int position){

        String id = moodDetailData.get(position).getQuoteId();
        share.setImageResource(R.drawable.icon_selected_share);
        notifyDataSetChanged();
        addToJson(id,"4","1");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello check out this cool App Brezie");
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);

    }

    public void addComment(int position){

        String id = moodDetailData.get(position).getQuoteId();
        String commentValue = enterComments.getText().toString();

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname","null");

        if(nickname.equalsIgnoreCase("null")){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("route", "comment");
            editor.putString("id_comment",id+"_"+commentValue);
            editor.commit();

            Log.d(Constants.LOG_TAG," the id comment entered is "+id+"_"+commentValue);

            Intent i = new Intent(context, LoginOrRegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);


        }
        else{

            TextView tv = new TextView(context);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(layoutParams1);
            tv.setText(nickname + " : " + enterComments.getText().toString());
            tv.setTextSize(18F);
            displayComments.addView(tv);
            enterComments.setText("");
            addToJson(id,"2","1");

        }

    }

    public void addToJson(String quoteId,String actionFlag,String action){


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","null");

        try{

            Constants.transactionParentJsonObject.put("quote_id",quoteId);
            Constants.transactionParentJsonObject.put("action_flag",actionFlag);
            Constants.transactionParentJsonObject.put("action",action);

            if(actionFlag.equalsIgnoreCase("2")){

                Constants.transactionChildJsonObject.put("nickname","nickname");
                Constants.transactionChildJsonObject.put("comment","abc1234");

                // This array will hold the comments array
                Constants.transactionChildJsonArray.put(Constants.transactionChildJsonObject);
                Constants.transactionParentJsonObject.put("comments",Constants.transactionChildJsonArray);

            }

            Constants.transactionParentJsonArray.put(Constants.transactionParentJsonObject);
            Constants.transactionGrandParentJsonObject.put("user_id",userId);
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

        switch (v.getId()){

            case R.id.like_included: like(position);
                break;
            case R.id.share_included: share(position);
                break;
            case R.id.add_comment_button_included: addComment(position);
                break;


        }

    }
};

}
