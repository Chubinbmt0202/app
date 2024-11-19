package BOOK_ACTIVITY;

public class GioHang {
    private int img ;
    private int gia  ;
    private String tenmon;

    public GioHang(int gia, int img, String tenmon) {
        this.gia = gia;
        this.img = img;
        this.tenmon = tenmon;
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
