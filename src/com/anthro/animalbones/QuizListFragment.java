package com.anthro.animalbones;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuizListFragment extends ListFragment {
	
	OnListItemSelectedListener listItemListener;
	ArrayAdapter<String> adapter;
	List<String> answers;

	public interface OnListItemSelectedListener {
		public void answerSelected(String answer);
		public void setAnswerList();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listItemListener = (OnListItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListItemSelectedListener");
        }
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		answers = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, answers);
		setListAdapter(adapter);
		
		// Get list of answers. Depends if quiz has started of comes from orientation change.
		listItemListener.setAnswerList();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		getListView().setItemChecked(position, true);
		listItemListener.answerSelected(item);
	}

}
