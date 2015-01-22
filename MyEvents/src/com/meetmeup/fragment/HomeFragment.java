package com.meetmeup.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.Session;
import com.meetmeup.activity.DashBoard;
import com.meetmeup.activity.LoginActivity;
import com.meetmeup.activity.R;
import com.meetmeup.adapters.EventAdapter;
import com.meetmeup.asynch.GetEventsAsync;
import com.meetmeup.bean.EventsBean;
import com.meetmeup.bean.UserBean;
import com.meetmeup.helper.Utill;


//This is the home page.here we are getting all events list,and showing them.
public class HomeFragment extends Fragment {

	static Context mContext;
	static FragmentManager mFragmentManager;
	static Fragment mfFragment;
	public static Activity mActivity;
	DashBoard dashBoard;
	Button eventDetail, sendMoney, Participants, yellowCard, Ratting;
	ListView listView;
	public static ArrayList<EventsBean> eventsList;

	//This method is used for instantiating class object.
	public static Fragment getInstance(Context ct, FragmentManager fm) {
		mContext = ct;
		mFragmentManager = fm;
		if (mfFragment == null) {
			mfFragment = new HomeFragment();
		}
		return mfFragment;
	}

	
	//This method is used for getting list of all events.
	void getEventsList() {
		UserBean user = Utill.getUserPreferance(mContext);
		if (user.getUser_id() == null) {
			Utill.showToast(mContext, "Error");
			return;
		}

		if (Utill.isNetworkAvailable(mContext)) {
			MultipartEntity multipart = new MultipartEntity();
			try {
				showProgress();
				multipart.addPart("user_id", new StringBody(user.getUser_id()));
				new GetEventsAsync(mContext, new GetEventListener(), multipart)
						.execute();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			Utill.showNetworkError(mContext);
		}

	}
//This is the call back method it will be called whenever current screen will be on front.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater
				.inflate(R.layout.event_list_view, container, false);
		dashBoard = (DashBoard) mActivity;
		initializeView(view);
		setOnClickeListeners();
		getEventsList();
		return view;
	}
	//This method is used for intializing all the view.
	void initializeView(View view) {
		eventDetail = (Button) view.findViewById(R.id.eventDetail);
		sendMoney = (Button) view.findViewById(R.id.sendmoney);
		Participants = (Button) view.findViewById(R.id.participants);
		yellowCard = (Button) view.findViewById(R.id.yellow);
		Ratting = (Button) view.findViewById(R.id.ratting);
		listView = (ListView) view.findViewById(R.id.list);
	}
	//This method is used for setting click listener to all the views.
	void setOnClickeListeners() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				EventDetailFragement.index = arg2;
				dashBoard.swithFragment(DashBoard.FRAGMENT_EVENT_DETAIL);
			}
		});

		/*eventDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_EVENT_DETAIL);
			}
		});*/
	/*	sendMoney.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_SEND_MONEY);
			}
		});
		Participants.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_PARTICIPANTS);
			}
		});
		yellowCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_YELLOW_CARD);
			}
		});
		Ratting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_RATING);
			}
		});*/

		DashBoard.rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_CREATE_EVENT);
			}
		});
		DashBoard.leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Utill.showToast(mContext, "Logout");
				dashBoard.swithFragment(DashBoard.FRAGMENT_SETTING);
				//logoutFun();
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = activity;
		super.onAttach(activity);
	}

	@Override
	public void onStart() {
		if (DashBoard.actionBar != null) {
			DashBoard.resetActionBarTitle("Meet Me UP");
			DashBoard.rightButton.setVisibility(View.VISIBLE);
			DashBoard.leftButton.setVisibility(View.VISIBLE);
			DashBoard.leftButton.setImageResource(R.drawable.setting_icon);
			DashBoard.chatIcon.setVisibility(View.GONE);
			DashBoard.rightButton
					.setImageResource(R.drawable.create_event_icon);
		}
		super.onStart();
	}

	//This class is used for notify weather event list got successfully or not. 
	public class GetEventListener {
		public void onSuccess(ArrayList<EventsBean> list, String msg) {
			eventsList = list;
			EventAdapter adapter = new EventAdapter(mContext, eventsList);
			listView.setAdapter(adapter);
			Utill.showToast(mContext, msg);
			hideProgress();
		}

		public void onError(String msg) {
			Utill.showToast(mContext, msg);
			hideProgress();
		}

	}

	ProgressDialog progress;
	//This method is used for showing progress bar
	public void showProgress() {
		try {
			if (progress == null)
				progress = new ProgressDialog(mActivity);
			progress.setMessage("Please Wait..");
			progress.setCancelable(false);
			progress.show();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				progress = new ProgressDialog(mActivity);
				progress.setMessage("Please Wait..");
				progress.setCancelable(false);
				progress.show();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	//This method is used for hiding progress bar.
	public void hideProgress() {
		if (progress != null) {
			progress.dismiss();
		}
	}
}
