package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clickntravel.R;

public class DealAdapter extends BaseAdapter {

	private static ArrayList<Deal> dealsList;
	private LayoutInflater mInflater;

	public DealAdapter(Context context, List<Deal> results) {
		dealsList = (ArrayList<Deal>) results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return dealsList.size();
	}

	public Object getItem(int position) {
		return dealsList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.my_deals_row, null);
			holder = new ViewHolder();
			holder.airlineImg = (ImageView) convertView.findViewById(R.id.airline_deal_image_row);
			holder.dataTxt = (TextView) convertView.findViewById(R.id.data_row);
			convertView.setTag(holder);
			
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.citiesTxt.setText(R.string.deal);
		holder.citiesTxt.append(" "
				+ dealsList.get(position).getFlightNumber() + "  |  "
				+ getCityFromString(dealsList.get(position).getNameFrom()) + " - "
				+ getCityFromString(dealsList.get(position).getNameTo()));

		return convertView;
	}

	private String getCityFromString(String s) {
		return s.substring(0, s.indexOf(','));
	}

	static class ViewHolder {
		TextView dataTxt;
		ImageView airlineImg;
		TextView citiesTxt;
		TextView statusTxt;
	}
}