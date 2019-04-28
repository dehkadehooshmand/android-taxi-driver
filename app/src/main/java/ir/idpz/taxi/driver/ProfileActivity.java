package ir.idpz.taxi.driver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parisa.viewpager.R;

import ir.idpz.taxi.driver.Utils.Helpers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView arrow_back;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    int PICK_PICTURE = 971;

    Button btn_confrim;
    TextView mobile, txx_codemeli, txt_pelak, txt_taxi_code,txt_credit,txt_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        btn_confrim=findViewById(R.id.btn_confrim);
        btn_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"درحال حال حاظر امکان واریز به حساب در این نسخه از نرم افزار وجود ندارد.",Snackbar.LENGTH_SHORT).show();
            }
        });
        txt_pelak = findViewById(R.id.txt_pelak);
        txt_taxi_code = findViewById(R.id.txt_taxi_code);
        txx_codemeli = findViewById(R.id.txt_codemeli);

        txt_credit=findViewById(R.id.txt_credit);

        txt_name=findViewById(R.id.name);


        mobile = findViewById(R.id.mobile);

        txt_credit.setText("0"+" ریال ");
        try {
            txt_taxi_code.setText(Helpers.getCodeTaxi());
            txt_pelak.setText(Helpers.getPelak());
            txx_codemeli.setText(Helpers.getCodemeli());
            txt_name.setText(Helpers.getUser_name());
            mobile.setText(Helpers.getMobile());

        } catch (Exception e) {
        }

        toolbar = findViewById(R.id.toolbar);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        toolbar.setBackgroundColor(Color.TRANSPARENT);


        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


//    private void showBottomSheetDialog() {
//        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }
//
//        final View view = getLayoutInflater().inflate(R.layout.select_pic, null);
//
//        (view.findViewById(R.id.lnCamera)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//
//        (view.findViewById(R.id.lnGallery)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(
//                        Intent.ACTION_PICK,
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_PICTURE);
//            }
//        });
//
//        mBottomSheetDialog = new BottomSheetDialog(this);
//        mBottomSheetDialog.setContentView(view);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        mBottomSheetDialog.show();
//        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                mBottomSheetDialog = null;
//            }
//        });
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
