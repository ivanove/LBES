package com.lbes.application.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Adapters.FavorisAdapter;
import com.lbes.application.Beans.Favoris;
import com.lbes.application.R;

import java.util.List;

import io.realm.Realm;


public class FavorisFragment extends Fragment  {



    private Realm realm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realm = Realm.getInstance(getActivity());


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show a toast message about the touched event.
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.heart);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));


    }

    @Override
    public void onResume() {
        super.onResume();
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.heart);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.favoris_fragment, container, false);
        ListView listView = (ListView) v.findViewById(R.id.list_view_clothes_favoris);

        Realm r = Realm.getInstance(getActivity());
        List<Favoris> favorises = r.where(Favoris.class).findAll();
        listView.setAdapter(new FavorisAdapter((MainActivity) getActivity(),favorises));



                return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


}