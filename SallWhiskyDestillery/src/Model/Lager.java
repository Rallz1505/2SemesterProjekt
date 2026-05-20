package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Lager implements Serializable {

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


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return navn + " (" + adresse + ")";
    }
}