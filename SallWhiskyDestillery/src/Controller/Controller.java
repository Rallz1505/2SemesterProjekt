package Controller;

import Model.*;
import storage.StorageList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller {

    private static Storage storage;



    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }

    public static Destillering createDestillering(LocalDate startDato, LocalDate slutDato, int maltBatch, String kornSort, double mængde, double alkoholProcent, String rygeMateriale, String kommentar) {
        Destillering destillering = new Destillering(startDato, slutDato, maltBatch, kornSort, mængde, alkoholProcent, rygeMateriale, kommentar);
        storage.addDestillering(destillering);
        StorageList.saveStorage(storage);
        return destillering;
    }

    public static List<Destillering> getDestilleringer() {
        return storage.getDestilleringer();
    }

    public static Fad createFad(double størrelse, int id, Leverandør leverandør, String tidligereIndhold, double nuværendeMængde, LagerPlads lagerPlads) {
        Fad fad = new Fad(størrelse, id, leverandør, tidligereIndhold, nuværendeMængde, lagerPlads);
        storage.addFad(fad);
        StorageList.saveStorage(storage);

        if (lagerPlads != null && lagerPlads.erLedig()) {
            lagerPlads.placerFad(fad);
        }

        return fad;
    }

    public static List<Fad> getFade() {
        return storage.getFade();
    }

    public static Fad findFad(int id) {
        for (Fad fad : storage.getFade()) {
            if (fad.getId() == id) {
                return fad;
            }
        }
        return null;
    }

    private static boolean erKlarTilAftapning(Fad fad) {
        return fad.getAlder() >= 3;
    }

    public static List<Fad> getFadeKlarTilAftapning() {
        ArrayList<Fad> klarFade = new ArrayList<>();

        for (Fad f : getFade()) {
            if (erKlarTilAftapning(f)) {
                klarFade.add(f);
            }
        }

        return klarFade;
    }

    public static Lager createLager(int id, String navn, String adresse, String beskrivelse) {
        Lager lager = new Lager(id, navn, adresse, beskrivelse);
        storage.addLager(lager);
        StorageList.saveStorage(storage);
        return lager;
    }

    public static List<Lager> getLagre() {
        return storage.getLagre();
    }

    public static Lager findLager(int id) {
        for (Lager lager : storage.getLagre()) {
            if (lager.getId() == id) {
                return lager;
            }
        }
        return null;
    }

    public static Leverandør createLeverandør(int id, String navn, String land, String kontaktPerson, String telefon, String email, String kommentar) {
        Leverandør leverandør = new Leverandør(id, navn, land, kontaktPerson, telefon, email, kommentar);
        storage.addLeverandør(leverandør);
        StorageList.saveStorage(storage);
        return leverandør;
    }

    public static List<Leverandør> getLeverandører() {
        return storage.getLeverandører();
    }

    public static Leverandør findLeverandør(int id) {
        for (Leverandør leverandør : storage.getLeverandører()) {
            if (leverandør.getId() == id) {
                return leverandør;
            }
        }
        return null;
    }

    public static void placerFadPåLagerPlads(Fad fad, LagerPlads lagerPlads) {

        if (fad == null || lagerPlads == null) {
            throw new IllegalArgumentException("Fad og lagerplads skal vælges.");
        }

        if (!lagerPlads.erLedig()) {
            throw new IllegalArgumentException("Lagerpladsen er allerede optaget.");
        }

        lagerPlads.placerFad(fad);

        StorageList.saveStorage(storage);
    }

    public static Påfyldning createPåfyldning(LocalDate dato, String ansvarlig, VæskeMængde væskeMængde, Fad fad) {
        if (!kanFordele(væskeMængde.getDestilat(), væskeMængde.getMængde())) {
            throw new IllegalArgumentException("Destilleringen har ikke nok resterende mængde.");
        }

        if (fad.ledigKapacitet() < væskeMængde.getMængde()) {
            throw new IllegalArgumentException("Fad har ikke nok plads.");
        }

        Påfyldning påfyldning = new Påfyldning(dato, ansvarlig, væskeMængde, fad);

        fad.addPåfyldning(påfyldning);
        fad.tilføjMængde(væskeMængde.getMængde());
        storage.addPåfyldning(påfyldning);
        StorageList.saveStorage(storage);

        return påfyldning;
    }

    public static WhiskyProdukt createWhiskyProdukt(int id, String navn, String beskrivelse, double alkoholProcent, double vandMængde) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(id, navn, beskrivelse, alkoholProcent, vandMængde);
        storage.addWhiskyProdukt(whiskyProdukt);
        StorageList.saveStorage(storage);
        return whiskyProdukt;
    }

    public static List<WhiskyProdukt> getWhiskyProdukter() {
        return storage.getWhiskyProdukter();
    }

    public static void fjernFadFraLagerPlads(LagerPlads lagerPlads) {

        if (lagerPlads == null) {
            throw new IllegalArgumentException("Vælg en lagerplads.");
        }

        if (lagerPlads.erLedig()) {
            throw new IllegalArgumentException("Der står ikke et fad på pladsen.");
        }

        lagerPlads.fjernFad();

        StorageList.saveStorage(storage);
    }

    public static WhiskyProdukt findWhiskyProdukt(int id) {
        for (WhiskyProdukt whiskyProdukt : storage.getWhiskyProdukter()) {
            if (whiskyProdukt.getId() == id) {
                return whiskyProdukt;
            }
        }
        return null;
    }

    public static WhiskyMængde createWhiskyMængde(WhiskyProdukt whiskyProdukt, Fad fad, double mængde) {

        if (whiskyProdukt == null || fad == null) {
            throw new IllegalArgumentException("Whiskyprodukt og fad skal vælges.");
        }
        if (mængde <= 0) {
            throw new IllegalArgumentException("Mængden skal være større end 0.");
        }
        if (fad.getAlder() < 3) {
            throw new IllegalArgumentException("Fadet har ikke lagret i mindst 3 år.");
        }
        if (fad.beregnTilgængeligMængde() < mængde) {
            throw new IllegalArgumentException("Der er ikke nok whisky i fadet.");
        }

        fad.fjernMængde(mængde);
        WhiskyMængde whiskyMængde = whiskyProdukt.createWhiskyMængde(fad, mængde);
        StorageList.saveStorage(storage);

        return whiskyMængde;
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

    public static double getResterendeMængde(Destillering destillering) {
        return destillering.getMængde() - getFordeltMængde(destillering);
    }

    public static boolean kanFordele(Destillering destillering, double mængde) {
        return getResterendeMængde(destillering) >= mængde;
    }

    public static Flaske createFlaske(WhiskyProdukt whiskyProdukt, int flaskeNr, double volumen) {

        if (volumen <= 0) {
            throw new IllegalArgumentException("Volumen skal være større end 0.");
        }
        double tappetMængde = 0;
        for (Flaske flaske : whiskyProdukt.getFlasker()) {
            tappetMængde += flaske.getVolumen();
        }
        if (tappetMængde + volumen > whiskyProdukt.beregnSamletMængde()) {
            throw new IllegalArgumentException("Der er ikke nok whisky tilbage i produktet.");
        }
        Flaske flaske = whiskyProdukt.createFlaske(flaskeNr, volumen);
        StorageList.saveStorage(storage);

        return flaske;
    }


}