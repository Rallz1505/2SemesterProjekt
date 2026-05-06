package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Destillering {

    private LocalDate startDato;
    private LocalDate slutDato;
    private int maltBatch;
    private String kornSort;
    private double mængde;
    private double alkoholProcent;
    private String rygeMateriale;
    private String kommentar;

    public Destillering(LocalDate startDato, LocalDate slutDato, int maltBatch, String kornSort, double mængde, double alkoholProcent, String rygeMateriale, String kommentar) {

        this.startDato = startDato;
        this.slutDato = slutDato;
        this.maltBatch = maltBatch;
        this.kornSort = kornSort;
        this.mængde = mængde;
        this.alkoholProcent = alkoholProcent;
        this.rygeMateriale = rygeMateriale;
        this.kommentar = kommentar;

    }


    public int getMaltBatch() {
        return maltBatch;
    }

    public String getKommentar() {
        return kommentar;
    }

    public String getRygeMateriale() {
        return rygeMateriale;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public double getMængde() {
        return mængde;
    }

    public String getKornSort() {
        return kornSort;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public LocalDate getStartDato() {
        return startDato;
    }


}
