package com.example.mobilebanking.model;

import java.util.ArrayList;
import java.util.List;

public class Musteri {
    private int musteriNo;
    private String musteriFullname;
    private String musteriTC;
    private String musteriSifre;
    private List<Hesap> hesaplar;
    private List<Alici> alicilar;

    private List <Islem> sonIslemler;

    public Musteri() {
    }

    public Musteri(String musteriFullname, String musteriTC, String musteriSifre) {
        this.musteriFullname = musteriFullname;
        this.musteriTC = musteriTC;
        this.musteriSifre = musteriSifre;
    }

    public int getMusteriNo() {
        return musteriNo;
    }

    public void setMusteriNo(int musteriNo) {
        this.musteriNo = musteriNo;
    }

    public String getMusteriFullname() {
        return musteriFullname;
    }

    public void setMusteriFullname(String musteriFullname) {
        this.musteriFullname = musteriFullname;
    }

    public String getMusteriTC() {
        return musteriTC;
    }

    public void setMusteriTC(String musteriTC) {
        this.musteriTC = musteriTC;
    }

    public String getMusteriSifre() {
        return musteriSifre;
    }

    public void setMusteriSifre(String musteriSifre) {
        this.musteriSifre = musteriSifre;
    }

    public List<Hesap> getHesaplar() {
        return hesaplar;
    }

    public void setHesaplar(List<Hesap> hesaplar) {
        this.hesaplar = hesaplar;
    }

    public void setAlicilar(List<Alici> alicilar) {
        this.alicilar = alicilar;
    }

    public List<Alici> getAlicilar() {
        return alicilar;
    }

    public void setSonIslemler(List<Islem> sonIslemler) {
        this.sonIslemler = sonIslemler;
    }

    public List<Islem> getSonIslemler() {
        return sonIslemler;
    }
}
