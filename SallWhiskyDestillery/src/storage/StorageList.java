package storage;

import Controller.Storage;
import Model.Destillering;
import Model.Fad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StorageList implements Storage, Serializable {
    private List<Destillering> destilleringer = new ArrayList<>();
    private  List<Fad> fade = new ArrayList<>();


    // -------------------------------------------------------------------------

    public List<Destillering> getDestilleringer() {
        return new ArrayList<Destillering>(destilleringer);
    }

    public  void addDestillering(Destillering destillering) {
        destilleringer.add(destillering);
    }

    public  void removeDestillering(Destillering destillering) {
        destilleringer.remove(destillering);
    }

    // -------------------------------------------------------------------------

    public List<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }

    public  void addFad(Fad fad) {
        fade.add(fad);
    }

    public  void removeFad(Fad fad) {
        fade.remove(fad);
    }

    // -------------------------------------------------------------------------

}
