package com.example.mobilebanking.model;
import java.util.Date;
public class Islem {
    private int islemNo;
    private int hesapNo;
    private float islemMiktar;
    private int islemTipi;
    private Date islemTarih;

    public Islem(){

    }

    public Islem(int islemNo, int hesapNo, float islemMiktar, int islemTipi, Date islemTarih) {
        this.islemNo = islemNo;
        this.hesapNo = hesapNo;
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

    public int getHesapNo() {
        return hesapNo;
    }

    public void setHesapNo(int hesapNo) {
        this.hesapNo = hesapNo;
    }

    public float getIslemMiktar() {
        return islemMiktar;
    }

    public void setIslemMiktar(float islemMiktar) {
        this.islemMiktar = islemMiktar;
    }

    public int getIslemTipi() {
        return islemTipi;
    }

    public void setIslemTipi(int islemTipi) {
        this.islemTipi = islemTipi;
    }

    public Date getIslemTarih() {
        return islemTarih;
    }

    public void setIslemTarih(Date islemTarih) {
        this.islemTarih = islemTarih;
    }
}
