package com.meetmeup.fragment;

import java.io.UnsupportedEncodingException;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import com.meetmeup.activity.DashBoard;
import com.meetmeup.activity.R;
import com.meetmeup.adapters.FriendAdapter;
import com.meetmeup.adapters.NearByAdapter;
import com.meetmeup.asynch.CreateEventAsync;
import com.meetmeup.asynch.GetFriendListAsync;
import com.meetmeup.asynch.GetNearByAsync;
import com.meetmeup.bean.FriendBean;
import com.meetmeup.bean.UserBean;
import com.meetmeup.helper.AppConstants;
import com.meetmeup.helper.Utill;

//This class is used for Creating an event.
@SuppressLint("NewApi")
public class CreateEventFragement extends Fragment {
	DashBoard dashBoard;
	static Context mContext;
	static FragmentManager mFragmentManager;
	static Fragment mfFragment;
	static EditText eventNameEt, evetnDescEt, eventAddress, evetnDate,
			eventTime, MinparticipantsEt,MaxparticipantsEt;
	Button submitButton;
	public static Activity mActivity;
	TextView collectMoneyText;
	RadioGroup collectMoneyRadioGroup, eventTypeRadioGroup;

	static boolean isClearFields = false;
	public static String EventAddress, eventLat, eventLong;
	ArrayList<FriendBean> fbFriendList = null;
	ArrayList<FriendBean> nearByFriendList = null;

	String startTimeText;
	public static final int DATE = 0;
	public static final int TIME = 1;
	ImageView AddParticipantsIV, ViewParticipantsIV;

	// This method is used for instatiating current class object.
	public static Fragment getInstance(Context ct, FragmentManager fm) {
		mContext = ct;
		mFragmentManager = fm;
		if (mfFragment == null) {
			mfFragment = new CreateEventFragement();
		}
		isClearFields = true;
		return mfFragment;
	}

	@Override
	public void onStart() {
		if (DashBoard.actionBar != null) {
			DashBoard.resetActionBarTitle("Create Event");
			DashBoard.rightButton.setVisibility(View.GONE);
			DashBoard.leftButton.setVisibility(View.VISIBLE);
			DashBoard.chatIcon.setVisibility(View.GONE);
			DashBoard.leftButton.setImageResource(R.drawable.back_btn);
		}
		if (isClearFields) {
			clearFields();
			isClearFields = false;
		}
		eventAddress.setText(EventAddress);
		super.onStart();
	}

	// This method is used whenever this view will be on front.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		dashBoard = (DashBoard) mActivity;
		View view = inflater.inflate(R.layout.create_event, container, false);
		initializeView(view);
		setOnClickeListeners();

		return view;
	}

	// This method is used for initailzing Views.
	void initializeView(View view) {
		eventNameEt = (EditText) view.findViewById(R.id.event_name);
		evetnDescEt = (EditText) view.findViewById(R.id.event_desc);
		eventAddress = (EditText) view.findViewById(R.id.event_add);
		evetnDate = (EditText) view.findViewById(R.id.event_date);
		eventTime = (EditText) view.findViewById(R.id.event_time);

		MinparticipantsEt = (EditText) view
				.findViewById(R.id.no_of_participants);
		submitButton = (Button) view.findViewById(R.id.submit);
		collectMoneyText = (TextView) view
				.findViewById(R.id.collect_money_text);
		collectMoneyRadioGroup = (RadioGroup) view
				.findViewById(R.id.collect_money_radio_group);
		eventTypeRadioGroup = (RadioGroup) view
				.findViewById(R.id.event_type_radio_group);

		AddParticipantsIV = (ImageView) view.findViewById(R.id.a);
		ViewParticipantsIV = (ImageView) view.findViewById(R.id.b);
		MaxparticipantsEt = (EditText) view.findViewById(R.id.no_of_participants_max);
	}

	// This method is used for setting Click listeners for all needed fields.ex.
	// on address,time.
	public void setOnClickeListeners() {
		AddParticipantsIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showFriendsList();
			}
		});
		ViewParticipantsIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Utill.showToast(mContext, "Show Selected User");
				// showFriendsList();
			}
		});

		eventAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dashBoard.swithFragment(DashBoard.FRAGMENT_LOCATION);
				EventAddress = "";
			}
		});
		eventTypeRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.privatee) {
							collectMoneyText.setVisibility(View.VISIBLE);
							collectMoneyRadioGroup.setVisibility(View.VISIBLE);
							// AddParticipants.setVisibility(View.VISIBLE);
						} else {
							collectMoneyText.setVisibility(View.GONE);
							collectMoneyRadioGroup.setVisibility(View.GONE);
							// AddParticipants.setVisibility(View.GONE);
						}
					}
				});

		evetnDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				showDatePopup(DATE);
			}
		});
		eventTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*String date = evetnDate.getText().toString().trim();
				if(Utill.isStringNullOrBlank(date)){
					Utill.showToast(mContext, "Please select date first.");
				}else*/
				{
					showDatePopup(TIME);	
				}
				
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
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cheackValidation();
			}
		});
	}

	// This method is used for validating form data and send event data to the
	// server.
	void cheackValidation() {
		String eventName = eventNameEt.getText().toString().trim();

		String eventDesc = evetnDescEt.getText().toString().trim();
		String eventAdd = eventAddress.getText().toString().trim();
		String eventDate = evetnDate.getText().toString().trim();
		String eventTim = eventTime.getText().toString().trim();
		String minNoOfPart = MinparticipantsEt.getText().toString()
				.trim();
		String maxNoOfPart = MaxparticipantsEt.getText().toString()
		.trim();
		
		String eventtype = "";
		if (eventTypeRadioGroup.getCheckedRadioButtonId() == R.id.publicc) {
			eventtype = "0";
		} else {
			eventtype = "1";
		}

		if (Utill.isStringNullOrBlank(eventName)) {
			Utill.showToast(mContext, "Please enter event name");
		} else if (Utill.isStringNullOrBlank(eventDesc)) {
			Utill.showToast(mContext, "Please enter event description");
		} else if (Utill.isStringNullOrBlank(eventAdd)) {
			Utill.showToast(mContext, "Please enter event address");
		} else if (Utill.isStringNullOrBlank(eventDate)) {
			Utill.showToast(mContext, "Please select date");
		} else if (Utill.isStringNullOrBlank(eventTim)) {
			Utill.showToast(mContext, "Please select time");
		}else if(!Utill.isCorrectDateAndTime(eventDate,eventTim)){
			Utill.showToast(mContext,"Time expired.");
		}
		else if (Utill.isStringNullOrBlank(minNoOfPart)) {
			Utill.showToast(mContext, "Please enter minimum no. of participants.");
		}  
		/*else if (Utill.isStringNullOrBlank(participantsFbIds)) {
			Utill.showToast(mContext, "Please select at least one participant.");
		}*/ else {
			if (Utill.isNetworkAvailable(mContext)) {
				MultipartEntity multipart = new MultipartEntity();
				try {
					UserBean user = Utill.getUserPreferance(mContext);
					multipart.addPart("user_id",
							new StringBody(user.getUser_id()));
					multipart.addPart("event_title", new StringBody(eventName));
					multipart.addPart("address", new StringBody(eventAdd));
					multipart.addPart("event_description", new StringBody(
							eventDesc));
					multipart.addPart("event_type", new StringBody(eventtype));
					multipart.addPart("lat", new StringBody(eventLat));
					multipart.addPart("long", new StringBody(eventLong));
					multipart.addPart("date", new StringBody(eventDate));
					multipart.addPart("time", new StringBody(eventTim));

					multipart.addPart("max_participants", new StringBody(
							minNoOfPart));
					String CollectMoney = "0";
					if (collectMoneyRadioGroup.getCheckedRadioButtonId() == R.id.yes)
						CollectMoney = "1";

					multipart.addPart("collect_money_from_participants",
							new StringBody(CollectMoney));
					Log.e("Participants fb id", participantsFbIds);

					multipart.addPart("participants_fb_id", new StringBody(
							participantsFbIds));

					showProgress();
					new CreateEventAsync(mContext, new CreateEventListener(),
							multipart).execute();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				Utill.showNetworkError(mContext);
			}
		}
	}

	// This method is used for initializing activity instance
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

	// This method is used for hiding progress bar
	public void hideProgress() {
		if (progress != null) {
			progress.dismiss();
		}
	}

	// This is the Listener Class which will be used for notify about an event
	// is successfully Created of not.
	public class CreateEventListener {
		public void onSuccess(String msg) {
			hideProgress();
			clearFields();
			Utill.showToast(mContext, "Event Created Successfully.");
			mFragmentManager.popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		public void onError(String msg) {
			Utill.showToast(mContext, msg);
			hideProgress();
		}
	}

	// This method is used for Clearing All text Fields of the Create event
	// Form.
	public static void clearFields() {
		try {
			eventNameEt.setText("");
			evetnDescEt.setText("");
			eventAddress.setText("");
			evetnDate.setText("");
			eventTime.setText("");
			MinparticipantsEt.setText("");
		} catch (Exception e) {

		}
		CreateEventFragement.EventAddress = "";
	}

	// This method is used for getting formatted time.
	private String getTime(int hr, int min) {
		Time tme = new java.sql.Time(hr, min, 0);
		Format formatter;
		formatter = new SimpleDateFormat("h:mm a");
		return formatter.format(tme);
	}

	// This method is used for showing Date and Time Dialogue Box according to
	// id and Fill date and time according to selection in text Fields.
	public void showDatePopup(final int id) {
		final Dialog dialog = new Dialog(mContext);
		LayoutInflater li = (LayoutInflater) mContext.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = li.inflate(R.layout.popup_date_time, null, false);
		final DatePicker datePick = (DatePicker) vi
				.findViewById(R.id.date_selector_wheel);
		final TimePicker timePick = (TimePicker) vi
				.findViewById(R.id.start_time);
		if (id == DATE) {
			datePick.setVisibility(View.VISIBLE);
			timePick.setVisibility(View.GONE);
		} else {
			datePick.setVisibility(View.GONE);
			timePick.setVisibility(View.VISIBLE);
		}

		final Calendar cal = new GregorianCalendar();
		Button subBtn = (Button) vi.findViewById(R.id.set);
		subBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int day = datePick.getDayOfMonth();
				int year = datePick.getYear();
				int month = datePick.getMonth() + 1;
				String date = day + "-" + month + "-" + year;

				startTimeText = timePick.getCurrentHour() + ":"
						+ timePick.getCurrentMinute();
				startTimeText = getTime(timePick.getCurrentHour(),
						timePick.getCurrentMinute());
				if (id == DATE)
					evetnDate.setText(date);
				else {
					String currentDate = evetnDate.getText().toString().trim();
					if(!Utill.isStringNullOrBlank(currentDate)){
						String tdArray[] = currentDate.split("-");
						
					}
					eventTime.setText(startTimeText);
				}
				dialog.dismiss();
			}
		});

		timePick.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				startTimeText = hourOfDay + ":" + minute;
				Log.e("time",startTimeText);
			}
		});

		long currentTime = cal.getTimeInMillis() - 1000;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			datePick.setMinDate(currentTime);
		} else {
			final int minYear = cal.get(Calendar.YEAR);
			final int minMonth = cal.get(Calendar.MONTH);
			final int minDay = cal.get(Calendar.DAY_OF_MONTH);
			datePick.init(minYear, minMonth, minDay,
					new OnDateChangedListener() {
						public void onDateChanged(DatePicker view, int year,
								int month, int day) {
							Calendar newDate = Calendar.getInstance();
							newDate.set(year, month, day);
							if (cal.after(newDate)) {
								view.init(minYear, minMonth, minDay, this);
							}
						}
					});
		}
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(vi);
		dialog.show();

	}

	// This method is used for showing Friends List and near by people On Dialogue,in this user can person's to invite.
	ListView fbFriendListView;

	public int INVITE_PERSON = -1;
	public static final int FACEBOOK = 0;
	public static final int NEARBY = 1;

	public String participantsFbIds = "";
	TextView facebookBtn;//,mapButton;

	void showFriendsList() {
		try {
			participantsFbIds = "";
			INVITE_PERSON = FACEBOOK;
			fbFriendList = null;
			nearByFriendList = null;
			FriendAdapter.cheackStatus = null;
			NearByAdapter.cheackStatus = null;

			final Dialog dialog = new Dialog(mContext,
					android.R.style.Theme_Black_NoTitleBar);
			LayoutInflater li = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View vi = li.inflate(R.layout.filter_popup, null, false);
			dialog.setContentView(vi);
	//		mapButton = (TextView) vi.findViewById(R.id.map);
			TextView selectAll = (TextView) vi.findViewById(R.id.selectall);
			TextView clearAll = (TextView) vi.findViewById(R.id.clearAll);
			facebookBtn = (TextView) vi.findViewById(R.id.fb_friend_button);
			final TextView nearByBtn = (TextView) vi
					.findViewById(R.id.near_by_button);
			ImageView filterImage = (ImageView) vi
					.findViewById(R.id.filterbutton);
			ImageView doneButton = (ImageView) vi.findViewById(R.id.done_btn);
			fbFriendListView = (ListView) vi.findViewById(R.id.list);
			
		/*	mapButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(nearByFriendList!=null && nearByFriendList.size()>0){
						Intent intent = new Intent(mActivity, MapActivity.class);
						startActivity(intent);
					}else{
						Utill.showToast(mContext,"Currently no user available.");
					}
				}
			});*/
			
			doneButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					getIds();
				}
			});

			filterImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			selectAll.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (INVITE_PERSON == FACEBOOK)
						FriendAdapter.selectAll();
					else if (INVITE_PERSON == NEARBY)
						NearByAdapter.selectAll();
				}
			});

			clearAll.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (INVITE_PERSON == FACEBOOK)
						FriendAdapter.clearAll();
					else if (INVITE_PERSON == NEARBY)
						NearByAdapter.clearAll();
				}
			});
			facebookBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
		//			mapButton.setVisibility(View.GONE);
					fbFriendListView.setVisibility(View.GONE);
					INVITE_PERSON = FACEBOOK;
					nearByBtn.setBackgroundColor(getResources().getColor(
							R.color.unselectedd));
					facebookBtn.setBackgroundColor(getResources().getColor(
							R.color.selectedd));
					if (fbFriendList == null || fbFriendList.size() == 0) {
						showProgress();
						MultipartEntity multipart= new MultipartEntity();
						UserBean user = Utill.getUserPreferance(mContext);						try {
							multipart.addPart("user_id",new StringBody(user.getUser_id()));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						new GetFriendListAsync(mContext,
								new GetFriendListListener(), multipart).execute();
					} else {
						FriendAdapter adapter = new FriendAdapter(mContext,
								fbFriendList);
						fbFriendListView.setAdapter(adapter);
						fbFriendListView.setVisibility(View.VISIBLE);
					}
				}
			});
			nearByBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
		//			mapButton.setVisibility(View.GONE);
					fbFriendListView.setVisibility(View.GONE);
					INVITE_PERSON = NEARBY;
					nearByBtn.setBackgroundColor(getResources().getColor(
							R.color.selectedd));
					facebookBtn.setBackgroundColor(getResources().getColor(
							R.color.unselectedd));
					if (nearByFriendList == null
							|| nearByFriendList.size() == 0) {
						MultipartEntity multipart = new MultipartEntity();
						try {
							UserBean user = Utill.getUserPreferance(mContext);
							multipart.addPart("user_id",
									new StringBody(user.getUser_id()));
							multipart.addPart("lat", new StringBody(
									AppConstants.getLattitude()));
							multipart.addPart("long", new StringBody(
									AppConstants.getLogitude()));
							multipart.addPart("radius", new StringBody(Utill.getRadius(mContext)));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						showProgress();
						new GetNearByAsync(mContext,
								new GetNearByListListener(), multipart)
								.execute();
					} else {
						NearByAdapter adapter = new NearByAdapter(mContext,
								nearByFriendList);
						fbFriendListView.setAdapter(adapter);
						fbFriendListView.setVisibility(View.VISIBLE);
			//			mapButton.setVisibility(View.VISIBLE);
					}
				}
			});

			LinearLayout linearBack = (LinearLayout) vi
					.findViewById(R.id.backLinear);
			linearBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			dialog.show();
			facebookBtn.performClick();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	
	//This is the listener class for notify,is facebook friends list succefully got or not.
	public class GetFriendListListener {
		public void onSuccess(ArrayList<FriendBean> list, String msg) {
			fbFriendList = list;
			FriendAdapter adapter = new FriendAdapter(mContext, list);
			fbFriendListView.setAdapter(adapter);
			fbFriendListView.setVisibility(View.VISIBLE);
			hideProgress();
		}

		public void onError(String msg) {
			hideProgress();
			Utill.showToast(mContext,msg);
			fbFriendListView.setVisibility(View.GONE);
		}
	}

	
	//This is the listener class for notify,is nearby people succefully got or not.
	public class GetNearByListListener {
		public void onSuccess(ArrayList<FriendBean> list, String msg) {
			nearByFriendList = list;
			NearByAdapter adapter = new NearByAdapter(mContext, list);
			fbFriendListView.setAdapter(adapter);
			fbFriendListView.setVisibility(View.VISIBLE);
		//	mapButton.setVisibility(View.VISIBLE);
			hideProgress();
		}

		public void onError(String msg) {
			fbFriendListView.setVisibility(View.GONE);
			hideProgress();
		//	mapButton.setVisibility(View.GONE);
		}
	}

	
	
	//This method is used for getting all the selected people who are selected from the list of facebook and nearby user list.
	String getIds() {
		String ids = "";
		if (NearByAdapter.cheackStatus != null) {
			for (int i = 0; i < NearByAdapter.cheackStatus.length; i++) {
				if (NearByAdapter.cheackStatus[i])
					ids = nearByFriendList.get(i).getFriend_fb_id() + "," + ids;
			}
		}
		if (FriendAdapter.cheackStatus != null) {
			for (int i = 0; i < FriendAdapter.cheackStatus.length; i++) {
				if (FriendAdapter.cheackStatus[i])
					ids = fbFriendList.get(i).getFriend_fb_id() + "," + ids;
			}
		}
		Log.e("Id", ids);
		if (ids.length() > 1) {
			ids = ids.substring(0, ids.length() - 1);
		}
		//Utill.showToast(mContext, ids);
		participantsFbIds = ids;
		return ids;
	}

}
