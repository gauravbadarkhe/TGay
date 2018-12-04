package tgay.gbad555.com.tgay;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MusicService extends Service {

    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
        /*mp=MediaPlayer.create(this,R.raw.music);
        mp.setLooping(true);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);*/

    }

    public int onStartCommand(Intent intent,int flags,int startId){

         mp= MediaPlayer.create(this,R.raw.music);
        //mp.setLooping(true);
        //mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.d(MainActivity.class.getName(), "onError: ");
                return false;
            }
        });
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        mp.stop();
    }

    /*private void addNotification(long dif) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default");
        mBuilder.setContentTitle("Live Difference").setContentText(""+dif).setSmallIcon(R.mipmap.ic_launcher)
        .setOngoing(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null,null);
            mNotificationManager.createNotificationChannel(channel);
        }

        Notification notification = mBuilder.build();
        startForeground(012345,notification);

    }*/
}
