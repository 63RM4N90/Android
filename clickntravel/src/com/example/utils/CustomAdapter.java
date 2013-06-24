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
import com.example.handlers.ImageHandler;
import com.example.handlers.StatusHandler;

public class CustomAdapter extends BaseAdapter {
	
	private static ArrayList<AddedFlight> addedFlightsList;

	private LayoutInflater mInflater;

	public CustomAdapter(Context context, List<AddedFlight> results) {
		addedFlightsList = (ArrayList<AddedFlight>) results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return addedFlightsList.size();
	}

	public Object getItem(int position) {
		return addedFlightsList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		ImageHandler ih = new ImageHandler();
		StatusHandler sh = new StatusHandler();
		String departure = addedFlightsList.get(position).getDeparture().getCityName();
		String arrival = addedFlightsList.get(position).getArrival().getCityName();
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.my_flights_row, null);
			holder = new ViewHolder();
			holder.airlineImg = (ImageView) convertView.findViewById(R.id.airline_image_row);
			holder.citiesTxt = (TextView) convertView.findViewById(R.id.cities_row);
			holder.statusTxt = (TextView) convertView.findViewById(R.id.status_row);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.airlineImg.setImageResource((ih.getImage(addedFlightsList.get(position).getAirline().getName())));
		holder.citiesTxt.setText(R.string.flight);
		holder.citiesTxt.append(" " + addedFlightsList.get(position).getFlightNumber() + "  |  " + getCityFromString(departure) + " - " + getCityFromString(arrival));
		holder.statusTxt.setTextColor(sh.getStatusColor(addedFlightsList.get(position).getFlightStatus()));
		holder.statusTxt.setText(sh.getStatus(addedFlightsList.get(position).getFlightStatus()));

		return convertView;
	}
	
	private String getCityFromString(String s) {
		return s.substring(0, s.indexOf(','));
	}

	static class ViewHolder {
		ImageView airlineImg;
		TextView citiesTxt;
		TextView statusTxt;
	}
}
