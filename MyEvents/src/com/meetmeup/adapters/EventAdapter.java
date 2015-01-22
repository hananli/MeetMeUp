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
import com.meetmeup.bean.EventsBean;
//This class is used for showing List All the event's of current user.
public class EventAdapter extends BaseAdapter {
	ViewHolder mHolder;
	LayoutInflater inflator;
	View view;
	Context mContext;
	ListView listView;
	EventAdapter adapter;
	ArrayList<EventsBean> list;

	public EventAdapter(Context context, ArrayList<EventsBean> l) {
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
			view = inflator.inflate(R.layout.listview_itemview, parent, false);
			mHolder = new ViewHolder();
			mHolder.eventName = (TextView) view.findViewById(R.id.event_name);
			mHolder.createdBy = (TextView) view.findViewById(R.id.created_by);
			mHolder.datetime = (TextView) view.findViewById(R.id.datetime);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		EventsBean bean = list.get(position);
		mHolder.eventName.setText(bean.getEvent_title());
		mHolder.createdBy.setText("By " + bean.getEvent_owner_name());
		mHolder.datetime.setText(bean.getEvent_date() + " "
				+ bean.getEvent_time());

		return view;
	}

	public class ViewHolder {
		public TextView eventName, createdBy, datetime;
	}
}
