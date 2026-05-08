package Model;

public class VæskeMængde {

    private double mængde;
    private Destillering destilat;

    public VæskeMængde(double mængde, Destillering destilat) {
        this.mængde = mængde;
        this.destilat = destilat;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public Destillering getDestilat() {
        return destilat;
    }

    public void setDestilat(Destillering destilat) {
        this.destilat = destilat;
    }
}
