package com.example.mobilebanking.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilebanking.model.Currency;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Musteri;

import java.util.ArrayList;
import java.util.List;

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

        //db.execSQL("UPDATE SQLITE_SEQUENCE SET seq = 86630014 WHERE name = 'hesaplar'");
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

    public Musteri selectMusteri(String musteriTC, String musteriSifre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Musteri musteri = null;

        // Sorguyu hazırla
        String[] columns = {"musteriNo", "musteriFullname", "musteriTC", "musteriSifre"};
        String selection = "musteriTC=? AND musteriSifre=?";
        String[] selectionArgs = {musteriTC, musteriSifre};

        // Veritabanından müşteriyi seç
        Cursor cursor = db.query("musteriler", columns, selection, selectionArgs, null, null, null);

        // Eğer müşteri bulunursa, bilgilerini al
        if (cursor != null && cursor.moveToFirst()) {
            musteri = new Musteri();
            musteri.setMusteriNo(cursor.getInt(cursor.getColumnIndex("musteriNo")));
            musteri.setMusteriFullname(cursor.getString(cursor.getColumnIndex("musteriFullname")));
            musteri.setMusteriTC(cursor.getString(cursor.getColumnIndex("musteriTC")));
            musteri.setMusteriSifre(cursor.getString(cursor.getColumnIndex("musteriSifre")));
        }

        // Cursor'ı kapat
        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return musteri;
    }

    public Musteri selectMusteri(String musteriNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Musteri musteri = null;

        // Sorguyu hazırla
        String[] columns = {"musteriNo", "musteriFullname", "musteriTC", "musteriSifre"};
        String selection = "musteriNo=?";
        String[] selectionArgs = {musteriNo};

        Cursor cursor = db.query("musteriler", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            musteri = new Musteri();
            musteri.setMusteriNo(cursor.getInt(cursor.getColumnIndex("musteriNo")));
            musteri.setMusteriFullname(cursor.getString(cursor.getColumnIndex("musteriFullname")));
            musteri.setMusteriTC(cursor.getString(cursor.getColumnIndex("musteriTC")));
            musteri.setMusteriSifre(cursor.getString(cursor.getColumnIndex("musteriSifre")));

            // Ait hesapları getir
            String[] hesapColumns = {"hesapNo", "hesapAdi", "hesapDovizTipi", "hesapBakiye", "musteriNo"};
            String hesapSelection = "musteriNo=?";
            String[] hesapSelectionArgs = {musteriNo};

            Cursor cursorHesap = db.query("hesaplar", hesapColumns, hesapSelection, hesapSelectionArgs, null, null, null);

            List<Hesap> hesaplar = new ArrayList<Hesap>();

            if (cursorHesap != null && cursorHesap.moveToFirst()) {
                do {
                    Hesap hesap = new Hesap();
                    hesap.setHesapNo(cursorHesap.getInt(cursorHesap.getColumnIndex("hesapNo")));
                    hesap.setHesapAdi(cursorHesap.getString(cursorHesap.getColumnIndex("hesapAdi")));
                    hesap.setHesapDovizTipi(Currency.fromValue(cursorHesap.getInt(cursorHesap.getColumnIndex("hesapDovizTipi"))));
                    hesap.setHesapBakiye(cursorHesap.getFloat(cursorHesap.getColumnIndex("hesapBakiye")));
                    hesap.setMusteriNo(cursorHesap.getInt(cursorHesap.getColumnIndex("musteriNo")));

                    hesaplar.add(hesap);
                } while (cursorHesap.moveToNext());
            }

            if (cursorHesap != null) {
                cursorHesap.close();
            }

            musteri.setHesaplar(hesaplar);

        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return musteri;
    }

    public void updateMusteriSifre(int musteriNo, String yeniSifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("musteriSifre", yeniSifre);
        int affectedRows = db.update("musteriler", values, "musteriNo" + " = ?",
                new String[]{String.valueOf(musteriNo)});
        db.close();
    }


}
