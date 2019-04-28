package ir.idpz.taxi.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parisa.viewpager.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


import ir.idpz.taxi.driver.Classes.Station;
import ir.idpz.taxi.driver.Classes.UserTaxi;
import ir.idpz.taxi.driver.Utils.Helpers;

import static ir.idpz.taxi.driver.Utils.AppController.*;

@SuppressLint("ValidFragment")

public class MapViewFragment extends Fragment implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap googleMap;

    MapView mMapView;
    private GoogleApiClient mGoogleApiClient;

    ImageView mylocation,currentlocation;
    Context mContext;

    Location mLastKnownLocation;
    PolylineOptions options;

    private int location_time = 0;
    public static int location_int = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.location_fragment, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        currentlocation=v.findViewById(R.id.currentlocation);

        turnOnGPS();
        //  mylocation=v.findViewById(R.id.currentlocation);
        buildGoogleApiClient();


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {


                googleMap = mMap;
                LatLng latLong = new LatLng(35.684209, 51.388263);


                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLong).zoom(18).build();

                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                        if (location_time < location_int)
                            mMap.moveCamera(cameraUpdate);
                        location_time = location_time + 1;
                    }
                });


                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                try {
                    draw_lines();
                }catch (Exception e){}
            }
        });

        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });



//getLastLocation();


        return v;
    }

    public MapViewFragment() {
    }

    public MapViewFragment(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void onResume() {
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


    public static MapViewFragment newInstance() {

        MapViewFragment f = new MapViewFragment();


        return f;
    }

    private Location getLastLocation() {
        if (ContextCompat.checkSelfPermission(getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's Response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 124;
                final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        try {
            mLastKnownLocation = LocationServices.
                    FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastKnownLocation != null) {
                LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//                StaticVars.getCurrentRequest().setLat(mLastKnownLocation.getLatitude());
//                StaticVars.getCurrentRequest().setLng(mLastKnownLocation.getLongitude());

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                googleMap.animateCamera(cameraUpdate);
                onLocationChanged(mLastKnownLocation);


            }
        } catch (SecurityException e) {

        }

        return mLastKnownLocation;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getAppContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    public void onLocationChanged(Location location) {
        try {

            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.709561, 51.372652), 18);
        googleMap.moveCamera(cameraUpdate);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                googleMap.moveCamera(cameraUpdate);
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location loc) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    loc.getLongitude(), loc.getLatitude()
            );
            Toast.makeText(getAppContext(), message, Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String arg0) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    private void turnOnGPS() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();
        }


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(), 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });


    }


    public void draw_lines() {
        Helpers.getline().getStations();
        options = new PolylineOptions().width(5).geodesic(true);

        for (Station station : Helpers.getline().getStations()) {



                LatLng point = new LatLng(station.getLat(), station.getLng());

                options.add(point);


            }
            googleMap.addPolyline(options);
        }



    public Location getMyLocation() {

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's Response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 124;
                final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        try {
            mLastKnownLocation = LocationServices.
                    FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastKnownLocation != null) {
                LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//                StaticVars.getCurrentRequest().setLat(mLastKnownLocation.getLatitude());
//                StaticVars.getCurrentRequest().setLng(mLastKnownLocation.getLongitude());


            }
        } catch (SecurityException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return mLastKnownLocation;
    }



        }

//    private void turnOnGPS() {
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this).build();
//            mGoogleApiClient.connect();
//        }
//
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//
//        //**************************
//        builder.setAlwaysShow(true); //this is the key ingredient
//        //**************************
//
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                final LocationSettingsStates state = result.getLocationSettingsStates();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can initialize location
//                        // requests here.
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the user
//                        // a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(
//                                    getActivity(), 1000);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        break;
//                }
//            }
//        });
//    }


