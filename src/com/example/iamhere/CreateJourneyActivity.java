package com.example.iamhere;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

class Checkin
{
    public String checkinName, comment;
    Double latitude, longitude;
    	
    public Checkin(String c, String com, Double lat, Double lon) {
    		checkinName = c;
    		comment = com;
    		latitude = lat;
    		longitude = lon;
    }
}

public class CreateJourneyActivity extends Activity {

	 // this context will use when we work with Alert Dialog
    final Context context = this;
    public String journeyName = null;
    
    public List<Checkin> checkinList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_journey);
		
		Parse.initialize(this, "euDIlnZNac4M0Jl6wgds8qHOERXaJsHTKiAgmesT", "yD5SB9avxORf8KIp9XQhTy2Zmn1gTuLHzt0RSbyT");
		
		checkinList = new ArrayList<Checkin>();
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Alert Dialog With EditText"); //Set Alert dialog title here
        alert.setMessage("Enter Your Name Here"); //Message here
        
        // Set an EditText view to get user input 
        final EditText input = new EditText(context);
        alert.setView(input);
        
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
             //You will get as string input data in this variable.
             // here we convert the input to a string and show in a toast.
             String srt = input.getEditableText().toString();  
             journeyName = srt;
             TextView tView = ((TextView) findViewById(R.id.JourneyName));
             tView.setText(journeyName);
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
              // Canceled.
                dialog.cancel();
                finish();
            }
      }); //End of alert.setNegativeButton
        
      AlertDialog alertDialog = alert.create();
      alertDialog.show();
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_journey, menu);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK) {
	        	
	    	    String checkinName = data.getStringExtra("checkin_name");
	    	    String comment = data.getStringExtra("comment");
	    	    Double latitude = data.getDoubleExtra("latitude", 0.0);
	    	    Double longitude = data.getDoubleExtra("longitude", 0.0);
	    	    
	    	    Checkin checkin = new Checkin(checkinName, comment, latitude, longitude);
	            checkinList.add(checkin);  // adding checkin to be displayed in list
	            
	            TableLayout tl = (TableLayout) findViewById(R.id.checkinList);
	            TextView cName = new TextView(this);
	            TextView latLong = new TextView(this);
	            
	            cName.setText(checkinName);
	            latLong.setText(new String(latitude.toString() + longitude.toString()));
	            TableRow tr = new TableRow(this);
	            
	            tr.setLayoutParams(new LayoutParams(
	            		LayoutParams.WRAP_CONTENT,
	            		LayoutParams.WRAP_CONTENT));
	            tr.addView(cName);
	            tr.addView(latLong);
	            
	            tl.addView(tr);

	            	
	    	    
	        }
	        else if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}//onActivityResult
	
	public void addCheckin(View v)
	{
		Intent intent = new Intent(this, AddCheckinActivity.class);
		//startActivity(intent);
		startActivityForResult(intent, 1);
	}
	
	public void mapJourney(View v)
	{
		Intent intent = new Intent(this, MapJourney.class);
		String data = new String();
		for(Checkin c : checkinList)
		{
		//	LatLng l = new LatLng(c.latitude, c.longitude);
			data = c.checkinName;
			data += ' ';
			data += c.latitude.toString();
			data += ' ';
			data += c.longitude.toString();
			data += '\n'; // end of 1 checkin info
		}
		intent.putExtra("latlon", data);
		//intent.putExtra(name, value);
		startActivity(intent);
		
	}
	
	public void saveJourney(View v)
	{
		// Make parse request
		ParseObject journey = new ParseObject("JourneyDetails");
		journey.put("user", LoginActivity.loginName);
		journey.put("journeyName", journeyName);
		for (Checkin c: checkinList)
		{
			ParseObject checkin = new ParseObject("CheckinDetails");
			checkin.put("checkinName", c.checkinName);
			checkin.put("checkinComment", c.comment);
			ParseGeoPoint gPoint = new ParseGeoPoint(c.latitude, c.longitude);
			checkin.put("checkin", gPoint);
			
			journey.add("checkin", checkin);
		}
		
		journey.saveInBackground();
		
		finish();
	}
}
