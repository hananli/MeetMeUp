package com.meetmeup.activity;

import static com.meetmeup.helper.CommonUtilities.SENDER_ID;
import static com.meetmeup.helper.CommonUtilities.displayMessage;
import org.json.JSONObject;


import com.google.android.gcm.GCMBaseIntentService;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class GCMIntentService extends GCMBaseIntentService {

	//public String msg, status, request_id, notification_id, msg_id;
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		displayMessage(context, "Your device registred with GCM");
	}

	/**
	 * Method called on device un registred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
	}

	/**
	 * Method called on Receiving a new message
	 * */
	
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		String message1 = intent.getExtras().getString("price");
		Log.v("Notification", message1);//{"message":"hello"}
	//	displayMessage(context, message1);
		// notifies user
		generateNotification(context, message1);
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private void generateNotification(Context context, String msg) {
		int requestId = (int) System.currentTimeMillis();
		try {
			
	//		JSONObject json = new JSONObject(msg);
	//		String message = json.getString("message");
	//		String screen_id = json.getString("screen_id");
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			int icon = R.drawable.ic_launcher;
			String title = context.getString(R.string.app_name);
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, title, when);
			Intent notificationIntent = new Intent(context,DashBoard.class);
			notificationIntent.putExtra("notificationId", requestId);
	//		notificationIntent.putExtra("screen_id",screen_id);
			notificationIntent.putExtra("menuFragment", "favoritesMenuItem");
			notificationIntent.setAction("myString" + requestId);
			PendingIntent contentIntent = PendingIntent.getActivity(context, requestId, notificationIntent, 0);
			notificationIntent.setData((Uri.parse("mystring" + requestId)));
			notification.setLatestEventInfo(context, title,"MeetMeUP", contentIntent);
			// testing
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			mNotificationManager.notify(0, notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
