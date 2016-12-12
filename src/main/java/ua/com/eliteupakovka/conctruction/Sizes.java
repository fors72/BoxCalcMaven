package ua.com.eliteupakovka.conctruction;


public class Sizes {
    private double width,length,heightBottom,heightTop;

    public Sizes(double width, double length, double heightBottom, double heightTop) {
        this.width = width;
        this.length = length;
        this.heightBottom = heightBottom;
        this.heightTop = heightTop;
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

    @Override
    public String toString() {
        if (width == 0 && length == 0 && heightBottom == 0 & heightTop == 0){
            return "Другой";
        }
        return width + "x" + length + "x" + heightBottom;
    }
}
