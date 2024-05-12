package com.example.mobilebanking;

public class AccountItem {
    int accId;
    String accName;
    String balance;
    public AccountItem(int accId, String accName, String balance) {
        this.accId = accId;
        this.accName = accName;
        this.balance = balance;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
