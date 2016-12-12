package ua.com.eliteupakovka.material;


public class Carton extends Material {

    private double thickness;

    public Carton(String name, double width, double length, double cost, double thickness) {
        super(name, width, length, cost);
        this.thickness = thickness;
    }


    @Override
    public String toString() {
        if ((thickness % 1) == 0)
            return name + " " + (int)thickness + "мм";
        return name + " " + thickness + "мм";
    }
}
