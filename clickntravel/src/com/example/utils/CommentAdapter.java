package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.clickntravel.R;

public class CommentAdapter extends BaseAdapter {
	
	private static ArrayList<Comment> commentList;

	private LayoutInflater mInflater;

	public CommentAdapter(Context context, List<Comment> results) {
		commentList = (ArrayList<Comment>) results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return commentList.size();
	}

	public Object getItem(int position) {
		return commentList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.comment_row, null);
			holder = new ViewHolder();
			holder.foodRating = (TextView) convertView.findViewById(R.id.food_row_text_value);
			holder.kindnessRating = (TextView) convertView.findViewById(R.id.kindness_row_text_value);
			holder.punctuallityRating = (TextView) convertView.findViewById(R.id.punctuallity_row_text_value);
			holder.millageRating = (TextView) convertView.findViewById(R.id.millage_program_row_text_value);
			holder.comfortRating = (TextView) convertView.findViewById(R.id.comfort_row_text_value);
			holder.priceQualityRating = (TextView) convertView.findViewById(R.id.price_quality_relation_row_value);
			holder.commentRating = (TextView) convertView.findViewById(R.id.comment_row_text_value);
			holder.generalRating = (TextView) convertView.findViewById(R.id.general_ranking_row_value);
			holder.recommend = (TextView) convertView.findViewById(R.id.recommend_row_text_value);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		holder.foodRating.setText(commentList.get(position).getFoodRating() + "/10");
		holder.kindnessRating.setText(commentList.get(position).getFriendlinessRating() + "/10");
		holder.punctuallityRating.setText(commentList.get(position).getPunctualityRating() + "/10");
		holder.millageRating.setText(commentList.get(position).getMileageProgramRating() + "/10");
		holder.comfortRating.setText(commentList.get(position).getComfortRating() + "/10");
		holder.priceQualityRating.setText(commentList.get(position).getQualityPriceRating() + "/10");
		holder.commentRating.setText(commentList.get(position).getComments());
		
		if(commentList.get(position).getYesRecommend() == R.string.yes)
			holder.recommend.setTextColor(Color.rgb(0, 140, 0));
		else
			holder.recommend.setTextColor(Color.rgb(178, 0, 0));
		
		holder.generalRating.setText(commentList.get(position).getOverallRating() + "");
		holder.recommend.setText(commentList.get(position).getYesRecommend());

		return convertView;
	}

	static class ViewHolder {
		TextView foodRating;
		TextView kindnessRating;
		TextView punctuallityRating;
		TextView millageRating;
		TextView comfortRating;
		TextView priceQualityRating;
		TextView commentRating;
		TextView generalRating;
		TextView recommend;
	}
}
