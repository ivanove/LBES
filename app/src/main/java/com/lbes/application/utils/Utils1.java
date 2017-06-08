package com.lbes.application.utils;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.widget.LinearLayout;


import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Activities.SplashActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Random;

import io.realm.Realm;

/**
 * Created by Euphor on 23/03/2015.
 */
public class Utils1 {
    static boolean[] b = new boolean[]{
            false, false, false, false,
            false, false, false, false,
            false, false, false, false};
    static LinearLayout gridImgDownloaded;
    static int[][] imageRowColumn;
    static final int rowCount = 3;
    static final int columnCount = 4;
    static int count = 0;
    private Context context;
    //private static ProgressWheel pw;
    static int nbr = 10;
    static int j;
    static int id = 0;
    public static Realm realm;
    public static int nbr_cle = 98; //value
    public static Long time;


    public Utils1() {
    }

    public static SplashActivity activity;


    public Utils1(SplashActivity _activity) {
        this.activity = _activity;
    }


    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }


    public static String Download_icon(String link) throws IOException {
        if (!link.contains(".png")) {
            link = link.concat(".png");
        }

        String paths = "";
        String DIRECTORY_IMAGES = activity.getPackageName() + "_images";
        if (link != null && !link.isEmpty() && link.contains("http")) {

            String path = link;

            String[] temp = path.split("/");
            String imageName = temp[temp.length - 1];
            //Log.i("Image Name", imageName);
            path.replaceAll(imageName, "");
            String newFilePath = "";
            int templength = temp.length;
            for (int j = 0; j < templength - 1; j++) {
                newFilePath = newFilePath + temp[j] + "/";
            }

            String root = getFirstWritableDirectory().toString();
            String optimized = "thumbs1";

            File myDir = new File(root + "/" + DIRECTORY_IMAGES + "/" + optimized);
            myDir.mkdirs();

           /* Log.e("path image", myDir.getAbsolutePath()+"/"+imageName);*/

            // Setting up file to write the image to.

            File f = new File(myDir, imageName);


            if (!f.exists()) {
                HttpClient httpclient = new DefaultHttpClient();


                if (link != null && !link.isEmpty()) {

                    link = link.replaceAll(" ", "%20");

                    String[] tmp = link.split("/");
                    String nameImage = tmp[tmp.length - 1];
                    String correctName = URLEncoder.encode(nameImage);
                    link = link.replace(nameImage, correctName);
                    HttpGet httpget = new HttpGet(link);
                    //Log.e(":::::::::::>"+link,"<::::::::");
                    HttpResponse response = httpclient.execute(httpget);
                    System.out.println(response.getStatusLine());
                    HttpEntity entity = response.getEntity();
                    InputStream is = entity.getContent();
                    OutputStream os = new FileOutputStream(f);
                    ImageUtils.CopyStream(is, os);
                    final File file_illustration = f;


                }
            }

            paths = (myDir.toString() + "/" + imageName);
            FileInputStream fis = new FileInputStream(f);
            //  Point point = ImageUtils.getSizeOfImage(fis);

            //    illustration = new Illustration(id, link, paths, point.y, point.x);


        }


        return paths;

    }


    public static File getFirstWritableDirectory() {
        File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (file1.exists()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else if (file2.exists()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else if (file3.exists()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return Environment.getExternalStorageDirectory();
        }

    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    /*----Method to Check GPS is enable or disable ----- */
    public static Boolean displayGpsStatus(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    public static void alertbox(String title, String mymessage, final MainActivity mainActivity
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                mainActivity.startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
