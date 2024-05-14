package com.example.mobilebanking.model;
import java.util.Date;
public class Islem {
    private int islemNo;
    private Hesap hesap;
    private float islemMiktar;
    private IslemTipi islemTipi;
    private Date islemTarih;

    public Islem(){

    }

    public Islem(int islemNo, Hesap hesap, float islemMiktar, IslemTipi islemTipi, Date islemTarih) {
        this.islemNo = islemNo;
        this.hesap = hesap;
        this.islemMiktar = islemMiktar;
        this.islemTipi = islemTipi;
        this.islemTarih = islemTarih;
    }

    public int getIslemNo() {
        return islemNo;
    }

    public void setIslemNo(int islemNo) {
        this.islemNo = islemNo;
    }

    public Hesap getHesap() {
        return hesap;
    }

    public void setHesap(Hesap hesap) {
        this.hesap = hesap;
    }

    public float getIslemMiktar() {
        return islemMiktar;
    }

    public void setIslemMiktar(float islemMiktar) {
        this.islemMiktar = islemMiktar;
    }

    public IslemTipi getIslemTipi() {
        return islemTipi;
    }

    public void setIslemTipi(IslemTipi islemTipi) {
        this.islemTipi = islemTipi;
    }

    public Date getIslemTarih() {
        return islemTarih;
    }

    public void setIslemTarih(Date islemTarih) {
        this.islemTarih = islemTarih;
    }
}
