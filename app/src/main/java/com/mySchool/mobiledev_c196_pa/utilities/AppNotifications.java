package com.mySchool.mobiledev_c196_pa.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.mySchool.mobiledev_c196_pa.R;

public class AppNotifications extends BroadcastReceiver {
    private static final String CHANNEL = "Channel";
    private static final String TEXT = "Text";
    private static final String COURSE_CHANNEL = "Course Reminder";
    private static final String ASSESSMENT_CHANNEL = "Assessment Reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channel;
        int notificationID = intent.getIntExtra(CHANNEL, 0);
        if (notificationID == 1) {
            channel = COURSE_CHANNEL;
        } else {
            channel = ASSESSMENT_CHANNEL;
        }
        Notification n = new NotificationCompat.Builder(context, channel)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(channel)
                .setContentText(intent.getStringExtra(TEXT))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(notificationID, n);
        //TODO clear pending intent after notification.
    }

    /**
     * Initialize at start of application.
     * @param context context.
     */
    public static void createNotificationChannel(Context context) {
        CharSequence course = context.getString(R.string.course_channel_name);
        CharSequence assessment = context.getString(R.string.assessment_channel_name);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel courseChannel = new NotificationChannel(COURSE_CHANNEL, course, importance);
        NotificationChannel assessmentChannel = new NotificationChannel(ASSESSMENT_CHANNEL,assessment,importance);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(courseChannel);
        notificationManager.createNotificationChannel(assessmentChannel);
    }

    /**
     * Set an Alarm notification using this static method.
     *
     * @param context context.
     * @param type 1 = Course & 2 = Assessment.
     * @param text Text to appear in notification.
     * @param id The object rowID.
     * @param end false if beginning, true if end.
     */
    public static PendingIntent pendingIntentLoader(Context context, int type, String text, int id, boolean end) {
        int separator = (end)? 1 : 0;
        Intent intent = new Intent(context, AppNotifications.class);
        intent.putExtra(CHANNEL, type);
        intent.putExtra(TEXT, text);
        // Ensure each PendingIntent has a unique request code.
        return PendingIntent.getBroadcast( context,
                Integer.parseInt(type+""+id+""+separator),intent,
                PendingIntent.FLAG_ONE_SHOT);
    }
    /**
     * To check if broadcast was already created.
     * Must match the pendingIntent that would have been created.
     * @param context context.
     * @param type 1 = Course & 2 = Assessment.
     * @param text Text to appear in notification.
     * @param id The object rowID.
     * @param end false if beginning, true if end.
     */
    public static boolean checkPendingIntent(Context context, int type, String text, int id, boolean end) {
        int separator = (end)? 1 : 0;
        Intent intent = new Intent(context, AppNotifications.class);
        intent.putExtra(CHANNEL, type);
        intent.putExtra(TEXT, text);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, Integer.parseInt(type+""+id+""+separator),intent,
                PendingIntent.FLAG_NO_CREATE + PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent != null;
    }
}