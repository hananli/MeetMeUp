package com.meetmeup.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.meetmeup.bean.UserBean;
import com.meetmeup.fragment.CreateEventFragement;
import com.meetmeup.fragment.EditEventFragement;
import com.meetmeup.fragment.EventDetailFragement;
import com.meetmeup.fragment.HomeFragment;
import com.meetmeup.fragment.MapFragement;
import com.meetmeup.fragment.ParticipantsFragement;
import com.meetmeup.fragment.RatingParticipantsFragement;
import com.meetmeup.fragment.SendMoneyFragement;
import com.meetmeup.fragment.SettingFragement;
import com.meetmeup.fragment.YelloCardFragement;
import com.meetmeup.helper.Utill;

public class DashBoard extends ActionBarActivity {
	Context mContext;
	public static ActionBar actionBar;
	private static FragmentManager fm;
	private static FragmentTransaction ft;
	public static Activity mActivity;
	public static final int FRAGMENT_CREATE_EVENT = 0;
	public static final int FRAGMENT_HOME = 1;
	public static final int FRAGMENT_EVENT_DETAIL = 2;
	public static final int FRAGMENT_SEND_MONEY = 3;
	public static final int FRAGMENT_PARTICIPANTS = 4;
	public static final int FRAGMENT_RATING = 5;
	public static final int FRAGMENT_YELLOW_CARD = 6;
	public static final int FRAGMENT_LOCATION = 7;
	public static final int FRAGMENT_SETTING = 8;
	public static final int FRAGMENT_EDIT_EVENT = 9;
	
	public static ImageButton leftButton,rightButton,chatIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		mActivity = this;
		fm = getSupportFragmentManager();
		getSupportActionBar();
		setActionBarCustom();
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		updateTitle("DashBoard");
		swithFragment(FRAGMENT_HOME);
	}
	
	
	public void setActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_HOME);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
	}
	public void setActionBarCustom(){
		actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbar_bg, null),
		        new ActionBar.LayoutParams(
		                ActionBar.LayoutParams.WRAP_CONTENT,
		                ActionBar.LayoutParams.MATCH_PARENT,
		                Gravity.CENTER
		        )
		);
		leftButton = (ImageButton) actionBar.getCustomView().findViewById(R.id.left_logo);
		rightButton = (ImageButton) actionBar.getCustomView().findViewById(R.id.right_logo);
		chatIcon = (ImageButton) actionBar.getCustomView().findViewById(R.id.group_chat_icon);
		chatIcon.setVisibility(View.GONE);
	}
	
	public static void updateTitle(String title) {
		actionBar.setTitle(title);
		
	}

	public static void showBackEnabled() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	public static void showBackDisabled() {
		actionBar.setDisplayHomeAsUpEnabled(false);
	}
	
	public static void resetActionBarTitle(String title){
		((TextView)actionBar.getCustomView().findViewById(R.id.title)).setText(title);
	}
	
	public void swithFragment(int type) {
		Utill.hideSoftKeyboard(mActivity);
		Fragment mFragment = null;
		ft = fm.beginTransaction();
		switch (type) {
		case FRAGMENT_HOME:
			mFragment = HomeFragment.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			break;
		case FRAGMENT_CREATE_EVENT:
			CreateEventFragement.clearFields();
			
			mFragment = CreateEventFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_EVENT_DETAIL:
				mFragment = EventDetailFragement.getInstance(mContext, fm);
				ft.replace(R.id.container, mFragment, "");
				ft.addToBackStack(null);
				break;
		case FRAGMENT_PARTICIPANTS:
			mFragment = ParticipantsFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_YELLOW_CARD:
			mFragment = YelloCardFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_RATING:
			mFragment = RatingParticipantsFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_SETTING:
			mFragment = SettingFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_EDIT_EVENT:
			mFragment = EditEventFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_SEND_MONEY:
			mFragment = SendMoneyFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case FRAGMENT_LOCATION:
			mFragment = MapFragement.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
				
				
				
/*		case SETTING:
			Fragment myFragment = (Fragment) fm.findFragmentByTag("SETTING1");
			if (myFragment != null && myFragment.isVisible()) {
				String s = myFragment.getTag();
				Log.e("fragment Name", s);
				break;
			}
			mFragment = SettingFragment.getInstance(mContext, fm);
			String backStateName = mFragment.getClass().getName();
			boolean fragmentPopped = fm.popBackStackImmediate(backStateName, 0);
			if (!fragmentPopped) {
				ft.replace(R.id.container, mFragment, "SETTING1");
			}
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(backStateName);
			break;

		case GALLERY:
			mFragment = GalleryHomeFragment.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case CAMERA:
			mFragment = GalleryHomeFragment.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;

		case NOTIFICATION:
			Fragment myNotFragment = (Fragment) fm.findFragmentByTag("NOTIFICATION");
			if (myNotFragment != null && myNotFragment.isVisible()) {
				String s = myNotFragment.getTag();
				Log.e("fragment Name", s);
				break;
			}
			mFragment = NotificationFragment.getInstance(mContext, fm);
			String notificationName = mFragment.getClass().getName();
			boolean mfragmentPopped = fm.popBackStackImmediate(notificationName, 0);
			if (!mfragmentPopped) {
				ft.replace(R.id.container, mFragment, "NOTIFICATION");
			}
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(notificationName);
			break;
		case EMERGENCY:
			Fragment emergencyFragment = (Fragment) fm.findFragmentByTag("EMERGENCY");
			if (emergencyFragment != null && emergencyFragment.isVisible()) {
				String s = emergencyFragment.getTag();
				Log.e("fragment Name", s);
				break;
			}
			mFragment = EmergencyFragment.getInstance(mContext, fm);
			String emergencyName = mFragment.getClass().getName();
			boolean emerfragmentPopped = fm.popBackStackImmediate(emergencyName, 0);
			if (!emerfragmentPopped) {
				ft.replace(R.id.container, mFragment, "EMERGENCY");
			}
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(emergencyName);

			break;
		case TIMELINE:
			Fragment timelineFragment = (Fragment) fm.findFragmentByTag("TIMELINE");
			if (timelineFragment != null && timelineFragment.isVisible()) {
				String s = timelineFragment.getTag();
				Log.e("fragment Name", s);
				break;
			}
			mFragment = TimeLineFragment.getInstance(mContext, fm);
			String timelineName = mFragment.getClass().getName();
			boolean timeLinePopUP = fm.popBackStackImmediate(timelineName, 0);
			if (!timeLinePopUP) {
				ft.replace(R.id.container, mFragment, "TIMELINE");
			}
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(timelineName);

			break;
		case MY_PROFILE:
			mFragment = MyAccount.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case LAB:
			mFragment = LabRecieptFragment.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		case OPTICAL:
			mFragment = opticalFragment.getInstance(mContext, fm);
			ft.replace(R.id.container, mFragment, "");
			ft.addToBackStack(null);
			break;
		// ft.commit();
		default:
			break;*/
		}
		ft.commit();
	}
	public static void removeAllFromBackStack() {
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		UserBean mUser = Utill.getUserPreferance(this);
		String extraString = getIntent().getStringExtra("notificationId");
		Bundle extra = intent.getExtras();
		if (extra != null) {
			int notificationId = extra.getInt("notificationId");
			String screen_id = extra.getString("screen_id");
			if (mUser.getUser_id() != null) {
				removeAllFromBackStack();
			} else {
				finish();
				Intent intent1 = new Intent(DashBoard.this,LoginActivity.class);
				startActivity(intent1);
			}
		}
		super.onNewIntent(intent);
	}
	@Override
	protected void onResume() {
		UserBean mUser = Utill.getUserPreferance(this);
		String extraString = getIntent().getStringExtra("notificationId");
		Bundle extra = getIntent().getExtras();
		if (extra != null) {
			int notificationId = extra.getInt("notificationId");
			String screen_id = extra.getString("screen_id");
			if (mUser.getUser_id() != null && notificationId!=0) {
				removeAllFromBackStack();
			} else {
				finish();
				Intent intent1 = new Intent(DashBoard.this,LoginActivity.class);
				startActivity(intent1);
			}
		}
	
		super.onResume();
	}
}
