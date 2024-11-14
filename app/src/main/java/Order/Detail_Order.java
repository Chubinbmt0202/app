package Order;

public class Detail_Order {
    private int imgdanhmuc;
    private String tendanhmuc;
    private int imgicondanhmuc;

    public Detail_Order(int imgdanhmuc, int imgicondanhmuc, String tendanhmuc) {
        this.imgdanhmuc = imgdanhmuc;
        this.imgicondanhmuc = imgicondanhmuc;
        this.tendanhmuc = tendanhmuc;
    }

    public int getImgdanhmuc() {
        return imgdanhmuc;
    }

    public void setImgdanhmuc(int imgdanhmuc) {
        this.imgdanhmuc = imgdanhmuc;
    }

    public int getImgicondanhmuc() {
        return imgicondanhmuc;
    }

    public void setImgicondanhmuc(int imgicondanhmuc) {
        this.imgicondanhmuc = imgicondanhmuc;
    }

    public String getTendanhmuc() {
        return tendanhmuc;
    }

    public void setTendanhmuc(String tendanhmuc) {
        this.tendanhmuc = tendanhmuc;
    }
}
