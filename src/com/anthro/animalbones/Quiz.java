package com.anthro.animalbones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {
	
	public Question currentQuestion;
	public Animal animal;
	public int numberOfOptions;
	public int numberOfQuestionsAnswered;
	
	public Quiz(Animal animal) {
		this.animal = animal;
		getNextQuestion();
		numberOfOptions = 4;
		numberOfQuestionsAnswered = 0;
	}
	
	public boolean isCorrect(String answer) {
		return currentQuestion.isCorrect(answer);
	}
	
	public void getNextQuestion() {
		String bone = animal.getRandomBone();
		if (currentQuestion != null) {
			while (bone == currentQuestion.correctAnswer) {
				bone = animal.getRandomBone();
			}
		}
		currentQuestion = new Question(bone, animal, numberOfOptions);
	}
	
	public List<String> getAnswerList() {
		// Create list with correct and wrong answers.
		List<String> answers = new ArrayList<String>(currentQuestion.wrongAnswers);
		answers.add(currentQuestion.correctAnswer);
		
		// Shuffle the list
		Collections.shuffle(answers);
		
		return answers;
	}
	
}
