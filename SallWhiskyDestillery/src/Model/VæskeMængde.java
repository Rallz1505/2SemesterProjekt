package Model;

import java.io.Serializable;

public class VæskeMængde implements Serializable {

    private double mængde;
    private Destillering destilat;

    public VæskeMængde(double mængde, Destillering destilat) {
        this.mængde = mængde;
        this.destilat = destilat;
    }

    public double getMængde() {
        return mængde;
    }

    public Destillering getDestilat() {
        return destilat;
    }

}
