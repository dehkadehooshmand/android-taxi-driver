package ir.idpz.taxi.driver.Adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parisa.viewpager.R;

import java.util.List;

import ir.idpz.taxi.driver.Classes.Message;
import ir.idpz.taxi.driver.Classes.Request;
import ir.idpz.taxi.driver.Utils.AppController;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    List<Message> messages;
    Message message;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_adapter, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        message = messages.get(position);



        holder.title.setText(message.getTitle());

        holder.body.setText(message.getBody());

        holder.date.setText(message.getDate());




    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, body;
        public MyViewHolder(View view) {
            super(view);

            Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
                    "IRANSans(FaNum).ttf");
            title = view.findViewById(R.id.title);
            body = view.findViewById(R.id.body);
            date = view.findViewById(R.id.date);


            title.setTypeface(face);

        }


    }

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }
}
