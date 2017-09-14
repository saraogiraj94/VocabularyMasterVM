package raj.saraogi.vocabularymastervm;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Holder.Word;

public class SplashScreen extends AppCompatActivity {

    ImageView v, m, w1, w2, f;
    List<Word> arrayList = new ArrayList<>();
    private RequestQueue requestQueue;
    int flag = 0;
    ProgressBar progressBar;
    String url = "http://myandroiddevelopment.esy.es/words.php";
    ObjectAnimator animation;
    boolean imageFlag;
    String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_splash_screen);
       // v = (ImageView) findViewById(R.id.imageView2);

        final Bundle bundle = new Bundle();
        if (getIntent().getExtras() != null) {
            Log.d("getintentonclick",getIntent().getExtras().toString());

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);

                if (key.equals("pos")) {
                    pos=value;
                    Log.d("positioninsplash",pos);
                    imageFlag=true;
                }

            }

   /*         Bundle bundle1 =getIntent().getExtras();
            Log.d("bundle1",bundle1.toString());
            Log.d("imageFlag",bundle1.getString("imageFlag"));
            if(bundle1.getString("imageFlag").equals("true")){
                imageFlag=true;
                pos=bundle1.getString("pos");
                Log.d("postion",bundle1.getString("pos"));

                Log.d("positioninsplash", String.valueOf(pos));
            }
            else if(bundle1.getString("imageFlag").equals("false")){
                imageFlag=false;
            }
            else
                imageFlag=false;*/
        }


        //  v.setVisibility(View.INVISIBLE);
/*        f = (ImageView) findViewById(R.id.imageView);
        f.setVisibility(View.INVISIBLE);
        m = (ImageView) findViewById(R.id.imageView3);
        m.setVisibility(View.INVISIBLE);
        w1 = (ImageView) findViewById(R.id.imageView4);
        w1.setVisibility(View.INVISIBLE);
        w2 = (ImageView) findViewById(R.id.imageView5);
        w2.setVisibility(View.INVISIBLE);

        new CountDownTimer(500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                v.setVisibility(View.VISIBLE);
            }
        }.start();
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                m.setVisibility(View.VISIBLE);
            }
        }.start();
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                w1.setVisibility(View.VISIBLE);
            }
        }.start();
        new CountDownTimer(3000, 500) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                f.setVisibility(View.VISIBLE);
            }
        }.start();
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                w2.setVisibility(View.VISIBLE);
            }
        }.start();
        new CountDownTimer(5000, 500) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Log.d("key","in on finish final");
                if (!isNetworkAvailable()) {
                    Log.e("flag", String.valueOf(flag));

                    bundle.putInt("flag", flag);
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class).putExtras(bundle));
                    finish();

                } else {

                    progressDialog.show();
                    getList();

*//*
                        bundle.putInt("flag", flag);
                    bundle.putSerializable("list", (Serializable) arrayList);
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class).putExtras(bundle));
*//*
                }

            }
        }.start();
   *//*     if (isNetworkAvailable()) {
            progressDialog.setTitle("Loading");
            progressDialog.show();
            getList();
           *//**//* new AsyncTask<Void,Void,Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    getList();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    flag=1;
                }
            }.execute();

        }*//**//*

        }*/
        if (!isNetworkAvailable()) {
            Log.e("flag", String.valueOf(flag));

            bundle.putInt("flag", flag);
            new CountDownTimer(500, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    progressBar= (ProgressBar)findViewById(R.id.progressBar);
                    progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class).putExtras(bundle));
                    finish();
                }
            }.start();


        } else {
            progressBar= (ProgressBar)findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
            progressBar.setVisibility(View.VISIBLE);
         //  progressDialog.show();
            getList();

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getList() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        for (int i = 0; i < response.length(); i++) {
                            //Creating the superhero object
                          //  progressDialog.hide();

                            Word word = new Word();
                            JSONObject json = null;
                            try {
                                //Getting json
                                json = response.getJSONObject(i);

                                //Adding data to the superhero object
                                word.setUrl(json.getString(Config.TAG_IMAGE_URL));
                                word.setWname(json.getString(Config.TAG_WORD_NAME));
                                word.setDesc(json.getString(Config.TAG_WORD_DESC));
                                word.setUse(json.getString(Config.TAG_WORD_USE));
                                word.setSynonyms(json.getString(Config.TAG_WORD_SYNONYMS));
                                word.setExample(json.getString(Config.TAG_WORD_EXAMPLE));
                                word.setId(json.getString(Config.TAG_WORD_ID));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Adding the superhero object to the list
                            arrayList.add(word);
                            Log.d("arraylist", word.getWname() + " " + word.getDesc() + " " + word.getUrl() + " " + word.getUse() + " " + word.getSynonyms() + " " + word.getExample());
                        }
                        flag = 1;

                        Bundle bundle = new Bundle();
                        bundle.putInt("flag", flag);
                        bundle.putSerializable("list", (Serializable) arrayList);
                        //If image notification is clicked then display word detail
                        if(imageFlag){
                            Bundle bundle3 = new Bundle();
                            bundle3.putSerializable("list",(Serializable)arrayList);
                            bundle3.putInt("pos", Integer.parseInt(pos));
                            startActivity(new Intent(getApplicationContext(),Main2Activity.class).putExtras(bundle3));
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), HomeScreen.class).putExtras(bundle));
                        }
                        progressBar.setVisibility(View.VISIBLE);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        //   Toast.makeText(Packing.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);

    }
}
