package raj.saraogi.vocabularymastervm.MyDictionary;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.HashMap;
import java.util.Map;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.R;

public class AddWord extends AppCompatActivity {
    EditText word, mean, syn, example,name,email,dword;
    Button a;
    private RequestQueue requestQueue;
    Dialog dialog;
    String word_name, usr_name, usr_email;
    DatabaseHelper databaseHelper;
    String TAG_URL = "http://myandroiddevelopment.esy.es/shared.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar8);
        //  toolbar.setTitle("Words List");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        word = (EditText) findViewById(R.id.aword);
        mean = (EditText) findViewById(R.id.amean);
        syn = (EditText) findViewById(R.id.asyn);
        example = (EditText) findViewById(R.id.aexample);
        a = (Button) findViewById(R.id.addButton);
        databaseHelper = new DatabaseHelper(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void add(View view) {
        if (word.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Word", Toast.LENGTH_LONG).show();
        } else {
            Boolean result = databaseHelper.addMyDWord(word.getText().toString(), mean.getText().toString(), syn.getText().toString(), example.getText().toString());
            if (result) {
                Toast.makeText(this,"The Word has been added",Toast.LENGTH_SHORT).show();
                dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.shareaword);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                // Tag used to cancel the request
                dword=(EditText)dialog.findViewById(R.id.word);
                dword.setText(word.getText());
                name = (EditText) dialog.findViewById(R.id.name);
                email = (EditText) dialog.findViewById(R.id.email);
                Button share = (Button) dialog.findViewById(R.id.sharebutton);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        word_name = word.getText().toString();
                        usr_name = name.getText().toString();
                        usr_email = email.getText().toString();
                        Log.e("details", word_name + " " + usr_name + " " + usr_email);
                        if (word_name.isEmpty() || usr_email.isEmpty() || usr_name.isEmpty()) {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter your details!", Toast.LENGTH_LONG).show();
                        } else {
                            senddetails(word_name, usr_name, usr_email);
                        }
                    }
                });

                onRestart();
            }
            else{
                Toast.makeText(this,"Some Error in adding the word",Toast.LENGTH_LONG).show();
            }

        }
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
}

