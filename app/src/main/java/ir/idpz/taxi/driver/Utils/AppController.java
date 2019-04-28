package ir.idpz.taxi.driver.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.example.parisa.viewpager.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {

    private static Context context;

    private static Activity activity;


    public static Activity getActivity() {
        return activity;
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = getApplicationContext();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("IRANSans(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
