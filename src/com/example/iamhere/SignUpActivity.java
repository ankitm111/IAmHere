package com.example.iamhere;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		Parse.initialize(this, "euDIlnZNac4M0Jl6wgds8qHOERXaJsHTKiAgmesT", "yD5SB9avxORf8KIp9XQhTy2Zmn1gTuLHzt0RSbyT");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
	
	public void SendSignUpRequest(View v)
	{
		
		String userName = ((EditText)findViewById(R.id.editTextUsername)).getText().toString();
		String userPassword = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
		String userEmail = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
		
		ParseUser user = new ParseUser();
		user.setUsername(userName);
		user.setPassword(userPassword);
		user.setEmail(userEmail);

		user.signUpInBackground(new SignUpCallback() { 
		    @Override
		    public void done(ParseException e) { 
		      // Handle the response
		    	if (e != null) {
		    	    // Show the error message
		    	    Toast.makeText(SignUpActivity.this, e.getMessage(), 
		    	      Toast.LENGTH_LONG).show();
		    	 }
		    	else{
		    		finish();
		    	}
		    } 
		  });

		finish();
	}
}
