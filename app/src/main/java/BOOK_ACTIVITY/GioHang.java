package BOOK_ACTIVITY;

public class GioHang {
    private int id;
    private int img ;
    private int gia  ;
    private int sl  ;
    private String tenmon;
    private int tinhtrang;

    public GioHang(int id ,int gia, int img, String tenmon , int sl,int tinhtrang) {
        this.id = id;
        this.gia = gia;
        this.img = img;
        this.sl = sl;
        this.tinhtrang = tinhtrang;
        this.tenmon = tenmon;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }
}
