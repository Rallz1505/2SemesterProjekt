package Model;

public class WhiskyMængde {

    private Påfyldning påfyldning;
    private double mængde;
    private WhiskyProdukt whiskyProdukt;


    public WhiskyMængde(Påfyldning påfyldning, double mængde, WhiskyProdukt whiskyProdukt) {
        this.påfyldning = påfyldning;
        this.mængde = mængde;
        this.whiskyProdukt = whiskyProdukt;
    }

    public Påfyldning getPåfyldning() {
        return påfyldning;
    }

    public Destillering getDestillering(){
        return påfyldning.getVæskeMængde().getDestilat();
    }

    public Fad getFad(){
        return påfyldning.getFad();
    }

    public void setPåfyldning(Påfyldning påfyldning) {
        this.påfyldning = påfyldning;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public WhiskyProdukt getWhiskyProdukt() {
        return whiskyProdukt;
    }

    public void setWhiskyProdukt(WhiskyProdukt whiskyProdukt) {
        this.whiskyProdukt = whiskyProdukt;
    }
}
