package User_Restaurant;

public class PhanHoi_Restaurant {
    private int imguser ;
    private String nameuser , text_phanhoi ;

    public PhanHoi_Restaurant(int imguser, String nameuser, String text_phanhoi) {
        this.imguser = imguser;
        this.nameuser = nameuser;
        this.text_phanhoi = text_phanhoi;
    }

    public int getImguser() {
        return imguser;
    }

    public void setImguser(int imguser) {
        this.imguser = imguser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getText_phanhoi() {
        return text_phanhoi;
    }

    public void setText_phanhoi(String text_phanhoi) {
        this.text_phanhoi = text_phanhoi;
    }
}
