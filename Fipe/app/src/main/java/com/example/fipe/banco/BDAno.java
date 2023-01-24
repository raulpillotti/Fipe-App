package com.example.fipe.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fipe.model.Ano;

import java.util.ArrayList;

public class BDAno extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AnoDB";
    private static final String TABELA_ANOS = "anos";
    private static final String ID = "id";
    private static final String ANO = "ano";
    private static final String[] COLUNAS = {ID, ANO};

    public BDAno(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE anos ("+
                "id TEXT,"+
                "ano TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_ANOS + " ORDER BY " + ANO;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return false;
        }
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS anos");
        this.onCreate(db);
    }

    public void addAno(Ano ano){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, ano.getCode());
        values.put(ANO, ano.getName());
        db.insert(TABELA_ANOS, null, values);
        db.close();
    }

    public Ano getAno(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_ANOS, // a. tabela
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
            Ano ano = cursorToAno(cursor);
            return ano;
        }
    }

    private Ano cursorToAno(Cursor cursor) {
        Ano ano = new Ano();
        ano.setCode(cursor.getString(0));
        ano.setName(cursor.getString(1));
        return ano;
    }

    public ArrayList<Ano> getAllAnos() {
        ArrayList<Ano> listaAnos = new ArrayList<Ano>();
        String query = "SELECT * FROM " + TABELA_ANOS + " ORDER BY " + ANO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Ano ano = cursorToAno(cursor);
                listaAnos.add(ano);
            } while (cursor.moveToNext());
        }
        return listaAnos;
    }

    public int updateAno(Ano ano) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ANO, ano.getName());
        values.put(ID, ano.getCode());
        int i = db.update(TABELA_ANOS, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(ano.getCode()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteAno(Ano ano) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_ANOS, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(ano.getCode()) });
        db.close();
        return i; // número de linhas excluídas
    }

    public void deleteAllAnos(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from anos";
        db.execSQL(query);
        db.close();
    }
}