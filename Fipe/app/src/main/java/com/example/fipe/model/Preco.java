package com.example.fipe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Preco {

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("modelYear")
    @Expose
    private Integer modelYear;
    @SerializedName("fuel")
    @Expose
    private String fuel;
    @SerializedName("codeFipe")
    @Expose
    private String codeFipe;
    @SerializedName("referenceMonth")
    @Expose
    private String referenceMonth;
    @SerializedName("vehicleType")
    @Expose
    private Integer vehicleType;
    @SerializedName("fuelAcronym")
    @Expose
    private String fuelAcronym;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getCodeFipe() {
        return codeFipe;
    }

    public void setCodeFipe(String codeFipe) {
        this.codeFipe = codeFipe;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getFuelAcronym() {
        return fuelAcronym;
    }

    public void setFuelAcronym(String fuelAcronym) {
        this.fuelAcronym = fuelAcronym;
    }
}