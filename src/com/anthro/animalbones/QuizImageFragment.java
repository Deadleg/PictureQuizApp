package com.anthro.animalbones;

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
		void imagePressed(int colour);
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
	        		if (ct.coloursMatch(Color.BLACK, touchColour, tolerance)) {
	        			Log.w("color", "black");
	        			imageListener.imagePressed(Color.BLACK);
	        			imageListener.displayBackgroundImage(backgroundView);
	        			imageListener.displayForegroundImage(foregroundView);
	        		} else if (ct.coloursMatch(Color.CYAN, touchColour, tolerance)) {
	        			Log.w("color", "cyan");
	        			imageListener.imagePressed(Color.CYAN);
	        			imageListener.displayBackgroundImage(backgroundView);
	        			imageListener.displayForegroundImage(foregroundView);
	        		} else if (ct.coloursMatch(Color.DKGRAY, touchColour, tolerance)) {
	        			Log.w("color", "dkgrey");
	        			imageListener.imagePressed(Color.DKGRAY);
	        			imageListener.displayBackgroundImage(backgroundView);
	        			imageListener.displayForegroundImage(foregroundView);
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
