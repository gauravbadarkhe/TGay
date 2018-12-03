package tgay.gbad555.com.tgay;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bastet.socialblade.com/")
                .build();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    loadSubs();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void loadSubs() throws Throwable {

        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String subcount = httpGetRequest.execute().get();
        Log.d(MainActivity.class.getName(), "response: " + subcount);
    }


    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {

            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://bastet.socialblade.com/youtube/lookup?query=UC-lHJZR3Gqxm24_Vd_AJ5Yw")
                        .get()
                        .build();

                okhttp3.Response response = client.newCall(request).execute();
                Log.d(MainActivity.class.getName(), "response: " + response.body());
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "NA";
        }
    }
}
