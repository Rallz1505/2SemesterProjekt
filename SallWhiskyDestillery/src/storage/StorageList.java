package storage;

import Controller.Storage;
import Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageList implements Storage, Serializable {

    private List<Destillering> destilleringer = new ArrayList<>();
    private List<Fad> fade = new ArrayList<>();
    private List<Lager> lagre = new ArrayList<>();
    private List<Leverandør> leverandører = new ArrayList<>();
    private List<Påfyldning> påfyldninger = new ArrayList<>();

    // -------------------------------------------------------------------------

    public List<Destillering> getDestilleringer() {
        return new ArrayList<>(destilleringer);
    }

    public void addDestillering(Destillering destillering) {
        destilleringer.add(destillering);
    }

    public void removeDestillering(Destillering destillering) {
        destilleringer.remove(destillering);
    }

    // -------------------------------------------------------------------------

    public List<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public void addFad(Fad fad) {
        fade.add(fad);
    }

    public void removeFad(Fad fad) {
        fade.remove(fad);
    }

    // -------------------------------------------------------------------------

    public List<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }

    public void addLager(Lager lager) {
        lagre.add(lager);
    }

    public void removeLager(Lager lager) {
        lagre.remove(lager);
    }

    // -------------------------------------------------------------------------

    public List<Leverandør> getLeverandører() {
        return new ArrayList<>(leverandører);
    }

    public void addLeverandør(Leverandør leverandør) {
        leverandører.add(leverandør);
    }

    public void removeLeverandør(Leverandør leverandør) {
        leverandører.remove(leverandør);
    }

    // -------------------------------------------------------------------------
    public List<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public void addPåfyldning(Påfyldning påfyldning) {
        påfyldninger.add(påfyldning);
    }

    public void removePåfyldning(Påfyldning påfyldning) { påfyldninger.remove(påfyldning); }

    // -------------------------------------------------------------------------

    public void saveStorage(String fileName) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StorageList loadStorage(String fileName) {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {
            return (StorageList) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new StorageList();
        }
    }
}