package com.zgb.ticket;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class CustomProgressDialog extends ProgressDialog{

	public CustomProgressDialog(Context context) {
		super(context);
	}
	
	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customprogressdialog);
	}
	
	public static CustomProgressDialog show(Context ctx){
		CustomProgressDialog d = new CustomProgressDialog(ctx);
		Window wd= d.getWindow();
		LayoutParams lp = wd.getAttributes();
		lp.alpha = 0.7f;
		wd.setAttributes(lp);
		d.show();
		return d;
	}
}
