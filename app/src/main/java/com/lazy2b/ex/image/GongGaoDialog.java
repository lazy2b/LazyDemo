package com.lazy2b.ex.image;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lazy2b.app.R;

public class GongGaoDialog extends Dialog {

	private TextView tv_message;

	public GongGaoDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getContext().setTheme(android.R.style.Theme_InputMethod);
		setContentView(R.layout.dialog_gonggao);
		tv_message = (TextView) getWindow().findViewById(R.id.tv_message);
		ImageView im = (ImageView) getWindow().findViewById(R.id.im_exit);
		im.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	public void setMessage(String message) {
		tv_message.setText(message);
	}

}