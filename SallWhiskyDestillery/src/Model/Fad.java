package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fad {

    private double størrelse;
    private int id;
    private Leverandør leverandør;
    private String tidligereIndhold;
    private double nuværendeMængde;
    private LagerPlads lagerPlads;
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private WhiskyMængde whiskyMængde;

    public Fad(double størrelse, int id, Leverandør leverandør, String tidligereIndhold, double nuværendeMængde, LagerPlads lagerPlads) {
        this.størrelse = størrelse;
        this.id = id;
        this.leverandør = leverandør;
        this.tidligereIndhold = tidligereIndhold;
        this.nuværendeMængde = nuværendeMængde;
        this.lagerPlads = lagerPlads;
    }

    public void setLeverandør(Leverandør leverandør){
        if (this.leverandør != leverandør){
            Leverandør oldLeverandør = this.leverandør;
            if (oldLeverandør != null){
                oldLeverandør.removeFad(this);
            }
            this.leverandør = leverandør;
            if (leverandør != null){
                leverandør.addFad(this);
            }
        }
    }

    public void addPåfyldning(Påfyldning påfyldning){
        if(!påfyldninger.contains(påfyldning)){
            påfyldninger.add(påfyldning);
            påfyldning.setFad(this);
        }
    }

    public void removePåfyldning(Påfyldning påfyldning){
        if(påfyldninger.contains(påfyldning)){
            påfyldninger.remove(påfyldning);
            påfyldning.setFad(null);
        }
    }

    public void tilføjMængde(double liter) {
        if (nuværendeMængde + liter <= størrelse) {
            nuværendeMængde += liter;
        }
    }

    public LagerPlads getLagerPlads() {
        return lagerPlads;
    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public void fjernMængde(double liter) {
        if (nuværendeMængde - liter >= 0) {
            nuværendeMængde -= liter;
        }
    }

    public void setLagerPlads(LagerPlads lagerPlads) {
        this.lagerPlads = lagerPlads;
    }

    public boolean erTomt() {
        return nuværendeMængde == 0;
    }

    public boolean erFuldt() {
        return nuværendeMængde == størrelse;
    }

    public double ledigKapacitet() {
        return størrelse - nuværendeMængde;
    }

    public Leverandør getLeverandør() {
        return leverandør;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public void setStørrelse(double størrelse) {
        this.størrelse = størrelse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    public void setTidligereIndhold(String tidligereIndhold) {
        this.tidligereIndhold = tidligereIndhold;
    }

    public double getNuværendeMængde() {
        return nuværendeMængde;
    }

    public void setNuværendeMængde(double nuværendeMængde) {
        this.nuværendeMængde = nuværendeMængde;
    }

    public WhiskyMængde getWhiskyMængde() {
        return whiskyMængde;
    }

    public void setWhiskyMængde(WhiskyMængde whiskyMængde) {
        this.whiskyMængde = whiskyMængde;
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

    @Override
    public String toString() {

        String kornsort = "Ukendt";
        int alder = getAlder();

        if (!påfyldninger.isEmpty()) {
            kornsort = getKornsorter();
        }

        return "Fad id " + id + " | Tidligere indhold: " + tidligereIndhold + " | Kornsort: " + kornsort + " | Alder: " + alder + " år";
    }
}
