package ua.com.eliteupakovka.material;


import ua.com.eliteupakovka.Kashirovka;

public class Paper extends Material {
    private Kashirovka kash;
    private double priseForWork;


    public Paper(String name, double width, double length, double cost, boolean isNeedKashirovka) {
        super(name, width, length, cost);
        if (isNeedKashirovka)
            this.kash = new Kashirovka(name,width,length,cost);
    }
    public Kashirovka getKash() {
        return kash;
    }

}
