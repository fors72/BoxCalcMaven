package ua.com.eliteupakovka;

import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Deprecated
class Construction {

    String name;
    List<PossibleResults<Carton>> listTopResultCarton = new ArrayList<>();
    List<PossibleResults<Paper>> listTopResultPaper = new ArrayList<>();
    List<Carton> cartonSelectList;
    List<Paper> paperSelectList;
    List<Paper> kashirovkaSelectList;
    double addCosts;

    Construction(String name, double width,double length,double heightBottom,double heightTop,List<Carton> cartonSelectList, List<Paper> paperSelectList,List<Paper> kashirovkaSelectList,int quantity,Parameters parameters) {
        this.name = name;
        this.cartonSelectList = cartonSelectList;
        this.paperSelectList = paperSelectList;
        this.kashirovkaSelectList = kashirovkaSelectList;

        switch (name){
            case "кришка+дно":
                listTopResultCarton.add(bottomC(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultCarton.add(galmoKompensator(topC(width,length,heightTop,"Кришка",quantity,parameters),listTopResultCarton.get(listTopResultCarton.size()-1)));

                listTopResultPaper.add(bottomP(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultPaper.add(galmoKompensator(topP(width,length,heightTop,"Кришка",quantity,parameters),listTopResultPaper.get(listTopResultPaper.size()-1)));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomK(width, length, heightBottom, heightTop, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topK(width, length, heightTop, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                break;

            case "книга на магните":
                listTopResultCarton.add(bottomBookMagC(width,length,heightBottom,"Дно",quantity));
                listTopResultCarton.add(galmoKompensator(topBookMagC(width,length,heightBottom,"Кришка",quantity),listTopResultCarton.get(listTopResultCarton.size()-1)));

                listTopResultPaper.add(bottomBookMagP(width,length,heightBottom,"Дно",quantity));
                listTopResultPaper.add(galmoKompensator(topBookMagP(width,length,heightBottom,"Кришка",quantity),listTopResultPaper.get(listTopResultPaper.size()-1)));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomBookMagK(width, length, heightBottom, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topBookMagK(width, length, heightBottom, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                addCosts = 2 * 2 + 3;//magnit + rabota
                break;
            case "книга на ленте":
                Comparator<PossibleResults<Carton>> comparator = (o1, o2) -> (int) o2.ost-(int) o1.ost;
                listTopResultCarton.add(bottomBookLentaC(width,length,heightBottom,"Дно",quantity));
                listTopResultCarton.add(galmoKompensator(topBookLentaC(width,length,heightBottom,"Кришка",quantity),listTopResultCarton.get(listTopResultCarton.size()-1)));
                listTopResultCarton.sort(comparator);
                listTopResultPaper.add(bottomBookLentaP(width,length,heightBottom,"Дно",quantity));
                listTopResultPaper.add(galmoKompensator(topBookLentaP(width,length,heightBottom,"Кришка",quantity),listTopResultPaper.get(listTopResultPaper.size()-1)));

                listTopResultCarton.add(vstavkaBookLentaC(width,length,"Вставка",quantity));
                listTopResultPaper.add(vstavkaBookLentaP(width,length,"Вставка",quantity));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomBookLentaK(width, length, heightBottom, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topBookLentaK(width, length, heightBottom, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                addCosts = 5 + 3;//lenta + rabota
                break;
            case "шкатулка":

            case "гардеробная":
                listTopResultCarton.add(bottomC(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultCarton.add(galmoKompensator(topC(width,length,heightTop,"Кришка",quantity,parameters),listTopResultCarton.get(listTopResultCarton.size()-1)));

                listTopResultPaper.add(bottomP(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultPaper.add(galmoKompensator(topP(width,length,heightTop,"Кришка",quantity,parameters),listTopResultPaper.get(listTopResultPaper.size()-1)));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomK(width, length, heightBottom, heightTop, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topK(width, length, heightTop, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                addCosts = 5 + 6;
                break;

            case "круглая":
                double bottomCarton=0; double topCarton=0; double bottomPaper=0; double topPaper=0; double bottomKashirovka=0; double topKashirovka=0; double bottomPaperO=0; double topPaperO=0; double bottomPaperHeight=0; double topPaperHeight=0; double topCartonTape=0; double topKashirovkaTape=0;
                if (width == 15.2){
                    addCosts = 3.5;
                    bottomCarton = 16.85;
                    topCarton = 17.35;
                    bottomPaper = 17.5;
                    topPaper = 18;
                    bottomKashirovka = bottomCarton - 1;
                    topKashirovka = topCarton - 1;
                    bottomPaperO = 49.8;
                    topPaperO = 50.6;
                    bottomPaperHeight = heightBottom + 0.8;
                    topPaperHeight = 3 + 0.8;
                    topCartonTape = 50.1 + 1.5;
                    topKashirovkaTape = topCartonTape - 1;
                }else if (width == 20){
                    addCosts = 5.5;
                    bottomCarton = 21.85;
                    topCarton = 22.35;
                    bottomPaper = 22.5;
                    topPaper = 23;
                    bottomKashirovka = bottomCarton - 1;
                    topKashirovka = topCarton - 1;
                    bottomPaperO = 64.8;
                    topPaperO = 66.3;
                    bottomPaperHeight = heightBottom + 0.8;
                    topPaperHeight = 3 + 0.8;
                    topCartonTape = 65.8 + 1.5;
                    topKashirovkaTape = topCartonTape - 1;
                }else if (width == 30){
                    addCosts = 7.5;
                    bottomCarton = 31.85;
                    topCarton = 32.35;
                    bottomPaper = 32.5;
                    topPaper = 33;
                    bottomKashirovka = 30.85;
                    topKashirovka = topCarton - 1;
                    bottomPaperO = 96;
                    topPaperO = 97;
                    bottomPaperHeight = heightBottom + 0.8;
                    topPaperHeight = 3 + 0.8;
                    topCartonTape = 96.8 + 1.5;
                    topKashirovkaTape = topCartonTape - 1;
                }

                listTopResultCarton.add(circleC(topCarton,"К-кругляшка",quantity));
                listTopResultCarton.add(circleC(bottomCarton,"Д-кругляшка",quantity));
                listTopResultCarton.add(tapeC(topCartonTape,10.5,3,"К-лента",quantity));

                listTopResultPaper.add(circleP(topPaper,"К-кругляшка",quantity));
                listTopResultPaper.add(circleP(bottomPaper,"Д-кругляшка",quantity));

                listTopResultPaper.add(tapeP(bottomPaperO,bottomPaperHeight,1,"Д-лента",quantity));
                listTopResultPaper.add(tapeP(topPaperO,topPaperHeight,1,"К-лента",quantity));

                listTopResultPaper.add(circleK(topKashirovka,"К-кашировка",quantity));
                listTopResultPaper.add(circleK(bottomKashirovka,"Д-кашировка",quantity));
                listTopResultPaper.add(tapeK(topKashirovkaTape,9.5,3,"К-Каш-лента",quantity));
                addCosts = addCosts + 12;
                break;


            case "с ложементом":
                listTopResultCarton.add(bottomC(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultCarton.add(galmoKompensator(topC(width,length,heightTop,"Кришка",quantity,parameters),listTopResultCarton.get(listTopResultCarton.size()-1)));

                listTopResultPaper.add(bottomP(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultPaper.add(galmoKompensator(topP(width,length,heightTop,"Кришка",quantity,parameters),listTopResultPaper.get(listTopResultPaper.size()-1)));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomK(width, length, heightBottom, heightTop, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topK(width, length, heightTop, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                break;

            case "с окошком из пласт.":
                listTopResultCarton.add(bottomC(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultCarton.add(galmoKompensator(topC(width,length,heightTop,"Кришка",quantity,parameters),listTopResultCarton.get(listTopResultCarton.size()-1)));

                listTopResultPaper.add(bottomP(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultPaper.add(galmoKompensator(topP(width,length,heightTop,"Кришка",quantity,parameters),listTopResultPaper.get(listTopResultPaper.size()-1)));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomK(width, length, heightBottom, heightTop, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topK(width, length, heightTop, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                break;

            case "с ложементом из флок.":
                listTopResultCarton.add(bottomC(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultCarton.add(galmoKompensator(topC(width,length,heightTop,"Кришка",quantity,parameters),listTopResultCarton.get(listTopResultCarton.size()-1)));

                listTopResultPaper.add(bottomP(width,length,heightBottom,heightTop,"Дно",quantity,parameters));
                listTopResultPaper.add(galmoKompensator(topP(width,length,heightTop,"Кришка",quantity,parameters),listTopResultPaper.get(listTopResultPaper.size()-1)));

                if (!kashirovkaSelectList.get(0).toString().equals("нет")) {
                    listTopResultPaper.add(bottomK(width, length, heightBottom, heightTop, "Кашировка дно", quantity));
                    listTopResultPaper.add(galmoKompensator(topK(width, length, heightTop, "Кашировка кришка", quantity), listTopResultPaper.get(listTopResultPaper.size() - 1)));
                }
                break;

        }

    }


    private PossibleResults<Carton> bottomC(double width,double length,double heightBottom,double heightTop, String name,int quantity,Parameters parameters){
        double cartonWidthB = width + heightBottom * 2; //+ parameters.getToleranceOfwidthCartonBottom();
        double cartonLengthB = length + heightBottom * 2; // +  parameters.getToleranceOflengthCartonBottom();
        double cartonWidthT = width + heightTop * 2 + 0.5; // + parameters.getToleranceOfwidthCartonTop();
        double cartonLengthT = length + heightTop * 2 + 0.5; // + parameters.getToleranceOflengthCartonTop();
        List<PossibleResults> resultsList = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthB, cartonLengthB,cartonWidthT,cartonLengthT, name,quantity);
            resultsList.add(results);
        }
        return getMinOst(resultsList);
    }
    private PossibleResults<Carton> topC(double width,double length,double heightTop, String name,int quantity,Parameters parameters){
        double cartonWidthT = width + heightTop * 2 +  0.5; // + parameters.getToleranceOfwidthCartonTop();
        double cartonLengthT = length + heightTop * 2 + 0.5; // + parameters.getToleranceOflengthCartonTop();
        List<PossibleResults> resultsList3 = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthT,cartonLengthT,cartonWidthT,cartonLengthT, name,quantity);
            resultsList3.add(results);
        }
        return getMinOst(resultsList3);
    }
    private PossibleResults<Paper> bottomP(double width,double length,double heightBottom,double heightTop, String name,int quantity,Parameters parameters){
        double paperWidthB = width + heightBottom * 2 + 3; // + parameters.getToleranceOfwidthPaperBottom();
        double paperLengthB = length + heightBottom * 2 + 3; // + parameters.getToleranceOflengthPapeBottomr();
        double paperWidthT = width + heightTop * 2 + 0.5 + 3; // + parameters.getToleranceOfwidthPaperTop();
        double paperLengthT = length + heightTop * 2 + 0.5 + 3; // + parameters.getToleranceOflengthPaperTop();
        List<PossibleResults> resultsList1 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthB, paperLengthB,paperWidthT,paperLengthT, name,quantity);
            resultsList1.add(results);
        }
        PossibleResults<Paper> toppap = getMinOst(resultsList1);
        toppap.lamin=(paperWidthB * paperLengthB + paperWidthT * paperLengthT);
        return toppap ;

    }
    private PossibleResults<Paper> topP(double width,double length,double heightTop, String name,int quantity,Parameters parameters){
        double paperWidthT = width + heightTop * 2 + 0.5 + 3; // + parameters.getToleranceOfwidthPaperTop();
        double paperLengthT = length + heightTop * 2 + 0.5 + 3; // + parameters.getToleranceOflengthPaperTop();
        List<PossibleResults> resultsList4 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthT,paperLengthT,paperWidthT,paperLengthT, name,quantity);
            resultsList4.add(results);
        }
        return getMinOst(resultsList4);
    }
    private PossibleResults<Paper> bottomK(double width, double length, double heightBottom, double heightTop,String name,int quantity){
        double kashirovkaWidthB = width + heightBottom * 2 + 0.5 - 1;
        double kashirovkaLengthB = length + heightBottom * 2 + 0.5 - 1;
        double kashirovkaWidthT = width + heightTop * 2 + 1 - 1;
        double kashirovkaLengthT = length + heightTop * 2 + 1 - 1;
        List<PossibleResults> resultsList2 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,kashirovkaWidthB, kashirovkaLengthB,kashirovkaWidthT,kashirovkaLengthT, name,quantity);
            resultsList2.add(results);
        }
        return getMinOst(resultsList2);

    }
    private PossibleResults<Paper> topK(double width,double length,double heightTop, String name,int quantity){
        double kashirovkaWidthT = width + heightTop * 2 + 1 - 1;
        double kashirovkaLengthT = length + heightTop * 2 + 1 - 1;
        List<PossibleResults> resultsList5 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,kashirovkaWidthT,kashirovkaLengthT,kashirovkaWidthT,kashirovkaLengthT, name,quantity);
            resultsList5.add(results);
        }
        return getMinOst(resultsList5);
    }



    private PossibleResults<Carton> bottomBookLentaC(double width,double length,double heightBottom, String name,int quantity){
        double cartonWidthB = width + heightBottom * 2 + 1;
        double cartonLengthB = length + heightBottom * 2 + 1;
        double cartonWidthT = length * 2 + 1.2 + heightBottom;
        double cartonLengthT = width + 0.4 * 2;
        List<PossibleResults> resultsList = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthB, cartonLengthB,cartonWidthT,cartonLengthT, name,quantity);
            resultsList.add(results);
        }
        return getMinOst(resultsList);
    }


    private PossibleResults<Carton> topBookLentaC(double width,double length,double heightBottom, String name,int quantity){
        double cartonWidthT = length * 2 + 1.2 + heightBottom;
        double cartonLengthT = width + 0.4 * 2;
        List<PossibleResults> resultsList3 = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthT,cartonLengthT,cartonWidthT,cartonLengthT, name,quantity);
            resultsList3.add(results);
        }
        return getMinOst(resultsList3);
    }

    private PossibleResults<Carton> vstavkaBookLentaC(double width,double length, String name,int quantity){
        double cartonWidthT = width - 0.5;
        double cartonLengthT = length - 0.5;
        List<PossibleResults> resultsList3 = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthT,cartonLengthT,cartonWidthT,cartonLengthT, name,quantity);
            resultsList3.add(results);
        }
        return getMinOst(resultsList3);
    }
    private PossibleResults<Paper> bottomBookLentaP(double width,double length,double heightBottom, String name,int quantity){
        double paperWidthB = width + heightBottom * 2 + 0.5 + 3;
        double paperLengthB = length + heightBottom * 2 + 0.5 + 3;
        double paperWidthT = length * 2 + 1.2 + heightBottom  + 5;
        double paperLengthT = width + 0.4 * 2 + 4;
        List<PossibleResults> resultsList1 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthB, paperLengthB,paperWidthT,paperLengthT, name,quantity);
            resultsList1.add(results);
        }
        PossibleResults<Paper> toppap = getMinOst(resultsList1);
        toppap.lamin=(paperWidthB * paperLengthB + paperWidthT * paperLengthT);
        return toppap ;

    }
    private PossibleResults<Paper> topBookLentaP(double width,double length,double heightBottom, String name,int quantity){
        double paperWidthT = length * 2 + 1.2 + heightBottom  + 5;
        double paperLengthT = width + 0.4 * 2 + 4;
        List<PossibleResults> resultsList4 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthT,paperLengthT,paperWidthT,paperLengthT, name,quantity);
            resultsList4.add(results);
        }
        return getMinOst(resultsList4);
    }
    private PossibleResults<Paper> vstavkaBookLentaP(double width,double length, String name,int quantity){
        double paperWidthT = width + 2.5;
        double paperLengthT = length + 2.5;
        List<PossibleResults> resultsList4 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthT,paperLengthT,paperWidthT,paperLengthT, name,quantity);
            resultsList4.add(results);
        }
        return getMinOst(resultsList4);
    }


    private PossibleResults<Paper> bottomBookLentaK(double width, double length, double heightBottom,String name,int quantity){
        double kashirovkaWidthB = width + heightBottom * 2 + 0.5 - 1;
        double kashirovkaLengthB = length + heightBottom * 2 + 0.5 - 1;
        double kashirovkaWidthT = length + 2.5;
        double kashirovkaLengthT = width + 0.4 * 2 - 0.5;
        List<PossibleResults> resultsList2 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,kashirovkaWidthB, kashirovkaLengthB,kashirovkaWidthT,kashirovkaLengthT, name,quantity);
            resultsList2.add(results);
        }
        return getMinOst(resultsList2);

    }
    private PossibleResults<Paper> topBookLentaK(double width,double length,double heightBottom, String name,int quantity){
        double kashirovkaWidthT = length + 2.5;
        double kashirovkaLengthT = width + 0.4 * 2 - 0.5;
        List<PossibleResults> resultsList5 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,kashirovkaWidthT,kashirovkaLengthT,kashirovkaWidthT,kashirovkaLengthT, name,quantity);
            resultsList5.add(results);
        }
        return getMinOst(resultsList5);
    }


    private PossibleResults<Carton> bottomBookMagC(double width,double length,double heightBottom, String name,int quantity){
        double cartonWidthB = width + heightBottom * 2 + 1;
        double cartonLengthB = length + heightBottom * 2 + 1;
        double cartonWidthT = length * 2 + 0.9 + heightBottom * 2;
        double cartonLengthT = width + 0.4 * 2;
        List<PossibleResults> resultsList = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthB, cartonLengthB,cartonWidthT,cartonLengthT, name,quantity);
            resultsList.add(results);
        }
        return getMinOst(resultsList);
    }


    private PossibleResults<Carton> topBookMagC(double width,double length,double heightBottom, String name,int quantity){
        double cartonWidthT = length * 2 + 0.9 + heightBottom * 2;
        double cartonLengthT = width + 0.4 * 2;
        List<PossibleResults> resultsList3 = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,cartonWidthT,cartonLengthT,cartonWidthT,cartonLengthT, name,quantity);
            resultsList3.add(results);
        }
        return getMinOst(resultsList3);
    }
    private PossibleResults<Paper> bottomBookMagP(double width,double length,double heightBottom, String name,int quantity){
        double paperWidthB = width + heightBottom * 2 + 0.5 + 3;
        double paperLengthB = length + heightBottom * 2 + 0.5 + 3;
        double paperWidthT = length * 2 + 0.9 + heightBottom * 2 + 5.5;
        double paperLengthT = width + 0.4 * 2 + 4;
        List<PossibleResults> resultsList1 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthB, paperLengthB,paperWidthT,paperLengthT, name,quantity);
            resultsList1.add(results);
        }
        PossibleResults<Paper> toppap = getMinOst(resultsList1);
        toppap.lamin=(paperWidthB * paperLengthB + paperWidthT * paperLengthT);
        return toppap ;

    }
    private PossibleResults<Paper> topBookMagP(double width,double length,double heightBottom, String name,int quantity){
        double paperWidthT = length * 2 + 0.9 + heightBottom * 2 + 5.5;
        double paperLengthT = width + 0.4 * 2 + 4;
        List<PossibleResults> resultsList4 = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,paperWidthT,paperLengthT,paperWidthT,paperLengthT, name,quantity);
            resultsList4.add(results);
        }
        return getMinOst(resultsList4);
    }


    private PossibleResults<Paper> bottomBookMagK(double width, double length, double heightBottom,String name,int quantity){
        double kashirovkaWidthB = width + heightBottom * 2 + 0.5 - 1;
        double kashirovkaLengthB = length + heightBottom * 2 + 0.5 - 1;
        double kashirovkaWidthT = length + 2.7 + heightBottom;
        double kashirovkaLengthT = width + 0.4 * 2 - 0.5;
        List<PossibleResults> resultsList2 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,kashirovkaWidthB, kashirovkaLengthB,kashirovkaWidthT,kashirovkaLengthT, name,quantity);
            resultsList2.add(results);
        }
        return getMinOst(resultsList2);

    }
    private PossibleResults<Paper> topBookMagK(double width,double length,double heightBottom, String name,int quantity){
        double kashirovkaWidthT = length + 2.7 + heightBottom;
        double kashirovkaLengthT = width + 0.4 * 2 - 0.5;
        List<PossibleResults> resultsList5 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,kashirovkaWidthT,kashirovkaLengthT,kashirovkaWidthT,kashirovkaLengthT, name,quantity);
            resultsList5.add(results);
        }
//        return getMinOst(resultsList5);
        return getMinOst(resultsList5);
    }












    private PossibleResults<Carton> circleC(double o, String name,int quantity){
        List<PossibleResults> resultsList5 = new ArrayList<>();
        for (Carton ct:cartonSelectList){
//            PossibleResults<Carton> results = new PossibleResults<>(ct,o,o, name,quantity);
//            resultsList5.add(results);
        }
        return getMinOst(resultsList5);
    }
    private PossibleResults<Paper> circleP(double o, String name,int quantity){
        List<PossibleResults> resultsList5 = new ArrayList<>();
        for (Paper ct:paperSelectList){
//            PossibleResults<Paper> results = new PossibleResults<>(ct,o,o, name,quantity);
//            resultsList5.add(results);
        }
        return getMinOst(resultsList5);
    }
    private PossibleResults<Paper> circleK(double o, String name,int quantity){
        List<PossibleResults> resultsList5 = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
//            PossibleResults<Paper> results = new PossibleResults<>(ct,o,o, name,quantity);
//            resultsList5.add(results);
        }
        return getMinOst(resultsList5);
    }

    private PossibleResults<Carton> tapeC(double width,double length,int count, String name,int quantity){
        List<PossibleResults> resultsList = new ArrayList<>();
        for (Carton ct:cartonSelectList){
            PossibleResults<Carton> results = new PossibleResults<>(ct,width, length,width,length, name,quantity);
            resultsList.add(results);
        }
        PossibleResults<Carton> results = getMinOst(resultsList);
        if (((double)results.quantityMaterial % (double)count) != 0.0) {
            results.quantityMaterial =  results.quantityMaterial / count + 1;
        }else {
            results.quantityMaterial =  results.quantityMaterial / count ;
        }
        return results;
    }
    private PossibleResults<Paper> tapeP(double width,double length,int count, String name,int quantity){
        List<PossibleResults> resultsList = new ArrayList<>();
        for (Paper ct:paperSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,width, length,width,length, name,quantity);
            resultsList.add(results);
        }
        PossibleResults<Paper> results = getMinOst(resultsList);
        if (((double)results.quantityMaterial % (double)count) != 0.0) {
            results.quantityMaterial =  results.quantityMaterial / count + 1;
        }else {
            results.quantityMaterial =  results.quantityMaterial / count;
        }
        return results;
    }
    private PossibleResults<Paper> tapeK(double width,double length,int count, String name,int quantity){
        List<PossibleResults> resultsList = new ArrayList<>();
        for (Paper ct:kashirovkaSelectList){
            PossibleResults<Paper> results = new PossibleResults<>(ct,width, length,width,length, name,quantity);
            int i = 0;//block duplication
            resultsList.add(results);
        }
        PossibleResults<Paper> results = getMinOst(resultsList);
        if (((double)results.quantityMaterial % (double)count) != 0.0) {
            results.quantityMaterial =  results.quantityMaterial / count + 1;
        }else {
            results.quantityMaterial =  results.quantityMaterial / count;
        }
        return results;
    }






    public static<T extends Material> PossibleResults<T> getMinOst(List<PossibleResults> list){
        double min = Double.MAX_VALUE;
        PossibleResults<T> topResult = null;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).ost < min){
                min = list.get(i).ost;
                topResult = list.get(i);
            }
        }
        return topResult;
    }

    private<T extends Material> PossibleResults<T> galmoKompensator(PossibleResults<T> possibleResultsKompens,PossibleResults<T> possibleResultsFrom){
        if (possibleResultsFrom.quantTop > 0){
            possibleResultsKompens.quantityMaterial = ((possibleResultsKompens.quantityMaterial * possibleResultsKompens.quantBottom) - (possibleResultsFrom.quantityMaterial * possibleResultsFrom.quantTop))/possibleResultsKompens.quantBottom;
            if (possibleResultsKompens.quantityMaterial < 0) possibleResultsKompens.quantityMaterial = 0;
        }
        return possibleResultsKompens;
    }


}
