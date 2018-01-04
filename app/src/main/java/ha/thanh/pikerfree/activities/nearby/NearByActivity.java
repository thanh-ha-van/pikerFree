package ha.thanh.pikerfree.activities.nearby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.adapters.PostAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomInfoWindowAdapter;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.services.GPSTracker;

public class NearByActivity extends AppCompatActivity
        implements PostAdapter.ItemClickListener,
        GeoQueryEventListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {

    private FirebaseDatabase database;
    private List<Post> postList;
    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;
    private GPSTracker gpsTracker;

    @BindView(R.id.btn_list)
    CustomTextView btnListView;
    @BindView(R.id.btn_map)
    CustomTextView btnMapView;
    @BindView(R.id.rv_posts)
    RecyclerView rvPost;
    @BindView(R.id.tv_search_distance)
    CustomTextView tvDistance;
    @BindView(R.id.mapView)
    MapView mMapView;

    private PostAdapter postAdapter;
    private GoogleMap googleMap;
    private SharedPreferences sharedPreferences;
    private int distance = 10;
    private GeoFire geoFire;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
        ButterKnife.bind(this);
        waitingDialog = new WaitingDialog(this);
        waitingDialog.showDialog();
        initData();
        initView();
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    private void initData() {
        database = FirebaseDatabase.getInstance();
        postList = new ArrayList<>();
        gpsTracker = new GPSTracker(this);
        sharedPreferences = getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        distance = sharedPreferences.getInt(Constants.DISTANCE, 10);
        tvDistance.setText(distance + "");

        ref = database.getReference("geofire");
        geoFire = new GeoFire(ref);
        searchNearBy();

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(Constants.POST_VIEW, Integer.valueOf(marker.getSnippet()));
        startActivity(intent);
    }

    @OnClick(R.id.btn_refresh)

    public void doRefresh() {
        postList.clear();
        googleMap.clear();
        waitingDialog.showDialog();
        searchNearBy();
    }

    @OnClick(R.id.btn_down_distance)
    public void downDistance() {

        distance--;
        tvDistance.setText(distance + "");
    }

    @OnClick(R.id.btn_up_distance)
    public void upDistance() {
        distance++;
        tvDistance.setText(distance + "");
    }

    @OnClick(R.id.btn_list)
    public void onViewListSelected() {

        btnListView.setClickable(false);
        btnMapView.setClickable(true);
        btnMapView.setBackground(getDrawable(R.drawable.bg_rectangle_greeen_bold_right));
        btnListView.setTextColor(getResources().getColor(R.color.white));
        btnMapView.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnListView.setBackground(getDrawable(R.drawable.bg_rectangle_green_bold));
        Animation slidein = AnimationUtils.loadAnimation(this, R.anim.slide_from_right);
        rvPost.setVisibility(View.VISIBLE);
        rvPost.setAnimation(slidein);
        Animation slideout = AnimationUtils.loadAnimation(this, R.anim.slide_to_left);
        mMapView.setAnimation(slideout);
        mMapView.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_map)
    public void onViewMapSelected() {

        btnListView.setClickable(true);
        btnMapView.setClickable(false);
        btnMapView.setBackground(getDrawable(R.drawable.bg_rectangle_white_bold_right));
        btnMapView.setTextColor(getResources().getColor(R.color.white));
        btnListView.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnListView.setBackground(getDrawable(R.drawable.bg_rectangle_white_bold_left));
        Animation slidein = AnimationUtils.loadAnimation(this, R.anim.slide_from_right);
        mMapView.setVisibility(View.VISIBLE);
        mMapView.setAnimation(slidein);
        Animation slideout = AnimationUtils.loadAnimation(this, R.anim.slide_to_left);
        rvPost.setAnimation(slideout);
        rvPost.setVisibility(View.GONE);

    }

    @OnClick(R.id.ic_back)
    public void goback() {
        onBackPressed();
    }

    private void initView() {

        alertDialog = new CustomAlertDialog(this);
        postAdapter = new PostAdapter(this, postList, this, getUserLat(), getUserLng());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPost.setLayoutManager(layoutManager);
        rvPost.setAdapter(postAdapter);

        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.DISTANCE, distance);
        editor.commit();
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.setOnInfoWindowClickListener(this);
        LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            e.getMessage();
        }
    }

    void updateMap() {
        googleMap.clear();
        for (Post post : postList
                ) {
            try {
                CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(this, post);
                googleMap.setInfoWindowAdapter(adapter);
                LatLng sydney = new LatLng(post.getLocation().latitude, post.getLocation().longitude);
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title(post.getTitle())
                        .snippet(post.getPostId() + "")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } catch (Exception e) {
                e.toString();
            }
        }
    }

    private double getUserLat() {
        return gpsTracker.getLatitude();
    }

    private double getUserLng() {
        return gpsTracker.getLongitude();
    }

    @Override
    public void onItemClick(int position) {
        try {
            Intent intent = new Intent(this, PostActivity.class);
            intent.putExtra(Constants.POST_VIEW, Integer.valueOf(postList.get(position).getPostId()));
            startActivity(intent);
        } catch (Exception e) {
            alertDialog.showAlertDialog("Error", "Can not complete your action.");
        }
    }

    private void setTimeOut() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (postList.size() == 0)
                    onNoResult();
            }
        }, 8000);
    }

    private void onNoResult() {
        waitingDialog.hideDialog();
        alertDialog.showAlertDialog("No result found", "Sorry, we can not find any data for your input, please try other key.");
    }

    private void onHasResult() {
        waitingDialog.hideDialog();
    }

    private void getPostByID(int i) {
        final DatabaseReference postRef;
        postRef = database
                .getReference("posts")
                .child(i + "");
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    postList.add(dataSnapshot.getValue(Post.class));
                    postAdapter.notifyDataSetChanged();
                    updateMap();
                    onHasResult();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchNearBy() {
        setTimeOut();
        GeoQuery geoQuery = geoFire.queryAtLocation(
                new GeoLocation(
                        gpsTracker.getLatitude(), gpsTracker.getLongitude()),
                distance);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(this);
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        getPostByID(Integer.valueOf(key));
    }

    @Override
    public void onKeyExited(String key) {

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
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
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
