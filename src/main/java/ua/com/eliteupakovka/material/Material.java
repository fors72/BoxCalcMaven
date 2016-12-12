package ua.com.eliteupakovka.material;


public class Material {
    String name;
    private double width;
    private double length;
    private double cost;
    private boolean enable;

    public Material(String name, double width, double length, double cost) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.cost = cost;
        this.enable = true;
    }

    public String getName() {
        return name;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name;
    }
}
