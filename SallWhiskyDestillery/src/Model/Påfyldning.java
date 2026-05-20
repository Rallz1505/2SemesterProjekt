package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Påfyldning implements Serializable {

    private LocalDate dato;
    private String ansvarlig;
    private VæskeMængde væskeMængde;
    private Fad fad;

    public Påfyldning(LocalDate dato, String ansvarlig, VæskeMængde væskeMængde, Fad fad) {
        this.dato = dato;
        this.ansvarlig = ansvarlig;
        this.væskeMængde = væskeMængde;
        this.fad = fad;
    }

    public void setFad(Fad fad){
        if(this.fad != fad){
            Fad oldFad = this.fad;
            if(oldFad != null){
                oldFad.removePåfyldning(this);
            }
            this.fad = fad;
            if(fad != null){
                fad.addPåfyldning(this);
            }
        }
    }

    public VæskeMængde createVaeskeMaengde(double maengde, Destillering destillering) {
        this.væskeMængde = new VæskeMængde(maengde, destillering);
        return this.væskeMængde;
    }

    public LocalDate getDato() {
        return dato;
    }

    public String getAnsvarlig() {
        return ansvarlig;
    }

    public VæskeMængde getVæskeMængde() {
        return væskeMængde;
    }


    public Fad getFad() {
        return fad;
    }

    @Override
    public String toString() {

        String fadId = "Ingen fad";

        if (fad != null) {
            fadId = String.valueOf(fad.getId());
        }

        return dato +
                " | " + væskeMængde.getMængde() + " L" +
                " | Fad " + fadId +
                " | " + væskeMængde.getDestilat().getKornSort() +
                " | Ansvarlig: " + ansvarlig;
    }
}
