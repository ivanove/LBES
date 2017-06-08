package com.lbes.application.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Beans.Favoris;
import com.lbes.application.R;
import com.lbes.application.utils.Utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by nizar on 15/07/16.
 */
public class FavorisAdapter extends BaseAdapter {

   private MainActivity mainActivity;
   private List<Favoris> favorises;

    public FavorisAdapter(MainActivity mainActivity, List<Favoris> favoris) {
        this.mainActivity = mainActivity;
        favorises = favoris;
    }

    @Override
    public int getCount() {
        return favorises.size();
    }

    @Override
    public Object getItem(int i) {
        return favorises.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        holder.save_clothes.setImageDrawable(mainActivity.getDrawable(R.drawable.delete_clean));
        holder.save_clothes.setColorFilter(ContextCompat.getColor(mainActivity,R.color.bleuDark));
        holder.pic.setImageResource(favorises.get(i).getImage());
        holder.name.setText(favorises.get(i).getName());
        holder.save_clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm r = Realm.getInstance(mainActivity);
                r.beginTransaction();
               Favoris f =  r.where(com.lbes.application.Beans.Favoris.class).equalTo("id",favorises.get(i).getId()).findFirst();
                if(f != null) {
                    f.removeFromRealm();

                    notifyDataSetChanged();
                }
                r.commitTransaction();
                Utils.SnackBar("Element supprimé !",view, mainActivity);
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
