package vickypatel.ca.volleycache;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.RemoteConnection;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class SecondActivity extends AppCompatActivity {

    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        volleySingleton = new VolleySingleton(this);
        requestQueue = volleySingleton.getRequestQueue();
        imageLoader = volleySingleton.getImageLoader();


        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new UrlConnectionDownloader(this));
        Picasso built = builder.build();
        built.setLoggingEnabled(true);
        built.setIndicatorsEnabled(true);


        String data = getIntent().getStringExtra("data");


        System.out.println("singleton object");



        System.out.println("again added some data after push");

        TextView textView = (TextView) findViewById(R.id.text2);



        gridLayout = (GridLayout) findViewById(R.id.gridLayout);



        int total = 50;
        int column = 2;
        int row = total / column;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row + 1);

        for (int i = 1, c = 0, r=0; i < 50; i++, c++) {
            final String URL = "http://dev.trainerpl.us/img/exercises/jpgs/" + i + ".jpg";
            final ImageView newImageView = new ImageView(this);

//            imageLoader.get("http://dev.trainerpl.us/img/exercises/jpgs/" + i + ".jpg", new ImageLoader.ImageListener() {
//                @Override
//                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    newImageView.setImageBitmap(response.getBitmap());
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//
//            });


            Picasso.with(SecondActivity.this)
                    .load(URL)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(newImageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(SecondActivity.this)
                                    .load(URL)
                                    .into(newImageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                        }
                                    });
                        }
                    });

            if(c == column)
            {
                c = 0;
                r++;
            }

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            newImageView.setLayoutParams(param);

            gridLayout.addView(newImageView);


        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
