package com.example.mobilebanking.model;

public class IslemTipi {
    private int itNo;
    private String itBaslik;
    private String itRenk;

    public IslemTipi(int itNo, String itBaslik, String itRenk){
        this.itNo = itNo;
        this.itBaslik = itBaslik;
        this.itRenk = itRenk;
    }

    public int getItNo() {
        return itNo;
    }

    public void setItNo(int itNo) {
        this.itNo = itNo;
    }

    public String getItBaslik() {
        return itBaslik;
    }

    public void setItBaslik(String itBaslik) {
        this.itBaslik = itBaslik;
    }

    public String getItRenk() {
        return itRenk;
    }

    public void setItRenk(String itRenk) {
        this.itRenk = itRenk;
    }
}
