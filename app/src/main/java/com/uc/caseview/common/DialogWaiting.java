package com.uc.caseview.common;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.uc.caseview.R;

public class DialogWaiting {
	

	private Dialog mDialog = null;
	private TextView mTextView = null;
	
	public DialogWaiting(Context context) {
		mDialog = new Dialog(context, R.style.dialog_translucent);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mDialog.setContentView(R.layout.dialog_waiting);
		mTextView = (TextView)mDialog.findViewById(R.id.waittingText);
	}
	
	
	public void show() {
		mTextView.setText("");
		mDialog.show();
	}
	
	public void show(String hint) {
		mTextView.setText(hint);
		mDialog.show();
	}
	
	public void show(int hint) {
		mTextView.setText(hint);
		mDialog.show();
	}
	
	public void dismiss() {
		mDialog.dismiss();
	}

	public boolean isShowing() {
		return mDialog.isShowing();
	}
}
