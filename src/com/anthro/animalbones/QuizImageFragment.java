package com.anthro.animalbones;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class QuizImageFragment extends Fragment {
	
	QuizImageListener imageListener;
	
	public interface QuizImageListener {

	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            imageListener = (QuizImageListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement QuizImageListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_quiz_image, container, false);
		
		return layout;
	}

}
