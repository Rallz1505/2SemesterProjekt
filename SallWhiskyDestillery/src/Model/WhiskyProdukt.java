package Model;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {

    private int id;
    private String navn;
    private String beskrivelse;
    private double alkoholProcent;
    private double vandMængde;
    private List<Flaske> flasker = new ArrayList<>();
    private List<WhiskyMængde> whiskyMængder = new ArrayList<>();


    public WhiskyProdukt(int id, String navn, String beskrivelse, double alkoholProcent, double vandMængde) {
        this.id = id;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.alkoholProcent = alkoholProcent;
        this.vandMængde = vandMængde;
    }

    public double beregnSamletMængde(){
        double result = vandMængde;

        for (WhiskyMængde wm : whiskyMængder){
            result += wm.getMængde();
        }

        return result;
    }

    public Flaske createFlaske(int flaskeNr, double volumen ){
        Flaske flaske = new Flaske(flaskeNr, volumen, this);
        flasker.add(flaske);
        return flaske;
    }

    public WhiskyMængde createWhiskyMængde(Påfyldning påfyldning, double mængde){
        WhiskyMængde whiskyMængde = new WhiskyMængde(påfyldning, mængde, this);
        whiskyMængder.add(whiskyMængde);
        return whiskyMængde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public double getVandMængde() {
        return vandMængde;
    }

    public void setVandMængde(double vandMængde) {
        this.vandMængde = vandMængde;
    }

    public List<Flaske> getFlasker() {
        return new ArrayList<>(flasker);
    }

    public void setFlasker(List<Flaske> flasker) {
        this.flasker = flasker;
    }

    public List<WhiskyMængde> getWhiskyMængder() {
        return new ArrayList<>(whiskyMængder);
    }

    public void setWhiskyMængder(List<WhiskyMængde> whiskyMængder) {
        this.whiskyMængder = whiskyMængder;
    }

    @Override
    public String toString() {
        return id + " - " + navn;
    }
}
