package com.meetmeup.asynch;

import java.util.ArrayList;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.meetmeup.bean.EventsBean;
import com.meetmeup.bean.ParticipantsBean;
import com.meetmeup.fragment.EventDetailFragement.GetEventDetailListener;
import com.meetmeup.helper.AppConstants;
import com.meetmeup.helper.HttpRequest;
import com.meetmeup.helper.Utill;
import com.meetmeup.helper.WebServices;

//This class is used for Getting Detail of a perticular event according to event id.
public class GetEventDetialAsync extends AsyncTask<Void, Void, String> {

	Context mContext;
	boolean isNetworkError = false;
	GetEventDetailListener mListener;
	MultipartEntity multipart;

	public GetEventDetialAsync(Context ct, GetEventDetailListener listener, MultipartEntity multi) {
		mContext = ct;
		mListener = listener;
		multipart = multi;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			return HttpRequest.post(WebServices.WEB_GET_EVENT_DETAIL, multipart);
		} catch (Exception e) {
			e.printStackTrace();
			isNetworkError = true;
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.e("result", "result : " + result);
		if (isNetworkError) {
			//Utill.showNetworkError(mContext);
			if(mListener!=null)
				mListener.onError(AppConstants.networkError);
		} else {
			if (Utill.isStringNullOrBlank(result)) {
			//	Utill.showServerError(mContext);
				mListener.onError(AppConstants.networkError);
			} else {
				try {
					JSONObject json = new JSONObject(result);
					String response = json.getString("status");
					if (response.equalsIgnoreCase("false")) {
						String msg = json.getString("msg");
						mListener.onError(msg);
					} else if (response.equalsIgnoreCase("true")) {
						
					

						
							EventsBean bean = new EventsBean();
							 
							bean.setEvent_id(json.getString("event_id"));
							bean.setEvent_title(json.getString("event_title"));
							bean.setEvent_date(json.getString("date"));
							bean.setEvent_time(json.getString("time"));
							bean.setEvent_description(json.getString("event_description"));
							bean.setEvent_type(json.getString("event_type"));
							bean.setEvent_createdby(json.getString("created_by"));
							bean.setEvent_Address(json.getString("event_address"));
							bean.setEvent_lat(json.getString("lat"));
							bean.setEvent_eventLong(json.getString("long"));
							bean.setEvent_owner_id(json.getString("event_owner_id"));
							bean.setCollect_money_from_participants(json.getString("collect_money_from_participants"));
							if(json.has("participants_list")){
								ArrayList<ParticipantsBean> plist = new ArrayList<ParticipantsBean>();
								JSONArray tempArray = json.getJSONArray("participants_list");
								for(int i=0;i<tempArray.length();i++){
									JSONObject jObj = tempArray.getJSONObject(i);
									ParticipantsBean pBean = new ParticipantsBean();
									pBean.setUser_id(jObj.getString("user_id"));
									pBean.setUser_fname(jObj.getString("user_fname"));
									pBean.setUser_lname(jObj.getString("user_lname"));
									pBean.setImage(jObj.getString("image"));
									pBean.setStatus(jObj.getString("status"));
									plist.add(pBean);
								}
								bean.setParticipantsList(plist);
							}
							
							
							/*bean.setEvent_owner_name(jsonObj.getString("event_owner_name"));
							bean.setEvent_owner_id(jsonObj.getString("event_owner_id"));*/
							
						
						mListener.onSuccess(bean,"Successfull");
					} else {
						mListener.onError("error");
					}
				} catch (Exception e) {
					e.printStackTrace();
					mListener.onError("");
				}
			}
		}
	}
}