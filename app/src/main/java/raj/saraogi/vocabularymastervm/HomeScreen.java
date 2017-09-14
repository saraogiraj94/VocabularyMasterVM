package raj.saraogi.vocabularymastervm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raj.saraogi.vocabularymastervm.About.AboutApp;
import raj.saraogi.vocabularymastervm.About.AboutUS;
import raj.saraogi.vocabularymastervm.Holder.Word;
import raj.saraogi.vocabularymastervm.MyDictionary.AddWord;
import raj.saraogi.vocabularymastervm.MyDictionary.MyDList;

public class HomeScreen extends AppCompatActivity {

    Dialog dialog;
    int position,flag;
    List<Word> list=new ArrayList<>();
    EditText word, name, email;
    private RequestQueue requestQueue;
    String word_name, usr_name, usr_email;
    String TAG_URL = "http://myandroiddevelopment.esy.es/shared.php";
    public static String[] str;

    // the number of pages to show

    private static final int NUM_PAGES = 3;


    /*
     The pager widget that handles animation and allows swiping horizontally to access previous and next pages
     */
    private ViewPager vPager;

    // the pager adapter that provides the pages to the view pager widge
    private PagerAdapter pAdapter;
    private PagerAdapter1 p;

    private Handler handler = new Handler();
    private Runnable runnale = new Runnable() {
        public void run() {
            vPager.setCurrentItem(position, true);
            if (position >= NUM_PAGES) position = 0;
            else position++;
            // Move to the next page after 10s
            handler.postDelayed(runnale, 5000);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        // Initialize position variable used in auto slideshow
        position = 0;
        // Initialize the array of page contents
        str = getResources().getStringArray(R.array.page_content);
        // Instantiate a ViewPager and a PagerAdapter
        vPager = (ViewPager) findViewById(R.id.pager3);

        //Bundle Arguments
        Bundle bundle = getIntent().getExtras();
        flag=bundle.getInt("flag");

        if(flag==0){
            p=new PagerAdapter1(getSupportFragmentManager());
            vPager.setAdapter(p);
        }
        else
        {
            list= (List<Word>) bundle.getSerializable("list");
            pAdapter = new PagerAdapter(getSupportFragmentManager());
            vPager.setAdapter(pAdapter);

        }




        requestQueue = Volley.newRequestQueue(this);

    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Bundle bundle =new Bundle();
            bundle.putInt("pos",pos);
            bundle.putSerializable("list",(Serializable) list);
            SlidePageFragment slidePageFragment = new SlidePageFragment();
            slidePageFragment.setArguments(bundle);
            return slidePageFragment;
            //return SlidePageFragment.newInstance(pos);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    private class PagerAdapter1 extends FragmentStatePagerAdapter {
        public PagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            mainoffline man =new mainoffline();
            return man;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    public void onBackPressed() {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

    }

    public void onPause() {
        super.onPause();
        if (handler != null)
            handler.removeCallbacks(runnale);
    }

    public void onResume() {
        super.onResume();
        // Start auto screen slideshow after 1s
        handler.postDelayed(runnale, 1000);
    }

    public void share(View view) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.shareaword);
               dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        // Tag used to cancel the request
        word = (EditText) dialog.findViewById(R.id.word);
        name = (EditText) dialog.findViewById(R.id.name);
        email = (EditText) dialog.findViewById(R.id.email);
        Button share = (Button) dialog.findViewById(R.id.sharebutton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word_name = word.getText().toString();
                usr_name = name.getText().toString();
                usr_email = email.getText().toString();
             //   Log.e("details", word_name + " " + usr_name + " " + usr_email);
                if (word_name.isEmpty() || usr_email.isEmpty() || usr_name.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG).show();
                } else {
                    senddetails(word_name, usr_name, usr_email);
                }
            }
        });
    }

    public void senddetails(String w_name, String u_name, String u_email) {
       /* pDialog.setMessage("Registering ...");
        showDialog();*/
        // Log.e("details", name + " " + email + " " + " " + " " + phone + " " + " " + college + " " + " " + ip + " " + eventName + " " + eventDate + " " + eventTime);

        final HashMap<String, String> bodyParams = new HashMap<>();
        bodyParams.put("Word", w_name);
        bodyParams.put("Name", u_name);
        bodyParams.put("Email", u_email);
        StringRequest request = new StringRequest(Request.Method.POST, TAG_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    boolean error = object.getBoolean("error");
                        if (error) {
                        Toast.makeText(getApplicationContext(), "Thanks For Sharing!!", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Your Internet", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("registration post", "error in sending data");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Server Could not be reached.\nKindly Check internet connection.\nTry Again Later.", Toast.LENGTH_LONG).show();
                dialog.cancel();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return bodyParams;
            }
        };
        requestQueue.add(request);

    }

    public void call1(View view){
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",(Serializable) list);
        startActivity(new Intent(this,NameList.class).putExtras(bundle));
    }
    public void call2(View view){
        startActivity(new Intent(this,StaredWordList.class));
    }

    public void addmyd(View view){startActivity(new Intent(this, HallofFame.class));}

    public void myd(View view){
        startActivity(new Intent(this, MyDList.class));
    }

    public void aboutus(View view){
        startActivity(new Intent(this, AboutUS.class));
    }

    public void aboutapp(View view){
        startActivity(new Intent(this, AboutApp.class));
    }
}


