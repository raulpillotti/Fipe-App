package com.example.fipe.model;

public class Dados {

    private static Dados instancia;

    private String IdBrand = "";
    private int IdMarca = 0;
    private int IdModelo = 0;
    private String IdAno = "";
    private String aux = "";

    public static Dados getInstance() {
        if (instancia == null) {
            instancia = new Dados();
        }
        return instancia;
    }

    public void setIdBrand(String id) {
        this.IdBrand = id;
    }

    public String getIdBrand() {
        return this.IdBrand;
    }

    public void setIdMarca(int id) {
        this.IdMarca = id;
    }

    public int getIdMarca() {
        return this.IdMarca;
    }

    public void setIdModelo(int id) {
        this.IdModelo = id;
    }

    public int getIdModelo() {
        return this.IdModelo;
    }

    public void setIdAno(String id) {
        this.IdAno = id;
    }

    public String getIdAno() {
        return this.IdAno;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }
}