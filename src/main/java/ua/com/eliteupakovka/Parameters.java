package ua.com.eliteupakovka;


public class Parameters {

    private double tolerance;
    private double widthMulti;
    private double widthAdd;
    private double lengthMulti;
    private double lengthAdd;
    private double heightBottomMulti;
    private double heightBottomAdd;
    private double heightTopMulti;
    private double heightTopAdd;
    private double heightBottomMultiByWidth;
    private double heightBottomMultiByLength;
    private double heightTopMultiByWidth;
    private double heightTopMultiByLength;

    public Parameters(double tolerance, double widthMulti, double widthAdd, double lengthMulti, double lengthAdd, double heightBottomMulti, double heightBottomAdd, double heightTopMulti, double heightTopAdd) {
        this.tolerance = tolerance;
        this.widthMulti = widthMulti;
        this.widthAdd = widthAdd;
        this.lengthMulti = lengthMulti;
        this.lengthAdd = lengthAdd;
        this.heightBottomMulti = heightBottomMulti;
        this.heightBottomAdd = heightBottomAdd;
        this.heightTopMulti = heightTopMulti;
        this.heightTopAdd = heightTopAdd;
    }

    public Parameters(double tolerance, double widthMulti, double widthAdd, double lengthMulti, double lengthAdd, double heightBottomMulti, double heightBottomAdd, double heightTopMulti, double heightTopAdd, double heightBottomMultiByWidth, double heightBottomMultiByLength, double heightTopMultiByWidth, double heightTopMultiByLength) {
        this.tolerance = tolerance;
        this.widthMulti = widthMulti;
        this.widthAdd = widthAdd;
        this.lengthMulti = lengthMulti;
        this.lengthAdd = lengthAdd;
        this.heightBottomMulti = heightBottomMulti;
        this.heightBottomAdd = heightBottomAdd;
        this.heightTopMulti = heightTopMulti;
        this.heightTopAdd = heightTopAdd;
        this.heightBottomMultiByWidth = heightBottomMultiByWidth;
        this.heightBottomMultiByLength = heightBottomMultiByLength;
        this.heightTopMultiByWidth = heightTopMultiByWidth;
        this.heightTopMultiByLength = heightTopMultiByLength;
    }

    public double getTolerance() {
        return tolerance;
    }

    public double getWidthMulti() {
        return widthMulti;
    }

    public double getWidthAdd() {
        return widthAdd;
    }

    public double getLengthMulti() {
        return lengthMulti;
    }

    public double getLengthAdd() {
        return lengthAdd;
    }

    public double getHeightBottomMulti() {
        return heightBottomMulti;
    }

    public double getHeightBottomAdd() {
        return heightBottomAdd;
    }

    public double getHeightTopMulti() {
        return heightTopMulti;
    }

    public double getHeightTopAdd() {
        return heightTopAdd;
    }

    public double getHeightBottomMultiByWidth() {
        return heightBottomMultiByWidth;
    }

    public double getHeightBottomMultiByLength() {
        return heightBottomMultiByLength;
    }

    public double getHeightTopMultiByWidth() {
        return heightTopMultiByWidth;
    }

    public double getHeightTopMultiByLength() {
        return heightTopMultiByLength;
    }
}

