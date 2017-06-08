/**
 * 
 */
package com.lbes.application.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lbes.application.R;
import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Beans.ElementSwipe;

import com.lbes.application.utils.Colors;


import java.io.File;

import io.realm.Realm;

/**
 * @author euphordev02
 *
 */
public class SwipePage extends Fragment {

	private String txt;

	private Colors colors;
	private MainActivity mainActivity;
	ElementSwipe elementSwipe;
    public Realm realm;

    @Override
	public void onAttach(Context activity) {

		realm =  Realm.getInstance(getActivity());


        mainActivity = (MainActivity)activity;
		int id_element = getArguments().getInt(EXTRA_MESSAGE);
        elementSwipe = realm.where(ElementSwipe.class).equalTo("id",id_element).findFirst();

        super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.myfragment_layout, container, false);
		if (elementSwipe != null) {
			ImageView image_swipe = (ImageView)layout.findViewById(R.id.image_swipe);
			 {
				Glide.with(this).load(elementSwipe.getImage()).into(image_swipe);
			}
			
		}
		LinearLayout linkHolder = (LinearLayout)layout.findViewById(R.id.linkHolder);
		
		if (elementSwipe.getPage_id()!= 0) {
			linkHolder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {}
			});
		}else {
			linkHolder.setVisibility(View.GONE);
		}

		
		TextView linkTitle = (TextView)layout.findViewById(R.id.linkTitle);
		linkTitle.setText(elementSwipe.getCaption());

		
		return layout;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public SwipePage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static final String EXTRA_MESSAGE = "ID_ELEMENT";
	public static SwipePage newInstance(int id_element) {
		SwipePage f = new SwipePage();
		
		Bundle bdl = new Bundle(1);
	    bdl.putInt(EXTRA_MESSAGE, id_element);
	    f.setArguments(bdl);
	    return f;

	}



}
