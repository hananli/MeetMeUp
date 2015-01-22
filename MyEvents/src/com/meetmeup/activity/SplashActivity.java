package com.meetmeup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.meetmeup.bean.UserBean;
import com.meetmeup.helper.Utill;



//This is the splash class which shows splash image for 3 seconds,if user is logged in previously then redirect to the home screen other wise login screen will be shown.
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Thread t = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					Thread.sleep(3000);
					startActivity();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		if(Utill.isNetworkAvailable(SplashActivity.this))
		{
		Intent I = new Intent(SplashActivity.this,BackgroundService.class);
		startService(I);
		}
		else
		{
			Toast.makeText(SplashActivity.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	void startActivity(){
			Intent intent = null;
			UserBean mUser = Utill.getUserPreferance(this);
			if (mUser.getUser_id() == null) {
				intent = new Intent(SplashActivity.this,LoginActivity.class);
			} else {
				intent = new Intent(SplashActivity.this,DashBoard.class);
			}
			finish();
			startActivity(intent);
	}

}
