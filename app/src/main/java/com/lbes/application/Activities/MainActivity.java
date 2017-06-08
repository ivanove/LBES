package com.lbes.application.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lbes.application.Beans.Coordinates;
import com.lbes.application.Fragments.ClothesFragment;
import com.lbes.application.Fragments.FavorisFragment;
import com.lbes.application.Fragments.SettingsFragment;
import com.lbes.application.Fragments.SwipperFragment;
import com.lbes.application.Fragments.MapFragment;
import com.lbes.application.MyApplication;
import com.lbes.application.R;
import com.lbes.application.Widgets.slidingMenu.SlidingMenu;
import com.lbes.application.utils.Colors;
import com.lbes.application.utils.Utils;
import com.lbes.application.utils.InfoActivity;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import android.app.AlertDialog;
public class MainActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {


    public String bodyFragment;
    private SlidingMenu menu;
    private View shadow;
    public static short positionNav = -1;
    public static Typeface FONT_REGULAR;
    public static Typeface FONT_BOLD;
    public static Typeface FONT_TITLE;
    public static Typeface FONT_ITALIC;
    public static Typeface FONT_BODY;
    public Colors colors;
    public static InfoActivity infoActivity;
    public Bundle extras;
    private boolean isTablet;
    private Realm realm;
    public String regid;
    public MediaPlayer player;
    private GoogleApiClient googleApiClient;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        isTablet = Utils.isTablet(getApplicationContext());
        /* fixer orientation*/

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /***/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
            googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).addApi(AppIndex.API).build();

        }

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).addApi(AppIndex.API).build();

        realm = Realm.getInstance(getApplicationContext());


        if (isTablet) {

        } else {
                setContentView(R.layout.bottom_frame_container);
        }

        Map<String, Object> map = (HashMap<String, Object>) getLastCustomNonConfigurationInstance();
        String type = null;
        if (map == null || map.isEmpty()) {

            Log.e(" swipperFragment :", " Yes it is " + " ");
            SwipperFragment swipperFragment = new SwipperFragment();
            bodyFragment = "SwipperFragment";
            swipperFragment.setArguments(extras);
            ((MyApplication) getApplication()).setFragment(MainActivity.this, swipperFragment, R.id.fragment_container);


        }else {

        }

        menu = new SlidingMenu(MainActivity.this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);

        menu.setSlidingEnabled(false);
        menu.setFadeDegree(0.25f);
        menu.setMenu(R.layout.selection_favorites_view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.widthPixels > metrics.heightPixels) {
            menu.setBehindOffset(metrics.widthPixels - (metrics.widthPixels / 2));
        } else {
            menu.setBehindOffset(metrics.widthPixels - (metrics.heightPixels / 2));
        }

        if (isTablet)
            menu.setBehindWidthRes(R.dimen.menu_width_res);
        else
            menu.setBehindWidthRes(R.dimen.menu_width_res_phone);


        ImageView Homebutton = (ImageView) findViewById(R.id.menu_home_button);
        shadow = findViewById(R.id.shadowview);
        Homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMenu();
            }
        });
        shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
            }
        });




        findViewById(R.id.open_favoris).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                FavorisFragment ag = new FavorisFragment();
                ((MyApplication)getApplication()).setFragment(MainActivity.this, ag, R.id.fragment_container);

            }
        });
        findViewById(R.id.open_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                SwipperFragment ag = new SwipperFragment();
                ((MyApplication)getApplication()).setFragment(MainActivity.this, ag, R.id.fragment_container);

            }
        });
        findViewById(R.id.open_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();

                MapFragment ag = new MapFragment();
                ((MyApplication)getApplication()).setFragment(MainActivity.this, ag, R.id.fragment_container);
            }
        });
        findViewById(R.id.open_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();

                SettingsFragment ag = new SettingsFragment();
                ((MyApplication)getApplication()).setFragment(MainActivity.this, ag, R.id.fragment_container);

            }
        });

        findViewById(R.id.open_clothes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseMenu();
                ClothesFragment ag = new ClothesFragment();
                ((MyApplication)getApplication()).setFragment(MainActivity.this, ag, R.id.fragment_container);

            }
        });

    }

    @Override
    public Map<String, Object> onRetainCustomNonConfigurationInstance() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("body", bodyFragment);
        map.put("EXTRAS", extras);
        return map;
    }



    public void CloseMenu() {
        shadow.setVisibility(View.GONE);

        menu.toggle(true);
    }

    public void OpenMenu() {

        menu.showMenu(true);
        shadow.setVisibility(View.VISIBLE);
    }


    private LocationManager mLocationManager;
    private Location lastLocation;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            lastLocation = location;
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return;
            }
            mLocationManager.removeUpdates(mLocationListener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onConnected(Bundle bundle) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if(lastLocation != null) {
                double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
                  //Toast.makeText(this, "lat : " + lat + " lon : " + lon, Toast.LENGTH_SHORT).show();

                Realm r = Realm.getInstance(getApplicationContext());
                r.beginTransaction();
                Coordinates c = new Coordinates();
                c.setLongitude(lon);
                c.setId(0);
                c.setLatitude(lat);
                r.copyToRealmOrUpdate(c);
                r.commitTransaction();
                Coordinates c1 = r.where(Coordinates.class).equalTo("id",0).findFirst();


            }
            else {
                Toast.makeText(this, "Need your location !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Need your location !", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Need your location !", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).addApi(AppIndex.API).build();

                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(getSupportFragmentManager().getBackStackEntryCount() < 1)
        {
            final AlertDialog.Builder b =  new AlertDialog.Builder(this, R.style.MyDialogTheme);
            b.setTitle("LBES");

            b.setMessage("Voulez-vous quitter l'application");
            b.setCancelable(false)
                    .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            b.show();
        }


    }
}