package com.example.fipe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fipe.R;
import com.example.fipe.adapter.AnoAdapter;
import com.example.fipe.banco.BDAno;
import com.example.fipe.model.Ano;
import com.example.fipe.model.Dados;
import com.example.fipe.rest.ApiClient;
import com.example.fipe.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnoActivity extends AppCompatActivity {

    private int id_modelo;
    private List<Ano> anos = new ArrayList<>();
    private AnoAdapter adapter;
    private RecyclerView recyclerView;
    private BDAno bdAno;
    private Button buttonAtualizar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recebeIdModelo();
        setTitle("Selecione um Ano");
        setContentView(R.layout.activity_app);

        buttonAtualizar = (Button) findViewById(R.id.buttonAtualizar);
        buttonAtualizar.setVisibility(View.GONE);

        fetchAnos();
    }

    public void fetchAnos(){
        recyclerView = (RecyclerView) findViewById(R.id.marca_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bdAno = new BDAno(this);

        ApiInterface apiService = ApiClient.getIdAno().create(ApiInterface.class);

        Call<List<Ano>> call = apiService.listAnos(Dados.getInstance().getIdBrand(), Dados.getInstance().getIdMarca(), id_modelo);
        call.enqueue(new Callback<List<Ano>>() {
            @Override
            public void onResponse(Call<List<Ano>> call, Response<List<Ano>> response) {
                if(response.isSuccessful()){
                    bdAno.deleteAllAnos();
                    anos = response.body();
                    if(bdAno.isEmpty()){
                        for(Ano ano : anos){
                            bdAno.addAno(ano);
                        }
                    }
                    List<Ano> allAnos = bdAno.getAllAnos();

                    adapter = new AnoAdapter(anos);

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Ano>> call, Throwable exception) {
                List<Ano> allAnos = bdAno.getAllAnos();
                adapter = new AnoAdapter(allAnos);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                exception.printStackTrace();
            }
        });
    }

    private void recebeIdModelo(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_modelo = extras.getInt("id_modelo");
        }
    }
}