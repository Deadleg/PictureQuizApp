package com.anthro.animalbones;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class QuizActivity extends Activity implements QuizListFragment.OnListItemSelectedListener, QuizImageFragment.QuizImageListener {
	
	QuizImageFragment imageFragment;
	final static String FRAGMENT_IMAGE = "com.anthro.animalbones.FRAGMENT_IMAGE";
	public Quiz quiz;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		// Initialize quiz
		quiz = new Quiz(new CircleAnimal(this));
		
		// Initialize fragments
		imageFragment = new QuizImageFragment();
		
		// Setup fragments
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
		TextView textView = (TextView) imageFragment.getView().findViewById(R.id.textViewAnswerFeedback);
		if (quiz.isCorrect(answer)) {
			setNextQuestion();
			setImages();
			textView.setText("Correct!");
		} else {
			textView.setText("Wrong!");
		}
		
	}
	
	public void setNextQuestion() {
		quiz.getNextQuestion();
			
		List<String> newAnswers = quiz.getAnswerList();
			
		// Get listView.
		ListFragment list = (ListFragment) getFragmentManager().findFragmentById(R.id.fragment_quiz_list);
		ListView listView = (ListView)list.getListView();
		
		// Get the ArrayAdapter.
		@SuppressWarnings("unchecked")
		ArrayAdapter<String> adapter = (ArrayAdapter<String>)listView.getAdapter();
		
		// Set the new list in the array adapter.
		adapter.clear();
		adapter.addAll(newAnswers);
		adapter.notifyDataSetChanged();
	}
	
	private void setImages() {
		ImageView foregroundView = (ImageView) imageFragment.getView().findViewById(R.id.imageViewForeground);
		ImageView backgroundView = (ImageView) imageFragment.getView().findViewById(R.id.imageViewBackground);
		
		displayForegroundImage(foregroundView);
		displayBackgroundImage(backgroundView);
	}
	
	public void displayForegroundImage(ImageView iView) {
		Bitmap foreground = quiz.currentQuestion.imageForeground;
		iView.setImageBitmap(foreground);
	}
	
	public void displayBackgroundImage(ImageView iView) {
		Bitmap background = quiz.currentQuestion.imageBackground;
		iView.setImageBitmap(background);
	}

	@Override
	public void setAnswerList() {
		// TODO Check where question already exists or is new
		setNextQuestion();
	}

	@Override
	public void imagePressed(int colour) {
		if (!quiz.currentQuestion.isOptionQuestion) {
			if (quiz.animal.boneColours.get(quiz.currentQuestion.correctAnswer) == colour) {
				answerSelected(quiz.currentQuestion.correctAnswer);
			} else {
				answerSelected("");
			}
		} else {
			// Do nothing.
		}
		
	}


}
