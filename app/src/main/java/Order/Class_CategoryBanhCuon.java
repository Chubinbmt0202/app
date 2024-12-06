package Order;

public class Class_CategoryBanhCuon {
    private int imgmonan ;
    private String giadonvi  , tenmonan;
    private String madanhmuc,masanpham ,mota;
    private int tinhtrang;

    public Class_CategoryBanhCuon(String madanhmuc, String masanpham, String tenmonan, int imgmonan, String giadonvi, String mota,int tt) {
        this.madanhmuc = madanhmuc;
        this.masanpham = masanpham;
        this.tenmonan = tenmonan;
        this.imgmonan = imgmonan;
        this.giadonvi = giadonvi;
        this.mota = mota;
        this.tinhtrang = tt;
    }

    public String getGiadonvi() {
        return giadonvi;
    }

    public void setGiadonvi(String giadonvi) {
        this.giadonvi = giadonvi;
    }

    public int getImgmonan() {
        return imgmonan;
    }

    public void setImgmonan(int imgmonan) {
        this.imgmonan = imgmonan;
    }

    public String getMadanhmuc() {
        return madanhmuc;
    }

    public void setMadanhmuc(String madanhmuc) {
        this.madanhmuc = madanhmuc;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
}
