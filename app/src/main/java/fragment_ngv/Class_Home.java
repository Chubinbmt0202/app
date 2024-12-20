package fragment_ngv;

public class Class_Home {
    private int imgmonan ;
    private String giadonvi  , tenmonan;
    private String mota;
    private String madanhmuc;
    private String masp;
    private int traitim;
    private int Mahome;

    public Class_Home(int Mahome ,String madanhmuc, String masp, String tenmonan, int imgmonan, String giadonvi, String mota, int traitim) {
        this.madanhmuc = madanhmuc;
        this.masp = masp;
        this.tenmonan = tenmonan;
        this.imgmonan = imgmonan;
        this.giadonvi = giadonvi;
        this.mota = mota;
        this.traitim = traitim;
        this.Mahome = Mahome;
    }

    public int getMahome() {
        return Mahome;
    }

    public void setMahome(int mahome) {
        Mahome = mahome;
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

    public String getMadanhmuc() {
        return madanhmuc;
    }

    public void setMadanhmuc(String madanhmuc) {
        this.madanhmuc = madanhmuc;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
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
}

