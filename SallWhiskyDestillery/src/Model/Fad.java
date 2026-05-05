package Model;

public class Fad {

    private double størrelse;
    private int id;
    private String leverandør;
    private String tidligereIndhold;
    private double nuværendeMængde;

    public Fad(double størrelse, int id, String leverandør, String tidligereIndhold, double nuværendeMængde) {
        this.størrelse = størrelse;
        this.id = id;
        this.leverandør = leverandør;
        this.tidligereIndhold = tidligereIndhold;
        this.nuværendeMængde = nuværendeMængde;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public int getId() {
        return id;
    }

    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    public double getNuværendeMængde() {
        return nuværendeMængde;
    }


}
