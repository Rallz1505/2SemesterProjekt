package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Fad implements Serializable {

    private double størrelse;
    private int id;
    private Leverandør leverandør;
    private String tidligereIndhold;
    private double nuværendeMængde;
    private LagerPlads lagerPlads;
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>();

    public Fad(double størrelse, int id, Leverandør leverandør, String tidligereIndhold, double nuværendeMængde, LagerPlads lagerPlads) {
        this.størrelse = størrelse;
        this.id = id;
        this.leverandør = leverandør;
        this.tidligereIndhold = tidligereIndhold;
        this.nuværendeMængde = nuværendeMængde;
        this.lagerPlads = lagerPlads;
    }

    public void addPåfyldning(Påfyldning påfyldning) {
        if (!påfyldninger.contains(påfyldning)) {
            påfyldninger.add(påfyldning);
            påfyldning.setFad(this);
        }
    }

    public void tilføjMængde(double liter) {
        if (nuværendeMængde + liter <= størrelse) {
            nuværendeMængde += liter;
        }
    }

    public void fjernMængde(double liter) {
        if (nuværendeMængde - liter >= 0) {
            nuværendeMængde -= liter;
        }
    }

    public double beregnAngelsShare() {
        return nuværendeMængde * 0.02 * getAlder();
    }

    public double beregnDevilsCut() {
        return nuværendeMængde * 0.01 * getAlder();
    }

    public void removePåfyldning(Påfyldning påfyldning) {
        if (påfyldninger.contains(påfyldning)) {
            påfyldninger.remove(påfyldning);
            påfyldning.setFad(null);
        }
    }

    public double beregnTilgængeligMængde() {
        double tilgængelig = nuværendeMængde - beregnAngelsShare() - beregnDevilsCut();

        if (tilgængelig < 0) {
            return 0;
        }

        return tilgængelig;
    }

    public int getAlder() {
        if (påfyldninger.isEmpty()) {
            return 0;
        }

        LocalDate yngsteDato = påfyldninger.get(0).getDato();

        for (Påfyldning påfyldning : påfyldninger) {
            if (påfyldning.getDato().isAfter(yngsteDato)) {
                yngsteDato = påfyldning.getDato();
            }
        }
        return yngsteDato.until(LocalDate.now()).getYears();
    }

    public String getKornsorter() {
        ArrayList<String> kornsorter = new ArrayList<>();

        for (Påfyldning påfyldning : påfyldninger) {
            String kornsort = påfyldning.getVæskeMængde().getDestilat().getKornSort();

            if (!kornsorter.contains(kornsort)) {
                kornsorter.add(kornsort);
            }
        }

        return String.join(", ", kornsorter);
    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public double ledigKapacitet() {
        return størrelse - nuværendeMængde;
    }

    public LagerPlads getLagerPlads() {
        return lagerPlads;
    }

    public void setLagerPlads(LagerPlads lagerPlads) {
        this.lagerPlads = lagerPlads;
    }

    public void setLeverandør(Leverandør leverandør) {
        this.leverandør = leverandør;
    }

    public int getId() {
        return id;
    }

    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    public double getNuværendeMængde() {
        return nuværendeMængde;
    }

    @Override
    public String toString() {
        String kornsort = "Ukendt";

        if (!påfyldninger.isEmpty()) {
            kornsort = getKornsorter();
        }

        return "Fad id " + id +
                " | Tidligere indhold: " + tidligereIndhold +
                " | Kornsort: " + kornsort +
                " | Nuværende: " + nuværendeMængde + " L" +
                " | Tilgængelig efter svind: " + String.format("%.2f", beregnTilgængeligMængde()) + " L" +
                " | Alder: " + getAlder() + " år";
    }
}