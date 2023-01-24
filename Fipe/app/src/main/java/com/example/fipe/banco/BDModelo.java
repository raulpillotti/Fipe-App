package com.example.fipe.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fipe.model.Modelo;

import java.util.ArrayList;

public class BDModelo extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ModeloDB";
    private static final String TABELA_MODELOS = "modelos";
    private static final String ID = "id";
    private static final String MODELO = "modelo";
    private static final String[] COLUNAS = {ID, MODELO};

    public BDModelo(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE modelos ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "modelo TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_MODELOS + " ORDER BY " + MODELO;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return false;
        }
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS modelos");
        this.onCreate(db);
    }

    public void addModelo(Modelo modelo){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, Integer.parseInt(modelo.getCode()));
        values.put(MODELO, modelo.getName());
        db.insert(TABELA_MODELOS, null, values);
        db.close();
    }

    public Modelo getModelo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_MODELOS, // a. tabela
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
            Modelo modelo = cursorToModelo(cursor);
            return modelo;
        }
    }

    private Modelo cursorToModelo(Cursor cursor) {
        Modelo modelo = new Modelo();
        modelo.setCode(cursor.getString(0));
        modelo.setName(cursor.getString(1));
        return modelo;
    }

    public ArrayList<Modelo> getAllModelos() {
        ArrayList<Modelo> listaModelos = new ArrayList<Modelo>();
        String query = "SELECT * FROM " + TABELA_MODELOS + " ORDER BY " + MODELO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Modelo modelo = cursorToModelo(cursor);
                listaModelos.add(modelo);
            } while (cursor.moveToNext());
        }
        return listaModelos;
    }

    public void deleteAllModelos(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from modelos";
        db.execSQL(query);
        db.close();
    }
}