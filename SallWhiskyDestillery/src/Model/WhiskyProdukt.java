package Model;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProdukt {

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

    public double beregnSamletMængde(){
        double result = vandMængde;

        for (WhiskyMængde wm : whiskyMængder){
            result += wm.getMængde();
        }

        return result;
    }

    public Flaske createFlaske(int flaskeNr, double volumen ){
        Flaske flaske = new Flaske(flaskeNr, volumen, this);
        flasker.add(flaske);
        return flaske;
    }

    public WhiskyMængde createWhiskyMængde(Påfyldning påfyldning, double mængde){
        WhiskyMængde whiskyMængde = new WhiskyMængde(påfyldning, mængde, this);
        whiskyMængder.add(whiskyMængde);
        return whiskyMængde;
    }

    public String getHistorik() {
        StringBuilder result = new StringBuilder();

        result.append("Whiskyprodukt: ").append(navn).append("\n");
        result.append("Produktnummer: ").append(id).append("\n");
        result.append("Alkoholprocent: ").append(alkoholProcent).append("%\n");
        result.append("Tilført vand: ").append(vandMængde).append(" L\n");
        result.append("Samlet mængde: ").append(beregnSamletMængde()).append(" L\n\n");

        if (whiskyMængder.isEmpty()) {
            result.append("Ingen registrerede whiskymængder.\n");
            return result.toString();
        }

        int whiskyMængdeNr = 1;

        for (WhiskyMængde wm : whiskyMængder) {
            Påfyldning p = wm.getPåfyldning();
            Fad fad = p.getFad();
            VæskeMængde vm = p.getVæskeMængde();
            Destillering d = vm.getDestilat();

            result.append("----------------------------------------\n\n");

            result.append("Whiskymængde #").append(whiskyMængdeNr).append("\n");
            result.append("Brugt mængde: ").append(wm.getMængde()).append(" L\n\n");

            result.append("Fad:\n");
            result.append("Fad id: ").append(fad.getId()).append("\n");
            result.append("Tidligere indhold: ").append(fad.getTidligereIndhold()).append("\n");
            result.append("Kornsorter: ").append(fad.getKornsorter()).append("\n");
            result.append("Alder: ").append(fad.getAlder()).append(" år\n\n");

            result.append("Påfyldning:\n");
            result.append("Dato: ").append(p.getDato()).append("\n");
            result.append("Ansvarlig: ").append(p.getAnsvarlig()).append("\n");
            result.append("Oprindelig påfyldt mængde: ").append(vm.getMængde()).append(" L\n\n");

            result.append("Destillering:\n");
            result.append("Maltbatch: ").append(d.getMaltBatch()).append("\n");
            result.append("Kornsort: ").append(d.getKornSort()).append("\n");
            result.append("Mængde: ").append(d.getMængde()).append(" L\n");
            result.append("Alkoholprocent: ").append(d.getAlkoholProcent()).append("%\n");
            result.append("Rygemateriale: ").append(d.getRygeMateriale()).append("\n");
            result.append("Kommentar: ").append(d.getKommentar()).append("\n\n");

            whiskyMængdeNr++;
        }

        return result.toString();
    }

    public List<String> getKornSorter(){

        List<String> kornSorter = new ArrayList<>();

        for (WhiskyMængde w : whiskyMængder){

            kornSorter.add(w.getDestillering().getKornSort());

        }

        return kornSorter;
    }

    public List<Fad> getFade(){

        List<Fad> fade = new ArrayList<>();

        for (WhiskyMængde w : whiskyMængder){

            fade.add(w.getPåfyldning().getFad());

        }

        return fade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public double getVandMængde() {
        return vandMængde;
    }

    public void setVandMængde(double vandMængde) {
        this.vandMængde = vandMængde;
    }

    public List<Flaske> getFlasker() {
        return new ArrayList<>(flasker);
    }

    public void setFlasker(List<Flaske> flasker) {
        this.flasker = flasker;
    }

    public List<WhiskyMængde> getWhiskyMængder() {
        return new ArrayList<>(whiskyMængder);
    }

    public void setWhiskyMængder(List<WhiskyMængde> whiskyMængder) {
        this.whiskyMængder = whiskyMængder;
    }

    @Override
    public String toString() {
        return id + " - " + navn;
    }
}
