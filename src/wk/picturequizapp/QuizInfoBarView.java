package wk.picturequizapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizInfoBarView extends LinearLayout {
	
	private TextView titleView;					// Animal name
	private TextView responseView;				// Correct/incorrect view
	private TextView numCorrectAnswersView;		
	private boolean correctDrawn;
	public CorrectAnswerListener answerListener;
	
	public interface CorrectAnswerListener {
		void goToNextQuestion();
		public void setNumberView();
		public void setAnimalText();
	}

	public QuizInfoBarView(Context context) {
		super(context);
		init(context);
	}
	
	public QuizInfoBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
	
	private void init(Context context) {
		try {
            answerListener = (CorrectAnswerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CorrectAnswerListener");
        }
		
		titleView = new TextView(context);
		
		LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1f);
		titleView.setPadding(20, 0, 0, 0);
		titleView.setLayoutParams(params);
		titleView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		addView(titleView);
		
		responseView = new TextView(context);
		
		responseView.setLayoutParams(params);
		responseView.setGravity(Gravity.CENTER);
		responseView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				disableCorrectAnswerView();
				answerListener.goToNextQuestion();
			}
		});
		responseView.setClickable(false);
		addView(responseView);
		
		numCorrectAnswersView = new TextView(context);
		numCorrectAnswersView.setPadding(0, 0, 20, 0);
		numCorrectAnswersView.setLayoutParams(params);
		numCorrectAnswersView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		addView(numCorrectAnswersView);
		
		correctDrawn = false;
	}
	
	public void setResponse(boolean isCorrect) {
		correctDrawn = isCorrect;
		if (isCorrect) {
			responseView.setText("Correct!");
			drawCorrectAnswerView();
		} else {
			responseView.setText("Incorrect!");
			drawIncorrectAnswerView();
		}
	}
	
	private void drawIncorrectAnswerView() {
		int redIncorrectColour = getResources().getColor(R.color.redIncorrect);
		responseView.setBackgroundColor(redIncorrectColour);
		
	}

	private void drawCorrectAnswerView() {
		int correctGreenColour = getResources().getColor(R.color.lightGreenCorrect);
		responseView.setClickable(true);
		responseView.setBackgroundColor(correctGreenColour);
	}
	
	public void disableCorrectAnswerView() {
		responseView.setBackgroundColor(0000000000);
		responseView.setClickable(false);
		responseView.setText("");
		correctDrawn = false;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public boolean isCorrectDrawn() {
		return correctDrawn;
	}
	
	public void setNumberOfAnswers(int number) {
		numCorrectAnswersView.setText("" + number);
	}

	public void setAnimalText(String animalName) {
		titleView.setText(animalName);
	}
	
}
