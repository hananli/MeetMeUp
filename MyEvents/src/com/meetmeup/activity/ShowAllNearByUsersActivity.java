package com.meetmeup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//This class is used for Showing google map
public class ShowAllNearByUsersActivity extends FragmentActivity {

	GoogleMap googleMap;
	MarkerOptions markerOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {

			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_map);
			Intent intent = getIntent();
			String Latti = intent.getStringExtra("lat");
			String Longi = intent.getStringExtra("lon");

			String title = "MeetMeUp";
			double Lat = Double.parseDouble(Latti);// 22.719569;
			double Lon = Double.parseDouble(Longi);// 75.857726;

			SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			googleMap = supportMapFragment.getMap();
			LatLng latLng = new LatLng(Lat, Lon);
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					latLng, 15);
			googleMap.animateCamera(cameraUpdate);
			googleMap.setMyLocationEnabled(true);
			googleMap.addMarker(new MarkerOptions().position(new LatLng(Lat, Lon)).title(title));
			googleMap.addMarker(new MarkerOptions().position(new LatLng(22.719569, 75.857726)).title("T.I."));
			googleMap.addMarker(new MarkerOptions().position(new LatLng(22.756413, 75.883894)).title("Bhawarkua"));
			googleMap.addMarker(new MarkerOptions().position(new LatLng(22.719760, 75.857027)).title("Rajwada"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
