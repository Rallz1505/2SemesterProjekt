package Model;

import java.util.ArrayList;

public class Lager {

    private int id;
    private String navn;
    private String adresse;
    private String beskrivelse;
    private final ArrayList<LagerPlads> lagerPladser;

    public Lager(int id, String navn, String adresse, String beskrivelse) {
        this.id = id;
        this.navn = navn;
        this.adresse = adresse;
        this.beskrivelse = beskrivelse;
        this.lagerPladser = new ArrayList<>();
    }

    public LagerPlads createLagerPlads(int reolNr, int hyldeNr, int pladsNr, String beskrivelse) {
        LagerPlads lagerPlads = new LagerPlads(reolNr, hyldeNr, pladsNr, beskrivelse);
        lagerPladser.add(lagerPlads);
        return lagerPlads;
    }

    public void removeLagerPlads(LagerPlads lagerPlads) {
        lagerPladser.remove(lagerPlads);
    }

    public ArrayList<LagerPlads> getLagerPladser() {
        return new ArrayList<>(lagerPladser);
    }

    public ArrayList<LagerPlads> getLedigePladser(){

        ArrayList<LagerPlads> ledigePladser = new ArrayList<>();

        for (LagerPlads l : lagerPladser){
            if (l.erLedig()){
                ledigePladser.add(l);
            }
        }

        return ledigePladser;
    }

    public LagerPlads findLagerPlads(int reolNr, int hyldeNr, int pladsNr){

        LagerPlads lagerPlads = null;

        for (LagerPlads l : lagerPladser){
            if (l.getReolNr() == reolNr && l.getHyldeNr() == hyldeNr && l.getPladsNr() == pladsNr){
                lagerPlads = l;
            }
        }
        return lagerPlads;
    }

    public ArrayList<LagerPlads> getOptagedePladser(){

        ArrayList<LagerPlads> optagedePladser = new ArrayList<>();

        for (LagerPlads l : lagerPladser){
            if (!l.erLedig()){
                optagedePladser.add(l);
            }
        }

        return optagedePladser;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString() {
        return navn + " (" + adresse + ")";
    }
}