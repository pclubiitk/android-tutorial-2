package com.example.test;

import java.util.ArrayList;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class FeedListAdapter extends BaseAdapter {
    
    private Context context;
    private ArrayList<FeedItem> FeedItems;
    public FeedListAdapter(Context context){
        this.context = context;
        FeedItems = new ArrayList<FeedItem>();
    }
 
    @Override
    public int getCount() {
        return FeedItems.size();
    }
 
    @Override
    public Object getItem(int position) {      
        return FeedItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(FeedItem item){
    	FeedItems.add(item);
    	notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row, null);
        }
        FeedItem item = FeedItems.get(position);
        ImageView img = ((ImageView)convertView.findViewById(R.id.image));
        img.setImageResource(android.R.color.transparent);
        final ProgressBar pb = (ProgressBar) convertView.findViewById(R.id.aa);
        pb.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(item.image, img,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				Log.e("!", "Failed " + imageUri);
				
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				Log.e("!", "Compleete  " + imageUri);
				pb.setVisibility(View.GONE);
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				Log.e("!", "Cancelled " + imageUri);
				
			}
		});
//        AsyncHttpClient cl = new AsyncHttpClient();
//        cl.get(item.image, new AsyncHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//				FeedItems.get(position).imageBitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
//				notifyDataSetChanged();
//			}
//			
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					byte[] responseBody, Throwable error) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
        String summary = item.message;
        if(summary.length()>100){
        	summary = summary.substring(0, 100);
        	summary = summary + "...";
        }
        ((TextView)convertView.findViewById(R.id.desc)).setText(summary);
        
        return convertView;
    }
 
}