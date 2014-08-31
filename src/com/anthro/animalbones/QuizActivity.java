package com.anthro.animalbones;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

public class QuizActivity extends Activity implements QuizListFragment.OnListItemSelectedListener, QuizImageFragment.QuizImageListener, QuizInfoBarView.CorrectAnswerListener {
	
	QuizImageFragment imageFragment;
	final static String FRAGMENT_IMAGE = "com.anthro.animalbones.FRAGMENT_IMAGE";
	public Quiz quiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		setTitle(R.string.activity_quiz_name);
		
		// Initialize quiz
		quiz = new Quiz(new CircleAnimal(this));
		
		// Initialize fragments
		imageFragment = new QuizImageFragment();
		
		// Set text in infoBarView.
		setNumberView();		// TODO Set number of correct question answered.
		setAnimalText();
		
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
	
	// Called by list fragment
	@Override
	public void answerSelected(String answer) {
		boolean isCorrect = quiz.isCorrect(answer);
		setBarResponse(isCorrect);
	}
	
	public void setNextQuestion() {
		quiz.getNextQuestion();
			
		List<String> newAnswers = quiz.getAnswerList();
			
		// Get listView.
		Fragment list = getFragmentManager().findFragmentById(R.id.fragment_quiz_list);
		AbsListView listView = (AbsListView)list.getView().findViewById(R.id.gridViewAnswers);
		// Get the adapter.
		AnswersAdapter adapter = (AnswersAdapter) listView.getAdapter();
		
		// Set list view to be clickable if it is options question.
		if (quiz.currentQuestion.isOptionQuestion) {
			adapter.setIsClickable(true);
		} else {
			adapter.setIsClickable(false);
		}
		
		// Set the new list in the array adapter.
		adapter.clear();
		adapter.addAll(newAnswers);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void setImages() {
		// Get ImageViews
		ImageView foregroundView = (ImageView) imageFragment.getView().findViewById(R.id.imageViewForeground);
		ImageView backgroundView = (ImageView) imageFragment.getView().findViewById(R.id.imageViewBackground);
		
		// Get the images
		Bitmap foreground = quiz.currentQuestion.imageForeground;
		Bitmap background = quiz.currentQuestion.imageBackground;
		
		// Display the Images
		backgroundView.setImageBitmap(background);
		foregroundView.setImageBitmap(foreground);
		
		if (quiz.currentQuestion.isOptionQuestion) {
			backgroundView.setVisibility(View.VISIBLE);
			foregroundView.bringToFront();
		} else {
			backgroundView.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void setAnswerList() {
		// TODO Check where question already exists or is new
		setNextQuestion();
	}

	// Called by QuizImageFragment
	@Override
	public boolean imagePressed(int colour) {
		if (!quiz.currentQuestion.isOptionQuestion) {
			// Check if answer is correct.
			if (quiz.animal.boneColours.get(quiz.currentQuestion.correctAnswer) == colour) {
				answerSelected(quiz.currentQuestion.correctAnswer);
				return true;
			} else {
				answerSelected("");
			}
		} else {
			// Do nothing.
		}
		return false;
	}

	// Called by QuizInfoBar when green check is clicked.
	@Override
	public void goToNextQuestion() {
		setNextQuestion();
		quiz.numberOfQuestionsAnswered++;
		setImages();
		setNumberView();
	}

	@Override
	public boolean setBarResponse(boolean isCorrect) {
		QuizInfoBarView qView = (QuizInfoBarView) findViewById(R.id.quizInfoView);
		qView.setResponse(isCorrect);
		return false;
	}

	@Override
	public boolean isResponseViewCorrect() {
		QuizInfoBarView qView = (QuizInfoBarView) findViewById(R.id.quizInfoView);
		return qView.isCorrectDrawn();
	}

	@Override
	public void resetBarView() {
		QuizInfoBarView qView = (QuizInfoBarView) findViewById(R.id.quizInfoView);
		qView.disableCorrectAnswerView();
	}

	@Override
	public void setNumberView() {
		QuizInfoBarView bar = (QuizInfoBarView) findViewById(R.id.quizInfoView);
		bar.setNumberOfAnswers(quiz.numberOfQuestionsAnswered);
	}

	@Override
	public void setAnimalText() {
		QuizInfoBarView bar = (QuizInfoBarView) findViewById(R.id.quizInfoView);
		Log.w("test", quiz.animal.animalName);
		bar.setAnimalText(quiz.animal.animalName);
	}

}
