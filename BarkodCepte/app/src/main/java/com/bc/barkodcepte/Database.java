package com.bc.barkodcepte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    //DATABASE INFORMATION -------------------------------------------------------------------------
    private static final String DATABASE_NAME = "CepteBarkod";
    private static final int DATABASE_VERSION = 1;
    //----------------------------------------------------------------------------------------------

    //TABLE_URUNLER INFORMATION --------------------------------------------------------------------
    private static final String TABLE_URUNLER = "urunler";
    private static final String ROW_ID = "id";
    private static final String ROW_BARKOD = "barkod";
    private static final String ROW_URUNADI = "urunAdi";
    private static final String ROW_FIYAT = "urunFiyat";
    //----------------------------------------------------------------------------------------------

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_URUNLER + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_BARKOD + " TEXT NOT NULL,"
                + ROW_URUNADI + " TEXT NOT NULL,"
                + ROW_FIYAT + " TEXT NOT NULL)"
        );
        Log.d("DEBUG", "Veritabanı oluşturuldu!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URUNLER);
        onCreate(db);
    }

    public void InsertData(String urunBarkod, String urunAdi, String fiyat) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues cv = new ContentValues();

            cv.put(ROW_BARKOD, urunBarkod);
            cv.put(ROW_URUNADI, urunAdi);
            cv.put(ROW_FIYAT, fiyat);


            db.insert(TABLE_URUNLER, null, cv);
            Log.d("DEBUG", "maşarılı mro");
        } catch (Exception e) {
            Log.d("DEBUG", "Veritabanı oluşturma hatası!");
        }
        db.close();
    }

    public void Delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = ROW_ID + " = " + id;
            db.delete(TABLE_URUNLER, where, null);
        } catch (Exception e) {
        }
        db.close();
    }


    public List<String> SelectData() {
        List<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] columns = {ROW_ID, ROW_BARKOD, ROW_URUNADI, ROW_FIYAT};
            Cursor cursor = db.query(TABLE_URUNLER, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                data.add(cursor.getInt(0)
                        + "   -   "
                        + cursor.getString(1)
                        + "   -   "
                        + cursor.getString(2)
                        + "   -   "
                        + cursor.getString(3));
            }
        } catch (Exception e) {
        }
        db.close();
        return data;
    }

       /*public List<String> getProductType() {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + ROW_URUNTURU + " FROM " + TABLE_URUNLER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<String> list_productType = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                list_productType.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list_productType;
    }*/

}