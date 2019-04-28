package ir.idpz.taxi.driver.CloudMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


import com.example.parisa.viewpager.R;
import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ir.idpz.taxi.driver.MainActivity;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";

    /**
     * این متد زمانی که نوتیفیکشن دریافت شد فرخوانی می شود
     *
     * @Param remoteMessage : برای گرفتن دیتا استفاده می شود
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
if (remoteMessage.getNotification().getBody().length()>0){

}
        if (remoteMessage.getData().size() > 0) {

           ArrayList<String> data= (ArrayList<String>) remoteMessage.getData();

         Intent newReq=new Intent(getApplicationContext(),MainActivity.class);

         newReq.putExtra("newReq", (Serializable) remoteMessage.getData());
         startActivity(newReq);
}



        createNotification(remoteMessage.getNotification().getBody());
    }

    private void createNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("پیام جدید")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }




}
