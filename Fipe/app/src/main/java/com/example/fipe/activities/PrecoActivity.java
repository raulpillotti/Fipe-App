package com.example.fipe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fipe.R;
import com.example.fipe.model.Dados;
import com.example.fipe.model.Preco;
import com.example.fipe.rest.ApiClient;
import com.example.fipe.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrecoActivity extends AppCompatActivity {

    private String num_ano;
    private Preco preco = new Preco();
    private TextView price, brand, model, modelYear, fuel, codeFipe, referenceMonth, vehicleType, fuelAcronym;
    private Button btnInicio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recebeNumAno();
        setContentView(R.layout.activity_preco);

        ApiInterface apiService = ApiClient.getIdPreco().create(ApiInterface.class);
        Call<Preco> call = apiService.getPreco(Dados.getInstance().getIdBrand(), Dados.getInstance().getIdMarca(), Dados.getInstance().getIdModelo(), num_ano);
        call.enqueue(new Callback<Preco>() {
            @Override
            public void onResponse(Call<Preco> call, Response<Preco> response) {
                if (response.isSuccessful()) {
                    preco = response.body();
                    price = (TextView) findViewById(R.id.price);
                    brand = (TextView) findViewById(R.id.brand);
                    model = (TextView) findViewById(R.id.model);
                    modelYear = (TextView) findViewById(R.id.modelYear);
                    fuel = (TextView) findViewById(R.id.fuel);
                    codeFipe = (TextView) findViewById(R.id.codeFipe);
                    referenceMonth = (TextView) findViewById(R.id.referenceMonth);
                    vehicleType = (TextView) findViewById(R.id.vehicleType);
                    fuelAcronym = (TextView) findViewById(R.id.fuelAcronym);
                    btnInicio = (Button) findViewById(R.id.btnInicio);
                    price.setText("Preço: " + preco.getPrice());
                    brand.setText("Marca: " + preco.getBrand());
                    model.setText("Modelo: " + preco.getModel());
                    modelYear.setText("Ano: " + String.valueOf(preco.getModelYear()));
                    fuel.setText("Combustível: " + preco.getFuel());
                    codeFipe.setText("Código Fipe: " + preco.getCodeFipe());
                    referenceMonth.setText("Preço referente a " + preco.getReferenceMonth());
                    vehicleType.setText("Categoria: " + String.valueOf(preco.getVehicleType()));
                    fuelAcronym.setText("Sigla Combustível: " + preco.getFuelAcronym());

                    btnInicio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(PrecoActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Preco> call, Throwable exception) {
                exception.printStackTrace();

            }
        });
    }

    public void recebeNumAno() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            num_ano = extras.getString("num_ano");
        }
    }
}