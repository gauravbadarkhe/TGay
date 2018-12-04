package tgay.gbad555.com.tgay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MusicService extends Service {

    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
        mp=MediaPlayer.create(this,R.raw.music);
        mp.setLooping(false);
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        mp.start();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        mp.stop();
    }
}
