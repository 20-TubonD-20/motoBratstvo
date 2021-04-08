package com.example.motobratstvo.ui.map;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.motobratstvo.R;
import com.example.motobratstvo.ScrActivity.ScrActivity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.GeoObjectCollection;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.VisibleRegionUtils;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.mapkit.traffic.TrafficLayer;
import com.yandex.mapkit.traffic.TrafficLevel;
import com.yandex.mapkit.traffic.TrafficListener;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;


public class MapFragment extends Fragment implements TrafficListener, Session.SearchListener, CameraListener {

    private TextView levelText;
    private ImageButton levelIcon;
    private TrafficLevel trafficLevel = null;
    private enum TrafficFreshness {Loading, OK, Expired}

    private TrafficFreshness trafficFreshness;
    private MapView mapView;
    private TrafficLayer traffic;


    private EditText searchEdit;
    private SearchManager searchManager;
    private Session searchSession;


    private void submitQuery(String query) {
        searchSession = searchManager.submit(
                query,
                VisibleRegionUtils.toPolygon(mapView.getMap().getVisibleRegion()),
                new SearchOptions(),
                (Session.SearchListener) this);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** INFLATE AND POSITION **/

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        mapView = v.findViewById(R.id.mapView_);
        mapView.getMap()
                .move(new CameraPosition(new Point(55.648100, 37.652216), 15.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0), null);

        mapView.getMap().addCameraListener(this);
        searchEdit = v.findViewById(R.id.search_edit);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    submitQuery(searchEdit.getText().toString());
                }

                return false;
            }
        });


//        levelText = v.findViewById(R.id.traffic_light_text);
        levelIcon = v.findViewById(R.id.traffic_light);
        levelIcon.setOnClickListener(this::onLightClick);
        traffic = MapKitFactory.getInstance().createTrafficLayer(mapView.getMapWindow());
        traffic.setTrafficVisible(true);
        traffic.addTrafficListener(this);
        updateLevel();

        submitQuery(searchEdit.getText().toString());
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
//        levelText.setText(level);
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

    @Override
    public void onSearchResponse(Response response) {
        MapObjectCollection mapObjects = mapView.getMap().getMapObjects();
        mapObjects.clear();

        for (GeoObjectCollection.Item searchResult : response.getCollection().getChildren()) {
            Point resultLocation = searchResult.getObj().getGeometry().get(0).getPoint();
            if (resultLocation != null) {
                mapObjects.addPlacemark(
                        resultLocation,
                        ImageProvider.fromResource((ScrActivity) getActivity(), R.drawable.search_result));
            }
        }
    }


    @Override
    public void onSearchError(Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText((ScrActivity )getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPositionChanged(
            Map map,
            CameraPosition cameraPosition,
            CameraUpdateReason cameraUpdateReason,
            boolean finished) {
        if (finished) {
            submitQuery(searchEdit.getText().toString());
        }
    }
}