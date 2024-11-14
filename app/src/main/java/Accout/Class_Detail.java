package Accout;

public class Class_Detail {
    private int img , solg;
    private int tonggia , giagoc ;
    private String tenmon;

    public Class_Detail(int giagoc, String tenmon, int tonggia, int solg, int img) {
        this.giagoc = giagoc;
        this.tenmon = tenmon;
        this.tonggia = tonggia;
        this.solg = solg;
        this.img = img;
    }

    public int getGiagoc() {
        return giagoc;
    }

    public void setGiagoc(int giagoc) {
        this.giagoc = giagoc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getSolg() {
        return solg;
    }

    public void setSolg(int solg) {
        this.solg = solg;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getTonggia() {
        return tonggia;
    }

    public void setTonggia(int tonggia) {
        this.tonggia = tonggia;
    }
}
