package Accout;

public class Class_sumdeltaihistory {
    private int tinhtrang;
    private String ngaydat;
    private String idhoadon;

    public Class_sumdeltaihistory(String idhoadon, int tinhtrang, String ngaydat) {
        this.idhoadon = idhoadon;
        this.tinhtrang = tinhtrang;
        this.ngaydat = ngaydat;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getIdhoadon() {
        return idhoadon;
    }

    public void setIdhoadon(String idhoadon) {
        this.idhoadon = idhoadon;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
}
