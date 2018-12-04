package tgay.gbad555.com.tgay;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonParser;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TickerView pewd, tseries, diff;
    //TextView diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this,MusicService.class));

        pewd = findViewById(R.id.tv_pew);
        tseries = findViewById(R.id.tv_tseries);
        diff = findViewById(R.id.tv_dif);
        pewd.setCharacterLists(TickerUtils.provideNumberList());
        tseries.setCharacterLists(TickerUtils.provideNumberList());
        diff.setCharacterLists(TickerUtils.provideNumberList());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    loadSubs();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }


    public void loadSubs() throws Throwable {

        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String subcount = httpGetRequest.execute().get();
        String[] subscribers = subcount.split(",");
            pewd.setText(subscribers[0]);
            tseries.setText(subscribers[1]);
            try {
                long dif = Long.valueOf(subscribers[0]) - Long.valueOf(subscribers[1]);
                diff.setText(dif + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {

            StringBuilder subs = new StringBuilder();

            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://www.googleapis.com/youtube/v3/channels?part=statistics&id=UC-lHJZR3Gqxm24_Vd_AJ5Yw&fields=items%2Fstatistics%2FsubscriberCount&key=AIzaSyCmRPZ4hLkijSTtJYnjIitvAd45z291Bzs")
                        .get()
                        .build();

                okhttp3.Response response = client.newCall(request).execute();


                Request request2 = new Request.Builder()
                        .url("https://www.googleapis.com/youtube/v3/channels?part=statistics&id=UCq-Fj5jknLsUf-MWSy4_brA&fields=items%2Fstatistics%2FsubscriberCount&key=AIzaSyCmRPZ4hLkijSTtJYnjIitvAd45z291Bzs")
                        .get()
                        .build();


                okhttp3.Response response2 = client.newCall(request2).execute();

                Log.d(MainActivity.class.getName(), "response: " + response.body());

                long subcount = new JSONObject(response.body().string()).getJSONArray("items").getJSONObject(0).getJSONObject("statistics").getLong("subscriberCount");
                long subcount2 = new JSONObject(response2.body().string()).getJSONArray("items").getJSONObject(0).getJSONObject("statistics").getLong("subscriberCount");


                subs.append(subcount).append(",").append(subcount2);

                return subs.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "NA";
        }
    }
}
