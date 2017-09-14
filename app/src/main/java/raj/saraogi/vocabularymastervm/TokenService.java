package raj.saraogi.vocabularymastervm;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raj Saraogi on 28-05-2016.
 */
public class TokenService extends FirebaseInstanceIdService {
    RequestQueue requestQueue ;
    @Override
    public void onTokenRefresh() {
        Log.d("In1231111 ","In on token refresh");
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("default");
        Log.d("TAGTOKEN", "Refreshed token: " + refreshedToken);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);

    }

    public void sendRegistrationToServer(String token) {
       /* pDialog.setMessage("Registering ...");
        showDialog();*/
        // Log.e("details", name + " " + email + " " + " " + " " + phone + " " + " " + college + " " + " " + ip + " " + eventName + " " + eventDate + " " + eventTime);
        Log.d("token12355","In sending token registration method");
        final HashMap<String, String> bodyParams = new HashMap<>();
        bodyParams.put("token",token);
        StringRequest request = new StringRequest(Request.Method.POST,"http://myandroiddevelopment.esy.es/tokenreg.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("token123qResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    boolean error = object.getBoolean("error");
                    if (error) {
                        Log.e("token123", "token success ");
                       // Toast.makeText(getApplicationContext(), "Thanks For Sharing!!", Toast.LENGTH_LONG).show();
                      //  dialog.cancel();
                    } else {
                        Log.e("token123", "token failed ");
                    //    Toast.makeText(getApplicationContext(), "Check Your Internet", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("token123", "token failed exception  ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("token123", "token failed volley error ");
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