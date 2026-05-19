package Model;

public class Flaske {

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

    public void setFlaskeNr(int flaskeNr) {
        this.flaskeNr = flaskeNr;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public WhiskyProdukt getWhiskyProdukt() {
        return whiskyProdukt;
    }

    public void setWhiskyProdukt(WhiskyProdukt whiskyProdukt) {
        this.whiskyProdukt = whiskyProdukt;
    }

    @Override
    public String toString() {
        return "Flaske " + flaskeNr + " (" + volumen + " L)";
    }
}
