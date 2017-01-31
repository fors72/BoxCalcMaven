package ua.com.eliteupakovka.material;

public class MaterialType {
    private int id;
    private String name;
    private double thickness;

    public MaterialType(int id, String name, double thickness) {
        this.id = id;
        this.name = name;
        this.thickness = thickness;
    }


    @Override
    public String toString() {
        if (thickness != 0) {
            if ((thickness % 1) == 0)
                return name + " " + (int)thickness + "мм";
            return name + " " + thickness + "мм";
        }else {
            return name;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getThickness() {
        return thickness;
    }
}
