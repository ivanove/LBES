package com.lbes.application.Fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lbes.application.MyApplication;
import com.lbes.application.R;
import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Beans.ElementSwipe;
import com.lbes.application.Beans.Parameters_swipe;

import com.lbes.application.utils.Colors;
import com.lbes.application.utils.Utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

public class SwipperFragment extends Fragment {

	private View view;
	private MyPageAdapter pageAdapter;
	private ViewPager mViewPager;

	private Colors colors;

	private boolean isTablet;
	private List<ElementSwipe> elementSwipes;
	private Parameters_swipe parameters_swipe;
	public Realm realm;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);


	}


	@Override
	public void onAttach(Activity activity) {

		//new AppController(getActivity());
		realm =   Realm.getInstance(getActivity());

		((MainActivity)getActivity()).bodyFragment = "SwipperFragment";
		if(((MainActivity)getActivity()).extras == null)
			((MainActivity)getActivity()).extras = new Bundle();
		int section_id = -1;
		if (getArguments() != null && getArguments().getInt("Section_id")!=0) {
			section_id = getArguments().getInt("Section_id");
			((MainActivity)getActivity()).extras.putInt("Section_id", section_id);
			//id = section_id;
		}
		else if (getArguments() != null && getArguments().getInt("Category_id") != 0)
		{
			int category_id = getArguments().getInt("Category_id");
			((MainActivity)getActivity()).extras.putInt("Category_id", category_id);
			//id = category_id;
		}
		isTablet = Utils.isTablet(activity);
		//time = System.currentTimeMillis();

		super.onAttach(activity);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);

		elementSwipes = new ArrayList<ElementSwipe>();

		elementSwipes = realm.where(ElementSwipe.class).findAll();
		parameters_swipe = realm.where(Parameters_swipe.class).findFirst();//appController.getParameters_swipeDao().queryForId(1);
		List<Fragment> fragments2 = getFragments(elementSwipes);
		//		Collections.reverse(fragments2);
		pageAdapter = new MyPageAdapter(getChildFragmentManager()/*getChildFragmentManager()*/, fragments2);
		((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.home);
		((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));


		super.onCreate(savedInstanceState);
	}

	//	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		try {
			mViewPager.setAdapter(pageAdapter);

			mViewPager.setCurrentItem(0);

			if (elementSwipes.size()>1) {
				pageSwitcher(5, elementSwipes.size());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		    view = new View(getActivity());
			view = inflater.inflate(R.layout.swipper_center_top_layout_smart, container, false);

		buildSwipe();
		view.findViewById(R.id.open_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				MapFragment t = new MapFragment();
				((MyApplication)getActivity().getApplication()).setFragment(getActivity(),t, R.id.fragment_container );

			}

		});
		view.findViewById(R.id.open_favoris).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				FavorisFragment ag = new FavorisFragment();
				((MyApplication)getActivity().getApplication()).setFragment(getActivity(), ag, R.id.fragment_container );

			}
		});
		view.findViewById(R.id.open_settings).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				SettingsFragment ser = new SettingsFragment();
				((MyApplication)getActivity().getApplication()).setFragment(getActivity(), ser, R.id.fragment_container );

			}
		});view.findViewById(R.id.open_clothes).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ClothesFragment ser = new ClothesFragment();
				((MyApplication)getActivity().getApplication()).setFragment(getActivity(), ser, R.id.fragment_container );

			}
		});
		return view;
	}



	public void buildSwipe() {
		mViewPager = (ViewPager)view.findViewById(R.id.pager);

	}



	public Timer timer;
	int page = 0;

	public void pageSwitcher(int seconds, int max_page) {
		timer = new Timer(); // At this line a new Thread will be created
		timer.scheduleAtFixedRate(new RemindTask(max_page), 0, seconds * 1000); // delay
		// in
		// milliseconds
	}

	// this is an inner class...
	class RemindTask extends TimerTask {

		int maxPage;


		@Override
		public void run() {

			// As the TimerTask run on a separate thread from UI thread we have
			// to call runOnUiThread to do work on UI thread.
			if(((MainActivity)getActivity()) == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				((MainActivity)getActivity()).runOnUiThread(new Runnable() {
					public void run() {

						if (page >= maxPage) { // In my case the number of pages are 5
							page = 0;
							mViewPager.setCurrentItem(page++);

						} else {
							mViewPager.setCurrentItem(page++);
						}

					}
					//				}
				});

		}


		public RemindTask(int maxPage) {
			super();
			this.maxPage = maxPage;
		}
	}

	private List<Fragment> getFragments(List<ElementSwipe> elementSwipes){
		List<Fragment> fList = new ArrayList<Fragment>();
		for (int i = 0; i < elementSwipes.size(); i++) {
			fList.add(SwipePage.newInstance(elementSwipes.get(i).getId()));
		}



		return fList;
	}

	private class MyPageAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;

		public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}
		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}


	@Override
	public void onPause() {
		if (timer!=null) {
			timer.cancel();
			//timer.purge();
		}
		super.onPause();
	}

	@Override
	public void onStop() {
		if (timer!=null) {
			Runtime.getRuntime().gc();
			timer.cancel();
			//timer.purge();
		}
		super.onStart();
	}

	@Override
	public void onDestroy(){
		Runtime.getRuntime().gc();
		if (timer!=null) {
			timer.cancel();
		}


		super.onDestroy();
	}

	@Override
	public void onResume() {

		super.onResume();
		((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.home);
		((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));

	}

}
