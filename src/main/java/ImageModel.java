import java.io.Serializable;
import java.util.Objects;

/**
 * model to be saved to file
 * @author sufu
 */
public class ImageModel implements Serializable {
    String imageUrl;
    double x;
    double y;
    double w;
    double h;

    public ImageModel() {
    }

    public ImageModel(String imageUrl, double x, double y, double w, double h) {
        this.imageUrl = imageUrl;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public String toString() {
        return imageUrl + "," + x + "," + y + "," + w + "," + h;
    }
    /**
     * override equals and hashcode to put this class in hashmap
     * @author sufu
     * @date 2020/12/6 0:02
     * @param o the Object to be compared
     * @return boolean true if two object is the same object
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageModel that = (ImageModel) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.w, w) == 0 &&
                Double.compare(that.h, h) == 0 &&
                imageUrl.equals(that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, x, y, w, h);
    }
}
