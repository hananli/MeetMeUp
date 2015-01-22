package com.meetmeup.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meetmeup.activity.DashBoard;
import com.meetmeup.activity.LoginActivity;
import com.meetmeup.activity.R;
import com.meetmeup.activity.ShowAllNearByUsersActivity;
import com.meetmeup.helper.Utill;

//This class is used for sending money to the event owner.
public class SettingFragement extends Fragment {

	static Context mContext;
	static FragmentManager mFragmentManager;
	static Fragment mfFragment;
	TextView logoutTV,radiusTV;
	public static Fragment getInstance(Context ct, FragmentManager fm) {
		mContext = ct;
		mFragmentManager = fm;
		if (mfFragment == null) {
			mfFragment = new SettingFragement();
		}
		return mfFragment;
	}
	
	@Override
	public void onStart() {
		if(DashBoard.actionBar!=null){
			DashBoard.resetActionBarTitle("Setting");
			DashBoard.rightButton.setVisibility(View.GONE);
			DashBoard.leftButton.setVisibility(View.VISIBLE);
			DashBoard.leftButton.setImageResource(R.drawable.back_btn);
			DashBoard.chatIcon.setVisibility(View.GONE);
		}
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.setting, container, false);
		initializeView(view);
		setOnClickeListeners();
		return view;
	}
	
	void initializeView(View view){
		logoutTV = (TextView) view.findViewById(R.id.logout);
		radiusTV = (TextView) view.findViewById(R.id.Radius);
	}
	// logout function dialog
	private void logoutFun() {
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.final_logout);
		dialog.setTitle("Do you want to logout?");
		Button logout_yes = (Button) dialog.findViewById(R.id.logout_yes);
		Button logout_no = (Button) dialog.findViewById(R.id.logout_no);
		logout_yes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*
				 * SessionManager.clearUserLogin(mContext); ((Activity)
				 * mContext).finish();
				 */
				onLogout();
				dialog.dismiss();
			}
		});

		logout_no.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	private void radiusFun() {
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.radius_dialogue);
		dialog.setTitle("radius for near by app,in Km.");
		Button ok = (Button) dialog.findViewById(R.id.ok);
		final EditText radiusET = (EditText)dialog.findViewById(R.id.radiusET);
		radiusET.setText(Utill.getRadius(mContext));
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			    String radius = radiusET.getText().toString();
			    Utill.setRadius(mContext, radius);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	
	// when clicked on logout.all data from shared preference will be cleared.
	public void onLogout() {
		Utill.removeUserPreference(mContext);
		LoginActivity.callFacebookLogout(mContext);
		
		getActivity().finish();
		/*Intent intent = new Intent(mActivity,LoginActivity.class);
		startActivity(intent);*/
	}

	public void setOnClickeListeners() {
		
		DashBoard.leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mFragmentManager.getBackStackEntryCount() > 0) {
					mFragmentManager.popBackStack();
				}
			}
		});
		logoutTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logoutFun();
			}
		});
		radiusTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				radiusFun();
			}
		});
	}

	
}
