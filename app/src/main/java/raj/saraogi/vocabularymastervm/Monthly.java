package raj.saraogi.vocabularymastervm;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Users;
import raj.saraogi.vocabularymastervm.Holder.Word;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monthly extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    List<Users> arrayList;
    public String url ;
    RequestQueue requestQueue;

    public Monthly() {
        // Required empty public constructor
    }

  /*  public Monthly(String u){
        url=u;
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle b=  getArguments();
        url=b.getString("url");
        Log.d("URL OF MONTHLY",url);


        View v = inflater.inflate(R.layout.fragment_monthly, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_monthly);
        recyclerView.setHasFixedSize(true);
    arrayList=new ArrayList<>();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        requestQueue= Volley.newRequestQueue(getContext());
        getList();
        return v;
    }

    public void getList() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        for (int i = 0; i < response.length(); i++) {
                            //Creating the superhero object
                            progressDialog.hide();
                            Users user = new Users();
                            JSONObject json = null;
                            try {
                                //Getting json
                                json = response.getJSONObject(i);
                                Log.d("obj",json.toString());
                              //  user.setID(json.getString("ID"));
                                user.setName(json.getString(Config.TAG_USER_NAME));
                                user.setEmail(json.getString(Config.TAG_USER_EMAIL));
                                user.setDate(json.getString(Config.TAG_USER_DATE));
                                user.setSelected(json.getString(Config.TAG_USER_SELECTED));

                                //Adding data to the superhero object
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Adding the superhero object to the list
                            arrayList.add(user);
                            Log.d("userarraylist",user.getName()+" "+user.getEmail() );

                        }
                        Log.d("arraylistusers",arrayList.toString());
                        adapter=new MonthlyAdapter(getContext(),arrayList);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Internet Connection Error",Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        //  progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        //   Toast.makeText(Packing.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}


