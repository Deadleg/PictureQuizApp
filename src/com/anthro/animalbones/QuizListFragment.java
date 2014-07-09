package com.anthro.animalbones;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuizListFragment extends ListFragment {
	
	OnListItemSelectedListener listItemListener;

	public interface OnListItemSelectedListener {
		public void answerSelected(String answer);
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
		
		String[] values = new String[] { "Pairings", "Standings", "Brackets", "Players"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		getListView().setItemChecked(position, true);
		listItemListener.answerSelected(item);
	}

}
