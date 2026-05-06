package GUI;

import Controller.Controller;
import Controller.Storage;
import Model.Fad;
import Model.Lager;
import Model.LagerPlads;
import Model.Leverandør;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storage.StorageList;

import java.io.*;
import java.time.LocalDate;

public class App  {



    public static void main(String[] args)  {
        initStorage();
        Storage storage = loadStorage();
        if (storage == null) {
            storage = new StorageList();
            System.out.println("Empty ListStorage created");
        }
        Controller.setStorage(storage);

        if (Controller.getFade().isEmpty()){
            System.out.println("Storage initialized");
        }
        Application.launch(ForsidePane.class);
        saveStorage(storage);

    }

    public static void initStorage() {
        StorageList storage = new StorageList();
        Controller.setStorage(storage);

        Leverandør lev1 = Controller.createLeverandør(
                1, "Bodega Casks", "Spanien", "Carlos",
                "12345678", "carlos@bodega.dk", "Leverer sherryfade"
        );

        Leverandør lev2 = Controller.createLeverandør(
                2, "Oak Barrel Europe", "Frankrig", "Jean",
                "87654321", "jean@oak.dk", "Leverer egetræsfade"
        );

        Lager lager1 = Controller.createLager(
                1, "Containerlager", "Bag destilleriet", "Primært lager"
        );

        LagerPlads plads1 = lager1.createLagerPlads(1, 1, 1, "");
        LagerPlads plads2 = lager1.createLagerPlads(1, 1, 2, "");
        LagerPlads plads3 = lager1.createLagerPlads(1, 2, 1, "Øverste hylde");

        Fad fad1 = Controller.createFad(
                100.0, 1, lev1, "Sherry", 50.0, plads1
        );

        Fad fad2 = Controller.createFad(
                200.0, 2, lev2, "Bourbon", 0.0, plads2
        );

        plads1.placerFad(fad1);
        plads2.placerFad(fad2);

        Controller.createDestillering(
                LocalDate.of(2024, 1, 10),
                LocalDate.of(2024, 1, 12),
                101,
                "Evergreen",
                250.0,
                63.5,
                "Tørv",
                "Testdestillering 1"
        );

        Controller.createDestillering(
                LocalDate.of(2024, 2, 5),
                LocalDate.of(2024, 2, 7),
                102,
                "Stairway",
                180.0,
                61.2,
                "",
                "Testdestillering 2"
        );
    }

    public static Storage loadStorage() {
        String fileName = "storage.ser";
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            Object obj = objIn.readObject();
            Storage storage = (StorageList) obj;
            System.out.println("Storage loaded from file " + fileName);
            return storage;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error deserializing storage");
            System.out.println(ex);
            return null;
        }
    }

    public static void saveStorage(Storage storage) {
        String fileName = "storage.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)
        ) {
            objOut.writeObject(storage);
            System.out.println("Storage saved in file " + fileName);
        } catch (IOException ex) {
            System.out.println("Error serializing storage");
            System.out.println(ex);
            throw new RuntimeException();
        }
    }


}
