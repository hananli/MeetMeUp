package com.meetmeup.helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.meetmeup.bean.UserBean;

public class Utill {
	public static final String loginUser = "loginuser";

	public static boolean isStringNullOrBlank(String str) {
		if (str == null) {
			return true;
		} else if (str.equals("null") || str.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
		return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
	}
	
	public static final void showToast(Context mContext,String msg){
		Toast.makeText(mContext, ""+msg,Toast.LENGTH_SHORT).show();
	}
	public static final void showNetworkError(Context mContext){
		Toast.makeText(mContext,"No network.",Toast.LENGTH_SHORT).show();
	}
	public static void createUserPreference(UserBean user, Context context) {
		SharedPreferences pref = context.getSharedPreferences(loginUser, 0);
		Editor edit = pref.edit();
		edit.putString("fb_id", user.getFb_id());
		edit.putString("email", user.getEmail());
		edit.putString("f_name", user.getF_name());
		edit.putString("l_name", user.getL_name());
		edit.putBoolean("isRegistered", true);
		edit.putString("lattitude", user.getLattitude());
		edit.putString("longitute", user.getLongitute());
		edit.putString("profile_pic_url", user.getProfile_pic_url());
		edit.putString("fb_access_token", user.getFb_access_token());
		edit.putString("fb_token_expire", user.getFb_access_token());
		edit.putString("user_id", user.getUser_id());
		edit.commit();
	}
	public static void setRadius(Context mContext,String rad){
		//SharedPreferences pref  = mContext.getSharedPreferences(Utill.radius, Context.MODE_PRIVATE);
		SharedPreferences pref = mContext.getSharedPreferences("myPref", Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString("radius",rad);
		edit.commit();
	}
	public static String getRadius(Context mContext){
		SharedPreferences pref = mContext.getSharedPreferences("myPref", Context.MODE_PRIVATE);
		String radius = pref.getString("radius",null);
		if(radius == null)
			return "0";
		return radius;
	}
	

	public static UserBean getUserPreferance(Context context) {
		UserBean user = new UserBean();
		SharedPreferences pref = context.getSharedPreferences(loginUser, 0);
		boolean isRegistered = pref.getBoolean("isRegistered", false);
		user.setRegistered(isRegistered);
		if (isRegistered) {
			user.setFb_id(pref.getString("fb_id", null));
			user.setEmail(pref.getString("email",null));
			user.setF_name(pref.getString("f_name",null));
			user.setL_name(pref.getString("l_name",null));
			user.setLattitude(pref.getString("lattitude",null));
			user.setLongitute(pref.getString("longitute",null));
			user.setProfile_pic_url(pref.getString("profile_pic_url",null));
			user.setFb_access_token(pref.getString("fb_access_token",null));
			user.setFb_token_expire(pref.getString("fb_token_expire",null));
			user.setUser_id(pref.getString("user_id", null));
		}
		return user;
	}
	
	public static void removeUserPreference(Context context) {
		SharedPreferences pref  = context.getSharedPreferences(loginUser, 0);
		Editor edit = pref.edit();
		UserBean user = new UserBean();
		edit.putString("fb_id", user.getFb_id());
		edit.putString("email", user.getEmail());
		edit.putString("f_name", user.getF_name());
		edit.putString("l_name", user.getL_name());
		edit.putBoolean("isRegistered", true);
		edit.putString("lattitude", user.getLattitude());
		edit.putString("longitute", user.getLongitute());
		edit.putString("profile_pic_url", user.getProfile_pic_url());
		edit.putString("fb_access_token", user.getFb_access_token());
		edit.putString("fb_token_expire", user.getFb_access_token());
		edit.putString("user_id", user.getUser_id());
		edit.commit();
	}
	public static void hideSoftKeyboard(Activity activity) {
		try {
			activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static String loadJSONFromAsset(Context mContext) {
	    String json = null;
	    try {
	        InputStream is = mContext.getAssets().open("friendlist.json");
	        int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        json = new String(buffer, "UTF-8");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;

	}
	public static Date getDateTime(String date, String time) {
		Calendar cal = Calendar.getInstance();
		int year = 0, monthOfYear = 0, dayOfMonth = 0, hourOfDay = 0, minute = 0, sec = 0;
		String d[] = date.split("-");
		
		year = Integer.parseInt(d[0]);
		monthOfYear = Integer.parseInt(d[1]);
		dayOfMonth = Integer.parseInt(d[2]);
		String t[] = time.split(" ");
		t = t[0].split(":");
		hourOfDay = Integer.parseInt(t[0]);
		minute = Integer.parseInt(t[1]);
	//	sec = Integer.parseInt(t[2]);

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear);
		cal.set(Calendar.DATE, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, sec);
		return cal.getTime();
	}
	
	public static boolean isCorrectDateAndTime(String date,String time) {
		String d[] = date.split("-");//[19, 1, 2015]
		//String t[] = time.split(":");//[1:36 PM]
	//	Date l;
		String s = "";
		
		
		
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
	    SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
	       try {
			Date dat = parseFormat.parse(time);
			System.out.println(parseFormat.format(dat) + " = " + displayFormat.format(dat));
			 s = displayFormat.format(dat);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String t[] = s.split(":");
	       
	      
	/*	try {
		//Date l=	new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-01-01 00:00:00");
			l=	new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(d[2]+"-"+d[1]+"-"+d[0]+" "+t[0]+":"+t[1]+":"+"00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		int hr = Integer.parseInt(t[0]);
		int min =  Integer.parseInt(t[1]);
		int intyear = Integer.parseInt(d[2]);
		int intmonth = Integer.parseInt(d[1]);
		int intday = Integer.parseInt(d[0]);
		
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(intyear, Integer.parseInt(d[1])-1,Integer.parseInt(d[0]), 
				Integer.parseInt(t[0]), Integer.parseInt(t[1]), 0);
		
		long startTime = calendar.getTimeInMillis();
		Log.e("Selected time",""+startTime);
		Log.e("Current time",""+System.currentTimeMillis());
		if(startTime<=System.currentTimeMillis()){
			return false;
			
		}
		else{
			return true;
		}
		
	}
	

}
