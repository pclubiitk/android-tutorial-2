package com.example.test;

import android.graphics.Bitmap;

public class FeedItem {
	public String message;
	public String date;
	public String image;
	public Bitmap imageBitmap = null;
	public FeedItem(String msg, String date, String image){
		this.message = msg;
		this.date = date;
		this.image = image;
		
	}
}
