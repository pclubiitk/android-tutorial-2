package com.example.test;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends SherlockActivity {
	private void initializeImageCache(){
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();
		ImageLoader.getInstance().init(config); // Do it on Application start
		//TODO image to be showed on error
	}
	private ArrayList<FeedItem> FeedItems = new ArrayList<FeedItem>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AsyncHttpClient cl = new AsyncHttpClient();
		ListView mList = (ListView) findViewById(R.id.listAAP);
		initializeImageCache();
		final FeedListAdapter mAdapter = new FeedListAdapter(MainActivity.this);
		mList.setAdapter(mAdapter);
		
		cl.get("http://www.pclub.in/aap.json", new JsonHttpResponseHandler(){
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Log.e("!", responseString);
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Log.e("!", responseString);
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				// TODO Auto-generated method stub
				Log.e("!", response.toString());
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				Log.e("!", response.toString());
				try {
					JSONArray posts = response.getJSONObject("posts").getJSONArray("data");
					int len = posts.length();
					for (int i = 0; i < len; i++) {
						JSONObject it = posts.getJSONObject(i);
						if( !(it.has("message") && it.has("picture")) )
							continue;
						String desc = it.getString("message");
						String img = it.getString("picture");
						mAdapter.addItem(new FeedItem(desc, "Today", img));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		getSupportActionBar().setTitle("Programming Club");
		getSupportActionBar().setIcon(R.drawable.icon);
	}
	@Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.only_refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
            	//updateUserCredits();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
