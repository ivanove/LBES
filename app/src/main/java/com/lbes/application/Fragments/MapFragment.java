package com.lbes.application.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Beans.Coordinates;
import com.lbes.application.MyApplication;
import com.lbes.application.R;

import io.realm.Realm;

/**
 * Created by next on 25/07/16.
 */
public class MapFragment extends  Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    public MarkerOptions marker0;

    private Realm r;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        r =  Realm.getInstance(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.map);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflat and return the layout
        final View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        final LinearLayout bar_save = (LinearLayout) v.findViewById(R.id.bar_save);

        Coordinates coordinates = r.where(Coordinates.class).equalTo("id",0).findFirst();
 
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        final Animation slide_out_bottom = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
        final Animation slide_in_bottom = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom);

         final TextView coordonny;

        coordonny = (TextView) v.findViewById(R.id.coordony);

        final Coordinates c = r.where(Coordinates.class).equalTo("id",0).findFirst();
        if( c != null) {
            coordonny.setText(c.getLatitude() + " " + c.getLongitude());
            if (bar_save != null) {
                if (bar_save.getVisibility() == View.GONE) {
                    bar_save.setVisibility(View.VISIBLE);
                    bar_save.setAnimation(slide_in_bottom);
                }

                bar_save.findViewById(R.id.annuler).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bar_save.setAnimation(slide_out_bottom);
                        bar_save.setVisibility(View.GONE);

                    }
                });
                bar_save.findViewById(R.id.enregistrer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClothesFragment cl = new ClothesFragment();
                        Bundle b = new Bundle();
                        b.putDouble("long",+c.getLongitude());
                        b.putDouble("lat",+c.getLatitude() );
                        cl.setArguments(b);
                        ((MyApplication)getActivity().getApplication()).setFragment(getActivity(), cl,R.id.fragment_container);
                        bar_save.setAnimation(slide_out_bottom);
                        bar_save.setVisibility(View.GONE);


                    }
                });
            }
        }
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

    if(coordinates != null) {
        marker0 = new MarkerOptions().position(new LatLng(coordinates.getLatitude(), coordinates.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Local");
        marker0.draggable(true);

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.e("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(final Marker arg0) {
                // TODO Auto-generated method stub
                Log.e("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                if (coordonny != null)
                    coordonny.setText(arg0.getPosition().latitude + " " + arg0.getPosition().longitude);

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                if (bar_save != null) {
                    if (bar_save.getVisibility() == View.GONE) {
                        bar_save.setVisibility(View.VISIBLE);
                        bar_save.setAnimation(slide_in_bottom);
                    }

                    bar_save.findViewById(R.id.annuler).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bar_save.setAnimation(slide_out_bottom);
                            bar_save.setVisibility(View.GONE);

                        }
                    });
                    bar_save.findViewById(R.id.enregistrer).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           ClothesFragment cl = new ClothesFragment();
                            Bundle b = new Bundle();
                            b.putDouble("long",+arg0.getPosition().longitude);
                            b.putDouble("lat",+arg0.getPosition().latitude );
                            cl.setArguments(b);
                            ((MyApplication)getActivity().getApplication()).setFragment(getActivity(), cl,R.id.fragment_container);
                            bar_save.setAnimation(slide_out_bottom);
                            bar_save.setVisibility(View.GONE);


                        }
                    });
                }
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });
        googleMap.addMarker(marker0);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(coordinates.getLatitude(), coordinates.getLongitude())).zoom(16.5F).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }
    else Toast.makeText(getActivity(),"Activez GPS !", Toast.LENGTH_SHORT).show();

        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.map);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));

        //	MainActivity.scroll = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //	MainActivity.scroll = true;

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}