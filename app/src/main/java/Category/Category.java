package Category;

public class Category {
    private String name;
    private int imageResId;
    private int icon;

    public Category(int icon, int imageResId, String name) {
        this.icon = icon;
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
