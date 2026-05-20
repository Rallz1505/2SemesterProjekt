package Model;

import java.io.Serializable;

public class Flaske implements Serializable {

    private int flaskeNr;
    private double volumen;
    private WhiskyProdukt whiskyProdukt;


    public Flaske(int flaskeNr, double volumen, WhiskyProdukt whiskyProdukt) {
        this.flaskeNr = flaskeNr;
        this.volumen = volumen;
        this.whiskyProdukt = whiskyProdukt;
    }

    public int getFlaskeNr() {
        return flaskeNr;
    }

    public double getVolumen() {
        return volumen;
    }

    @Override
    public String toString() {
        return "Flaske " + flaskeNr + " (" + volumen + " L)";
    }
}
