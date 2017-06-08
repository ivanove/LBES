package com.lbes.application.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbes.application.R;

import org.w3c.dom.Text;

/**
 * Created by nizar on 07/06/17.
 */

public class DetailsDialogue extends DialogFragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.details_item, container, false);
        String name = "";
        int bit = 0;
        Bundle b = getArguments();
        if(b != null) {
             name = b.getString("name");
             bit = b.getInt("bitmap");
        }

        ImageView photo_item = (ImageView) v.findViewById(R.id.photo_item);
        TextView title_item = (TextView) v.findViewById(R.id.title_item);
        if( bit > 0 && name != null) {
            photo_item.setImageResource(bit);
            title_item.setText("" + name);

        }
        return v;
    }
}
