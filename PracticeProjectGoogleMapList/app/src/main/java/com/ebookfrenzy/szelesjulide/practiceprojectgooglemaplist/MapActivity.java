package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
//import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data.MarkerObject;
import com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data.buttonfolder.OnInfoWindowElementTouchListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;


import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private GoogleMap mMap;
    //For the location update
    GoogleApiClient mGoogleApiClient;
    SupportMapFragment mapFragment;
    LocationRequest locationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    private String MY_PREFS_NAME = "PassDataPref";

    private double latitudeValue;
    private double longtitudeValue;

    private OnInfoWindowElementTouchListener infoButtonListener;
    private int didInteractionHappen = 0;

    LocationManager locationManager;
    private static final int LOCATION_REQUEST_CODE = 101;
    private String TAG = "MapDemo";
    MyLocListener loc;

    ArrayList<MarkerObject> markerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Allow zooming action
        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomGesturesEnabled(true);


        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        //Test on Emulator
        queryTheObjects();

        LatLng latLng = new LatLng(37.422006,-122.084095);
        Location mLocationj = new Location("");
        mLocationj.setLatitude(latLng.latitude);
        mLocationj.setLongitude(latLng.longitude);
        onLocationChanged(mLocationj);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                latitudeValue = latLng.latitude;
                longtitudeValue = latLng.longitude;
                Intent categoryActivity = new Intent(MapActivity.this,CategoryActivity.class);
                mMap.clear();
                startActivityForResult(categoryActivity,111);
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentView = getLayoutInflater().inflate(R.layout.raw_item_marker,null);
                TextView markerTitle = (TextView)myContentView.findViewById(R.id.textViewTitle);
                TextView commentTitle = (TextView)myContentView.findViewById(R.id.textViewComment);
                ImageView imageView = (ImageView)myContentView.findViewById(R.id.imageView2);
                Button buttonInfoView = (Button)myContentView.findViewById(R.id.buttonDelete);
                imageView.setImageResource(android.R.drawable.sym_def_app_icon);
                markerTitle.setText(marker.getTitle());
                commentTitle.setText(marker.getSnippet());

                return myContentView;
            }
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                String markerString = marker.getTitle();
                //TODO delete the marker from the map/list and database as the user

                final AlertDialog alertDialog = new AlertDialog.Builder(MapActivity.this).create();
                alertDialog.setTitle("WARNING!");
                alertDialog.setMessage("Do you want to delete the following marker?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Todo delete the marker
                        MarkerObject markerObject = new MarkerObject();
                        markerObject.setMarkerLatitude(marker.getPosition().latitude);
                        markerObject.setMarkerLongtitude(marker.getPosition().longitude);
                        markerObject.setMarkerTitle(marker.getTitle());
                        markerObject.setMarkerComment(marker.getSnippet());

                        boolean wasDeletingSuccesful = deleteMarkerFromDatabase(markerObject);
                        if (wasDeletingSuccesful){
                            marker.remove();
                        }
                        else {
                            marker.remove();//TODO figure out why sometimes it doesnt delete the row from the database even tough it is there when I query it (><)
                        }

                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        String restoredTitle = prefs.getString("title",null);
        String restoredComment = prefs.getString("comment",null);

        MarkerObject marker = new MarkerObject(restoredTitle,restoredComment,latitudeValue,longtitudeValue);

        didInteractionHappen = 1;
        //Add it to the database
        newMarker(marker);
        queryTheObjects();

        //Only for emulator because the onLocation will be called automatically
        LatLng latLng = new LatLng(37.422006,-122.084095);
        Location mLocationj = new Location("");
        mLocationj.setLatitude(latLng.latitude);
        mLocationj.setLongitude(latLng.longitude);
        onLocationChanged(mLocationj);

        prefs.edit().clear();

    }

    //Database part
    public void newMarker(MarkerObject markerObject){
        MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);
        dbHandler.addMarker(markerObject);
    }

    public void queryTheObjects(){
        MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);
        ArrayList<MarkerObject> listOfMarkers = new ArrayList<MarkerObject>();
        markerList = new ArrayList<MarkerObject>();
        listOfMarkers = dbHandler.queryAllObjects();

        for (int i = 0; i < listOfMarkers.size(); i++){
            MarkerObject markerObj = listOfMarkers.get(i);
            markerList.add(markerObj);
            LatLng latLng = new LatLng(markerObj.getMarkerLatitude(),markerObj.getMarkerLongtitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(markerObj.getMarkerTitle()).snippet(markerObj.getMarkerComment()));
            //markerList.add(m);
        }
        //Clear the list
        listOfMarkers.clear();
    }

    public boolean deleteMarkerFromDatabase(MarkerObject marker){
        MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);
        boolean result = dbHandler.deleteMarker(marker);

        return result;
    }

    //Location part
    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null){
            mCurrLocationMarker.remove();
        }

        //Place current location maker
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //Move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        float distanceInMetersBetweenTwoPoints;

        //Check if we are close to a marker
        for (int i = 0; i < markerList.size(); i++){
            MarkerObject obj = markerList.get(i);
            Location markerLocation = new Location("");
            markerLocation.setLatitude(obj.getMarkerLatitude());
            markerLocation.setLongitude(obj.getMarkerLongtitude());
            distanceInMetersBetweenTwoPoints = location.distanceTo(markerLocation);

            if (distanceInMetersBetweenTwoPoints < 1000){
                Log.i("Near a marker","We got near to a marker");
                sendNotification(obj);
            }

        }

    }

    public void sendNotification(MarkerObject marker){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info) //TODO change it to the selected category icon
                .setContentTitle(marker.getMarkerTitle())
                .setContentText(marker.getMarkerComment());

        //Add action button to notification
        Intent resultIntent = new Intent(this,MapActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationCompat.Action deleteMarkerAction = new NotificationCompat.Action.Builder(android.R.drawable.sym_action_chat,"Delete marker",pendingIntent).build();

        builder.addAction(deleteMarkerAction);

        NotificationManager notifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(marker.get_id(),builder.build());


    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}
