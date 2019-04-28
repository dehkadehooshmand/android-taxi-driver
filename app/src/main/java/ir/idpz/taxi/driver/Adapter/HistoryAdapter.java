package ir.idpz.taxi.driver.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parisa.viewpager.R;

import java.util.List;

import ir.idpz.taxi.driver.Classes.Request;
import ir.idpz.taxi.driver.Utils.AppController;
import omidi.mehrdad.moalertdialog.MoAlertDialog;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    Typeface face = Typeface.createFromAsset(AppController.getAppContext().getAssets(),
            "IRANSans(FaNum).ttf");
    List<Request> requests;
    Request request;
    Context mContext;
    Activity activity;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_adapter, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        request = requests.get(position);


        holder.origin.setText(request.getOrigin());

        holder.des.setText(request.getDestination());

        holder.name.setText(request.getDrivername());

        holder.price.setText(request.getPrice());


        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView origin, origin_title, des, des_title, price, name, title_name, title_price, detail, support, date;


        public MyViewHolder(View view) {
            super(view);


            origin = view.findViewById(R.id.origin);
            origin_title = view.findViewById(R.id.origin_title);
            des = view.findViewById(R.id.des);
            des_title = view.findViewById(R.id.des_title);
            price = view.findViewById(R.id.price);
            name = view.findViewById(R.id.name);
            title_name = view.findViewById(R.id.title_name);
            title_price = view.findViewById(R.id.title_price);
            detail = view.findViewById(R.id.detail);
            support = view.findViewById(R.id.support);


            date = view.findViewById(R.id.date);
            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog_detail();
                }
            });


            support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog_support();
                }
            });


            origin_title.setTypeface(face);

            des_title.setTypeface(face);

            origin.setTypeface(face);

            des.setTypeface(face);

            support.setTypeface(face);

            detail.setTypeface(face);

            title_price.setTypeface(face);

            title_name.setTypeface(face);

            price.setTypeface(face);

            name.setTypeface(face);

            date.setTypeface(face);


        }


    }

    public HistoryAdapter(List<Request> requests, Activity activity) {
        this.requests = requests;
        this.activity = activity;
    }


    public void AlertDialog_detail() {
        MoAlertDialog dialog = new MoAlertDialog(activity);

        dialog.showSuccessDialog("جزییات", "نمایش جزییات ....");

        dialog.setTypeface(face);

        dialog.setDilogIcon(R.drawable.ic_support);
        dialog.setDialogButtonText("باشه!");
    }

    public void AlertDialog_support() {
        MoAlertDialog dialog = new MoAlertDialog(activity);

        dialog.showSuccessDialog("تماس با پشتیبانی", "پیشنهادات و انتقادات خود را از طریق راه های ارتباطی زیر با ما درمیان بگذارید."
                +
                "\n" +
                "آدرس الکترونیکی :"
                +
                "\n" +
                " idpz@yahoo.com  " +
                "\n"
                +
                "شماره تماس :" +
                "\n" +
                "09144454444");

        dialog.setTypeface(face);

        dialog.setDilogIcon(R.drawable.ic_support);
        dialog.setDialogButtonText("باشه!");
    }
}
