package com.anthro.animalbones;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

public class QuizActivity extends Activity implements QuizListFragment.OnListItemSelectedListener, QuizImageFragment.QuizImageListener {
	
	QuizImageFragment imageFragment;
	final static String FRAGMENT_IMAGE = "com.anthro.animalbones.FRAGMENT_IMAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		// Initialize fragments
		imageFragment = new QuizImageFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		if (savedInstanceState == null) {
			transaction.add(R.id.fragment_quiz_container, imageFragment, FRAGMENT_IMAGE);
        } else {
        	// Find fragment index. tags and fragment are in same order.
        	String[] tags = {FRAGMENT_IMAGE};
        	List<QuizImageFragment> fragments = Arrays.asList(imageFragment);
        	
        	imageFragment = (QuizImageFragment) getFragmentManager().findFragmentByTag(FRAGMENT_IMAGE);
        	
        	int i = 0;
        	while (!savedInstanceState.getString(MainActivity.FRAGMENT_SHOWN).equals(tags[i])) {
        		i++;
        	}
        	for (int j = 0; j < fragments.size(); j++) {
        		if (j != i && fragments.get(j) != null) {
        			transaction.hide(fragments.get(j));
        		}
        	}
        	transaction.show(fragments.get(i));	
        }
		transaction.commit();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Array of fragment tags
		String tags[] = {FRAGMENT_IMAGE};
		
		// Iterate through the tags list to find the displayed fragment
		int i = 0;
		boolean displayedFragmentFound = false;
		while (!displayedFragmentFound && i < tags.length) {
			Fragment fragment = getFragmentManager().findFragmentByTag(tags[i]);
			if (fragment != null && fragment.isVisible()) {
				savedInstanceState.putString(MainActivity.FRAGMENT_SHOWN, fragment.getTag());
				displayedFragmentFound = true;
			}
			i++;
		}
		
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void answerSelected(String answer) {
		// TODO Auto-generated method stub
		
	}

}
