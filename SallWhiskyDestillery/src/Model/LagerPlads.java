package Model;

public class LagerPlads {

    private int reolNr;
    private int hyldeNr;
    private int pladsNr;
    private String beskrivelse;
    private Fad fad = null;

    public LagerPlads(int reolNr, int hyldeNr, int pladsNr, String beskrivelse) {
        this.reolNr = reolNr;
        this.hyldeNr = hyldeNr;
        this.pladsNr = pladsNr;
        this.beskrivelse = beskrivelse;
    }

    public void placerFad(Fad fad){
        if (this.fad == null){
            this.fad = fad;
            fad.setLagerPlads(this);
        }
    }

    public void fjernFad(){
        if (fad != null){
            fad = null;
        }
    }

    public boolean erLedig(){

        return fad == null;
    }

    public String getPlacering() {
        String placering = "Reol " + reolNr + ", Hylde " + hyldeNr + ", Plads " + pladsNr;

        if (beskrivelse != null && !beskrivelse.isEmpty()) {
            placering += ", Beskrivelse " + beskrivelse;
        }

        return placering;
    }



    public Fad getFad() {
        return fad;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
    }

    public int getReolNr() {
        return reolNr;
    }

    public void setReolNr(int reolNr) {
        this.reolNr = reolNr;
    }

    public int getHyldeNr() {
        return hyldeNr;
    }

    public void setHyldeNr(int hyldeNr) {
        this.hyldeNr = hyldeNr;
    }

    public int getPladsNr() {
        return pladsNr;
    }

    public void setPladsNr(int pladsNr) {
        this.pladsNr = pladsNr;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString() {

        String status = "Ledig";

        if (fad != null) {
            status = "Optaget af fad " + fad.getId();
        }

        return "Reol " + reolNr +
                ", Hylde " + hyldeNr +
                ", Plads " + pladsNr +
                " | " + status;
    }
}
