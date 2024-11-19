package BOOK_ACTIVITY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;

public class Database_GioHang extends SQLiteOpenHelper {

    public final int version = 1;
    public  String database = "DSGIOHANG";
    public  String table = "tb_GIOHANG";
    public final String id ="ID" ;
    private final String Tenmonan = "TENMONAN";
    private final String Gia = "GIADV";
    private final String img = "IMG";
    private SQLiteDatabase sqlite;
    private Apdate_OrderCategory order ;
    public final String creatable = "CREATE TABLE " + table +"(" + id  +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Tenmonan +" TEXT ," + Gia + " TEXT ," + img +" INTEGER )" ;


    public Database_GioHang( Context context) {
        super(context, "DSGIOHANG",null,1);
        this.sqlite = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creatable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(sqLiteDatabase);
    }

    public void Themsanpham(String tenmonan , String gia , int anh )
    {
        sqlite.isOpen();
        ContentValues content = new ContentValues();
        content.put(Tenmonan,tenmonan);
        content.put(Gia,gia);
        content.put(img,anh);
        sqlite.insert(table,null,content);
        sqlite.close();
    }

    public void Capnhatsanpham(String tenmonan , String gia )
    {
        sqlite.isOpen();
        ContentValues content = new ContentValues();
        content.put(Gia,gia);
        sqlite.update(table,content,Tenmonan +"=?",new String[]{tenmonan});
        sqlite.close();
    }

    public int XoaSanPham(String tenmonan, String dongia) {
        // Open the database
        int rowsDeleted = 0;
        if (sqlite == null || !sqlite.isOpen()) {
            throw new IllegalStateException("Database is not open!");
        }

        try {
            // Delete the row(s) that match the conditions
            rowsDeleted = sqlite.delete(
                    table,
                    Tenmonan + " = ? AND " + Gia + " = ?",
                    new String[]{tenmonan, dongia}
            );
        } finally {
            // Close the database
            sqlite.close();
        }
        return rowsDeleted;
    }


    public Cursor Duyetmonan()
    {
        sqlite.isOpen();
        return sqlite.query(table,null,null,null,null,null,null,null);
    }

    public Cursor getTenmonan()
    {
        sqlite.isOpen();
        return sqlite.query(table,new String[]{Tenmonan,Gia},null,null,null,null,null,null);
    }

    public void Xoatatca()
    {
        sqlite.isOpen();
        sqlite.delete(table,null,null);
        sqlite.close();
    }

}
