package com.example.fipe.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fipe.model.Marca;

import java.util.ArrayList;

public class BDMarca extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MarcaDB";
    private static final String TABELA_MARCAS = "marcas";
    private static final String ID = "id";
    private static final String MARCA = "marca";
    private static final String[] COLUNAS = {ID, MARCA};

    public BDMarca(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE marcas ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "marca TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_MARCAS + " ORDER BY " + MARCA;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return false;
        }
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS marcas");
        this.onCreate(db);
    }

    public void addMarca(Marca marca){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, Integer.parseInt(marca.getCode()));
        values.put(MARCA, marca.getName());
        db.insert(TABELA_MARCAS, null, values);
        db.close();
    }

    public Marca getMarca(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_MARCAS, // a. tabela
                COLUNAS, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (!cursor.moveToFirst()) {
            return null;
        } else {
            cursor.moveToFirst();
            Marca marca = cursorToMarca(cursor);
            return marca;
        }
    }

    private Marca cursorToMarca(Cursor cursor) {
        Marca marca = new Marca();
        marca.setCode(cursor.getString(0));
        marca.setName(cursor.getString(1));
        return marca;
    }

    public ArrayList<Marca> getAllMarcas() {
        ArrayList<Marca> listaMarcas = new ArrayList<Marca>();
        String query = "SELECT * FROM " + TABELA_MARCAS + " ORDER BY " + MARCA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Marca marca = cursorToMarca(cursor);
                listaMarcas.add(marca);
            } while (cursor.moveToNext());
        }
        return listaMarcas;
    }

    public int updateMarca(Marca marca) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MARCA, marca.getName());
        values.put(ID, marca.getCode());
        int i = db.update(TABELA_MARCAS, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(marca.getCode()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteMarca(Marca marca) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_MARCAS, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(marca.getCode()) });
        db.close();
        return i; // número de linhas excluídas
    }

    public void deleteAllMarcas(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from marcas";
        db.execSQL(query);
        db.close();
    }
}