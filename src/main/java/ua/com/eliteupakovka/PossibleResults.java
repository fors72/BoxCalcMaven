package ua.com.eliteupakovka;


import ua.com.eliteupakovka.conctruction.ConstructionPart;
import ua.com.eliteupakovka.material.Material;

import static java.lang.Math.abs;

public class PossibleResults<T extends Material> {

    PossibleResults<T> possibleResultWithMinTol;
    T material;
    public ConstructionPart part1;
    public ConstructionPart part2;
    public double ost;
    double widthBottom;
    double lengthBottom;
    double widthTop;
    double lengthTop;
    double ccc;
    double g;
    String name,curResult;
    public  int quantBottom, quantTop, k1, k2,xq = 0, quantityMaterial,qbl,qbw,qtl = k1,qtw = k2;
    boolean belowBottom ;
    boolean needTurnTop;
    boolean needTurnBottom;
    double lamin;





    public PossibleResults(T material, double widthBottom, double lengthBottom, double widthTop, double lengthTop,String name,int quantity) {

        this.material = material;
        this.widthBottom = widthBottom;
        this.lengthBottom = lengthBottom;
        this.widthTop = widthTop;
        this.lengthTop = lengthTop;
        this.name = name;
        widthBottom = round(widthBottom,2);

        initResult(material, widthBottom, lengthBottom, widthTop, lengthTop, quantity);
    }
    public PossibleResults(T material, ConstructionPart part1, ConstructionPart part2, String name) throws AnotherTypeExeption {
        if (part1.getMaterialTypeId() != part2.getMaterialTypeId()){
            throw new AnotherTypeExeption("material part 1 != material part 2");
        }
        this.material = material;
        this.part1 = part1;
        this.part2 = part2;
        this.widthBottom = part1.getSizes().getWidth();
        this.lengthBottom = part1.getSizes().getLength();
        this.widthTop = part2.getSizes().getWidth();
        this.lengthTop = part2.getSizes().getLength();
        this.name = name;

        widthBottom = round(widthBottom,2);

        initResult(material, widthBottom, lengthBottom, widthTop, lengthTop, part1.getQuantity());
    }

    private void initResult(T material, double widthBottom, double lengthBottom, double widthTop, double lengthTop, int quantity) {
        int qw = 0, ql = 0, qw2 = 0, ql2 = 0,q , q2;
        double r,a = material.getWidth(),b = material.getLength(),c = material.getWidth() ,d = material.getLength();

        while ((b - widthBottom) >= 0) {
            b = round(b - widthBottom,2);
            qw++;
        }
        while ((a - lengthBottom) >= 0) {
            a = round(a - lengthBottom,2);
            ql++;
        }
        while ((c - widthBottom) >= 0) {
            c = round(c - widthBottom,2);
            qw2++;
        }
        while ((d - lengthBottom) >= 0) {
            d = round(d - lengthBottom,2);
            ql2++;
        }
        q= ql * qw;
        q2 = ql2 * qw2;
        r = abs(a - b) - abs(c - d);
//        q0 = q + "x" + q2;


//        If qB = 0 Then ost = 100000

        if (q > q2||((q == q2) && (r >= 0))){
            curResult = widthBottom + "*" + qw + "+" + b + "по" + material.getLength() + "//" + lengthBottom + "*" + ql + "+" + a + "по" + material.getWidth();
            quantBottom = q;
            needTurnBottom = false;
            qbl = ql;
            qbw = qw;
            ost = a * material.getLength() + b * (material.getWidth() - a);
            if (a > widthTop && material.getLength()> lengthTop && xq !=1){
                g = a;
                ccc = material.getLength();
                k00();
                curResult = widthBottom + "*" + qw + "+" + b + "по" + material.getLength() + "//" + lengthBottom + "*" + ql + "+" + widthTop + "*" + k1 + "+" + g + "по" + material.getWidth() + "\\" + lengthTop + "*" + k2 + "+" + ccc + "по" +material.getLength();
                xq = 1;
                belowBottom = true;
                needTurnTop = false;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );

            }else if (b > widthTop && material.getWidth()> lengthTop&& xq !=1){
                g = b;
                ccc = material.getWidth();
                k00();
                int f;
                curResult = widthBottom + "*" + qw + "+" + widthTop + "*" + k1 + "+" + g + "по" + material.getLength() + "//" + lengthBottom + "*" + ql + "+" + a + "по" + material.getWidth() + "\\" + lengthTop + "*" + k2 + "+" + ccc + "по" + material.getWidth();
                xq = 1;
                belowBottom = false;
                needTurnTop = false;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }else if (a > lengthTop && material.getWidth()> widthTop && xq !=1){
                g = a;
                ccc = material.getLength();
                k0();
                int f;
                curResult = widthBottom + "*" + qw + "+" + b + "по" + material.getLength() + "//" + lengthBottom + "*" + ql + "+" + lengthTop + "*" + k1 + "+" + g + "по" + material.getWidth() + "\\" + widthTop + "*" + k2 + "+" + ccc + "по" + material.getLength();
                xq = 1;
                belowBottom = true;
                needTurnTop = true;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }else if (b > lengthTop && material.getWidth()> widthTop && xq !=1){
                g = b;
                ccc = material.getWidth();
                k0();
                curResult = widthBottom + "*" + qw + "+" + lengthTop + "*" + k1 + "+" + g + "по" + material.getLength() + "//" + lengthBottom + "*" + ql + "+" + a + "по" + material.getWidth() + "\\" + widthTop + "*" + k2 + "+" + ccc + "по" + material.getWidth();
                belowBottom = false;
                needTurnTop = true;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }

        }else if (q < q2||((q == q2) && (r < 0))) {
            curResult = lengthBottom + "*" + ql2 + "+" + d + "по" + material.getLength() + "//" + widthBottom + "*" + qw2 + "+" + c + "по" + material.getWidth();
//            qB = q2;
            quantBottom = q2;
            needTurnBottom = true;
            qbl = ql2;
            qbw = qw2;
            ost = c * material.getLength() + d * (material.getWidth() - c);
            if (c > widthTop && material.getLength()> lengthTop && xq !=1){
                 g = c;
                ccc = material.getLength();
                k00();
                curResult = lengthBottom + "*" + ql2 + "+" + d + "по" + material.getLength() + "//" + widthBottom + "*" + qw2 + "+" + widthTop + "*" + k1 + "+" + g + "по" + material.getWidth() + "\\" + lengthTop + "*" + k2 + "+" + ccc + "по" + material.getLength();
                xq = 1;
                belowBottom = true;
                int f;
                needTurnTop = false;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }else if (d > widthTop && material.getWidth()> lengthTop && xq !=1){
                 g = d;
                ccc = material.getWidth();
                k00();
                curResult = lengthBottom + "*" + ql2 + "+" + widthTop + "*" + k1 + "+" + g + "по" + material.getLength() + "//" + widthBottom + "*" + qw2 + "+" + c + "по" + material.getWidth() + "\\" + lengthTop + "*" + k2 + "+" + ccc + "по" + material.getWidth();
                xq = 1;
                belowBottom = false;
                needTurnTop = false;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }else if (c > lengthTop && material.getWidth()> widthTop && xq !=1){
                 g = c;
                ccc = material.getLength();
                k0();
                curResult = lengthBottom + "*" + ql2 + "+" + d + "по" + material.getLength() + "//" + widthBottom + "*" + qw2 + "+" + lengthTop + "*" + k1 + "+" + g + "по" + material.getWidth() + "\\" + widthTop + "*" + k2 + "+" + ccc + "по" + material.getLength();
                xq = 1;
                belowBottom = true;
                needTurnTop = true;
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }else if (d > lengthTop && material.getWidth()> widthTop && xq !=1){
                 g = d;
                ccc = material.getWidth();
                k0();
                belowBottom = false;
                needTurnTop = true;
                curResult = lengthBottom + "*" + ql2 + "+" + lengthTop + "*" + k1 + "+" + g + "по" + material.getLength() + "//" + widthBottom + "*" + qw2 + "+" + c + "по" + material.getWidth() + "\\" + widthTop + "*" + k2 + "+" + ccc + "по" + material.getWidth();
                setOst(material, widthBottom, lengthBottom, widthTop, lengthTop,quantBottom );
            }

            if (quantBottom == 0)ost = 10000;


        }
        if (widthBottom == widthTop && lengthBottom == lengthTop){
            quantBottom = quantBottom + quantTop;
            quantTop = 0;
        }
        quantityMaterial = (int) Math.ceil(((quantity + 0.0) / quantBottom)*1.05);
    }

    public PossibleResults(ConstructionPart part1, T material) {
        if (part1.getType().equals("магнит")){
            int multi = 2;
            if (part1.getSizes().getLength() > 20.0 && part1.getConstrId() != 20){
                multi *= 2;
            }
            if (part1.getConstrId() == 20){
                multi *= 2;
            }
            quantityMaterial = part1.getQuantity() * multi;
        }else if (part1.getType().equals("лента")){
            quantityMaterial = part1.getQuantity() * 2;
        }else if (part1.getType().equals("шнур")){
            quantityMaterial = part1.getQuantity();
        }else if (part1.getType().equals("люверс")){
            quantityMaterial = part1.getQuantity() * 4;
        }else {
            quantityMaterial = part1.getQuantity();
        }
        this.material = material;
        this.name = part1.getName();
        this.part1 = part1;
    }

    private void setOst(T material, double widthBottom, double lengthBottom, double widthTop, double lengthTop, int quantBottom) {
        if (quantTop > quantBottom) quantTop = quantBottom;
        ost = material.getWidth() * material.getLength() - quantBottom * widthBottom * lengthBottom - quantTop * widthTop * lengthTop;
    }


    private void k0(){
        k1 = 0;
        k2 = 0;
        while ((g - lengthTop) > 0) {
            g = round(g - lengthTop,2);
            k1 = k1 + 1;
        }
        while (ccc - widthTop > 0) {
            ccc = round(ccc - widthTop,2);
            k2 = k2 + 1;
        }
        quantTop = k1 * k2;
//        k0 = k1 & "x" & k2

    }
    private double round(double number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return (double) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }
    private  void k00(){
        k1 = 0;
        k2 = 0;
        while (g - widthTop > 0) {
            g = round(g - widthTop,2);
            k1 = k1 + 1;
        }
        while (ccc - lengthTop > 0) {
            ccc = round(ccc - lengthTop,2);
            k2 = k2 + 1;
        }
        quantTop = k1 * k2;
//        k00 = k1 & "x" & k2

    }

    @Override
    public String toString() {
//        return name + " (" + widthBottom + "x" + lengthBottom;
        return "(" + name + ") " + material.toString() + " " + quantityMaterial + " шт. " + (int)material.getLength() + "x" + (int)material.getWidth();
    }
}
