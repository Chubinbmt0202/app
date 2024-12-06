package fragment_ngv;

public class Class_Home {
    private int imgmonan ;
    private String giadonvi  , tenmonan;
    private String mota;
    private int traitim;
    private int mahome;

    public Class_Home(int mahome,String tenmonan, int imgmonan, String giadonvi, String mota, int traitim) {
        this.tenmonan = tenmonan;
        this.imgmonan = imgmonan;
        this.giadonvi = giadonvi;
        this.mota = mota;
        this.mahome = mahome;
        this.traitim = traitim;
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

    public int getTraitim() {
        return traitim;
    }

    public void setTraitim(int traitim) {
        this.traitim = traitim;
    }

    public int getMahome() {
        return mahome;
    }

    public void setMahome(int mahome) {
        this.mahome = mahome;
    }
}

