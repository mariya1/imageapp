package com.example.imagesapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private WebView picView;
	private final int IMG_PICK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		picView = (WebView) findViewById(R.id.pic_view);
		picView.setBackgroundColor(0);
		Button pickBtn = (Button) findViewById(R.id.pick_btn);
		pickBtn.setOnClickListener(this);
		Button loadBtn = (Button) findViewById(R.id.load_btn);
		loadBtn.setOnClickListener(this);
		Button appBtn = (Button) findViewById(R.id.app_btn);
		appBtn.setOnClickListener(this);
		picView.getSettings().setBuiltInZoomControls(true);
		picView.getSettings().setUseWideViewPort(true);

	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.pick_btn) {

			Intent pickIntent = new Intent();
			pickIntent.setType("image/*");
			pickIntent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"),IMG_PICK);

		} else if (arg0.getId() == R.id.load_btn) {
			picView.loadUrl("https://si0.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_bigger.png");

		} else if (arg0.getId() == R.id.app_btn) {
			picView.loadUrl("file:///android_asset/imagepage.html");

		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == IMG_PICK) {
				Uri pickedUri = data.getData();
				String imagePath = "";
				String[] imgData = { MediaStore.Images.Media.DATA };
				Cursor imgCursor = managedQuery(pickedUri, imgData, null, null,
						null);
				if (imgCursor != null) {
					int index = imgCursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					imgCursor.moveToFirst();
					imagePath = imgCursor.getString(index);
				} else
					imagePath = pickedUri.getPath();
				picView.loadUrl("file:///" + imagePath);
			}
		}
	}
}
