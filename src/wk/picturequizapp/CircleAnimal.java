package wk.picturequizapp;

import java.util.ArrayList;
import java.util.HashMap;

import com.anthro.animalbones.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;


public class CircleAnimal extends Animal {

	public CircleAnimal(Activity activity) {
		boneNames = new ArrayList<String>();
		boneImages = new HashMap<String, Bitmap>();
		sectionImage = new HashMap<String, Bitmap>();
		sectionImageHotspotButtons = new HashMap<String, Bitmap>();
		boneColours = new HashMap<String, Integer>();
		
		animalName = "Circle";
		
		boneNames.add("circle1");
		boneNames.add("circle2");
		boneNames.add("circle3");
		
		constructSectionImages(activity);
	}
	
	/**
	 * Creates the final images for the quiz question and
	 * adds them to sectionImage.
	 */
	@Override
	public void constructSectionImages(Activity activity) {
		for (String bone : boneNames) {	
			sectionImage.put(bone, BitmapFactory.decodeResource(activity.getResources(), R.drawable.foreground));
			sectionImageHotspotButtons.put(bone, BitmapFactory.decodeResource(activity.getResources(), R.drawable.background));
			
		}
		
		boneImages.put("circle1", BitmapFactory.decodeResource(activity.getResources(), R.drawable.circle1));
		boneImages.put("circle2", BitmapFactory.decodeResource(activity.getResources(), R.drawable.circle2));
		boneImages.put("circle3", BitmapFactory.decodeResource(activity.getResources(), R.drawable.circle3));
		
		boneColours.put("circle1", Color.BLACK);
		boneColours.put("circle2", Color.CYAN);
		boneColours.put("circle3", Color.DKGRAY);
	}

}
