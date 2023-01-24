package com.example.fipe.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fipe.R;
import com.example.fipe.adapter.ModeloAdapter;
import com.example.fipe.banco.BDModelo;
import com.example.fipe.model.Dados;
import com.example.fipe.model.Modelo;
import com.example.fipe.rest.ApiClient;
import com.example.fipe.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloActivity extends AppCompatActivity {

    private int id_marca;
    private List<Modelo> modelos = new ArrayList<>();
    private BDModelo bdModelo;
    private RecyclerView recyclerView;
    private ModeloAdapter adapter;
    private Button buttonAtualizar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recebeIdMarca();
        setTitle("Selecione um Modelo");
        setContentView(R.layout.activity_app);

        buttonAtualizar = (Button) findViewById(R.id.buttonAtualizar);
        buttonAtualizar.setVisibility(View.GONE);

        fetchModelos();
    }

    public void fetchModelos(){
        recyclerView = (RecyclerView) findViewById(R.id.marca_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bdModelo = new BDModelo(this);

        ApiInterface apiService = ApiClient.getIdModelo().create(ApiInterface.class);

        Call<List<Modelo>> call = apiService.listModelos(Dados.getInstance().getIdBrand(), id_marca);
        call.enqueue(new Callback<List<Modelo>>() {
            @Override
            public void onResponse(Call<List<Modelo>> call, Response<List<Modelo>> response) {
                if(response.isSuccessful()){
                    bdModelo.deleteAllModelos();
                    modelos = response.body();
                    if(bdModelo.isEmpty()){
                        for (Modelo modelo : modelos){
                            bdModelo.addModelo(modelo);
                        }
                    }
                    List<Modelo> allModelos = bdModelo.getAllModelos();

                    adapter = new ModeloAdapter(allModelos);

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Modelo>> call, Throwable exception) {
                List<Modelo> allModelos = bdModelo.getAllModelos();
                adapter = new ModeloAdapter(allModelos);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                exception.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private void recebeIdMarca(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_marca = extras.getInt("id_marca");
        }
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}