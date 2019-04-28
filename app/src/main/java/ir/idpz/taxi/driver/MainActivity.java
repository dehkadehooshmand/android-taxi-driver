package ir.idpz.taxi.driver;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parisa.viewpager.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.driver.Services.LocationUpdate;
import ir.idpz.taxi.driver.Utils.AppController;
import ir.idpz.taxi.driver.Utils.CustomTypefaceSpan;
import ir.idpz.taxi.driver.Utils.Helpers;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentSelectedListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    SegmentedControl segmentedControl;
    ImageView imgMenu;
    ImageButton arrow_back;
    TextView title;
    DrawerLayout drawer_layout;
    TextView txt_Credit;
    Button state_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);


        Intent newReq = getIntent();
        newReq.getSerializableExtra("newReq");


//        if (newReq != null) {
//            newRequest();
//        }

        try {
            LocationUpdate locationUpdate = new LocationUpdate();

            locationUpdate.StartSchedule(MainActivity.this);
        } catch (Exception e) {
        }

        SharedPreferences sharedPreferences = AppController.getAppContext().getSharedPreferences("verified_driver", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("flag", "true");
        editor.commit();

        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setVisibility(View.INVISIBLE);
        title = findViewById(R.id.title);
        title.setText("صفحه اصلی");
        title.setGravity(Gravity.CENTER);
        FirebaseInstanceId.getInstance().getToken();
        imgMenu = findViewById(R.id.imgMenu);
        drawer_layout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        View hView = navView.getHeaderView(0);

        TextView txt_name = hView.findViewById(R.id.nav_header_textView);
         txt_Credit=hView.findViewById(R.id.txt_credit);
        try {
            txt_name.setText(Helpers.getUser_name());
            showCredit();

        } catch (Exception e) {
        }
        navView.setItemIconTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, android.R.color.black)));
        navView.setItemTextColor(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, android.R.color.black)));
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer_layout.closeDrawers();
                switch (item.getItemId()) {

//todo mavard zir version badi
//                    case R.id.travel_list:
//                        Intent intentsetting = new Intent(MainActivity.this, TravelHistoryActivity.class);
//                        startActivity(intentsetting);
//                        break;
//
//
                    case R.id.qrcode:

                        startActivity(new Intent(MainActivity.this, QrCodeActivity.class));
                        break;


                    case R.id.profile:

                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;


                    case R.id.exit:

                        Alert_Exit();


                        break;

                    case R.id.applingfor_exit:

                        startActivity(new Intent(MainActivity.this, ApplyingForExitActivity.class));
                        break;

                }
                return true;
            }
        });
        Menu m = navView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer_layout.isDrawerOpen(Gravity.RIGHT)) {
                    drawer_layout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer_layout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        segmentedControl = (SegmentedControl) findViewById(R.id.segmented_control);
        Typeface typeface =
                Typeface.createFromAsset(getAssets(), "IRANSans(FaNum).ttf");

        segmentedControl.setTypeFace(typeface);

        final ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), segmentedControl.size()));
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------for api less than 23---------------------------------------------------------------------------------------------------
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                segmentedControl.setSelectedSegment(pager.getCurrentItem());

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //-------------------------------------------------------------------------------------------------------------------------------------------------------
        //------------------------------for api more than 23 --------------------------------------------------------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    segmentedControl.setSelectedSegment(pager.getCurrentItem());

                }
            });
        }

//====================================================================================================================================================
        segmentedControl.addOnSegmentSelectListener(new OnSegmentSelectedListener() {
            @Override
            public void onSegmentSelected(SegmentViewHolder segmentViewHolder, boolean isSelected, boolean isReselected) {
                pager.setCurrentItem(segmentedControl.getSelectedAbsolutePosition());


            }
        });
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        int numberOfTabs;

        public MyPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.numberOfTabs = NumOfTabs;

        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    CapacityFragment capacityFragment=new CapacityFragment(MainActivity.this);
                    return capacityFragment;


                case 1:

                    MapViewFragment mapViewFragment = new MapViewFragment(MainActivity.this);
                    return mapViewFragment;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "IRANSans(FaNum).ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
//--------------------------------------------------------------------------------------------------

    public void updateToken(String id, String token) {
        RequestParams params = new RequestParams();

        params.put("id", id);
        params.put("token", token);

        Helpers.client.post("http://admin.idpz.ir/api/user_notification", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
            try {
                if (responseString.contains("ok")) {

                }
            }catch (Exception e){}
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    public void Alert_Exit() {

       /* Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
                "IRANSans(FaNum).ttf");*/
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("خروج از حساب")
                .setMessage("آیا از خروج خود اطمینان دارید؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete


                        File Item2 = new File("/data/data/" + getPackageName() + "/shared_prefs/verified_driver.xml");

                        Item2.delete();


                        Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                        i1.setAction(Intent.ACTION_MAIN);
                        i1.addCategory(Intent.CATEGORY_HOME);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        overridePendingTransition(0, 0);

                        startActivity(i1);
                        finish();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing

                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)

                .show();


    }


    public void newRequest() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.new_request_dialog);
        dialog.setCancelable(false);
        dialog.show();
        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    public void showCredit(){
        String url = "http://bakhsh.idpz.ir/api/wallet/balance";
        RequestParams params = new RequestParams();
        params.put("api_token", "wVR3RtYivwvG1AzfNOaYRZteDFiqhgfGAeDuNAV4EuWHauUzqQjbZnYX6Uvri75r");
        params.put("id", "1662");
        Helpers.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("", "onFailure: "+responseString+throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

              try {
                  txt_Credit.setText("اعتبار شما :"+responseString);
              }catch (Exception e){}

            }
        });
    }
}








