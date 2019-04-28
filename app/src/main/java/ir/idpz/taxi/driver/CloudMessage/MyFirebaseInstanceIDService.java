package ir.idpz.taxi.driver.CloudMessage;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by hpen on 4/10/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";

    /**
     * این متد زمانی توکن توسط گوگل اپدیت شود فراخوانی می شود
     */
    @Override
    public void onTokenRefresh() {


        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);



        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {

        //Implement this method if you want to store the token on your server
    }

}
