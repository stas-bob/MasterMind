package de.stas.mm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Dialogs {
	public static final int DATE_DIALOG_FROM = 0;
	public final static int DATE_DIALOG_TILL = 1;
	public final static int TIME_DIALOG_FROM = 2;
	public final static int TIME_DIALOG_TILL = 3;
	public final static int CONFIRM = 4;
	
	public static String CONFIRMATION_STRING = "Are you sure you want to delete?";
	public static Runnable CONFIRMATION_POSITIVE = null;
	
	public static AlertDialog getConfirmDeleteDialog(final Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setMessage(CONFIRMATION_STRING)
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   if (CONFIRMATION_POSITIVE == null) {
		        		   dialog.dismiss();
		        	   } else {
		        		   CONFIRMATION_POSITIVE.run();
		        	   }
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.dismiss();
		           }
		       });
		return builder.create();
	}
	
	public static Dialog verticalDialog(Activity activity, String title, View... views) {
		LinearLayout layout = new LinearLayout(activity);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		for (View view : views) {
			view.setLayoutParams(lp);
			layout.addView(view);
		}
		
		Dialog dialog = new Dialog(activity);
		dialog.setTitle(title);
		dialog.setContentView(layout);
		ViewGroup parent = (ViewGroup)layout.getParent();
		FrameLayout.LayoutParams fll = (android.widget.FrameLayout.LayoutParams)parent.getLayoutParams();
		int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth() - 100;
		fll.width = screenWidth;
		
		return dialog;
	}

	public static Dialog verticalDialogCenter(Activity activity, String title, View... views) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout layout = new RelativeLayout(activity);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL, -1);
		layout.setLayoutParams(lp);
		
		views[0].setLayoutParams(lp);
		int id = 1234;
		views[0].setId(id);
		layout.addView(views[0]);
		for (int i = 1; i < views.length; i++) {
			lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.BELOW, id);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL, -1);
			views[i].setLayoutParams(lp);
			id++;
			views[i].setId(id);
			layout.addView(views[i]);
		}
		
		Dialog dialog = new Dialog(activity);
		dialog.setTitle(title);
		dialog.setContentView(layout);
		
		return dialog;
	}
}
