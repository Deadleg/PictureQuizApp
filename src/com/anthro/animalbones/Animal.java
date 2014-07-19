package com.anthro.animalbones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

public abstract class Animal {
	
	// Index of name == index of image.
	public List<String> boneNames;
	public HashMap<String, Bitmap> boneImages;
	public HashMap<String, Bitmap> sectionImage;
	public HashMap<String, Bitmap> sectionImageHotspotButtons;
	public HashMap<String, Integer> boneColours;
	
	public List<String> getWrongAnswers(String bone, int n) {
		int boneIndex = boneNames.indexOf(bone);
		List<String> questions = new ArrayList<String>();
		
		Random rand = new Random();
		for (int i = 0; i < n - 1 && i < boneNames.size() - 1; i++) {
			Log.w("i", 1 + "");
			String wrongBone = "";
			while (wrongBone.equals("")) {
				int j = rand.nextInt(boneNames.size());
				if (j != boneIndex) {
					if (!questions.contains(boneNames.get(j)))
						wrongBone = boneNames.get(j);
				}
			}
			questions.add(wrongBone);
		}
		
		return questions;
	}
	
	abstract void constructSectionImages(Activity activity);
	
	public Bitmap getImageSection(String bone) {
		return sectionImage.get(bone);
	}
	
	public Bitmap getImageSectionsHotspots(String bone) {
		return sectionImageHotspotButtons.get(bone);
	}
	
	public Bitmap getImageBone(String bone) {
		return boneImages.get(bone);
	}
	
	public String getRandomBone() {
		String bone = boneNames.get(new Random().nextInt(boneNames.size()));
		return bone;
	}
	
}
