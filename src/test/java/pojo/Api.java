package pojo;

//Need to create this class because inside Api there are two below variable are present
public class Api {
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String courseTitle;
    private String price;
}
