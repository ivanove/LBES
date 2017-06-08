package com.lbes.application.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lbes.application.Activities.MainActivity;
import com.lbes.application.Adapters.ClothesAdapter;
import com.lbes.application.Beans.Coordinates;
import com.lbes.application.Beans.WeatherApi.Weather;
import com.lbes.application.R;
import com.lbes.application.Widgets.SmallBang;
import com.lbes.application.utils.JSONWeatherParser;
import com.lbes.application.utils.Utils1;
import com.lbes.application.utils.WeatherHttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by next on 22/01/17.
 */
public class ClothesFragment extends Fragment {


    private SmallBang mSmallBang;
    private ImageView mImage;


    JSONWeatherTask task = null;

    private TextView fromVille;
    private TextView toVille;
    private TextView Date;
    private TextView timeDepart;
    private TextView timeArrivee;

    private TextView info;
    private ImageView imgView;
    long temp;
    private String from;
    private String to;
    private String date;
    private String depart;
    private String arrivee;
    private String infos;
    private String prix;

    private TextView time_depart1;
    private TextView prix1;
    private TextView ville_depart;
    private TextView time_arrivee;
    private TextView ville_arrivee;
    private TextView duree;
    private TextView tempValue;
    private ProgressBar progress;
    private LinearLayout band_weather;
    private ImageView save_travel;
    private LinearLayout weatherLayout;
    private LinearLayout progressbarWeather;
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    ListView listView ;
    private Realm realm;
    String link ="http://api.openweathermap.org/data/2.5/weather";
    String keyApi ="&appid=fb874a1d06e1d5190417ac3694a0562c";
    private String url;
    double longAddress;
    double latAddress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realm = Realm.getInstance(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSmallBang = SmallBang.attach2Window(getActivity());



        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.clothes);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));

    }
    @Override
    public void onResume() {
        super.onResume();
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setImageResource(R.drawable.clothes);
        ((ImageView)((MainActivity)getActivity()).findViewById(R.id.logo_bar)).setColorFilter(ContextCompat.getColor(getActivity(), R.color.bleuDark));

        Bundle b = getArguments();
        if(b != null) {
            longAddress = b.getDouble("long");
            latAddress = b.getDouble("lat");
        }

        if(longAddress > 0 && latAddress > 0) {
            url = "lat=" + ((float) latAddress) + "&lon=" + ((float) longAddress) + keyApi;
            Toast.makeText(getActivity(),"Les coordonneés sont changées",Toast.LENGTH_SHORT).show();
        }
        else {
            Coordinates c = realm.where(Coordinates.class).equalTo("id", 0).findFirst();
            if (c != null) {
                url = "lat=" + ((float) c.getLatitude()) + "&lon=" + ((float) c.getLongitude()) + keyApi;

            }
        }

        if (task != null)
            task.cancel(true);
        task = new JSONWeatherTask();
        task.execute(url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_general, container, false);

        Bundle b = getArguments();
        if(b != null) {
            longAddress = b.getDouble("long");
            latAddress = b.getDouble("lat");
        }

        if(longAddress > 0 && latAddress > 0) {
            url = "lat=" + ((float) latAddress) + "&lon=" + ((float) longAddress) + keyApi;
            Toast.makeText(getActivity(),"Les coordonneés sont changées",Toast.LENGTH_SHORT).show();
        }
        else {
            Coordinates c = realm.where(Coordinates.class).equalTo("id", 0).findFirst();
            if (c != null) {
                url = "lat=" + ((float) c.getLatitude()) + "&lon=" + ((float) c.getLongitude()) + keyApi;

            }
        }

        listView = (ListView) view.findViewById(R.id.list_view_clothes);
        tempValue = (TextView) view.findViewById(R.id.degree_temp);
        band_weather = (LinearLayout) view.findViewById(R.id.band_weather);
        imgView = (ImageView) view.findViewById(R.id.condIcon);
        progress = (ProgressBar) view.findViewById(R.id.progressWeather);
        task = new JSONWeatherTask();
        task.execute(url);


        return view;
    }

    public  class JSONWeatherTask extends AsyncTask<String, Integer, Weather> {
        String path = "";

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            if (data != null && !data.isEmpty()) {
                try {
                    weather = JSONWeatherParser.getWeather(data);
                    if (weather != null) {
                        String code = weather.currentCondition.getIcon();

                        try {
                            path = Utils1.Download_icon(code);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (path != null) {
                            if (path.isEmpty())
                                path = IMG_URL.concat(code.concat(".png"));

                        }
                    }

                    // Let's retrieve the icon
                    //weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return weather;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);


            if (weather != null && weather.location != null) {

                progress.setVisibility(View.GONE);
                band_weather.setVisibility(View.VISIBLE);
                imgView.setVisibility(View.VISIBLE);
                 temp = Math.round((weather.temperature.getTemp() - 273.15));
                tempValue.setText("" + temp+ "°C");
                Glide.with((MainActivity) getActivity()).load(path).into(imgView);

                    List<Integer> photosHiver = new ArrayList<>();
                    List<Integer> photosEte = new ArrayList<>();
                    List<String> namesHiver = new ArrayList<>();
                    List<String> namesEte = new ArrayList<>();

                        // load image as Drawable
                        int d = R.drawable.chaussures;
                        int d1 = R.drawable.jacket1;
                        int d2 = R.drawable.jacket;
                        int d3 = R.drawable.jean;
                        int d4 = R.drawable.jean1;
                        int d5 = R.drawable.tshirt1;
                        int d6 = R.drawable.tshirt2;
                        int d7 = R.drawable.tshirt3;

                        photosEte.add(d);
                        photosHiver.add(d);
                        photosHiver.add(d1);
                        photosHiver.add(d2);
                        photosEte.add(d3);
                        photosHiver.add(d3);
                        photosHiver.add(d4);
                        photosEte.add(d4);
                        photosEte.add(d5);
                        photosEte.add(d6);
                        photosEte.add(d7);

                        namesHiver.add("Chaussures");
                        namesEte.add("Chaussures");
                        namesHiver.add("Jacket 1");
                        namesHiver.add("Jacket");
                        namesHiver.add("Jean");
                        namesEte.add("Jean");
                        namesEte.add("Jean 1");
                        namesHiver.add("Jean 1");
                        namesEte.add("T shirt 1");
                        namesEte.add("T shirt 2");
                        namesEte.add("T shirt 3");

                if(temp > 25)
                {
                    listView.setAdapter(new ClothesAdapter(namesEte, photosEte, (MainActivity) getActivity()));
                }
                else
                    listView.setAdapter(new ClothesAdapter(namesHiver, photosHiver, (MainActivity) getActivity()));

            }

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null)
            task.cancel(true);
    }



}