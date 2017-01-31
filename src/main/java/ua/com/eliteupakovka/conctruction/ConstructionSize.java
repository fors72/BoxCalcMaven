package ua.com.eliteupakovka.conctruction;


public class ConstructionSize {
    private int id;
    private int idSize;
    private int idConstruction;
    private int idConstructionPart;
    private double ws;
    private double wd;

    public ConstructionSize(int id, int idSize, int idConstruction, int idConstructionPart, double ws, double wd) {
        this.id = id;
        this.idSize = idSize;
        this.idConstruction = idConstruction;
        this.idConstructionPart = idConstructionPart;
        this.ws = ws;
        this.wd = wd;
    }

    public int getId() {
        return id;
    }

    public int getIdSize() {
        return idSize;
    }

    public int getIdConstruction() {
        return idConstruction;
    }

    public int getIdConstructionPart() {
        return idConstructionPart;
    }

    public double getWs() {
        return ws;
    }

    public double getWd() {
        return wd;
    }
}
