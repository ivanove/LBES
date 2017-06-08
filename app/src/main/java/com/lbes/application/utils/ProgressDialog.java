package com.lbes.application.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lbes.application.R;

/**
 * Created by next on 30/03/17.
 */
public class ProgressDialog {
    public android.app.ProgressDialog processSnackbar(Context context, String s){
        final android.app.ProgressDialog pSnackbar = new android.app.ProgressDialog(context, R.style.CustomDialog) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                ProgressBar progress = (ProgressBar) findViewById(android.R.id.progress);
                LinearLayout bodyLayout = (LinearLayout) progress.getParent();
                TextView messageView = (TextView) bodyLayout.getChildAt(1);

                messageView.setPadding(20,0,0,0);
                LinearLayout.LayoutParams llp =
                        (LinearLayout.LayoutParams) messageView.getLayoutParams();
                llp.width = 0;
                llp.weight = 1;


                bodyLayout.removeAllViews();
                bodyLayout.addView(messageView, llp);
                bodyLayout.addView(progress);
            }
        };
        pSnackbar.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        pSnackbar.setMessage(s);
        pSnackbar.getWindow().setGravity(Gravity.BOTTOM);
        pSnackbar.setCancelable(false);
        return pSnackbar;
    }
}
