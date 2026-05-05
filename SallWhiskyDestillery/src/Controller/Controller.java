package Controller;

import Model.Destillering;
import Model.Fad;
import storage.StorageList;
import storage.StorageList;
import java.time.LocalDate;

public abstract class Controller {

    private static Storage storage;


    public static Destillering createDestillering(LocalDate startDato, LocalDate slutDato, int maltBatch, String kornSort, double mængde, double alkoholProcent, String rygeMateriale, String kommentar) {
        Destillering destillering = new Destillering(startDato, slutDato, maltBatch, kornSort, mængde, alkoholProcent, rygeMateriale, kommentar);
        storage.addDestillering(destillering);
        return destillering;
    }



    public static Fad createFad(double størrelse, int id, String leverandør, String tidligereIndhold, double nuværendeMængde) {
        Fad fad = new Fad(størrelse, id, leverandør, tidligereIndhold, nuværendeMængde);
        storage.addFad(fad);
        return fad;
    }

    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }
}
