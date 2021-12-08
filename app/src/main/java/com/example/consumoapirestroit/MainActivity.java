package com.example.consumoapirestroit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consumoapirestroit.interfaces.GorestService;
import com.example.consumoapirestroit.models.Indicador;
import com.example.consumoapirestroit.models.valorInd;
import com.google.gson.JsonIOException;

import java.sql.SQLOutput;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView lstInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstInfo = findViewById(R.id.lstDatos);
        consumeApiRastroit();
    }

    private void consumeApiRastroit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GorestService.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GorestService gs = retrofit.create(GorestService.class);
        Call<Indicador> data = gs.getPost();
        data.enqueue(new Callback<Indicador>() {
            @Override
            public void onResponse(Call<Indicador> call, Response<Indicador> response) {
                if (!response.isSuccessful()){
                    Log.e("CallService.onResponse", "Error: " + response.code());
                } else{
                    Indicador dat = response.body();
                    fileList(dat);
                }
            }

            @Override
            public void onFailure(Call<Indicador> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }

    private void fileList(Indicador ind){
        String[] valores = new String[ind.getData().size()];
        int i = 0;
        for (valorInd vi: ind.getData()){
            valores[i] = vi.getId().toString();
            System.out.println(vi.getId().toString());
            i++;
        }
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, valores);
        lstInfo.setAdapter(ad);
    }
}