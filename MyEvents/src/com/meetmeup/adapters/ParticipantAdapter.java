package com.meetmeup.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.meetmeup.activity.R;
import com.meetmeup.bean.ParticipantsBean;
//This class is used for showing List All the event's of current user.
public class ParticipantAdapter extends BaseAdapter {
	ViewHolder mHolder;
	LayoutInflater inflator;
	View view;
	Context mContext;
	ListView listView;
	ParticipantAdapter adapter;
	ArrayList<ParticipantsBean> list;

	public ParticipantAdapter(Context context, ArrayList<ParticipantsBean> l) {
		mContext = context;
		inflator = LayoutInflater.from(mContext);
		adapter = this;
		list = l;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		view = convertView;
		if (view == null) {
			view = inflator.inflate(R.layout.participants_view, parent, false);
			mHolder = new ViewHolder();
			mHolder.name = (TextView) view.findViewById(R.id.name);
			mHolder.status = (TextView) view.findViewById(R.id.status);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		ParticipantsBean bean = list.get(position);
		mHolder.name.setText(bean.getUser_fname()+" "+bean.getUser_lname());
		String Status  = bean.getStatus();
		if(Status.equalsIgnoreCase("1"))
			mHolder.status.setText("Accept");
		else if(Status.equalsIgnoreCase("2"))
			mHolder.status.setText("Reject");
		else 
			mHolder.status.setText("Pending");
		
		
		return view;
	}

	public class ViewHolder {
		public TextView name, status;
	}
}
