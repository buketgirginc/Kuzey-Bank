package com.example.mobilebanking.model;

public class Musteri {
    private int musteriNo;
    private String musteriFullname;
    private String musteriTC;
    private String musteriSifre;

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
}
