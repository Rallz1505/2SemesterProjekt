package Model;

import java.io.Serializable;

public class WhiskyMængde implements Serializable {

    private Fad fad;
    private double mængde;
    private WhiskyProdukt whiskyProdukt;

    public WhiskyMængde(Fad fad, double mængde, WhiskyProdukt whiskyProdukt) {
        this.fad = fad;
        this.mængde = mængde;
        this.whiskyProdukt = whiskyProdukt;
    }

    public Fad getFad() {
        return fad;
    }

    public double getMængde() {
        return mængde;
    }

    @Override
    public String toString() {
        return mængde + " L fra fad " + fad.getId();
    }
}