package Controller;

import Model.*;
import storage.StorageList;
import storage.StorageList;
import java.time.LocalDate;
import java.util.List;

public abstract class Controller {

    private static Storage storage;


    public static Destillering createDestillering(LocalDate startDato, LocalDate slutDato, int maltBatch, String kornSort, double mængde, double alkoholProcent, String rygeMateriale, String kommentar) {
        Destillering destillering = new Destillering(startDato, slutDato, maltBatch, kornSort, mængde, alkoholProcent, rygeMateriale, kommentar);
        storage.addDestillering(destillering);
        return destillering;
    }

    public static List<Destillering> getDestilleringer() {
        return storage.getDestilleringer();
    }

    public static Fad createFad(double størrelse, int id, Leverandør leverandør, String tidligereIndhold, double nuværendeMængde, LagerPlads lagerPlads) {
        Fad fad = new Fad(størrelse, id, leverandør, tidligereIndhold, nuværendeMængde, lagerPlads);
        storage.addFad(fad);
        return fad;
    }

    public static List<Fad> getFade() {
        return storage.getFade();
    }

    public static Fad findFad(int id) {

        Fad fundetFad = null;

        for (Fad fad : storage.getFade()) {
            if (fad.getId() == id) {
                fundetFad = fad;
            }
        }
        return fundetFad;
    }

    public static Lager createLager(int id, String navn, String adresse, String beskrivelse){
        Lager lager = new Lager(id, navn, adresse, beskrivelse);
        storage.addLager(lager);
        return lager;
    }

    public static List<Lager> getLagre() {
        return storage.getLagre();
    }

    public static Lager findLager(int id) {
        Lager fundetLager = null;

        for (Lager lager : storage.getLagre()) {
            if (lager.getId() == id) {
                fundetLager = lager;
            }
        }

        return fundetLager;
    }

    public static Leverandør createLeverandør(int id, String navn, String land, String kontaktPerson, String telefon, String email, String kommentar){
        Leverandør leverandør = new Leverandør(id, navn, land, kontaktPerson, telefon, email, kommentar);
        storage.addLeverandør(leverandør);
        return leverandør;
    }

    public static List<Leverandør> getLeverandører() {
        return storage.getLeverandører();
    }

    public static Leverandør findLeverandør(int id) {
        Leverandør fundetLeverandør = null;

        for (Leverandør leverandør : storage.getLeverandører()) {
            if (leverandør.getId() == id) {
                fundetLeverandør = leverandør;
            }
        }

        return fundetLeverandør;
    }

    public static void placerFadPåLagerPlads(Fad fad, LagerPlads lagerPlads){
        if (lagerPlads.erLedig()){
            lagerPlads.placerFad(fad);
        }
    }

    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }

    public static Påfyldning createPåfyldning(LocalDate dato, String ansvarlig, VæskeMængde væskeMængde, Fad fad){

        if (!kanFordele(væskeMængde.getDestilat(), væskeMængde.getMængde())) {
            throw new IllegalArgumentException("Destilleringen har ikke nok resterende mængde.");
        }

        if (fad.ledigKapacitet() < væskeMængde.getMængde()) {
            throw new IllegalArgumentException("Fad har ikke nok plads");
        }

            Påfyldning påfyldning = new Påfyldning(dato, ansvarlig, væskeMængde, fad);
            fad.addPåfyldning(påfyldning);
            fad.tilføjMængde(væskeMængde.getMængde());
            storage.addPåfyldning(påfyldning);
            return påfyldning;


    }



    public static double getFordeltMængde(Destillering destillering) {

        double fordeltMængde = 0;

        for (Påfyldning påfyldning : storage.getPåfyldninger()) {

            if (påfyldning.getVæskeMængde().getDestilat() == destillering) {

                fordeltMængde += påfyldning.getVæskeMængde().getMængde();

            }
        }

        return fordeltMængde;
    }

    public static double getResterendeMængde(Destillering destillering){

        double resterendeMængde = 0;


            resterendeMængde = destillering.getMængde() - getFordeltMængde(destillering);



        return resterendeMængde;
    }

    public static boolean kanFordele(Destillering destillering, Double mængde){

        boolean kanFordeles = false;

        if (getResterendeMængde(destillering) >= mængde){
            kanFordeles = true;
        }

        return kanFordeles;
    }


}
