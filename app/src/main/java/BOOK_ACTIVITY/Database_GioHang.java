package BOOK_ACTIVITY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Order.Apdate_OrderCategory;

public class Database_GioHang extends SQLiteOpenHelper {

    public final int version = 1;
    public String database = "HANG";
    public String table = "GIOHANG";
    public final String id = "ID";
    private final String Tenmonan = "TENMONAN";
    private final String Gia = "GIADV";
    private final String img = "IMG";
    private final String solg = "SoLuong";
    private SQLiteDatabase sqlite;
    private String tinhtrang = "TinhTrang";
    private Apdate_OrderCategory order;
    public final String creatable = "CREATE TABLE IF NOT EXISTS " + table + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Tenmonan + " TEXT, "
            + Gia + " TEXT, "
            + img + " INTEGER, "
            + tinhtrang + " INTEGER, "
            + solg + " INTEGER )";

    public Database_GioHang(Context context) {
        super(context, "HANG", null, 1);
        this.sqlite = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creatable);
        Log.d("Database_GioHang", "Table created: " + creatable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(sqLiteDatabase);
    }

    public void Themsanpham(String tenmonan, String gia, int anh) {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        ContentValues content = new ContentValues();
        content.put(Tenmonan, tenmonan);
        content.put(Gia, gia);
        content.put(img, anh);
        content.put(tinhtrang, 1);
        content.put(solg, 1);
        sqlite.insert(table, null, content);
        sqlite.close();
    }

    public void Capnhatsanpham(String tenmonan, String gia) {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        ContentValues content = new ContentValues();
        content.put(Gia, gia);
        sqlite.update(table, content, Tenmonan + "=?", new String[]{tenmonan});
        sqlite.close();
    }

    public int XoaSanPham(String tenmonan, String dongia) {
        int rowsDeleted = 0;
        if (sqlite == null || !sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }

        try {
            rowsDeleted = sqlite.delete(
                    table,
                    Tenmonan + " = ? AND " + Gia + " = ?",
                    new String[]{tenmonan, dongia}
            );
        } finally {
            sqlite.close();
        }
        return rowsDeleted;
    }

    public Cursor Duyetmonan() {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        return sqlite.query(table, null, null, null, null, null, null, null);
    }

    public Cursor getTenmonan() {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        return sqlite.query(table, new String[]{Tenmonan, Gia}, null, null, null, null, null, null);
    }

    public void Xoatatca() {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        sqlite.delete(table, null, null);
        sqlite.close();
    }

    public void Capnhattinhtrang(int id, int k) {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        ContentValues content = new ContentValues();
        content.put(tinhtrang, k);
       sqlite.update(table, content,  "ID =?", new String[]{String.valueOf(id)});
        sqlite.close();
    }

    public void Capnhatsolg(int id, int k) {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        ContentValues content = new ContentValues();
        content.put(solg, k);
        sqlite.update(table, content, "ID = ?", new String[]{String.valueOf(id)});
        sqlite.close();
    }


    public Cursor getTinhtrang() {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        String selection = tinhtrang + " = ?";
        String[] selectionArgs = { "1" };
        return sqlite.query(table, null, selection, selectionArgs, null, null, null);
    }

    public Cursor getKiemTra(int id) {
        if (!sqlite.isOpen()) {
            sqlite = this.getWritableDatabase();
        }
        String selection = "ID = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = sqlite.query(table, null, selection, selectionArgs, null, null, null);
        return cursor;
    }


        public void XoaGioHang() {
            SQLiteDatabase sqlite = null;
            try {
                sqlite = this.getWritableDatabase();
                sqlite.delete(table, tinhtrang + "=?", new String[]{String.valueOf(1)});
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (sqlite != null && sqlite.isOpen()) {
                    sqlite.close();
                }
            }
        }


}