package com.anthro.animalbones;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class QuizImageFragment extends Fragment {
	
	QuizImageListener imageListener;
	
	public interface QuizImageListener {
		void displayBackgroundImage(ImageView iView);
		void displayForegroundImage(ImageView iView);
		public boolean imagePressed(int colour);
		public boolean setBarResponse(boolean isCorrect);
		public boolean isResponseViewCorrect();
		public void resetBarView();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            imageListener = (QuizImageListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement QuizImageListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_quiz_image, container, false);
		
		layout.setOnTouchListener(new OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	        	final int action = event.getAction();
	        	final int evX = (int)event.getX();
	        	final int evY = (int)event.getY();
	        	
	        	ImageView foregroundView = (ImageView) QuizImageFragment.this.getView().findViewById(R.id.imageViewForeground);
	    		ImageView backgroundView = (ImageView) QuizImageFragment.this.getView().findViewById(R.id.imageViewBackground);
	    		
	        	switch (action) {
	        	case MotionEvent.ACTION_UP:
	        		int touchColour = getHotspotColour(R.id.imageViewBackground, evX, evY);
	        		ColourTool ct = new ColourTool();
	        		int tolerance = 25;
	        		Log.w("test", "onTouch");
	        		List<Integer> colours = Arrays.asList(Color.BLACK, Color.CYAN, Color.DKGRAY);
	        		
	        		// qView is drawn iff question is answered correctly.
	        		if (imageListener.isResponseViewCorrect()) {
	        			// imageListener.resetBarView();
	        		} else {
	        			boolean answerFound = false;
	        			int i = 0;
	        			while (!answerFound && i < colours.size()) {
	        				if (ct.coloursMatch(colours.get(i), touchColour, tolerance)) {
	        					Log.w("color", "" + colours.get(i));
			        			boolean isCorrect = imageListener.imagePressed(colours.get(i));
			        			imageListener.displayBackgroundImage(backgroundView);
			        			imageListener.displayForegroundImage(foregroundView);
			        			imageListener.setBarResponse(isCorrect);

			        			answerFound = true;
		        			} else {
		        				i++;
		        			}
	        			}
	        		}
	        	}
	        	
	        	return true;
	        }
	    });
		
		ImageView foregroundView = (ImageView) layout.findViewById(R.id.imageViewForeground);
		ImageView backgroundView = (ImageView) layout.findViewById(R.id.imageViewBackground);

		imageListener.displayBackgroundImage(backgroundView);
    	imageListener.displayForegroundImage(foregroundView);
    	
		return layout;
	}
	
	public int getHotspotColour (int hotspotId, int x, int y) {
		ImageView img = (ImageView) getActivity().findViewById (hotspotId);
		img.setDrawingCacheEnabled(true); 
		Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache()); 
		img.setDrawingCacheEnabled(false);
		return hotspots.getPixel(x, y);
	}

}
