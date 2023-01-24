package com.example.fipe.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fipe.R;
import com.example.fipe.model.Dados;

public class MainActivity extends AppCompatActivity {

    EditText vehicleType;
    TextView isOnline;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isOnline = (TextView) findViewById(R.id.isOnline);
        vehicleType = (EditText) findViewById(R.id.vehicleType);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        if (isOnline()) {
            isOnline.setText("Online - Dados Online");
            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vehicleType.getText().length() == 0) {
                        vehicleType.setError("Campo Vazio");
                    } else if (
                            vehicleType.getText().toString().equals("carros") ||
                                    vehicleType.getText().toString().equals("caminhoes") ||
                                    vehicleType.getText().toString().equals("motos") ||
                                    vehicleType.getText().toString().equals("Carros") ||
                                    vehicleType.getText().toString().equals("Caminhoes") ||
                                    vehicleType.getText().toString().equals("Motos")
                    ) {
                        Intent intent = new Intent(MainActivity.this, MarcaActivity.class);
                        intent.putExtra("vehicleType", vehicleType.getText().toString());
                        Dados.getInstance().setIdBrand(vehicleType.getText().toString());
                        startActivity(intent);
                    } else {
                        vehicleType.setError("Campo Inválido!");
                    }
                }
            });
        } else {
//            Toast.makeText(getApplicationContext(), "Você está Offline!", Toast.LENGTH_SHORT).show();
            isOnline.setText("Offline - Dados Local");
            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vehicleType.getText().length() == 0) {
                        vehicleType.setError("Campo Vazio");
                    } else if (
                            vehicleType.getText().toString().equals("carros") ||
                                    vehicleType.getText().toString().equals("caminhoes") ||
                                    vehicleType.getText().toString().equals("motos") ||
                                    vehicleType.getText().toString().equals("Carros") ||
                                    vehicleType.getText().toString().equals("Caminhoes") ||
                                    vehicleType.getText().toString().equals("Motos")
                    ) {
                        Intent intent = new Intent(MainActivity.this, MarcaActivity.class);
                        startActivity(intent);
                    } else {
                        vehicleType.setError("Campo Inválido!");
                    }
                }
            });
        }
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
