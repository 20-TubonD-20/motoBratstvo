package com.example.motobratstvo.ui.map;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.motobratstvo.R;
import com.example.motobratstvo.ui.RegistrationActivity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.search_layer.SearchLayer;
import com.yandex.mapkit.traffic.TrafficLayer;
import com.yandex.mapkit.traffic.TrafficLevel;
import com.yandex.mapkit.traffic.TrafficListener;


public class MapFragment extends Fragment implements TrafficListener{

    private TextView levelText;
    private ImageButton levelIcon;
    private TrafficLevel trafficLevel = null;
    private enum TrafficFreshness {Loading, OK, Expired};
    private TrafficFreshness trafficFreshness;
    private MapView mapView;
    private TrafficLayer traffic;



    public static Fragment newInstance() {
        return new MapFragment();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** INFLATE AND POSITION **/

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapView_);
        mapView.getMap()
                .move(new CameraPosition(new Point(55.648100, 37.652216), 15.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0), null);

        levelText = v.findViewById(R.id.traffic_light_text);
        levelIcon = v.findViewById(R.id.traffic_light);
        levelIcon.setOnClickListener(view -> {
            onLightClick(view);
        });
        traffic = MapKitFactory.getInstance().createTrafficLayer(mapView.getMapWindow());
        traffic.setTrafficVisible(true);
        traffic.addTrafficListener(this);
        updateLevel();

        return v;
    }



    @Override public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }

    private void updateLevel() {
        int iconId;
        String level = "";
        if (!traffic.isTrafficVisible()) {
            iconId = R.drawable.icon_traffic_light_dark;
        } else if (trafficFreshness == TrafficFreshness.Loading) {
            iconId = R.drawable.icon_traffic_light_violet;
        } else if (trafficFreshness == TrafficFreshness.Expired) {
            iconId = R.drawable.icon_traffic_light_blue;
        } else if (trafficLevel == null) {  // state is fresh but region has no data
            iconId = R.drawable.icon_traffic_light_grey;
        } else {
            switch (trafficLevel.getColor()) {
                case RED: iconId = R.drawable.icon_traffic_light_red; break;
                case GREEN: iconId = R.drawable.icon_traffic_light_green; break;
                case YELLOW: iconId = R.drawable.icon_traffic_light_yellow; break;
                default: iconId = R.drawable.icon_traffic_light_grey; break;
            }
            level = Integer.toString(trafficLevel.getLevel());
        }
        levelIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), iconId));
        levelText.setText(level);
    }

    public void onLightClick(View view) {
        traffic.setTrafficVisible(!traffic.isTrafficVisible());
        updateLevel();
    }


    public void onTrafficChanged(TrafficLevel trafficLevel) {
        this.trafficLevel = trafficLevel;
        this.trafficFreshness = TrafficFreshness.OK;
        updateLevel();
    }

    public void onTrafficLoading() {
        this.trafficLevel = null;
        this.trafficFreshness = TrafficFreshness.Loading;
        updateLevel();
    }

    public void onTrafficExpired() {
        this.trafficLevel = null;
        this.trafficFreshness = TrafficFreshness.Expired;
        updateLevel();
    }
}