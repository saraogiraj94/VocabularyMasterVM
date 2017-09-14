package raj.saraogi.vocabularymastervm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import raj.saraogi.vocabularymastervm.Holder.Word;

public class MainActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    Button b1;
    Button b2;
    private RequestQueue requestQueue;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        requestQueue = Volley.newRequestQueue(this);
        if(Config.arrayList.isEmpty()) {
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private JsonArrayRequest getDataFromServer() {



        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.Word_Url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response

                        parseData(response);
                        //Hiding the progressbar
                        // progressBar.setVisibility(View.GONE);
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

        //Returning the request
        return jsonArrayRequest;
    }
    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());

    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Word word =new Word();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

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
            Config.arrayList.add(word);
            Log.d("arraylist",word.getWname()+" "+word.getDesc()+" "+word.getUrl()+" "+word.getUse()+" "+word.getSynonyms()+" "+word.getExample());
        }
    }

    public void my(View view){
        //Config.arrayList.clear();
        if(Config.arrayList.isEmpty())
            getData();
        startActivity(new Intent(this,HomeScreen.class));
    }

}
