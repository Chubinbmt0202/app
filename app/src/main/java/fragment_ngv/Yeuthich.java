package fragment_ngv;

public class Yeuthich {
    private int imganh ;
    private String tenmonan , mota , gia,id;
    private int tinhtrang;

    public Yeuthich(String id, String tenmonan, int imganh, String gia, String mota, int tinhtrang) {
        this.id = id;
        this.tenmonan = tenmonan;
        this.imganh = imganh;
        this.gia = gia;
        this.mota = mota;
        this.tinhtrang = tinhtrang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getImganh() {
        return imganh;
    }

    public void setImganh(int imganh) {
        this.imganh = imganh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
}
