package ua.com.eliteupakovka.material;


public class Material {
    String name;
    private int id;
    private int idMaterial;
    private double width;
    private double length;
    private double cost;
    private boolean enable;
    private double thickness;

    public Material(int id, String name, double width, double length, double cost) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
        this.cost = cost;
        this.enable = true;
    }
    public Material(int id, String name, double width, double length, double cost,int enable,double thickness) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
        this.cost = cost;
        this.enable = enable == 1;
        this.thickness = thickness;
    }

    public Material(String name, int id, int idMaterial, double width, double length, double cost, boolean enable) {
        this.name = name;
        this.id = id;
        this.idMaterial = idMaterial;
        this.width = width;
        this.length = length;
        this.cost = cost;
        this.enable = enable;
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

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        if (width != 0 && length != 0) {
            if (thickness > 0) {
                return name + " " + thickness + "мм. " + width + "x" + length;
            }else {
                return name + " " + width + "x" + length;
            }
        } else {
            return name;
        }
    }
}
