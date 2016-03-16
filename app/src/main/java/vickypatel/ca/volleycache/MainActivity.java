package vickypatel.ca.volleycache;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        volleySingleton = new VolleySingleton(this);
        requestQueue = volleySingleton.getRequestQueue();
        imageLoader = volleySingleton.getImageLoader();

        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, "https://api.github.com/users/mralexgray/repos", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
                Adapter adapter = new Adapter(MainActivity.this, response);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);

                Cache.Entry entry = requestQueue.getCache().get("0:https://api.github.com/users/mralexgray/repos");
                String data = "";
                if (entry != null) {
                    try {
                        data = new String(entry.data, "UTF-8");

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // process data
                }

                System.out.println("from inside response call " + data);


				System.out.println("branch_test added");
				

				System.out.println("broken feature");

				System.out.println("branch_test 2 added");
				
				System.out.println("Cool branch is also done");

			System.out.println("new branch change");


		        System.out.println("some change in master");

			System.out.println"changes only belongs to new branch and branch created from new branch");



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        jsonObjectRequest.setTag("TAG");
        requestQueue.add(jsonObjectRequest);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(" Cache Key " + jsonObjectRequest.getCacheKey());
                System.out.println(" Another way " + jsonObjectRequest.getMethod() + ":" + "https://api.github.com/users/mralexgray/repos");
                System.out.println(" Request TAG is " + jsonObjectRequest.getTag());

                Cache.Entry entry = requestQueue.getCache().get(jsonObjectRequest.getCacheKey());
                String data = "";
                if (entry != null) {
                    try {
                        data = new String(entry.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // process data
                }

                System.out.println("from main Activity " + data);

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        public Context context;
        public JSONArray jsonObject;

        public Adapter(Context context, JSONArray jsonObject) {
            this.context = context;
            this.jsonObject = jsonObject;

            System.out.println(jsonObject.length() + " total objects");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_recycle_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            try {


                System.out.println(jsonObject.getJSONObject(position).getString("id"));
                String id = jsonObject.getJSONObject(position).getString("id");
                holder.textView.setText(id);


//                imageLoader.get("http://dev.trainerpl.us/img/exercises/jpgs/" + position + ".jpg", new ImageLoader.ImageListener() {
//                    @Override
//                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                        holder.imageView.setImageBitmap(response.getBitmap());
//                    }
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//
//                });

                Cache.Entry entry = requestQueue.getCache().get("0:http://dev.trainerpl.us/img/exercises/jpgs/"+ position + ".jpg");
                String data = "";
                if (entry != null) {
                    try {
                        data = new String(entry.data, "UTF-8");

                        System.out.println("image data " + data);

                        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length));

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // process data
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return jsonObject.length();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
            }


        }

    }
}
