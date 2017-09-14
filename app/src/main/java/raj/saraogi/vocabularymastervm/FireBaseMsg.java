package raj.saraogi.vocabularymastervm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Raj Saraogi on 28-05-2016.
 */
public class FireBaseMsg extends FirebaseMessagingService {
    String tittle, body,imageUrl;
    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    Bitmap image;
    String pos;
    boolean isImage;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("inonmessagerecieved", "in on message recieved"+remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        Log.d("NOTTAGDATA",data.toString());
        String from = remoteMessage.getFrom();
        imageUrl=null;
        tittle=data.get("tittle");
        body=data.get("body");
        if(tittle!=null)
            Log.d("NOTTitle",tittle);
        Log.d("NOTbody",body);

        if(data.get("imageFlag").equals("true")) {
            imageUrl = data.get("imageUrl");
            image = getBitmapfromUrl(imageUrl);
            pos = data.get("pos");
            isImage = true;
        }else {
            isImage=false;
        }

        createNotification();
        //RemoteMessage.Notification notification = remoteMessage.getNotification();
       // createNotification(notification);
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        /*Intent dialogIntent = new Intent(this,TestNote.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);*/
        //  handleNotification(body,tittle);
        //  LooperThread looperThread= new LooperThread();
        //    looperThread.start();
        //
    }

    private void createNotification() {
        Context context = getBaseContext();
        Intent notificationIntent ;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bundle bundle = new Bundle();
        if(isImage){
            Log.d("positioninfcm", String.valueOf(pos));
            bundle.putString("pos",pos);
            bundle.putString("imageFlag","true");
            notificationIntent=new Intent(context,SplashScreen.class).putExtra("pos",pos);
        }else {
            Log.d("NOTTYPE","Without imgae");

            notificationIntent=new Intent(context,SplashScreen.class);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                MESSAGE_NOTIFICATION_ID, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.logo);
        int color= getResources().getColor(R.color.yelloish);
        //If image is there then create image notification
        if(imageUrl!=null){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.vm_logo).setContentTitle(tittle)
                .setContentText(body)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setContentIntent(contentIntent);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
        }else{
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.vm_logo).setContentTitle(tittle)
                    .setContentText(body)
                    .setColor(color)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent);
            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
        }
    }
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
