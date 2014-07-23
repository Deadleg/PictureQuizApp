package com.anthro.animalbones;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class QuizListFragment extends Fragment {
	
	OnListItemSelectedListener listItemListener;
	AnswersAdapter adapter;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout rlayout = (LinearLayout) inflater.inflate(R.layout.fragment_quiz_list, container, false);
		
		AbsListView absListView = (AbsListView) rlayout.findViewById(R.id.gridViewAnswers);
		
		answers = new ArrayList<String>();
		adapter = new AnswersAdapter(getActivity(), android.R.layout.simple_list_item_1, answers);
		
		absListView.setAdapter(adapter);
		
		absListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				AbsListView view = (AbsListView) parent;
				String item = (String) parent.getItemAtPosition(position);
				view.setItemChecked(position, true);
				listItemListener.answerSelected(item);
			}
		});
		
		return rlayout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		 
		 // Get list of answers. Depends if quiz has started of comes from orientation change.
		 listItemListener.setAnswerList();
	}

}
