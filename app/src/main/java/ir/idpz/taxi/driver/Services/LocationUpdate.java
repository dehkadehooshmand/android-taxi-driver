package ir.idpz.taxi.driver.Services;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.driver.MainActivity;
import ir.idpz.taxi.driver.Utils.AppController;
import ir.idpz.taxi.driver.Utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationUpdate implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Context mAct;
    public static GoogleApiClient mGoogleApiClient;

    public static Timer myTimer = new Timer();
    Location mLastLocation;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(mAct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mAct, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        RequestParams params = new RequestParams();

        try { params.put("id", Helpers.getTaxi_id());

            params.put("lat", mLastLocation.getLatitude());

            params.put("lng", mLastLocation.getLongitude());

            params.put("notification_token", FirebaseInstanceId.getInstance().getToken());

        } catch (Exception e) {
        }
        Helpers.client.post(Helpers.baseUrl + "location_taxi", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

             try {
                 if (responseString.contains("ok")) {
                     // Toast.makeText(AppController.getAppContext(), "update", Toast.LENGTH_SHORT).show();
                 }
             }catch (Exception e){}
            }
        });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void StartSchedule(final Context act) {

        mAct = act;

        //


        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            getLastLocation();

                            buildGoogleApiClient();
                            if (mGoogleApiClient != null) {
                                mGoogleApiClient.connect();
                            } else {

                                Toast.makeText(mAct, "عدم دریافت موقعیت مکانی...", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {
                            Log.e("t", ex.toString());
                        }
                    }
                });

                thread.start();
            }

        }, 0, 10000);

    }

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(mAct)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } catch (Exception ex) {
            Log.d("buildGoogleApiClient", ex.getMessage());
        }


    }


}