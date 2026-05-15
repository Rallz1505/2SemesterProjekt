package GUI;

import Controller.Controller;
import Controller.Storage;
import Model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storage.StorageList;

import java.io.*;
import java.time.LocalDate;

public class App  {



    public static void main(String[] args)  {
        Storage storage = null;
//        Storage storage = loadStorage();
        if (storage == null) {
            storage = new StorageList();
            System.out.println("Empty ListStorage created");
        }
        Controller.setStorage(storage);

        if (Controller.getFade().isEmpty()){
            System.out.println("Storage initialized");
            initStorage();
        }
        Application.launch(ForsidePane.class);
//        saveStorage(storage);

    }

    public static void initStorage() {

        // Leverandører
        Leverandør l1 = Controller.createLeverandør(
                1, "Spanish Casks", "Spanien",
                "Pedro", "11111111",
                "spanish@casks.com", "Sherry fade"
        );

        Leverandør l2 = Controller.createLeverandør(
                2, "Bourbon Barrels", "USA",
                "John", "22222222",
                "bourbon@barrels.com", "Bourbon fade"
        );

        // Lagre
        Lager lager1 = Controller.createLager(
                1, "Container Lager", "Sall", "Bag destilleriet"
        );

        Lager lager2 = Controller.createLager(
                2, "Lade Lager", "Hos bondemand", "Ekstra lager"
        );

        // Lagerpladser
        LagerPlads lp1 = lager1.createLagerPlads(1,1,1,"Tæt ved døren");
        LagerPlads lp2 = lager1.createLagerPlads(1,1,2,"");

        LagerPlads lp3 = lager2.createLagerPlads(2,1,1,"Koldt område");
        LagerPlads lp4 = lager2.createLagerPlads(2,1,2,"");

        // Fade
        Fad fad1 = Controller.createFad(
                100, 1, l1,
                "Sherry", 0, lp1
        );

        Fad fad2 = Controller.createFad(
                200, 2, l2,
                "Bourbon", 0, lp2
        );

        Fad fad3 = Controller.createFad(
                150, 3, l1,
                "Portvin", 0, lp3
        );

        // Destilleringer
        Destillering d1 = Controller.createDestillering(
                LocalDate.of(2021,1,10),
                LocalDate.of(2021,1,15),
                101,
                "Evergreen",
                300,
                63,
                "Tørv",
                "Første batch"
        );

        Destillering d2 = Controller.createDestillering(
                LocalDate.of(2023,5,1),
                LocalDate.of(2023,5,5),
                102,
                "Irina",
                250,
                60,
                "",
                "Anden batch"
        );

        // Påfyldninger
        Controller.createPåfyldning(
                LocalDate.of(2021,2,1),
                "Anders",
                new VæskeMængde(50, d1),
                fad1
        );

        Controller.createPåfyldning(
                LocalDate.of(2021,2,1),
                "Anders",
                new VæskeMængde(80, d1),
                fad2
        );

        // Nyere påfyldning i samme fad
        Controller.createPåfyldning(
                LocalDate.of(2024,1,1),
                "Mikkel",
                new VæskeMængde(20, d2),
                fad1
        );

        Controller.createPåfyldning(
                LocalDate.of(2023,6,1),
                "Peter",
                new VæskeMængde(60, d2),
                fad3
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
