package com.meetmeup.fragment;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meetmeup.activity.DashBoard;
import com.meetmeup.activity.MapActivity;
import com.meetmeup.activity.R;
import com.meetmeup.adapters.EventAdapter;
import com.meetmeup.adapters.ParticipantAdapter;
import com.meetmeup.asynch.AcceptAsync;
import com.meetmeup.asynch.DeleteAsync;
import com.meetmeup.asynch.GetEventDetialAsync;
import com.meetmeup.bean.EventsBean;
import com.meetmeup.bean.ParticipantsBean;
import com.meetmeup.bean.UserBean;
import com.meetmeup.helper.Utill;

//This class is used for showing event detail.
public class EventDetailFragement extends Fragment {
	public static int index;
	static Context mContext;
	static FragmentManager mFragmentManager;
	static Fragment mfFragment;
	public static Activity mActivity;
	DashBoard dashBoard;
	EventAdapter adapter;
	TextView event_name, event_address, event_date, event_time;
	TextView event_description, event_createdby, event_type, accept_btn,
			reject_btn, total;
	View view;
	String currentDateTimeString;
	RelativeLayout accept_reject_btn;
	Button sendMoneyBtn;
	ImageButton editBT,deleteBT,participantsBT,chatBT;

	// This method is used for instantiating current class object.
	public static Fragment getInstance(Context ct, FragmentManager fm) {
		mContext = ct;
		mFragmentManager = fm;
		if (mfFragment == null) {
			mfFragment = new EventDetailFragement();
		}
		return mfFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		view = inflater.inflate(R.layout.event_detail_view, container, false);
		dashBoard = (DashBoard) mActivity;
		initializeView();
		setOnClickeListeners();
		getEventDetail();

		Calendar cal = Calendar.getInstance();
		currentDateTimeString = DateFormat.getDateTimeInstance().format(
				new Date());

		return view;
	}

	// This method is used for initializing Views.
	void initializeView() {
		event_name = (TextView) view.findViewById(R.id.event_name);
		event_address = (TextView) view.findViewById(R.id.event_address);
		event_date = (TextView) view.findViewById(R.id.date);
		event_time = (TextView) view.findViewById(R.id.time);
		event_description = (TextView) view.findViewById(R.id.description);
		event_createdby = (TextView) view.findViewById(R.id.created_by);
		event_type = (TextView) view.findViewById(R.id.event_type);
		accept_btn = (TextView) view.findViewById(R.id.accept);
		reject_btn = (TextView) view.findViewById(R.id.reject);
		sendMoneyBtn = (Button) view.findViewById(R.id.send_money);
		accept_reject_btn = (RelativeLayout) view
				.findViewById(R.id.accept_reject);
		total = (TextView) view.findViewById(R.id.total);
		editBT = (ImageButton)view.findViewById(R.id.edit);
		deleteBT = (ImageButton)view.findViewById(R.id.delete);
		participantsBT = (ImageButton)view.findViewById(R.id.participants);
		chatBT = (ImageButton)view.findViewById(R.id.chat);


	}

	// This method is used for set Click listenrs on the views.
	void setOnClickeListeners() {
		event_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (eventBean != null) {
					Intent intent = new Intent(mActivity, MapActivity.class);
					intent.putExtra("lat", eventBean.getEvent_lat());
					intent.putExtra("lon", eventBean.getEvent_eventLong());
					intent.putExtra("name", eventBean.getEvent_title());

					startActivity(intent);
				} else {
					Utill.showToast(mContext, "Some Error in Address.");
				}
			}
		});
		sendMoneyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_SEND_MONEY);
			}
		});
		DashBoard.leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mFragmentManager.getBackStackEntryCount() > 0) {
					mFragmentManager.popBackStack();
				}
			}
		});
		DashBoard.rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Utill.showToast(mContext, "Participants.");
				if(eventBean!=null){
					if(eventBean.getParticipantsList()!=null && eventBean.getParticipantsList().size()>0)
						showParticipants();	
					else
						Utill.showToast(mContext, "No participants invited.");
				}
				else{
					Utill.showToast(mContext, "No participants invited.");
				}
				
			}
		});
		DashBoard.chatIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Utill.showToast(mContext, "Group Chat.");
			}
		});

		accept_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserBean user = Utill.getUserPreferance(mContext);
				if (Utill.isNetworkAvailable(mContext)) {
					MultipartEntity multipart = new MultipartEntity();
					try {

						Log.e("Current date", "current date "
								+ currentDateTimeString);
						String event_id = HomeFragment.eventsList.get(index)
								.getEvent_id();
						multipart.addPart("user_id",
								new StringBody(user.getUser_id()));
						multipart.addPart("event_id", new StringBody(event_id));
						multipart.addPart("current_date", new StringBody(
								currentDateTimeString));
						multipart.addPart("status", new StringBody("1"));

						showProgress();
						new AcceptAsync(mContext, new AcceptRejectListner(),
								multipart).execute();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					Utill.showNetworkError(mContext);
				}
			}
		});

		reject_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserBean user = Utill.getUserPreferance(mContext);
				if (Utill.isNetworkAvailable(mContext)) {
					MultipartEntity multipart = new MultipartEntity();
					try {

						Log.e("Current date", "current date "
								+ currentDateTimeString);
						String event_id = HomeFragment.eventsList.get(index)
								.getEvent_id();
						multipart.addPart("user_id",
								new StringBody(user.getUser_id()));
						multipart.addPart("event_id", new StringBody(event_id));
						multipart.addPart("current_date", new StringBody(
								currentDateTimeString));
						multipart.addPart("status", new StringBody("2"));

						showProgress();
						new AcceptAsync(mContext, new AcceptRejectListner(),
								multipart).execute();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					Utill.showNetworkError(mContext);
				}

			}
		});
		editBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditEventFragement.dataBean = eventBean;
				Utill.showToast(mContext, "Edit");
				if(eventBean!=null){
					dashBoard.swithFragment(DashBoard.FRAGMENT_EDIT_EVENT);
				}else{
					Utill.showToast(mContext, "No data.");
				}
			}
		});
		deleteBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utill.isNetworkAvailable(mContext)){
					UserBean user = Utill.getUserPreferance(mContext);
					MultipartEntity multi = new MultipartEntity();
					try {
						multi.addPart("user_id",new StringBody(user.getUser_id()));
						multi.addPart("event_id",new StringBody(eventBean.getEvent_id()));
						showProgress();
						new DeleteAsync(mContext,new DeleteListener(), multi).execute();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					Utill.showNetworkError(mContext);
				}
			}
		});
		participantsBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				//Utill.showToast(mContext, "Participants.");
				if(eventBean!=null){
					if(eventBean.getParticipantsList()!=null && eventBean.getParticipantsList().size()>0)
						showParticipants();	
					else
						Utill.showToast(mContext, "No participants invited.");
				}
				else{
					Utill.showToast(mContext, "No participants invited.");
				}
				
			
			}
		});
		chatBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Utill.showToast(mContext, "Chat");
			}
		});
		

	}
	
	
	public class DeleteListener{
		public void onSuccess(String msg){
			hideProgress();
			Utill.showToast(mContext, msg);
			if (mFragmentManager.getBackStackEntryCount() > 0) {
				mFragmentManager.popBackStack();
			}
		}
		public void onError(String msg){
			hideProgress();
			Utill.showToast(mContext, msg);
		}
	}

	// This method is used for getting Detail for a perticular Event.
	void getEventDetail() {
		UserBean user = Utill.getUserPreferance(mContext);
		if (user.getUser_id() == null) {
			Utill.showToast(mContext, "Error");
			return;
		}
		if (Utill.isNetworkAvailable(mContext)) {
			MultipartEntity multipart = new MultipartEntity();
			try {
				String event_id = HomeFragment.eventsList.get(index)
						.getEvent_id();
				multipart.addPart("user_id", new StringBody(user.getUser_id()));
				multipart.addPart("event_id", new StringBody(event_id));
				showProgress();
				new GetEventDetialAsync(mContext, new GetEventDetailListener(),
						multipart).execute();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			Utill.showNetworkError(mContext);
		}
	}

	EventsBean eventBean = null;

	// This is the Listener Class used for Notify after getting response from
	// the server.
	public class GetEventDetailListener {
		public void onSuccess(EventsBean bean, String msg) {
			eventBean = bean;
			event_name.setText(bean.getEvent_title());
			event_date.setText(bean.getEvent_date());
			event_time.setText(bean.getEvent_time());
			event_description.setText(bean.getEvent_description());
			event_type.setText(bean.getEvent_type());
			event_createdby.setText(bean.getEvent_createdby());
			event_address.setText(bean.getEvent_Address());
			if (bean.getCollect_money_from_participants().equalsIgnoreCase("1")) {
				sendMoneyBtn.setVisibility(View.VISIBLE);
			} else if (bean.getCollect_money_from_participants()
					.equalsIgnoreCase("0")) {
				sendMoneyBtn.setVisibility(View.GONE);
			}
			
			if (bean.getEvent_type().equalsIgnoreCase("1")) {
				event_type.setText("Private");
			} else {
				event_type.setText("Public");
			}
			UserBean user = Utill.getUserPreferance(mContext);
			if (bean.getEvent_owner_id().equalsIgnoreCase(user.getUser_id())) {
				accept_reject_btn.setVisibility(View.GONE);
				editBT.setVisibility(View.VISIBLE);
				deleteBT.setVisibility(View.VISIBLE);
				
			} else if (bean.getCollect_money_from_participants()
					.equalsIgnoreCase("0")) {
				accept_reject_btn.setVisibility(View.VISIBLE);
				editBT.setVisibility(View.GONE);
				deleteBT.setVisibility(View.GONE);
			}
			if (bean.getParticipantsList() != null) {
				int count = 0;
				total.setText("0/" + bean.getParticipantsList().size());
				for (int i = 0; i < bean.getParticipantsList().size(); i++) {
					ParticipantsBean myB = bean.getParticipantsList().get(i);
					String status = myB.getStatus();
					if (status.equalsIgnoreCase("1")) {
						count++;
					}
				}
				total.setText("Participants : " + count + "/"
						+ bean.getParticipantsList().size());
			} else {
				total.setText("Participants : " + "0/0");
			}

			hideProgress();
		}

		public void onError(String msg) {
			hideProgress();
		}
	}

	public class AcceptRejectListner {
		public void onSuccess(String msg) {
			hideProgress();
			Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
		}

		public void onError(String msg) {
			Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
			hideProgress();
		}

	}

	// This is the call back method.
	@Override
	public void onStart() {
		if (DashBoard.actionBar != null) {
			DashBoard.resetActionBarTitle("Event Detail");
			DashBoard.leftButton.setImageResource(R.drawable.back_btn);
			DashBoard.rightButton
					.setImageResource(R.drawable.participants_icon);
			DashBoard.rightButton.setVisibility(View.GONE);
			DashBoard.chatIcon.setVisibility(View.GONE);
		}
		super.onStart();
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = activity;
		super.onAttach(activity);
	}

	ProgressDialog progress;

	// This method is used for showing progress bar.
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

	// This method is used for hiding progressbar.
	public void hideProgress() {
		if (progress != null) {
			progress.dismiss();
		}
	}

	// this is participants dialogue 
	void showParticipants() {
		try {
			final Dialog dialog = new Dialog(mContext,
					android.R.style.Theme_Black_NoTitleBar);
			LayoutInflater li = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View vi = li.inflate(R.layout.participants, null, false);
			dialog.setContentView(vi);
			LinearLayout linearBack = (LinearLayout) vi
					.findViewById(R.id.backLinear);
			ListView listView = (ListView) vi.findViewById(R.id.list);
			ParticipantAdapter adapter  = new ParticipantAdapter(mContext, eventBean.getParticipantsList());
			listView.setAdapter(adapter);
			linearBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
