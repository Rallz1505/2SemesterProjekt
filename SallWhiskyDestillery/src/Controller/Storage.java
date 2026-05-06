package Controller;

import Model.*;

import java.util.List;

public interface Storage {

    // -------------------------------------------------------------------------
    // Destillering

    List<Destillering> getDestilleringer();

    void addDestillering(Destillering destillering);

    void removeDestillering(Destillering destillering);

    // -------------------------------------------------------------------------
    // Fad

    List<Fad> getFade();

    void addFad(Fad fad);

    void removeFad(Fad fad);

    // -------------------------------------------------------------------------
    // Lager

    List<Lager> getLagre();

    void addLager(Lager lager);

    void removeLager(Lager lager);

    // -------------------------------------------------------------------------
    // Leverandør

    List<Leverandør> getLeverandører();

    void addLeverandør(Leverandør leverandør);

    void removeLeverandør(Leverandør leverandør);
}