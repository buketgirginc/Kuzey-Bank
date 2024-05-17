package com.example.mobilebanking.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilebanking.model.Alici;
import com.example.mobilebanking.model.Currency;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Islem;
import com.example.mobilebanking.model.IslemTipi;
import com.example.mobilebanking.model.Musteri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kuzeybank.db";
    public static final int DATABASE_VERSION = 1;
    List<IslemTipi> islemTipleri = new ArrayList<>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        islemTipleri.add(new IslemTipi(1, "Para Yatırma", "#188e1f"));
        islemTipleri.add(new IslemTipi(2, "Para Çekme", "#8e2f18"));
        islemTipleri.add(new IslemTipi(3, "Döviz Satım", "#188e1f"));
        islemTipleri.add(new IslemTipi(4, "Döviz Alım", "#8e2f18"));
        islemTipleri.add(new IslemTipi(5, "Döviz Alım", "#188e1f"));
        islemTipleri.add(new IslemTipi(6, "Para Gönderme", "#8e2f18"));
        islemTipleri.add(new IslemTipi(7, "Para Gönderme", "#188e1f"));
        islemTipleri.add(new IslemTipi(9, "Döviz Satım", "#8e2f18"));
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
                "islemTarih DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (hesapNo) REFERENCES hesaplar(hesapNo)," +
                "FOREIGN KEY (islemTipi) REFERENCES islemTipleri(itNo));");

        String createTableAlicilar = "CREATE TABLE IF NOT EXISTS alicilar (" +
                "kayitNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "aliciAdi VARCHAR(30), " +
                "aliciHesapNo INTEGER, " +
                "musteriNo INTEGER, " +
                "FOREIGN KEY (aliciHesapNo) REFERENCES hesaplar(hesapNo), " +
                "FOREIGN KEY (musteriNo) REFERENCES musteriler(musteriNo)" +
                ")";

        db.execSQL(createTableAlicilar);


        db.execSQL("INSERT INTO islemTipleri (itNo, itBaslik, itRenk) VALUES (1, 'Para Yatırma', '#188e1f'), (2, 'Para Çekme', '#8e2f18'), (3, 'Doviz Satım', '#188e1f'),(4, 'Döviz Alım', '#8e2f18')");

        // Tetikleyiciyi oluşturma sorgusunu ekleyin
        db.execSQL("CREATE TRIGGER IF NOT EXISTS yeni_musteri_trigger AFTER INSERT ON musteriler " +
                "BEGIN " +
                "INSERT INTO hesaplar (hesapNo, hesapAdi, hesapBakiye, musteriNo, hesapDovizTipi) " +
                "VALUES (NEW.musteriNo + 834521, NEW.musteriFullname, 10000, NEW.musteriNo, 1);" +
                "END;");

        db.execSQL("CREATE TRIGGER IF NOT EXISTS yeni_muster_islem_trigger AFTER INSERT ON musteriler " +
                "BEGIN " +
                "INSERT INTO islemler (hesapNo, islemMiktar, islemTipi) " +
                "VALUES (NEW.musteriNo + 834521, 10000, 1);" +
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

    public IslemTipi getIslemTipi(int id) {
        for (IslemTipi eleman : islemTipleri) {
            if (eleman.getItNo() == id) {
                return eleman;
            }
        }
        return null;
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
            List<Islem> tumIslemler = new ArrayList<Islem>();
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

            hesaplar.forEach(hesap -> {
                // Ait hesapları getir
                String[] islemColumns = {"islemNo", "hesapNo", "islemMiktar", "islemTipi", "islemTarih"};
                String islemSelection = "hesapNo=?";
                String[] islemSelectionArgs = {String.valueOf(hesap.getHesapNo())};

                Cursor cursorIslem = db.query("islemler", islemColumns, islemSelection, islemSelectionArgs, null, null, null);

                List<Islem> islemler = new ArrayList<Islem>();

                if (cursorIslem != null && cursorIslem.moveToFirst()) {
                    do {
                        Islem islem = new Islem();
                        islem.setIslemNo(cursorIslem.getInt(cursorIslem.getColumnIndex("islemNo")));
                        islem.setIslemTipi(getIslemTipi(cursorIslem.getInt(cursorIslem.getColumnIndex("islemTipi"))));
                        islem.setHesap(hesap);
                        islem.setIslemMiktar(cursorIslem.getFloat(cursorIslem.getColumnIndex("islemMiktar")));
                        islemler.add(islem);
                    } while (cursorIslem.moveToNext());
                }

                if (cursorIslem != null) {
                    cursorIslem.close();
                }

                tumIslemler.addAll(islemler);
                hesap.setIslemler(islemler);

            });


            List<Islem> son20Islem = tumIslemler.subList(0, Math.min(20, tumIslemler.size()));
            Collections.sort(son20Islem);

            musteri.setSonIslemler(son20Islem);
            musteri.setHesaplar(hesaplar);
            musteri.setAlicilar(getAlicilarByMusteriNo(musteri.getMusteriNo()));
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return musteri;
    }

    // Tablo ve sütun isimleri
    private static final String TABLE_ALICILAR = "alicilar";
    private static final String COLUMN_KAYIT_NO = "kayitNo";
    private static final String COLUMN_ALICI_ADI = "aliciAdi";
    private static final String COLUMN_ALICI_HESAP_NO = "aliciHesapNo";
    private static final String COLUMN_MUSTERI_NO = "musteriNo";

    public List<Alici> getAlicilarByMusteriNo(int musteriNo) {
        List<Alici> alicilar = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_KAYIT_NO, COLUMN_ALICI_ADI, COLUMN_ALICI_HESAP_NO, COLUMN_MUSTERI_NO};
        String selection = COLUMN_MUSTERI_NO + "=?";
        String[] selectionArgs = {String.valueOf(musteriNo)};
        Cursor cursor = db.query(TABLE_ALICILAR, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Alici alici = new Alici();
                alici.setKayitNo(cursor.getInt(cursor.getColumnIndex(COLUMN_KAYIT_NO)));
                alici.setAliciAdi(cursor.getString(cursor.getColumnIndex(COLUMN_ALICI_ADI)));
                alici.setAliciHesapNo(cursor.getInt(cursor.getColumnIndex(COLUMN_ALICI_HESAP_NO)));
                alici.setMusteriNo(cursor.getInt(cursor.getColumnIndex(COLUMN_MUSTERI_NO)));
                alicilar.add(alici);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return alicilar;
    }


    public void updateMusteriSifre(int musteriNo, String yeniSifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("musteriSifre", yeniSifre);
        int affectedRows = db.update("musteriler", values, "musteriNo" + " = ?",
                new String[]{String.valueOf(musteriNo)});
        db.close();
    }

    public long addHesap(Hesap hesap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hesapNo", hesap.getHesapNo());
        values.put("hesapAdi", hesap.getHesapAdi());
        values.put("hesapDovizTipi", hesap.getHesapDovizTipi().getValue());
        values.put("hesapBakiye", hesap.getHesapBakiye());
        values.put("musteriNo", hesap.getMusteriNo());

        long result = db.insert("hesaplar", null, values);
        db.close();
        return result;
    }

    public long saveHesap(Hesap hesap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hesapAdi", hesap.getHesapAdi());
        values.put("hesapDovizTipi", hesap.getHesapDovizTipi().getValue());
        values.put("hesapBakiye", hesap.getHesapBakiye());
        values.put("musteriNo", hesap.getMusteriNo());

        // Kayıt için whereClause ve whereArgs oluştur
        String whereClause = "hesapNo = ?";
        String[] whereArgs = { String.valueOf(hesap.getHesapNo()) };

        int result = db.update("hesaplar", values, whereClause, whereArgs);
        db.close();
        return result;
    }

    public long addAlici(Alici alici) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("aliciAdi", alici.getAliciAdi());
        values.put("aliciHesapNo", alici.getAliciHesapNo());
        values.put("musteriNo", alici.getMusteriNo());

        long result = db.insert("alicilar", null, values);
        db.close();
        return result;
    }

    public long addIslem(Islem islem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("hesapNo", islem.getHesap().getHesapNo());
        values.put("islemMiktar", islem.getIslemMiktar());
        values.put("islemTipi", islem.getIslemTipi().getItNo());

        long result = db.insert("islemler", null, values);
        db.close();
        return result;
    }

    public Hesap getHesapByHesapNo(int hesapNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Hesap hesap = null;

        // Veritabanından hesap bilgisini almak için sorgu yapılacak
        Cursor cursor = db.query("hesaplar", // Tablo adı
                null, // Sorgu için döndürülecek sütunlar (null tüm sütunları alır)
                "hesapNo = ?", // WHERE koşulu
                new String[]{String.valueOf(hesapNo)}, // Koşul parametreleri
                null, // GROUP BY
                null, // HAVING
                null); // ORDER BY

        // Cursor'ın başına geç
        if (cursor != null && cursor.moveToFirst()) {
            // Hesap bilgisini oluştur
            int hesapNoIndex = cursor.getColumnIndex("hesapNo");
            int hesapAdiIndex = cursor.getColumnIndex("hesapAdi");
            int hesapDovizTipiIndex = cursor.getColumnIndex("hesapDovizTipi");
            int hesapBakiyeIndex = cursor.getColumnIndex("hesapBakiye");
            int musteriNoIndex = cursor.getColumnIndex("musteriNo");

            int retrievedHesapNo = cursor.getInt(hesapNoIndex);
            String retrievedHesapAdi = cursor.getString(hesapAdiIndex);
            Currency retrievedHesapDovizTipi = Currency.fromValue(cursor.getInt(hesapDovizTipiIndex)); // Bu kısmı Currency enumuna dönüştürmeniz gerekebilir
            float retrievedHesapBakiye = cursor.getFloat(hesapBakiyeIndex);
            int retrievedMusteriNo = cursor.getInt(musteriNoIndex);

            hesap = new Hesap(retrievedHesapNo, retrievedHesapAdi, retrievedHesapDovizTipi, retrievedHesapBakiye, retrievedMusteriNo);

            // Cursor'ı kapat
            cursor.close();
        }

        // Veritabanını kapat
        db.close();

        return hesap;
    }




}
