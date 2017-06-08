/**
 * 
 */
package com.lbes.application.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbes.application.Beans.Favoris;
import com.lbes.application.Fragments.DetailsDialogue;
import com.lbes.application.Fragments.FavorisFragment;
import com.lbes.application.MyApplication;
import com.lbes.application.R;
import com.lbes.application.Activities.MainActivity;
import com.lbes.application.utils.Utils;

import java.util.List;

import io.realm.Realm;

/**
 * @author euphordev02
 *
 */
public class ClothesAdapter extends BaseAdapter {

private List<String> names;
private List<Integer> drawables;
	private MainActivity mainActivity;

	public ClothesAdapter(List<String> names, List<Integer> drawables, MainActivity mainActivity) {
		this.names = names;
		this.drawables = drawables;
		this.mainActivity = mainActivity;
	}

	@Override
	public int getCount() {
		return names.size();
	}

	@Override
	public Object getItem(int i) {
		return names.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(final int i, final View view, ViewGroup viewGroup) {
		View row = view;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((MainActivity) mainActivity).getLayoutInflater();
			row = inflater.inflate(R.layout.item, viewGroup, false);
			holder = new ViewHolder();
			holder.pic = (ImageView) row.findViewById(R.id.pic);
			holder.save_clothes = (ImageView) row.findViewById(R.id.save_clothes);
			holder.name = (TextView) row.findViewById(R.id.name);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		holder.name.setText(names.get(i));
		holder.pic.setImageResource(drawables.get(i	));
		row.findViewById(R.id.element).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final android.app.FragmentManager manager = mainActivity.getFragmentManager();
				android.app.Fragment frag = manager.findFragmentByTag("DetailsDialogue");
				if (frag != null) {
					manager.beginTransaction().remove(frag).commit();
				}

				DetailsDialogue detailsDialogue = new DetailsDialogue();
				Bundle b = new Bundle();

				b.putInt("bitmap",drawables.get(i));
				b.putString("name", names.get(i));
				detailsDialogue.setArguments(b);
				detailsDialogue.show(manager,"");

			}
		});
        holder.save_clothes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Realm r = Realm.getInstance(mainActivity);
				r.beginTransaction();
				int id = 1 ;
				Number size =  r.where(Favoris.class).findAll().max("id");
				 id = size.intValue() + 1;
				Favoris newFav = r.createObject(Favoris.class);
				newFav.setId(id);
				newFav.setName(names.get(i));
				newFav.setImage(drawables.get(i));
				r.commitTransaction();
				Handler h = new Handler();
				h.postDelayed(new Runnable() {
					@Override
					public void run() {
						((MyApplication)mainActivity.getApplication()).setFragment(mainActivity,  new FavorisFragment(), R.id.fragment_container);
					}
				},400);
				Utils.SnackBar("Element ajouté",view, mainActivity);
			}
		});
		return row;
	}

	static class ViewHolder {

		ImageView pic;
		ImageView save_clothes;
		TextView name;
	}

}
