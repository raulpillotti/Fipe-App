package com.example.fipe.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fipe.R;
import com.example.fipe.banco.BDMarca;
import com.example.fipe.model.Marca;

public class EditActivity extends AppCompatActivity {

    private BDMarca bd;
    private int id_marca;
    private EditText inputMarca, inputCodigo;

    private Button btnCancelar, btnSalvar, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recebeParametros();
        setContentView(R.layout.activity_editar);
        bd = new BDMarca(this);
        Marca marca = bd.getMarca(id_marca);

        inputMarca = (EditText) findViewById(R.id.inputMarca);
        inputCodigo = (EditText) findViewById(R.id.inputCodigo);
        inputMarca.setText(marca.getName());
        inputCodigo.setText(marca.getCode());

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MarcaActivity.class);
                context.startActivity(intent);
            }
        });
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnDelete = (Button) findViewById(R.id.btnDelete);

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!inputMarca.getText().toString().isEmpty()) {
                        Marca newMarca = new Marca();
                        newMarca.setName(inputMarca.getText().toString());
                        newMarca.setCode(inputCodigo.getText().toString());
                        bd.updateMarca(newMarca);
                        Intent intent1 = new Intent(EditActivity.this, MainActivity.class);
                        Toast.makeText(getApplicationContext(), "Editado com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(intent1);
                    } else {
                        inputMarca.setError("Campo inválido!");
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(EditActivity.this)
                            .setTitle(R.string.confirmar_exclusao)
                            .setMessage(R.string.quer_mesmo_apagar)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Marca marca = new Marca();
                                    marca.setCode(String.valueOf(id_marca));
                                    bd.deleteMarca(marca);
                                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                                    Toast.makeText(getApplicationContext(), "Excluido com sucesso! ", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
    }

    public void recebeParametros() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_marca = extras.getInt("id_marca");
        }
    }
}