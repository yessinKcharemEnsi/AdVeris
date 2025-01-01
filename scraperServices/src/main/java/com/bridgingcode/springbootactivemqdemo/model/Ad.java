package com.bridgingcode.springbootactivemqdemo.model;

import java.io.Serializable;

public class Ad implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String codeZimmo;
    private String prix;
    private String surfaceHabitable;
    private String ascenseur;
    private String superficieTerrain;
    private String meuble;
    private String extract1;
    private String piscine;
    private String anneeConstruction;
    private String etage;
    private String nbreEtage;
    private String nbreFacade;
    private String garage;
    private String douche;
    private String adress;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeZimmo() {
        return codeZimmo;
    }

    public void setCodeZimmo(String codeZimmo) {
        this.codeZimmo = codeZimmo;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getSurfaceHabitable() {
        return surfaceHabitable;
    }

    public void setSurfaceHabitable(String surfaceHabitable) {
        this.surfaceHabitable = surfaceHabitable;
    }

    public String getAscenseur() {
        return ascenseur;
    }

    public void setAscenseur(String ascenseur) {
        this.ascenseur = ascenseur;
    }

    public String getSuperficieTerrain() {
        return superficieTerrain;
    }

    public void setSuperficieTerrain(String superficieTerrain) {
        this.superficieTerrain = superficieTerrain;
    }

    public String getMeuble() {
        return meuble;
    }

    public void setMeuble(String meuble) {
        this.meuble = meuble;
    }

    public String getExtract1() {
        return extract1;
    }

    public void setExtract1(String extract1) {
        this.extract1 = extract1;
    }

    public String getPiscine() {
        return piscine;
    }

    public void setPiscine(String piscine) {
        this.piscine = piscine;
    }

    public String getAnneeConstruction() {
        return anneeConstruction;
    }

    public void setAnneeConstruction(String anneeConstruction) {
        this.anneeConstruction = anneeConstruction;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public String getNbreEtage() {
        return nbreEtage;
    }

    public void setNbreEtage(String nbreEtage) {
        this.nbreEtage = nbreEtage;
    }

    public String getNbreFacade() {
        return nbreFacade;
    }

    public void setNbreFacade(String nbreFacade) {
        this.nbreFacade = nbreFacade;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getDouche() {
        return douche;
    }

    public void setDouche(String douche) {
        this.douche = douche;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "name='" + name + '\'' +
                ", codeZimmo='" + codeZimmo + '\'' +
                ", prix='" + prix + '\'' +
                ", surfaceHabitable='" + surfaceHabitable + '\'' +
                ", ascenseur='" + ascenseur + '\'' +
                ", superficieTerrain='" + superficieTerrain + '\'' +
                ", meuble='" + meuble + '\'' +
                ", extract1='" + extract1 + '\'' +
                ", piscine='" + piscine + '\'' +
                ", anneeConstruction='" + anneeConstruction + '\'' +
                ", etage='" + etage + '\'' +
                ", nbreEtage='" + nbreEtage + '\'' +
                ", nbreFacade='" + nbreFacade + '\'' +
                ", garage='" + garage + '\'' +
                ", douche='" + douche + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}