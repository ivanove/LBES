package com.lbes.application.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lbes.application.Beans.ApplicationBean;
import com.lbes.application.Beans.Coordinates;
import com.lbes.application.Beans.ElementSwipe;
import com.lbes.application.Beans.Home_swipe;
import com.lbes.application.Beans.Location;
import com.lbes.application.Beans.Parameters;
import com.lbes.application.Beans.Parameters_swipe;
import com.lbes.application.R;
import com.lbes.application.utils.ConnectionDetector;
import com.lbes.application.utils.Utils;
import com.lbes.application.utils.Utils1;

import io.realm.Realm;


import org.json.JSONObject;


public class SplashActivity extends FragmentActivity {


    public static com.android.volley.RequestQueue mRequestQueue;
    Realm realm;
    private parsejson pj;
    private String TAG = "SplashActivity";
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils1.activity = this;
        ConnectionDetector  cd = new ConnectionDetector(getApplicationContext());
        if(cd.isConnectingToInternet()) {
            if (realm != null) {
                realm.close();
                Realm.deleteRealmFile(getApplicationContext());
            }

            progressBar = (ProgressBar) findViewById(R.id.progress);

            Log.e(" ===>", "First run !");
            pj = new parsejson();
            pj.execute();
        }
        else{
            Toast.makeText(getApplicationContext(), "Not connected to internet",Toast.LENGTH_SHORT).show();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },800);
        }



    }
         class parsejson extends AsyncTask<Void,Integer, Void> {
             JSONObject jsonObject;

             public parsejson(/*JSONObject jsonObject*/) {
                 /*this.jsonObject = jsonObject;*/
             }

             @Override
             protected void onPreExecute() {

                 Realm r =  Realm.getInstance(getApplicationContext());
                 r.beginTransaction();
                 r.where(ElementSwipe.class).findAll().clear();
                 r.where(Home_swipe.class).findAll().clear();
                 r.where(Location.class).findAll().clear();
                 r.where(Parameters.class).findAll().clear();
                 r.where(Parameters_swipe.class).findAll().clear();
                 r.where(Coordinates.class).findAll().clear();
                 r.commitTransaction();
                 super.onPreExecute();


             }

             @Override
             protected Void doInBackground(Void ... objects) {
                 realm =  Realm.getInstance(getApplicationContext());

                 realm.beginTransaction();
                 realm.createOrUpdateObjectFromJson(ApplicationBean.class, Utils.retreiveJsonFromAssetsFile("data.json", getApplicationContext()));
                 realm.commitTransaction();



                 return null;
             }

             @Override
             protected void onProgressUpdate(Integer... values) {
                 super.onProgressUpdate(values);
                 progressBar.setProgress(values[0]);
             }

             @Override
             protected void onPostExecute(Void aVoid) {
                 super.onPostExecute(aVoid);
                 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(i);
                 finish();
             }
         }
         }

