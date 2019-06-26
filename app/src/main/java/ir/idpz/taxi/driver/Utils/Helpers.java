package ir.idpz.taxi.driver.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.example.parisa.viewpager.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;

import ir.idpz.taxi.driver.Classes.UserTaxi;
import omidi.mehrdad.moalertdialog.MoAlertDialog;

public class Helpers {

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static String baseUrl = "http://admin.idpz.ir/api/";  //todo"http://admin.idpz.ir/api/";

    public static Gson gson = new Gson();

    public static String taxi_id;

    public static String user_name;


  public static UserTaxi userTaxi=Helpers.gson.fromJson(getUserTaxi(), UserTaxi.class);

    public static String getTaxi_id() {

        return userTaxi.getTaxiId().toString();
    }

    public static String getCodemeli(){
        return userTaxi.getCodeMeli();
    }


    public static String getPelak(){
        return userTaxi.getPelak();
    }


    public static String getMobile(){
        return userTaxi.getMobile();
    }


    public static String getCodeTaxi(){
        return userTaxi.getTaxiCode();
    }
    public static String getUser_name() {
//        JsonParser jsonParser = new JsonParser();
//        JsonObject objectFromString = jsonParser.parse(getUserTaxi()).getAsJsonObject();
//
//        UserTaxi userTaxi=Helpers.gson.fromJson(getUserTaxi(), UserTaxi.class);
//

        return  userTaxi.getFirstName()+" "+userTaxi.getLastName();
    }


    public static UserTaxi getline(){


            UserTaxi userTaxi=Helpers.gson.fromJson(getUserTaxi(), UserTaxi.class);


        return userTaxi;
    }

    public static String getUserTaxi() {

        SharedPreferences sharedPreferences = AppController.getAppContext().getSharedPreferences("USER", 0);
        return sharedPreferences.getString("user_taxi", "");
    }

    public static void setUserTaxi(String userTaxi) {

        SharedPreferences sharedPreferences = AppController.getAppContext().getSharedPreferences("USER", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user_taxi", userTaxi);
        editor.commit();

    }

    public static void noInternetDialog(Context context) {
        Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
                "IRANSans(FaNum).ttf");
        MoAlertDialog dialog = new MoAlertDialog(context);

        dialog.showSuccessDialog("عدم اتصال به اینترنت ", "لطفا اتصال خود را با اینترنت بررسی نمایید.");

        dialog.setTypeface(face);

        dialog.setDilogIcon(R.drawable.ic_no_internet);
        dialog.setDialogButtonText("باشه!");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void addToSharePrf(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(AppController.getAppContext()).edit()
                .putString(key, value).apply();
    }


    public static String getSharePrf(String key) {
        return PreferenceManager.getDefaultSharedPreferences(AppController.getAppContext()).getString(key, "");
    }

}
