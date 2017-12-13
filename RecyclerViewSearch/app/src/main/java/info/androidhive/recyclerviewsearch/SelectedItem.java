package info.androidhive.recyclerviewsearch;

/**
 * Created by ravi on 16/11/17.
 */

public class SelectedItem {
    String name;
    String image;

    public SelectedItem() {
    }

    public SelectedItem(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}
