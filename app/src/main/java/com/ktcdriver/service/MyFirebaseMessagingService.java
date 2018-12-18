package com.ktcdriver.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ktcdriver.R;
import com.ktcdriver.activities.home.HomeActivity;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rakhi on 10/16/2018.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    private static final int REQUEST_CODE = 123456;
    private int NOTIFICATION_ID = 24;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    /*    startService(new Intent(this, PlaySound.class));
//        MediaPlayer oneMedia = MediaPlayer.create(getApplicationContext(), R.raw.sound);
//        oneMedia.start();
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            MediaPlayer oneMedia = MediaPlayer.create(getApplicationContext(), R.raw.sound);
            oneMedia.start();
        }

        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//20905 ACC
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }*/

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        String pushMessages = remoteMessage.getNotification().getBody();

     /*   sharedPreference = SharedPreference.getInstance ( this );
        sharedPreference.putString ( Constants.SOUND, sound );*/
        showNotifications(pushMessages);

    }
//
//    private void handleNotification(String message) {
//
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            startService(new Intent(this, PlaySound.class));
//            MediaPlayer oneMedia = MediaPlayer.create(getApplicationContext(), R.raw.sound);
//            oneMedia.start();
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//            startService(new Intent(this, PlaySound.class));
//            // play notification sound
//
////            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
////            notificationUtils.playNotificationSound();
//
//
//        } else {
//            startService(new Intent(this, PlaySound.class));
////            MediaPlayer oneMedia = MediaPlayer.create(getApplicationContext(), R.raw.sound);
////            oneMedia.start();
//            // If the app is in background, firebase itself handles the notification
//        }
//    }
//
//    private void handleDataMessage(JSONObject json) {
//        Log.e(TAG, "push json: " + json.toString());
//        TinyDB tinyDB = new TinyDB(getApplicationContext());
//        tinyDB.putString("FromNoti", "1");
//        try {
//            JSONObject data = json.getJSONObject("data");
//
//            String title = data.getString("title");
//            String message = data.getString("message");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
//            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);
//
//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                MediaPlayer oneMedia = MediaPlayer.create(getApplicationContext(), R.raw.sound);
//                oneMedia.start();
////                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
////                notificationUtils.playNotificationSound();
//
//            } else {
//                // app is in background, show the notification in notification tray
//
//                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
//                resultIntent.putExtra("message", message);
//
//                // check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


    private void showNotifications(String pushMessagess) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "com.ktcdriver";
        String channelName = "KTC";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent;
        intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri notificationa = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("KTC")
                .setAutoCancel(true)
                .setContentText(pushMessagess)
                .setContentIntent(pendingIntent).setSound(notificationa)
                .setSmallIcon(R.drawable.app_icon)
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
         /*else {
            Notification notification = new NotificationCompat.Builder ( this )
                    .setContentTitle ( "KTC" )
                    .setAutoCancel ( true )
                    .setContentText ( pushMessagess )
                    .setContentIntent ( pendingIntent )
                    .setSmallIcon ( R.mipmap.ic_launcher_round )
                    .build ();
            notificationManager.notify ( NOTIFICATION_ID, notification );
        }*/

    }


}
