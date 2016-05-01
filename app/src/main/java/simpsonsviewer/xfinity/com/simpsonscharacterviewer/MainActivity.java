package simpsonsviewer.xfinity.com.simpsonscharacterviewer;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import simpsonsviewer.xfinity.com.simpsonscharacterviewer.Model.CustomAdapter;
import simpsonsviewer.xfinity.com.simpsonscharacterviewer.Model.ObjectDescriptor;

public class MainActivity extends AppCompatActivity {

    private final String URL_REQUEST = "http://api.duckduckgo.com/?q=simpsons+characters&format=json";
    private List<ObjectDescriptor> characterShortDetail = new ArrayList<>();
    private RecyclerView recyclerView;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 112;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        start();
    }


    public void start(){


        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                    Log.d("Result", "onCreate: " + "Show explanation");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                            REQUEST_CODE_ASK_PERMISSIONS);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }
            } else {
                Log.d("Result", "onCreate: " + "Permission already granted!");
                getData();
            }

        }else{
            getData();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Result", "onRequestPermissionsResult: Good to go!");
                    getData();

                } else {
                    Log.d("Result", "onRequestPermissionsResult: Bad user");
                }
            }
        }
    }




    public void getData(){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_REQUEST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("RelatedTopics");
                    for(int i = 0 ; i<jsonArray.length(); i++){
                        ObjectDescriptor od = new ObjectDescriptor();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject icon = jsonObject.getJSONObject("Icon");
                        od.setImageUrl(icon.optString("URL","Unknown"));
                        od.setDetailUrl(jsonObject.optString("FirstURL","Unknown")+"&format=json");
                        String[] details = jsonObject.optString("Text","Unknown").split("-",2);
                        od.setCharacterName(details[0]);
                        od.setCharacterDetail(details[1]);
                        characterShortDetail.add(od);
                        fillList();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

    }
    public void fillList(){
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),characterShortDetail,this);
        recyclerView.setAdapter(adapter);
    }
}
