package com.anthro.animalbones;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;

public class Question {

	public String correctAnswer;
	public List<String> wrongAnswers;
	public Bitmap imageForeground;
	public Bitmap imageBackground;
	public boolean isOptionQuestion;
	
	public Question(String bone, Animal animal, int n) {
		isOptionQuestion = new Random().nextBoolean();
		if (isOptionQuestion) {
			correctAnswer = bone;
			wrongAnswers = animal.getWrongAnswers(bone, n);
			imageForeground = animal.getImageBone(bone);
			imageBackground = animal.getImageSection(bone);
		} else {
			correctAnswer = bone;
			wrongAnswers = new ArrayList<String>();
			imageForeground = animal.getImageSection(bone);
			imageBackground = animal.getImageSectionsHotspots(bone);
		}	
	}
	
	public boolean isCorrect(String answer) {
		return answer.equals(correctAnswer);
	}
	
}
