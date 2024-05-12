package com.example.mobilebanking.model;

public class Alici {
    private int kayitNo;
    private String aliciAdi;
    private int aliciHesapNo;
    private int musteriNo;

    public Alici() {
        // Boş constructor
    }

    public Alici(int kayitNo, String aliciAdi, int aliciHesapNo, int musteriNo) {
        this.kayitNo = kayitNo;
        this.aliciAdi = aliciAdi;
        this.aliciHesapNo = aliciHesapNo;
        this.musteriNo = musteriNo;
    }

    public int getKayitNo() {
        return kayitNo;
    }

    public void setKayitNo(int kayitNo) {
        this.kayitNo = kayitNo;
    }

    public String getAliciAdi() {
        return aliciAdi;
    }

    public void setAliciAdi(String aliciAdi) {
        this.aliciAdi = aliciAdi;
    }

    public int getAliciHesapNo() {
        return aliciHesapNo;
    }

    public void setAliciHesapNo(int aliciHesapNo) {
        this.aliciHesapNo = aliciHesapNo;
    }

    public int getMusteriNo() {
        return musteriNo;
    }

    public void setMusteriNo(int musteriNo) {
        this.musteriNo = musteriNo;
    }
}
