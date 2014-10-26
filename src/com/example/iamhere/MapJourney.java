package com.example.iamhere;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapJourney extends Activity {

	List<LatLng> latLong;
	MapView mapView;
	GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_journey);
		
		latLong= new ArrayList<LatLng>();
		
		Intent intent = getIntent();
		String data = intent.getStringExtra("latlon");
		
		String lines[] = data.split("[\\r\\n]+");
		for (int i=0; i < lines.length; i++)
		{
			String checkinInfo[] = lines[i].split(" ");
			Double lat = Double.parseDouble(checkinInfo[1]);
			Double lon = Double.parseDouble(checkinInfo[2]);
			LatLng latlong = new LatLng(lat, lon);
			latLong.add(latlong);
		}
		
		mapView = (MapView)findViewById(R.id.mapView);		
		//#IMPORTANT: Without this line, your code will not work
		mapView.onCreate(savedInstanceState);
		map = mapView.getMap();
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 
		 //Map Settings
		map.getUiSettings().setMyLocationButtonEnabled(false);
		map.getUiSettings().setCompassEnabled(false);
		map.getUiSettings().setZoomControlsEnabled(false);
		
		MapsInitializer.initialize(this);
		mapView.onResume();
		plotPoints(); 
		
	}

	protected void plotPoints()
	{
		MarkerOptions points = new MarkerOptions();
		PolylineOptions pOptions = new PolylineOptions();
		LatLng ll = new LatLng(37.7833, -122.4167 );
		points.position(ll);
		pOptions.add(ll);
		for(LatLng latlon : latLong){
			points.position(latlon);
			pOptions.add(latlon);
		}

		map.addMarker(points);
		Polyline polyline = map.addPolyline(pOptions);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_journey, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
