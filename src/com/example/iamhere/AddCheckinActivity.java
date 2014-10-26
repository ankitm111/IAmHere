package com.example.iamhere;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class AddCheckinActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	static final int REQUEST_IMAGE_CAPTURE = 1;
	String mCurrentPhotoPath;
	private String provider;
    private Double latitude, longitude;
    private LocationClient mLocationClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(this, this, this);
		setContentView(R.layout.activity_add_checkin);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_checkin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	public void takePicture(View v)
	{
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) 
	    {
	    	File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        }
	        if (photoFile != null)
	        {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                                        Uri.fromFile(photoFile));
	        	startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	        }
	    }
	    
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	  //      ImageView mImageView = new ImageView(new Con);
	    //    mImageView.setImageBitmap(imageBitmap);
	        
	    }
	}
	
	public void saveCheckin(View v)
	{
		TextView checkin = (TextView)findViewById(R.id.checkinNameTextView);
		String checkin_name = checkin.getText().toString();
		
		TextView comment = (TextView)findViewById(R.id.commentsTextView);
		String commentString = comment.getText().toString();
	    
		Location currentLocation = mLocationClient.getLastLocation();
		
		latitude = currentLocation.getLatitude();
		longitude = currentLocation.getLongitude();

	    Intent returnIntent = new Intent();
	    returnIntent.putExtra("checkin_name", checkin_name);
	    returnIntent.putExtra("comment", commentString);
	    returnIntent.putExtra("latitude", latitude);
	    returnIntent.putExtra("longitude", longitude);
	    
	    setResult(RESULT_OK, returnIntent);
	    finish();
	}

	@Override
	protected void onStart() {
	   super.onStart();
	   // Connect the client.
	   mLocationClient.connect();   
	}
	
	@Override
	protected void onStop() {
      // Disconnect the client.
		mLocationClient.disconnect();
	    super.onStop();
	}
	
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
	      Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	}

	public void onDisconnected() {
		// TODO Auto-generated method stub
	      // Display the connection status
	      Toast.makeText(this, "Disconnected. Please re-connect.",
	      Toast.LENGTH_SHORT).show();
	}
	
}
