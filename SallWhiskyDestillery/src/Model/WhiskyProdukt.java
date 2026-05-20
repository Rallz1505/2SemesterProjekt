package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt implements Serializable {

    private int id;
    private String navn;
    private String beskrivelse;
    private double alkoholProcent;
    private double vandMængde;
    private List<Flaske> flasker = new ArrayList<>();
    private List<WhiskyMængde> whiskyMængder = new ArrayList<>();

    public WhiskyProdukt(int id, String navn, String beskrivelse, double alkoholProcent, double vandMængde) {
        this.id = id;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.alkoholProcent = alkoholProcent;
        this.vandMængde = vandMængde;
    }

    public double beregnSamletMængde() {
        double result = vandMængde;

        for (WhiskyMængde wm : whiskyMængder) {
            result += wm.getMængde();
        }

        return result;
    }

    public double beregnTappetMængde() {
        double result = 0;

        for (Flaske flaske : flasker) {
            result += flaske.getVolumen();
        }

        return result;
    }

    public double beregnResterendeProduktMængde() {
        return beregnSamletMængde() - beregnTappetMængde();
    }

    public Flaske createFlaske(int flaskeNr, double volumen) {
        Flaske flaske = new Flaske(flaskeNr, volumen, this);
        flasker.add(flaske);
        return flaske;
    }

    public WhiskyMængde createWhiskyMængde(Fad fad, double mængde) {
        WhiskyMængde whiskyMængde = new WhiskyMængde(fad, mængde, this);
        whiskyMængder.add(whiskyMængde);
        return whiskyMængde;
    }

    public String getHistorik() {
        StringBuilder result = new StringBuilder();

        result.append("Whiskyprodukt: ").append(navn).append("\n");
        result.append("Produktnummer: ").append(id).append("\n");
        result.append("Beskrivelse: ").append(beskrivelse).append("\n");
        result.append("Alkoholprocent: ").append(alkoholProcent).append("%\n");
        result.append("Tilført vand: ").append(vandMængde).append(" L\n");
        result.append("Samlet mængde: ").append(beregnSamletMængde()).append(" L\n");
        result.append("Tappet på flasker: ").append(beregnTappetMængde()).append(" L\n");
        result.append("Resterende produktmængde: ").append(beregnResterendeProduktMængde()).append(" L\n\n");

        if (whiskyMængder.isEmpty()) {
            result.append("Ingen registrerede whiskymængder.\n");
            return result.toString();
        }

        int whiskyMængdeNr = 1;

        for (WhiskyMængde wm : whiskyMængder) {
            Fad fad = wm.getFad();

            result.append("----------------------------------------\n\n");

            result.append("Whiskymængde #").append(whiskyMængdeNr).append("\n");
            result.append("Brugt mængde fra fad: ").append(wm.getMængde()).append(" L\n\n");

            result.append("Fad:\n");
            result.append("Fad id: ").append(fad.getId()).append("\n");
            result.append("Tidligere indhold: ").append(fad.getTidligereIndhold()).append("\n");
            result.append("Kornsorter: ").append(fad.getKornsorter()).append("\n");
            result.append("Alder: ").append(fad.getAlder()).append(" år\n");
            result.append("Nuværende mængde i fad: ").append(fad.getNuværendeMængde()).append(" L\n");
            result.append("Angel's share: ").append(String.format("%.2f", fad.beregnAngelsShare())).append(" L\n");
            result.append("Devil's cut: ").append(String.format("%.2f", fad.beregnDevilsCut())).append(" L\n");
            result.append("Tilgængelig mængde efter svind: ").append(String.format("%.2f", fad.beregnTilgængeligMængde())).append(" L\n\n");

            result.append("Påfyldninger på fadet:\n");

            for (Påfyldning p : fad.getPåfyldninger()) {
                VæskeMængde vm = p.getVæskeMængde();
                Destillering d = vm.getDestilat();

                result.append("\nPåfyldning:\n");
                result.append("Dato: ").append(p.getDato()).append("\n");
                result.append("Ansvarlig: ").append(p.getAnsvarlig()).append("\n");
                result.append("Påfyldt mængde: ").append(vm.getMængde()).append(" L\n");

                result.append("Destillering:\n");
                result.append("Maltbatch: ").append(d.getMaltBatch()).append("\n");
                result.append("Kornsort: ").append(d.getKornSort()).append("\n");
                result.append("Mængde: ").append(d.getMængde()).append(" L\n");
                result.append("Alkoholprocent: ").append(d.getAlkoholProcent()).append("%\n");
                result.append("Rygemateriale: ").append(d.getRygeMateriale()).append("\n");
                result.append("Kommentar: ").append(d.getKommentar()).append("\n");
            }

            result.append("\n");
            whiskyMængdeNr++;
        }

        return result.toString();
    }

    public int getId() {
        return id;
    }

    public String getNavn() {
        return navn;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public List<Flaske> getFlasker() {
        return new ArrayList<>(flasker);
    }

    public List<WhiskyMængde> getWhiskyMængder() {
        return new ArrayList<>(whiskyMængder);
    }

    @Override
    public String toString() {
        return id + " - " + navn +
                " | Samlet: " + beregnSamletMængde() + " L" +
                " | Resterende: " + beregnResterendeProduktMængde() + " L";
    }
}