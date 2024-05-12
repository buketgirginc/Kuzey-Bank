package com.example.mobilebanking.model;

import java.util.List;

public class Hesap {
    private int hesapNo;
    private String hesapAdi;
    private Currency hesapDovizTipi;
    private float hesapBakiye;
    private int musteriNo;
    private List<Islem> islemler;

    public Hesap(){

    }

    public Hesap(int hesapNo, String hesapAdi, Currency hesapDovizTipi, float hesapBakiye, int musteriNo) {
        this.hesapNo = hesapNo;
        this.hesapAdi = hesapAdi;
        this.hesapDovizTipi = hesapDovizTipi;
        this.hesapBakiye = hesapBakiye;
        this.musteriNo = musteriNo;
    }

    public int getHesapNo() {
        return hesapNo;
    }

    public void setHesapNo(int hesapNo) {
        this.hesapNo = hesapNo;
    }

    public String getHesapAdi() {
        return hesapAdi;
    }

    public void setHesapAdi(String hesapAdi) {
        this.hesapAdi = hesapAdi;
    }

    public Currency getHesapDovizTipi() {
        return hesapDovizTipi;
    }

    public void setHesapDovizTipi(Currency hesapDovizTipi) {
        this.hesapDovizTipi = hesapDovizTipi;
    }

    public float getHesapBakiye() {
        return hesapBakiye;
    }

    public void setHesapBakiye(float hesapBakiye) {
        this.hesapBakiye = hesapBakiye;
    }

    public int getMusteriNo() {
        return musteriNo;
    }

    public void setMusteriNo(int musteriNo) {
        this.musteriNo = musteriNo;
    }

    public void setIslemler(List<Islem> islemler) {
        this.islemler = islemler;
    }

    public List<Islem> getIslemler() {
        return islemler;
    }
}

