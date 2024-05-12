package com.example.mobilebanking.model;

public enum Currency {
    TL(1, " TL"),
    DOLAR(2, " $"),
    EURO(3, " €");

    private final int value;
    private final String name;

    Currency(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Currency fromValue(int value) {
        for (Currency currency : Currency.values()) {
            if (currency.getValue() == value) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Geçersiz para birimi değeri: " + value);
    }

    public static Currency fromName(String name) {
        for (Currency currency : Currency.values()) {
            if (currency.getName().equals(name)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Geçersiz para birimi adı: " + name);
    }
}
