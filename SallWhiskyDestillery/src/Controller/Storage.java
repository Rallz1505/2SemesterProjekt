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

    // -------------------------------------------------------------------------
    // Leverandør

    List<Påfyldning> getPåfyldninger();

    void addPåfyldning(Påfyldning påfyldning);

    void removePåfyldning(Påfyldning påfyldning);

    // -------------------------------------------------------------------------
    // Leverandør

    List<WhiskyProdukt> getWhiskyProdukter();

    void addWhiskyProdukt(WhiskyProdukt whiskyProdukt);

    void removeWhiskyProdukt(WhiskyProdukt whiskyProdukt);
}