package raj.saraogi.vocabularymastervm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raj.saraogi.vocabularymastervm.Holder.Users;
import raj.saraogi.vocabularymastervm.Holder.Word;

/**
 * Created by Raj Saraogi on 01-08-2016.
 */
public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.Holder> {
    Context context;
    List<Users> list =new ArrayList<>();
    List<String> usdet= new ArrayList<>();
    RequestQueue requestQueue;
    public MonthlyAdapter(Context context,List<Users> list) {
        this.context = context;
        this.list=list;
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdetail,parent, false);
        Holder mainCardViewHolder = new Holder(view);
        return mainCardViewHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Users user= list.get(position);
        int x=position+1;
        holder.sr.setText(String.valueOf(x));
        holder.name.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sr,name;
        public Holder(View itemView) {
            super(itemView);
            sr=(TextView)itemView.findViewById(R.id.srtv);
            name=(TextView)itemView.findViewById(R.id.nametv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos=getPosition();
            Users users = list.get(pos);
            Log.d("users on click ",users.toString());
            getUserDetails(users.getEmail(),users.getName());
        }
    }

    public void showUserDetails(List<String> listwords,String username){
         Dialog dialog = new Dialog(context);
     //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(username);
        dialog.setContentView(R.layout.userdetails);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        // Tag used to cancel the request
      //  TextView name = (TextView) dialog.findViewById(R.id.userNametv);
     //   name.setText(username);
        RecyclerView recyclerView=(RecyclerView)dialog.findViewById(R.id.rec1);
        RecyclerView.LayoutManager layoutManager;
        RecyclerView.Adapter adapter;

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new UserWordsAdapter(listwords,context);
        recyclerView.setAdapter(adapter);
   /*     share.setOnClickListener(new View.OnClickListener() {
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
*/
    }

    public void getUserDetails(final String email, final String name){
        Log.d("Email id iss ",email);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://myandroiddevelopment.esy.es/getNewUserWords.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("1234455","In onResponse");
                        Log.d("newuserresaasd",response.toString());
                        progressDialog.hide();

                        try {
                            List<String> list= new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("1233333",jsonArray.toString());
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
                                Log.d("123jobj",jsonObject.toString());
                                list.add(i,jsonObject.getString("word"));
                                Log.d("123list",list.toString());
                            }

                            showUserDetails(list,name);


                        } catch (JSONException e) {
                            Toast.makeText(context,"Sorry Error Caught !!!",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        //Calling method parseData to parse the json response
                    /*    for (int i = 0; i < response.length(); i++) {
                            try {
                               // JSONObject jsonObject = response.getJSONObject(i);
                             //   Log.d("user_object",jsonObject.toString());
                                progressDialog.hide();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("1234455","In Error Reponse");
                        Log.d("abcnewuser error",error.toString());
                        Toast.makeText(context,"Sorry Error Caught !!!",Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        //  progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        //   Toast.makeText(Packing.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Log.d("1234455","In Params");
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);

                return params;
            }

        };
       // progressDialog.hide();
        Log.d("usdet",usdet.toString());
        requestQueue.add(stringRequest);
    }
}