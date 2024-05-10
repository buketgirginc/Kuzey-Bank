package com.example.mobilebanking.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilebanking.model.Musteri;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kuzeybank.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS musteriler (" +
                "musteriNo INTEGER PRIMARY KEY," +
                "musteriFullname VARCHAR(30)," +
                "musteriTC CHAR(11)," +
                "musteriSifre CHAR(6));");

        db.execSQL("CREATE TABLE IF NOT EXISTS islemTipleri (" +
                "itNo INTEGER PRIMARY KEY," +
                "itBaslik VARCHAR(30)," +
                "itRenk VARCHAR(10));");

        db.execSQL("CREATE TABLE IF NOT EXISTS hesaplar (" +
                "hesapNo INTEGER PRIMARY KEY," +
                "hesapAdi VARCHAR(30)," +
                "hesapDovizTipi INT(3)," +
                "hesapBakiye FLOAT(10,2)," +
                "musteriNo INT(11)," +
                "FOREIGN KEY (musteriNo) REFERENCES musteriler(musteriNo));");

        db.execSQL("CREATE TABLE IF NOT EXISTS islemler (" +
                "islemNo INTEGER PRIMARY KEY," +
                "hesapNo INT(11)," +
                "islemMiktar FLOAT(10,2)," +
                "islemTipi INT(11)," +
                "islemTarih DATE DEFAULT CURRENT_DATE," +
                "FOREIGN KEY (hesapNo) REFERENCES hesaplar(hesapNo)," +
                "FOREIGN KEY (islemTipi) REFERENCES islemTipleri(itNo));");

        // Tetikleyiciyi oluşturma sorgusunu ekleyin
        db.execSQL("CREATE TRIGGER IF NOT EXISTS yeni_musteri_trigger AFTER INSERT ON musteriler " +
                "BEGIN " +
                "INSERT INTO hesaplar (hesapAdi, hesapBakiye, musteriNo, hesapDovizTipi) " +
                "VALUES (NEW.musteriFullname, 0, NEW.musteriNo, 1);" +
                "END;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long addMusteri(Musteri musteri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("musteriFullname", musteri.getMusteriFullname());
        values.put("musteriTC", musteri.getMusteriTC());
        values.put("musteriSifre", musteri.getMusteriSifre());

        long result = db.insert("musteriler", null, values);
        db.close();

        return result;
    }
}
