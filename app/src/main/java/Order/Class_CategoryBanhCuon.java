package Order;

public class Class_CategoryBanhCuon {
    private int imgmonan ;
    private String giadonvi  , tenmonan;

    public Class_CategoryBanhCuon(String tenmonan, int imgmonan, String giadonvi) {
        this.tenmonan = tenmonan;
        this.imgmonan = imgmonan;
        this.giadonvi = giadonvi;
    }

    public String getGiadonvi() {
        return giadonvi;
    }

    public void setGiadonvi(String giadonvi) {
        this.giadonvi = giadonvi;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }


    public int getImgmonan() {
        return imgmonan;
    }

    public void setImgmonan(int imgmonan) {
        this.imgmonan = imgmonan;
    }
}
