package com.example.iamhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Parse.initialize(this, "euDIlnZNac4M0Jl6wgds8qHOERXaJsHTKiAgmesT", "yD5SB9avxORf8KIp9XQhTy2Zmn1gTuLHzt0RSbyT");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	

	public void loginButtonClick(View v)
	{
		String uname  = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
		String password = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
		
		ParseUser.logInInBackground(uname, password, 
				  new LogInCallback() {
					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
					    // Start an intent for the homepage activity
						if (user != null){
							Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
							startActivity(intent);
						}
						else{
							if (e != null)
							{
					    	    Toast.makeText(LoginActivity.this, e.getMessage(), 
							    	      Toast.LENGTH_LONG).show();
							}
						}
					}
				});		
	
	}
	
	public void signUpButtonClick(View v)
	{
		 Intent intent = new Intent(this, SignUpActivity.class);
		 startActivity(intent);
	}

}
