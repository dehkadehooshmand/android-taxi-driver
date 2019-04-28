package ir.idpz.taxi.driver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.parisa.viewpager.R;

import java.util.ArrayList;

import ir.idpz.taxi.driver.Adapter.HistoryAdapter;
import ir.idpz.taxi.driver.Adapter.MessageAdapter;
import ir.idpz.taxi.driver.Classes.Message;
import ir.idpz.taxi.driver.Classes.Request;
import ir.idpz.taxi.driver.Utils.AppController;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MessageActivity extends AppCompatActivity {
    RecyclerView messages_recycle;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        arrow_back=findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        messages_recycle = findViewById(R.id.messages);

        Message m = new Message();
        m.setTitle("به روزآوری نرم افزار");
        m.setDate("98.8.19");
        m.setBody("باسلام نسخه جدید برنامه را میتوانید با مراجعه...");


        Message m2 = new Message();
        m2.setTitle("هدیه");
        m2.setDate("97.5.1");
        m2.setBody(" باسلام شما برنده ی 20000هزار توان شارژ هدیه...");


        ArrayList<Message> messages = new ArrayList<>();
        messages.add(m);
        messages.add(m2);

        MessageAdapter adaptor = new MessageAdapter(messages);

        messages_recycle.setAdapter(adaptor);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(AppController.getAppContext(), LinearLayoutManager.VERTICAL, true);
        messages_recycle.setLayoutManager(layoutManager);
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
