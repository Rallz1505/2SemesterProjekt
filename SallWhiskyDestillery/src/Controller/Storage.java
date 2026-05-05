package Controller;

import Model.Destillering;
import Model.Fad;

import java.util.List;

public interface Storage {

    public List<Destillering> getDestilleringer();

    public void addDestillering(Destillering destillering);

    public void removeDestillering(Destillering destillering);


    public List<Fad> getFade();

    public void addFad(Fad fad);

    public void removeFad(Fad fad);

}
