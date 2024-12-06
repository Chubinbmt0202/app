package Accout;

public class Class_Detail {
    private int img , solg;
    private int tonggia  ;
    private String tenmon;

    public Class_Detail( String tenmon, int tonggia, int solg, int img) {
        this.tenmon = tenmon;
        this.tonggia = tonggia;
        this.solg = solg;
        this.img = img;
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
