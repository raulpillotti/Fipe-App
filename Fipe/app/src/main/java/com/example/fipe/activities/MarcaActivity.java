package com.example.fipe.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fipe.R;
import com.example.fipe.adapter.MarcasAdapter;
import com.example.fipe.banco.BDMarca;
import com.example.fipe.model.Dados;
import com.example.fipe.model.Marca;
import com.example.fipe.rest.ApiClient;
import com.example.fipe.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarcaActivity extends AppCompatActivity {

    private String vehicleType;
    private List<Marca> marcas = new ArrayList<>();
    private BDMarca bdMarca;
    private MarcasAdapter adapter;
    private RecyclerView recyclerView;
    private Button buttonAtualizar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recebeVehicleType();
        setTitle("Selecione uma Marca");
        setContentView(R.layout.activity_app);
        fetchMarcas();

        if (isOnline()) {
            if (Dados.getInstance().getAux().equals(vehicleType)) {
                Dados.getInstance().setAux(vehicleType);
            } else {
                Dados.getInstance().setAux(vehicleType);
                bdMarca.deleteAllMarcas();
                fetchMarcas();
            }
        }

        buttonAtualizar = (Button) findViewById(R.id.buttonAtualizar);
        if (isOnline()) {
            buttonAtualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bdMarca.deleteAllMarcas();
                    Toast.makeText(getApplicationContext(), "Itens reinseridos!!", Toast.LENGTH_SHORT).show();
                    fetchMarcas();
                }
            });
        } else {
            buttonAtualizar.setVisibility(View.GONE);
        }

    }

    public void fetchMarcas() {
        recyclerView = (RecyclerView) findViewById(R.id.marca_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bdMarca = new BDMarca(this);

        ApiInterface apiService = ApiClient.getIdMarca().create(ApiInterface.class);

        Call<List<Marca>> call = apiService.listMarcas(vehicleType);
        call.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                if (response.isSuccessful()) {
                    marcas = response.body();

                    if (bdMarca.isEmpty()) {
                        for (Marca marca : marcas) {
                            bdMarca.addMarca(marca);
                        }
                    }

                    List<Marca> allMarcas = bdMarca.getAllMarcas();
                    adapter = new MarcasAdapter(allMarcas);

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable exception) {
                List<Marca> allMarcas = bdMarca.getAllMarcas();
                adapter = new MarcasAdapter(allMarcas);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                exception.printStackTrace();
            }
        });
    }

    public void recebeVehicleType() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vehicleType = extras.getString("vehicleType");
        }
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

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}