package com.example.iamhere;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SearchJourneyActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "euDIlnZNac4M0Jl6wgds8qHOERXaJsHTKiAgmesT", "yD5SB9avxORf8KIp9XQhTy2Zmn1gTuLHzt0RSbyT");

		setContentView(R.layout.activity_search_journey);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_journey, menu);
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
	
	public void searchJourney()
	{
		String searchString = ((EditText) findViewById(R.id.searchJourneyText)).getText().toString();
		
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		int selected = radioGroup.getCheckedRadioButtonId();
		RadioButton b = (RadioButton) findViewById(selected);
		
		if (selected == 0)
		{
			ParseQuery<ParseObject> query = ParseQuery.getQuery("JourneyDetails");
			query.whereEqualTo("user", searchString);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
			    public void done(List<ParseObject> scoreList, ParseException e) {
			        if (e == null)
			        {
			            Log.d("UserJourneys", "Retrieved " + scoreList.size() + " journeys");
			        }
			        else
			        {
			            Log.d("UserJourneys", "Error: " + e.getMessage());
			        }
			    }
			});	
		}
		else if(selected == 1)
		{
			ParseQuery<ParseObject> query = ParseQuery.getQuery("JourneyDetails");
			query.whereEqualTo("journeyName", searchString);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
			    public void done(List<ParseObject> scoreList, ParseException e) {
			        if (e == null)
			        {
			            Log.d("journeyName", "Retrieved " + scoreList.size() + " journeys");
			        }
			        else
			        {
			            Log.d("journeyName", "Error: " + e.getMessage());
			        }
			    }
			});	
		}
		else
		{
			ParseQuery<ParseObject> query = ParseQuery.getQuery("JourneyDetails");
			query.whereEqualTo("journeyName", searchString);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
			    public void done(List<ParseObject> scoreList, ParseException e) {
			        if (e == null)
			        {
			            Log.d("journeyName", "Retrieved " + scoreList.size() + " journeys");
			        }
			        else
			        {
			            Log.d("journeyName", "Error: " + e.getMessage());
			        }
			    }
			});	
		}
		
		
		
		
	}
	
}
