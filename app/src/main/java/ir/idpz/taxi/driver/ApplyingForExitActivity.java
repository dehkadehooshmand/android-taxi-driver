package ir.idpz.taxi.driver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parisa.viewpager.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.idpz.taxi.driver.Utils.AppController;
import ir.idpz.taxi.driver.Utils.Helpers;
import omidi.mehrdad.moalertdialog.MoAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ApplyingForExitActivity extends AppCompatActivity {
    TextView txt_from, txt_until,txt_demand,txt_pelak,txt_name,txt_mobile,txt_code;
            ;
    PersianDatePickerDialog from_picker , until_picker;
    Typeface face;
    ImageView arrow_back;
    Button btn_confrim;
     MaterialSpinner txt_city;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applying_for_exit);


        txt_pelak=findViewById(R.id.pelak);
        txt_code=findViewById(R.id.txt_code);
        txt_name=findViewById(R.id.txt_name);
        txt_mobile=findViewById(R.id.txt_mobile);




        try {
          txt_pelak.setText(Helpers.getPelak());
          txt_mobile.setText(Helpers.getMobile());
            txt_name.setText(Helpers.getUser_name());
            txt_code.setText(Helpers.getCodeTaxi());

        }catch (Exception e){}

        toolbar=findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.TRANSPARENT);


        txt_demand=findViewById(R.id.demand);

        txt_city=findViewById(R.id.txt_city);


//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        txt_from = findViewById(R.id.from);
        txt_until = findViewById(R.id.untill);

        btn_confrim=findViewById(R.id.btn_confrim);

        txt_city= (MaterialSpinner) findViewById(R.id.txt_city);
        txt_city.setItems("تهران", "اراک", "اصفهان", "یزد", "بوشهر","بندرعباس","حرمشهر","همدان","لرستان","مشهد","مازندران","قم","شیراز","اردبیل","کرمان","تبریز");
        txt_city.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
        txt_city.setText(item);

            }
        });

        btn_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Taxi_Leave();
            }
        });

        arrow_back=findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
                "IRANSans(FaNum).ttf");

       txt_from.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               from_picker = new PersianDatePickerDialog(ApplyingForExitActivity.this)
                       .setPositiveButtonString("باشه")
                       .setNegativeButton("بیخیال")
                       .setTodayButtonVisible(true)
                       // .setInitDate(initDate)
                       .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                       .setMinYear(1300)
                       .setActionTextColor(Color.GRAY)
                       .setTypeFace(face)
                       .setListener(new Listener() {
                           @Override
                           public void onDateSelected(PersianCalendar persianCalendar) {
                               txt_from.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

                           }

                           @Override
                           public void onDismissed() {

                           }


                       });

               from_picker.show();
           }
       });

        txt_until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                until_picker = new PersianDatePickerDialog(ApplyingForExitActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButtonVisible(true)
                        // .setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
                        .setTypeFace(face)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                txt_until.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                until_picker.show();
            }
        });


    }

    public void Alertdialog_confrim() {
        MoAlertDialog dialog = new MoAlertDialog(ApplyingForExitActivity.this);

        dialog.showSuccessDialog("درخواست خروج از خط", "درخواست شما ثبت شد ، پس از تایید به اطلاع شما میرسد.");

        dialog.setTypeface(face);

        dialog.setDilogIcon(R.drawable.ic_support);
        dialog.setDialogButtonText("باشه!");
    }

    public void Taxi_Leave(){

        RequestParams params = new RequestParams();

       try {
           params.put("id",Helpers.getTaxi_id());
       }catch (Exception e){}
        params.put("from",txt_from.getText().toString());
        params.put("till",txt_until.getText().toString());
        params.put("distention",txt_city.getText().toString());
        params.put("description",txt_demand.getText().toString());
        params.put("api_token",Helpers.getSharePrf("api_token"));

        Helpers.client.post(Helpers.baseUrl+"taxi_leave", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
           //     Toast.makeText(ApplyingForExitActivity.this, "", Toast.LENGTH_SHORT).show();
               // pd.dismiss();
             //  noInternetDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
          //      pd.dismiss();
           try {
               if (responseString.contains("ok")) {

                   Alertdialog_confrim();

//                    Intent i1 = new Intent(getApplicationContext(), VerificationActivity.class);
//                    i1.setAction(Intent.ACTION_MAIN);
//                    i1.addCategory(Intent.CATEGORY_HOME);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    i1.putExtra("mobile", mobile_input.getText().toString());
//
//                    startActivity(i1);
//                    overridePendingTransition(0, 0);
//
//                    finish();

               }
           }catch (Exception e){}
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
