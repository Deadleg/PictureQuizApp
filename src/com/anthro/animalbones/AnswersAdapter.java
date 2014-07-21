package com.anthro.animalbones;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnswersAdapter extends ArrayAdapter<String> {

	private List<String> data;
	private LayoutInflater inflater;
	
	public AnswersAdapter(Context context, int layoutResourceId, List<String> data) {
		super(context, layoutResourceId, data);
		inflater = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if (row == null) {
			row = inflater.inflate(R.layout.answer_row, null);
		}
		
		TextView answer = (TextView) row.findViewById(R.id.textViewAnswerString);
		
		answer.setText(data.get(position));
		
		return row;
	}
	
}
