package ir.idpz.taxi.driver;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.parisa.viewpager.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.driver.Utils.AppController;
import ir.idpz.taxi.driver.Utils.Helpers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ir.idpz.taxi.driver.Utils.AppController.getAppContext;
import static ir.idpz.taxi.driver.Utils.Helpers.isNetworkAvailable;
import static ir.idpz.taxi.driver.Utils.Helpers.noInternetDialog;

/**
 * Created by parisa on 1/25/2019.
 */
@SuppressLint("ValidFragment")

public class CapacityFragment extends Fragment {

//    ImageView img1, img2, img3, img4, clickedImg;
//    boolean flg1, flg2, flg3, flg4;

    ImageButton pluse, minuse;
    Button capacity_btn;
    ImageView person1, person2, person3, person4;
    int count;
    TextView title2, explain;

    Context context;
    Button state_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.capacity_fragment2, container, false);
        Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
                "IRANSans(FaNum).ttf");

        state_btn = v.findViewById(R.id.state_btn);

        state_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state_btn.getText().equals("فعال")) {

                    Snackbar.make(v, "برای غیرفعال سازی وضعیت خود دست خود را بر روی دکمه نگه دارید.", Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(v, "برای فعال سازی وضعیت خود دست خود را بر روی دکمه نگه دارید.", Snackbar.LENGTH_SHORT).show();

            }
        });
        state_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isNetworkAvailable(context)) {
                    if (state_btn.getText().equals("فعال")) {
                        Enable_Desable(1);
                        state_btn.setBackgroundColor(getResources().getColor(R.color.green_700));
                        state_btn.setText("غیرفعال");
                    } else if (state_btn.getText().equals("غیرفعال")) {
                        Enable_Desable(2);
                        state_btn.setBackgroundColor(getResources().getColor(R.color.blue_700));
                        state_btn.setText("فعال");
                    }
                } else noInternetDialog(context);
                return true;
            }
        });


        capacity_btn = v.findViewById(R.id.capacity);
        capacity_btn.setTypeface(face);

        title2 = v.findViewById(R.id.title2);
        explain = v.findViewById(R.id.explain);

        title2.setTypeface(face);

        explain.setTypeface(face);


        pluse = v.findViewById(R.id.pluse_btn);

        minuse = v.findViewById(R.id.minuse_btn);

        person1 = v.findViewById(R.id.person1);

        person2 = v.findViewById(R.id.person2);
        person3 = v.findViewById(R.id.person3);
        person4 = v.findViewById(R.id.person4);


        pluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(context)) {

                    if (count < 4)
                        count++;
                    capacity_btn.setText(String.valueOf(count));
                    setPerson(count);
                    try {
                        capacity(Helpers.getSharePrf("user_id"), count);
                    } catch (Exception e) {
                    }
                } else noInternetDialog(context);

            }
        });


        minuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(context)) {

                    if (count >= 1 && count != 0)
                        count--;
                    capacity_btn.setText(String.valueOf(count));
                    setPerson(count);
                    try {
                        capacity(Helpers.getTaxi_id(), count);
                    } catch (Exception e) {
                    }
                }else noInternetDialog(context);
            }
        });


//
//        img1 = v.findViewById(R.id.img1);
//        img2 = v.findViewById(R.id.img2);
//        img3 = v.findViewById(R.id.img3);
//
//        img4 = v.findViewById(R.id.img4);
//
//
//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                clickedImg = img1;
//                setClickedImg();
//               // img1.setVisibility(img1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//
//            }
//        });
//        img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               clickedImg = img2;
//               setClickedImg();
//                //img2.setVisibility(img2.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
//
//            }
//        });
//        img3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               clickedImg = img3;
//                setClickedImg();
//              //  img3.setVisibility(img3.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
//
//            }
//        });
//        img4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               clickedImg = img4;
//               setClickedImg();
//               // img4.setVisibility(img4.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
//
//            }
//        });


        return v;
    }


//    public static CapacityFragment newInstance(String text) {
//
//
//        CapacityFragment f = new CapacityFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);
//
//        return f;
//    }


//
//    public void setClickedImg() {
//        if (clickedImg == img1) {
//            if (flg1) {
//                count--;
//                img1.setImageResource(R.drawable.man_invisible);
//                flg1 = false;
//
//////////////////////////////////////////////
//                if (count>0)
//                capacity(Helpers.getTaxi_id(),count);
//           else capacity(Helpers.getTaxi_id(),0);
// ///////////////////////////////////////////
//
//            } else {
//                count++;
//                img1.setImageResource(R.drawable.man_png);
//                flg1 = true;
//
//           capacity(Helpers.getTaxi_id(),count);
//
//            }
//        } else if (clickedImg == img2) {
//            if (flg2) {
//                count--;
//                img2.setImageResource(R.drawable.man_invisible);
//                flg2 = false;
//////////////////////////////////////////////////////
//                if (count>0)
//                    capacity(Helpers.getTaxi_id(),count);
//                else capacity(Helpers.getTaxi_id(),0);
//////////////////////////////////////////////////////
//
//            } else {
//                count++;
//                img2.setImageResource(R.drawable.man_png);
//                flg2 = true;
//          capacity(Helpers.getTaxi_id(),count);
//
//            }
//
//        } else if (clickedImg == img3) {
//            if (flg3) {
//                count--;
//                img3.setImageResource(R.drawable.man_invisible);
//                flg3 = false;
//                ////////////////////////////////////////////////////
//                if (count>0)
//                    capacity(Helpers.getTaxi_id(),count);
//                else capacity(Helpers.getTaxi_id(),0);
//////////////////////////////////////////////////////
//
//            } else {
//                count++;
//                img3.setImageResource(R.drawable.man_png);
//                flg3 = true;
//                capacity(Helpers.getTaxi_id(),count);
//
//            }
//
//        } else if (clickedImg == img4) {
//            if (flg4) {
//                count--;
//                img4.setImageResource(R.drawable.man_invisible);
//                flg4 = false;
//
//                ////////////////////////////////////////////////////
//                if (count>0)
//                    capacity(Helpers.getTaxi_id(),count);
//                else capacity(Helpers.getTaxi_id(),0);
//////////////////////////////////////////////////////
//            } else {
//                count++;
//                img4.setImageResource(R.drawable.man_png);
//                flg4 = true;
//                capacity(Helpers.getTaxi_id(),count);
//
//            }
//        }
//    }


    public void capacity(String id, int capacity) {

        RequestParams params = new RequestParams();

        params.put("id", id);
        params.put("capacity", capacity);
        params.put("api_token",Helpers.getSharePrf("api_token"));

        Helpers.client.post(Helpers.baseUrl + "taxi_capacity", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.contains("ok")) {

                }
            }
        });


    }

    public void setPerson(int count) {
        switch (count) {

            case 0:
                person1.setImageResource(R.drawable.ic_person_gray);
                person2.setImageResource(R.drawable.ic_person_gray);
                person3.setImageResource(R.drawable.ic_person_gray);
                person4.setImageResource(R.drawable.ic_person_gray);

                break;

            case 1:

                person1.setImageResource(R.drawable.ic_person_black);
                person2.setImageResource(R.drawable.ic_person_gray);
                person3.setImageResource(R.drawable.ic_person_gray);
                person4.setImageResource(R.drawable.ic_person_gray);

                break;
            case 2:
                person1.setImageResource(R.drawable.ic_person_black);
                person2.setImageResource(R.drawable.ic_person_black);
                person3.setImageResource(R.drawable.ic_person_gray);
                person4.setImageResource(R.drawable.ic_person_gray);
                break;
            case 3:
                person1.setImageResource(R.drawable.ic_person_black);
                person2.setImageResource(R.drawable.ic_person_black);
                person3.setImageResource(R.drawable.ic_person_black);
                person4.setImageResource(R.drawable.ic_person_gray);
                break;
            case 4:
                person1.setImageResource(R.drawable.ic_person_black);
                person2.setImageResource(R.drawable.ic_person_black);
                person3.setImageResource(R.drawable.ic_person_black);
                person4.setImageResource(R.drawable.ic_person_black);
                break;
        }

    }

    public void Enable_Desable(int pub) {
        RequestParams params = new RequestParams();

        try {
            params.put("id", Helpers.getTaxi_id());
        } catch (Exception e) {
        }
        params.put("pub", pub);

        Helpers.client.post(Helpers.baseUrl + "taxi_turn", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("parisa", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("ok")) {

                        Log.d("parisa", responseString);

                    }
                } catch (Exception e) {
                }
            }
        });
    }

    public CapacityFragment(Context mContext) {
        this.context = mContext;
    }


//@Override
//    public void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//
//    }
}
