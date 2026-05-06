package Model;

import java.util.ArrayList;

public class Leverandør {

    private int id;
    private String navn;
    private String land;
    private String kontaktPerson;
    private String telefon;
    private String email;
    private String kommentar;
    private ArrayList<Fad> fade;


    public Leverandør(int id, String navn, String land, String kontaktPerson, String telefon, String email, String kommentar) {
        this.id = id;
        this.navn = navn;
        this.land = land;
        this.kontaktPerson = kontaktPerson;
        this.telefon = telefon;
        this.email = email;
        this.kommentar = kommentar;
        this.fade = new ArrayList<>();
    }

    public void addFad(Fad fad){
        if (!fade.contains(fad)){
            fade.add(fad);
            fad.setLeverandør(this);
        }
    }

    public void removeFad(Fad fad){
        if (fade.contains(fad)){
            fade.remove(fad);
            fad.setLeverandør(null);
        }
    }

    public int antalFade(){
        return fade.size();
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public String getKontaktPerson() {
        return kontaktPerson;
    }

    public void setKontaktPerson(String kontaktPerson) {
        this.kontaktPerson = kontaktPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }



}
