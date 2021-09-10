package io.alienlabs.gichukipaul.retro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MainData> arrayList;
    MainAdapter adapter;
    private static final String TAG = " ARRAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MainAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        getData();

    }

    private void getData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(" Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);

        Call<String> call = mainInterface.STRING_CALL();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "ENTERING ENQUEUE");
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    try {
                        JSONArray array = new JSONArray(response.body());
                        Log.i(TAG, array.toString());
                        parseArray(array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void parseArray(JSONArray array) {
        arrayList.clear();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                MainData data = new MainData();
                String url = jsonObject.getString("download_url");
                String author = jsonObject.getString("author");
                data.setImage(url);
                data.setName(author);
                arrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new MainAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
    }
}