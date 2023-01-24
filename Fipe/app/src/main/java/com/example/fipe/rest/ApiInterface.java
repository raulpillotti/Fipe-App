package com.example.fipe.rest;

import com.example.fipe.model.Ano;
import com.example.fipe.model.Preco;
import com.example.fipe.model.Marca;
import com.example.fipe.model.Modelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("{vehicleType}/brands") // lista marcas
    Call<List<Marca>> listMarcas(@Path("vehicleType") String vehicleType);

    @GET("{vehicleType}/brands/{marcaId}/models") //lista modelos
    Call<List<Modelo>> listModelos(@Path("vehicleType") String vehicleType, @Path("marcaId") int marcaId);

    @GET("{vehicleType}/brands/{marcaId}/models/{modeloId}/years") // lista Anos
    Call<List<Ano>> listAnos(@Path("vehicleType") String vehicleType, @Path("marcaId") int marcaId, @Path("modeloId") int modeloId);

    @GET("{vehicleType}/brands/{marcaId}/models/{modeloId}/years/{anoId}") // Fipe
    Call<Preco> getPreco(@Path("vehicleType") String vehicleType, @Path("marcaId") int marcaId, @Path("modeloId") int modeloId, @Path("anoId") String anoId);

}