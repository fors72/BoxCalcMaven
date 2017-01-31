package ua.com.eliteupakovka.conctruction;


import ua.com.eliteupakovka.Parameters;

public class ConstructionPart {
    private int id;
    private int constrId;
    private int materialTypeId;
    private int quantity;
    private int evenNeed;
    private String name;
    private String type;
    private String dependType;
    private Sizes sizes;
    private boolean pressing;
    private boolean laminable;
    private WorkCost workCost;
    private Parameters parameters;

    public ConstructionPart(int id, int constrId, int materialTypeId, String name, String type, String dependType, Sizes sizes, int quantity, boolean pressing, boolean laminable, WorkCost workCost, Parameters parameters) {
        this.id = id;
        this.constrId = constrId;
        this.materialTypeId = materialTypeId;
        this.name = name;
        this.type = type;
        this.dependType = dependType;
        this.sizes = sizes;
        this.evenNeed = quantity;
        this.quantity = quantity;
        this.pressing = pressing;
        this.laminable = laminable;
        this.workCost = workCost;
        this.parameters = parameters;
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

    public String getDependType() {
        return dependType;
    }

    public Sizes getSizes() {
        return sizes;
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

    public boolean isPressing() {
        return pressing;
    }

    public boolean isLaminable() {
        return laminable;
    }

    public WorkCost getWorkCost() {
        return workCost;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setMaterialTypeId(int materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPressing(boolean pressing) {
        this.pressing = pressing;
    }

    public void setLaminable(boolean laminable) {
        this.laminable = laminable;
    }

    public void setWorkCost(WorkCost workCost) {
        this.workCost = workCost;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return name;
    }
}
