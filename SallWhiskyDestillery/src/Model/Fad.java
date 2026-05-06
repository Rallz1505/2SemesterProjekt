package Model;

import java.util.ArrayList;

public class Fad {

    private double størrelse;
    private int id;
    private Leverandør leverandør;
    private String tidligereIndhold;
    private double nuværendeMængde;
    private LagerPlads lagerPlads;

    public Fad(double størrelse, int id, Leverandør leverandør, String tidligereIndhold, double nuværendeMængde, LagerPlads lagerPlads) {
        this.størrelse = størrelse;
        this.id = id;
        this.leverandør = leverandør;
        this.tidligereIndhold = tidligereIndhold;
        this.nuværendeMængde = nuværendeMængde;
        this.lagerPlads = lagerPlads;
    }

    public void setLeverandør(Leverandør leverandør){
        if (this.leverandør != leverandør){
            Leverandør oldLeverandør = this.leverandør;
            if (oldLeverandør != null){
                oldLeverandør.removeFad(this);
            }
            this.leverandør = leverandør;
            if (leverandør != null){
                leverandør.addFad(this);
            }
        }
    }


    public void tilføjMængde(double liter) {
        if (nuværendeMængde + liter <= størrelse) {
            nuværendeMængde += liter;
        }
    }

    public void fjernMængde(double liter) {
        if (nuværendeMængde - liter >= 0) {
            nuværendeMængde -= liter;
        }
    }

    public boolean erTomt() {
        return nuværendeMængde == 0;
    }

    public boolean erFuldt() {
        return nuværendeMængde == størrelse;
    }

    public double ledigKapacitet() {
        return størrelse - nuværendeMængde;
    }

    public Leverandør getLeverandør() {
        return leverandør;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public void setStørrelse(double størrelse) {
        this.størrelse = størrelse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    public void setTidligereIndhold(String tidligereIndhold) {
        this.tidligereIndhold = tidligereIndhold;
    }

    public double getNuværendeMængde() {
        return nuværendeMængde;
    }

    public void setNuværendeMængde(double nuværendeMængde) {
        this.nuværendeMængde = nuværendeMængde;
    }
}
