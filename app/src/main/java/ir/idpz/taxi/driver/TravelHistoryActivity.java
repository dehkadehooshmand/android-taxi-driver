package ir.idpz.taxi.driver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.parisa.viewpager.R;

import java.util.ArrayList;

import ir.idpz.taxi.driver.Adapter.HistoryAdapter;
import ir.idpz.taxi.driver.Classes.Request;
import ir.idpz.taxi.driver.Utils.AppController;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TravelHistoryActivity extends AppCompatActivity {
    RecyclerView travels_recycle;
ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);
        arrow_back=findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Request request = new Request();
        request.setOrigin("تهران _ ایستگاه نوآوری شریف");
        request.setDestination("تهران _ فرودگاه امام خمینی ");
        request.setDrivername("اصغر اصغری");
        request.setPrice("200000ریال");
        request.setDate("دوشنبه بعدظهر 22 بهمن");


        Request request2 = new Request();
        request2.setOrigin("تهران _ ایستگاه نوآوری شریف");
        request2.setDestination("تهران _دانشگاه شریف");
        request2.setDrivername("ممد ممدزاده");
        request2.setPrice("40000ریال");
        request2.setDate("شنبه ظهر 20 بهمن");

        travels_recycle = findViewById(R.id.travels);
        ArrayList<Request> travels = new ArrayList<>();
        travels.add(request);
        travels.add(request2);

        HistoryAdapter adaptor = new HistoryAdapter(travels,TravelHistoryActivity.this);

        travels_recycle.setAdapter(adaptor);
        LinearLayoutManager layoutManager3
                = new LinearLayoutManager(AppController.getAppContext(), LinearLayoutManager.VERTICAL, true);
        travels_recycle.setLayoutManager(layoutManager3);

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
