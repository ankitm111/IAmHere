package com.example.iamhere;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddCheckinActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	static final int REQUEST_IMAGE_CAPTURE = 1;
	String mCurrentPhotoPath;
	private LocationManager locationManager;
	private String provider;
    private Double latitude, longitude;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		
		//LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
	//	boolean enabled = service.isProviderEnabled(c);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
	/*	if (!enabled) {
		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent);
		} 
		
		// Define the criteria how to select the location provider -> use
	    // default
		try{
	       Criteria criteria = new Criteria();
	       provider = locationManager.getBestProvider(criteria, false);
	       Location location = locationManager.getLastKnownLocation(provider);
	       System.out.println(location.toString());
	    
	       if (location != null) {
	    	   System.out.println("Provider " + provider + " has been selected.");
	    	   onLocationChanged(location);
	       }
	       else {
	    	   System.out.println("Location not available");
	       }
		}
		catch(SecurityException e){
			System.out.println("Exception " + e.toString());
		}
		catch(IllegalArgumentException i){
			System.out.println("Exception " + i.toString());

		}
	    */
	    // Initialize the location fields
	    
	    

	    Intent returnIntent = new Intent();
	    returnIntent.putExtra("checkin_name", checkin_name);
	    returnIntent.putExtra("comment", commentString);
	    returnIntent.putExtra("latitude", latitude);
	    returnIntent.putExtra("latitude", longitude);
	    
	    setResult(RESULT_OK,returnIntent);
	    finish();
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
        latitude = location.getLatitude();
	   	longitude = location.getLongitude();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "Enabled new provider " + provider,
	            Toast.LENGTH_SHORT).show();
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	    Toast.makeText(this, "Disabled provider " + provider,
	            Toast.LENGTH_SHORT).show();
		
	}
	
}
