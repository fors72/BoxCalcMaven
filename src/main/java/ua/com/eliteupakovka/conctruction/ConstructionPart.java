package ua.com.eliteupakovka.conctruction;


import ua.com.eliteupakovka.Parameters;

import java.util.ArrayList;
import java.util.List;

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
    private List<ConstructionPart> childConstructionParts;
    private int idGroup;
    private boolean parentPart;

    public ConstructionPart(int id, int constrId, int materialTypeId, String name, String type, String dependType, Sizes sizes, int quantity, boolean pressing, boolean laminable, WorkCost workCost, Parameters parameters,int idGroup) {
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
        this.idGroup = idGroup;
    }


    public void initParentPart(){
        parentPart = true;

        for (int i = 0;i < childConstructionParts.size();i++){
            if (idGroup > 0) {
                sizes.setWidth(sizes.getWidth() + childConstructionParts.get(i).getSizes().getWidth());
            }else if (idGroup < 0) {
                sizes.setLength(sizes.getLength() + childConstructionParts.get(i).getSizes().getLength());
            }
        }

    }
    public Sizes getOwnSize(){
        Sizes size = sizes.clone();
        for (int i = 0 ;i < childConstructionParts.size();i++){
            if (idGroup > 0){
                size.setWidth(size.getWidth() - childConstructionParts.get(i).getSizes().getWidth());
            }else if (idGroup < 0){
                size.setLength(size.getLength() - childConstructionParts.get(i).getSizes().getLength());
            }
        }
        return size;
    }

    public int getId() {
        return id;
    }

    public void addChildConstructionParts(ConstructionPart childConstructionPart) {
        if (childConstructionParts == null){
            childConstructionParts = new ArrayList<>();
        }
        childConstructionParts.add(childConstructionPart);
    }

    public List<ConstructionPart> getChildConstructionParts() {
        return childConstructionParts;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public boolean isParentPart() {
        return parentPart;
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
