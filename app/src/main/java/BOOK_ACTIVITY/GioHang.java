package BOOK_ACTIVITY;

public class GioHang {
    private int img ;
    private int gia  ;
    private String tenmon;

    public GioHang(int gia, String tenmon, int img) {
        this.gia = gia;
        this.tenmon = tenmon;
        this.img = img;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
