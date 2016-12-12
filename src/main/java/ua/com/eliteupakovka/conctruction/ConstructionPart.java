package ua.com.eliteupakovka.conctruction;


import ua.com.eliteupakovka.material.Material;

public class ConstructionPart<T extends Material> {
    T material;
    int id;
    int constrId;
    int materialTypeId;
    int quantity;
    int evenNeed;
    String name;
    String type;
    int dependId;
    String dependType;
    Sizes sizes;
    double width;
    double length;
    private final double minWidthTol = 0.5;
    private final double minLengthTol = 0.5;
    double WidthTol;
    double LengthTol;

    public ConstructionPart(int id, int constrId, int materialTypeId, String name, String type, int dependId, String dependType, Sizes sizes) {
        this.id = id;
        this.constrId = constrId;
        this.materialTypeId = materialTypeId;
        this.name = name;
        this.type = type;
        this.dependId = dependId;
        this.dependType = dependType;
        this.sizes = sizes;
    }

    public int getId() {
        return id;
    }

    public int getConstrId() {
        return constrId;
    }

    public int getMaterialTypeId() {
        return materialTypeId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDependId() {
        return dependId;
    }

    public String getDependType() {
        return dependType;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getEvenNeed() {
        return evenNeed;
    }

    public void setEvenNeed(int evenNeed) {
        this.evenNeed = evenNeed;
    }
}
