package ua.com.eliteupakovka.conctruction;


public class Sizes {
    private double width,length,heightBottom,heightTop;
    private int id;
    private String name;

    public Sizes(int id, double width, double length, double heightBottom, double heightTop) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.heightBottom = heightBottom;
        this.heightTop = heightTop;
    }

    public Sizes(int id, double width, double length, double heightBottom, double heightTop,  String name) {
        this.width = width;
        this.length = length;
        this.heightBottom = heightBottom;
        this.heightTop = heightTop;
        this.id = id;
        this.name = name;
    }

    public Sizes(double width, double length) {
        this.width = width;
        this.length = length;
    }

    //    public Sizes(double diameter, double height) {
//        this.width = diameter;
//        this.heightBottom = height;
//    }


    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getHeightBottom() {
        return heightBottom;
    }

    public double getHeightTop() {
        return heightTop;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (width == 0 && length == 0 && heightBottom == 0 & heightTop == 0) {
            return "Другой";
        } else {
            return width + "x" + length + "x" + heightBottom;
        }

    }
}
