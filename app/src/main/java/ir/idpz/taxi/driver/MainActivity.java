package ir.idpz.taxi.driver;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.parisa.viewpager.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Authorizer;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.driver.Classes.UserCardModel;
import ir.idpz.taxi.driver.Services.LocationUpdate;
import ir.idpz.taxi.driver.Utils.AppController;
import ir.idpz.taxi.driver.Utils.CustomTypefaceSpan;
import ir.idpz.taxi.driver.Utils.Helpers;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentSelectedListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

public class MainActivity extends AppCompatActivity {
    SegmentedControl segmentedControl;
    ImageView imgMenu;
    ImageButton arrow_back;
    TextView title;

    DrawerLayout drawer_layout;
    TextView txt_Credit;
    Button state_btn, transfer, IncreaseValidity;
    public AsyncHttpClient client = new AsyncHttpClient();

    ProgressDialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);


//        PusherOptions options = new PusherOptions();
//        options.setCluster("ap2");
//        options.setAuthorizer(new Authorizer() {
//            @Override
//            public String authorize(String channelName, String socketId) throws AuthorizationFailureException {
//                return null;
//            }
//        });
//        Pusher pusher = new Pusher("3825fb1fb57812788f6f", options);
//
//        final Channel channel = pusher.subscribe("smartvillage");
//
//        channel.bind("message", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                System.out.println(data);
//                Log.d("data :", data);
//            }
//        });
//
//        pusher.connect();
//
//
//        final Public privateChannel = pusher.subscribePrivate("private-smartvillage", new PrivateChannelEventListener() {
//            @Override
//            public void onAuthenticationFailure(String message, Exception e) {
//            }
//
//            @Override
//            public void onSubscriptionSucceeded(String channelName) {
//
//            }
//
//            @Override
//            public void onEvent(String channelName, String eventName, String data) {
//
//            }
//
//        });
//


////////////////////////////////////////////////////////////////////////////////////////////////////

        dialog2 = new ProgressDialog(MainActivity.this);


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

        SharedPreferences sharedPreferences = AppController.getAppContext().getSharedPreferences("verified_driver1", 0);

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
        txt_Credit = hView.findViewById(R.id.txt_credit);
        transfer = hView.findViewById(R.id.transfer);
        IncreaseValidity = hView.findViewById(R.id.IncreaseValidity);

        IncreaseValidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.checkout_dialog);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                final EditText tbPrice = dialog.findViewById(R.id.txtPrice);
                final Button btnCheck = dialog.findViewById(R.id.btnCheck);
                final EditText tbCardNumber = dialog.findViewById(R.id.tbCardNumber);
                final EditText tbShebaNumber = dialog.findViewById(R.id.tbShebaNumber);
                final EditText tbDateNumber = dialog.findViewById(R.id.tbDateNumber);
                getCardData(tbCardNumber, tbShebaNumber, tbDateNumber);
                tbPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        int cursorPosition = tbPrice.getSelectionEnd();
                        String originalStr = tbPrice.getText().toString();
                        tbPrice.setFilters(new InputFilter[]{new MoneyValueFilter(2)});

                        try {
                            tbPrice.removeTextChangedListener(this);
                            String value = tbPrice.getText().toString();

                            if (value != null && !value.equals("")) {
                                if (value.startsWith(".")) {
                                    tbPrice.setText("0.");
                                }
                                if (value.startsWith("0") && !value.startsWith("0.")) {
                                    tbPrice.setText("");
                                }
                                String str = tbPrice.getText().toString().replaceAll(",", "");
                                if (!value.equals(""))
                                    tbPrice.setText(getDecimalFormattedString(str));

                                int diff = tbPrice.getText().toString().length() - originalStr.length();
                                tbPrice.setSelection(cursorPosition + diff);
                            }
                            tbPrice.addTextChangedListener(this);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            tbPrice.addTextChangedListener(this);
                        }

                    }
                });
                btnCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tbCardNumber.getText().length() < 19 || tbShebaNumber.getText().length() < 26)
                            Toast.makeText(MainActivity.this, "لطفا اطلاعات بانکی را به درستی وارد کنید.", Toast.LENGTH_LONG).show();
                            //  tools.alertShow("لطفا اطلاعات بانکی را به درستی وارد کنید.");
                        else if (tbPrice.getText().toString().length() < 1)
                            Toast.makeText(MainActivity.this, "مبلغی وارد نشده است.", Toast.LENGTH_LONG).show();
                            // tools.alertShow("مبلغی وارد نشده است.");
                        else if (Integer.valueOf(tbPrice.getText().toString().replace(",", "")) < 10000)
                            Toast.makeText(MainActivity.this, "مبلغ وارد شده کمتر از 10,000 تومان است.", Toast.LENGTH_LONG).show();
                            // tools.alertShow("مبلغ وارد شده کمتر از 10,000 تومان است.");
                        else if (tbDateNumber.getText().toString().length() < 5)
                            Toast.makeText(MainActivity.this, "تاریخ انقضای کارت اشتباه وارد شده.", Toast.LENGTH_LONG).show();
                            // tools.alertShow("تاریخ انقضای کارت اشتباه وارد شده.");
                        else
                            saveCardData(tbShebaNumber.getText().toString(), tbCardNumber.getText().toString(), tbDateNumber.getText().toString(), tbPrice.getText().toString(), dialog);
                    }
                });
                dialog.show();
                dialog.getWindow().setAttributes(lp);

                tbDateNumber.addTextChangedListener(new TextWatcher() {

                    private static final char space = '-';

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int pos = 0;
                        while (true) {
                            if (pos >= s.length()) break;
                            if (space == s.charAt(pos) && (((pos + 1) % 3) != 0 || pos + 1 == s.length())) {
                                s.delete(pos, pos + 1);
                            } else {
                                pos++;
                            }
                        }

                        // Insert char where needed.
                        pos = 2;
                        while (true) {
                            if (pos >= s.length()) break;
                            final char c = s.charAt(pos);
                            // Only if its a digit where there should be a space we insert a space
                            if ("0123456789".indexOf(c) >= 0) {
                                s.insert(pos, "" + space);
                            }
                            pos += 3;
                        }
                    }
                });

                tbCardNumber.addTextChangedListener(new TextWatcher() {

                    private static final char space = '-';

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int pos = 0;
                        while (true) {
                            if (pos >= s.length()) break;
                            if (space == s.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == s.length())) {
                                s.delete(pos, pos + 1);
                            } else {
                                pos++;
                            }
                        }

                        // Insert char where needed.
                        pos = 4;
                        while (true) {
                            if (pos >= s.length()) break;
                            final char c = s.charAt(pos);
                            // Only if its a digit where there should be a space we insert a space
                            if ("0123456789".indexOf(c) >= 0) {
                                s.insert(pos, "" + space);
                            }
                            pos += 5;
                        }
                    }
                });


            }
        });


        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.transfer_dialog);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                final EditText txtMobile = dialog.findViewById(R.id.txtMobile);
                final EditText txtPrice = dialog.findViewById(R.id.txtPrice);
                Button btnTransfer = dialog.findViewById(R.id.btnTransfer);
                btnTransfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (txtMobile.getText().length() < 11)
                            Toast.makeText(MainActivity.this, "شماره موبایل به درستی وارد نشده است", Toast.LENGTH_LONG).show();
                            //     tools.alertShow("شماره موبایل به درستی وارد نشده است");
                        else if (txtPrice.getText().toString().length() < 1)
                            Toast.makeText(MainActivity.this, "مبلغی وارد نشده است.", Toast.LENGTH_LONG).show();
                            //  tools.alertShow("مبلغی وارد نشده است.");
                        else if (Integer.valueOf(txtPrice.getText().toString().replace(",", "")) < 10000)
                            Toast.makeText(MainActivity.this, "حداقل مبلغ 10000 تومان است", Toast.LENGTH_LONG).show();

                            //tools.alertShow("حداقل مبلغ 10000 تومان است");
                        else {
                            dialog2.setMessage("لطفا صبر کنید ...");
                            dialog2.show();

                            String url = "http://bakhsh.idpz.ir/api/wallet/transfer";
                            RequestParams params = new RequestParams();
                            params.put("api_token", Helpers.getSharePrf("api_token"));
                            params.put("id", Helpers.getSharePrf("user_id"));
                            params.put("amount", txtPrice.getText().toString().replaceAll(",", ""));
                            params.put("state", Helpers.getSharePrf("state"));
                            params.put("mobile", txtMobile.getText().toString());
                            client.post(url, params, new TextHttpResponseHandler() {
                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    Log.d("", "onFailure: " + responseString + throwable);
                                    dialog2.dismiss();
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                    Log.d("", "succ: " + responseString);
                                    Toast.makeText(MainActivity.this, "منتقل شد", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    dialog2.dismiss();
                                }
                            });
                        }
                    }
                });

                txtPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        int cursorPosition = txtPrice.getSelectionEnd();
                        String originalStr = txtPrice.getText().toString();
                        txtPrice.setFilters(new InputFilter[]{new MoneyValueFilter(2)});

                        try {
                            txtPrice.removeTextChangedListener(this);
                            String value = txtPrice.getText().toString();

                            if (value != null && !value.equals("")) {
                                if (value.startsWith(".")) {
                                    txtPrice.setText("0.");
                                }
                                if (value.startsWith("0") && !value.startsWith("0.")) {
                                    txtPrice.setText("");
                                }
                                String str = txtPrice.getText().toString().replaceAll(",", "");
                                if (!value.equals(""))
                                    txtPrice.setText(getDecimalFormattedString(str));

                                int diff = txtPrice.getText().toString().length() - originalStr.length();
                                txtPrice.setSelection(cursorPosition + diff);
                            }
                            txtPrice.addTextChangedListener(this);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            txtPrice.addTextChangedListener(this);
                        }

                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });


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
                    CapacityFragment capacityFragment = new CapacityFragment(MainActivity.this);
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
                } catch (Exception e) {
                }
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


                        File Item2 = new File("/data/data/" + getPackageName() + "/shared_prefs/verified_driver1.xml");

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


    public void showCredit() {
        String url = "http://bakhsh.idpz.ir/api/wallet/balance";
        RequestParams params = new RequestParams();
        params.put("api_token", "wVR3RtYivwvG1AzfNOaYRZteDFiqhgfGAeDuNAV4EuWHauUzqQjbZnYX6Uvri75r");
        params.put("id", "1662");
        Helpers.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("", "onFailure: " + responseString + throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {

                    //   txt_Credit.setText("اعتبار شما :"+responseString);
                    txt_Credit.setText(" اعتبار شما : " + getFormatedAmount(Integer.valueOf(responseString)) + " ریال ");

                } catch (Exception e) {
                }

            }
        });
    }

    class MoneyValueFilter extends DigitsKeyListener {
        private int digits;

        public MoneyValueFilter(int i) {
            super(false, true);
            digits = i;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            CharSequence out = super.filter(source, start, end, dest, dstart, dend);

            // if changed, replace the source
            if (out != null) {
                source = out;
                start = 0;
                end = out.length();
            }

            int len = end - start;

            // if deleting, source is empty
            // and deleting can't break anything
            if (len == 0) {
                return source;
            }

            int dlen = dest.length();

            // Find the position of the decimal .
            for (int i = 0; i < dstart; i++) {
                if (dest.charAt(i) == '.') {
                    // being here means, that a number has
                    // been inserted after the dot
                    // check if the amount of digits is right
                    return getDecimalFormattedString((dlen - (i + 1) + len > digits) ? "" : String.valueOf(new SpannableStringBuilder(source, start, end)));
                }
            }

            for (int i = start; i < end; ++i) {
                if (source.charAt(i) == '.') {
                    // being here means, dot has been inserted
                    // check if the amount of digits is right
                    if ((dlen - dend) + (end - (i + 1)) > digits)
                        return "";
                    else
                        break; // return new SpannableStringBuilder(source,
                    // start, end);
                }
            }

            // if the dot is after the inserted part,
            // nothing can break
            return getDecimalFormattedString(String.valueOf(new SpannableStringBuilder(source, start, end)));
        }
    }


    public static String getDecimalFormattedString(String value) {
        if (value != null && !value.equalsIgnoreCase("")) {
            StringTokenizer lst = new StringTokenizer(value, ".");
            String str1 = value;
            String str2 = "";
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken();
                str2 = lst.nextToken();
            }
            String str3 = "";
            int i = 0;
            int j = -1 + str1.length();
            if (str1.charAt(-1 + str1.length()) == '.') {
                j--;
                str3 = ".";
            }
            for (int k = j; ; k--) {
                if (k < 0) {
                    if (str2.length() > 0)
                        str3 = str3 + "." + str2;
                    return str3;
                }
                if (i == 3) {
                    str3 = "," + str3;
                    i = 0;
                }
                str3 = str1.charAt(k) + str3;
                i++;
            }
        }
        return "";
    }

    private void saveCardData(String sheba, String card, String ex_date, final String amount, final Dialog checkDialog) {

        dialog2.setMessage("درحال ارسال درخواست...");
        dialog2.show();
        String url = "http://bakhsh.idpz.ir/api/wallet/edit_bank_detail";
        RequestParams params = new RequestParams();
        params.put("api_token", Helpers.getSharePrf("api_token"));
        params.put("id", Helpers.getSharePrf("user_id"));
        params.put("sheba", sheba);
        params.put("card", card.replace("-", ""));
        params.put("ex_date", ex_date);

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("", "onFailure: " + responseString + throwable);
                checkDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                checkOutWallet(amount, checkDialog);
            }
        });

    }


    private void checkOutWallet(String amount, final Dialog checkDialog) {
        dialog2.setMessage("لطفا صبر کنید ...");
        String url = "http://bakhsh.idpz.ir/api/wallet/bank_deposit";//todo change all base url of webservises
        RequestParams params = new RequestParams();
        params.put("api_token", Helpers.getSharePrf("api_token"));
        params.put("id", Helpers.getSharePrf("user_id"));
        params.put("amount", amount.replace(",", ""));
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("", "onFailure: " + responseString + throwable);
                dialog2.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                getUserBalance();
                if (responseString.toLowerCase().contains("notok")) {

                } else {
                    alertShow("درخواست تسویه ی شما ثبت شد");
                }
                checkDialog.dismiss();
            }
        });
    }


    private void getUserBalance() {
        dialog2.setMessage("درحال دریافت موجودی ...");
        dialog2.show();
        String url = "http://bakhsh.idpz.ir/api/wallet/balance";//todo change all webservis's base url
        RequestParams params = new RequestParams();
        params.put("api_token", Helpers.getSharePrf("api_token"));
        params.put("id", Helpers.getSharePrf("user_id"));
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("", "onFailure: " + responseString + throwable);
                dialog2.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                dialog2.hide();
                try {
                    NumberFormat formatter = new DecimalFormat("#,###");
                    double myNumber = Double.parseDouble(responseString);
                    String formattedNumber = formatter.format(myNumber);
                    txt_Credit.setText(formattedNumber);
                } catch (Exception e) {

                }
            }
        });
    }


    private void getCardData(final EditText tbCardNumber, final EditText tbShebaNumber, final EditText tbDateNumber) {
        dialog2.setMessage("درحال دریافت اطلاعات کارت ...");
        dialog2.show();

        String url = "http://bakhsh.idpz.ir/api/wallet/show_bank_detail";
        RequestParams params = new RequestParams();
        params.put("api_token", Helpers.getSharePrf("api_token"));
        params.put("id", Helpers.getSharePrf("user_id"));

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("", "onFailure: " + responseString + throwable);
                dialog2.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                dialog2.dismiss();
                Gson gson = new Gson();

                try {
                    UserCardModel model = gson.fromJson(responseString, UserCardModel.class);

                    tbCardNumber.setText(String.valueOf(model.getDetail().getCardNumber()));
                    tbShebaNumber.setText(model.getDetail().getSheba());
                    tbDateNumber.setText(model.getDetail().getCardExDate());

                } catch (Exception e) {
                    Log.d("parisa", "onSuccess: " + e.getMessage());
                }

            }
        });

    }


    public void alertShow(String text) {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSans(FaNum).ttf");
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(FontUtils.typeface(typeface, "پیام"));
        alertDialog.setIcon(R.drawable.ic_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        alertDialog.setMessage(FontUtils.typeface(typeface, text));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, FontUtils.typeface(typeface, "بستن"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }


    private String getFormatedAmount(int amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }
}








