package Model;

import Controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.StorageList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @BeforeEach
    void setUp() {
        Controller.setStorage(new StorageList());
    }

    @Test
    void createPåfyldningOpdatererFadOgStorage() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2), 1, "Evergreen", 100.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 20.0, null
        );

        VæskeMængde væskeMængde = new VæskeMængde(30.0, destillering);

        Påfyldning påfyldning = Controller.createPåfyldning(
                LocalDate.of(2024, 1, 3), "Anders", væskeMængde, fad
        );

        assertNotNull(påfyldning);
        assertEquals(50.0, fad.getNuværendeMængde());
        assertTrue(fad.getPåfyldninger().contains(påfyldning));
        assertEquals(30.0, Controller.getFordeltMængde(destillering));
    }

    @Test
    void createPåfyldningFejlerHvisDestilleringIkkeHarNokMængde() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2), 1, "Evergreen", 50.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(
                200.0, 1, null, "Sherry", 0.0, null
        );

        VæskeMængde væskeMængde = new VæskeMængde(60.0, destillering);

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createPåfyldning(
                        LocalDate.of(2024, 1, 3),
                        "Anders",
                        væskeMængde,
                        fad
                )
        );
    }

    @Test
    void createPåfyldningFejlerHvisFadIkkeHarPlads() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2), 1, "Evergreen", 200.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 90.0, null
        );

        VæskeMængde væskeMængde = new VæskeMængde(20.0, destillering);

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createPåfyldning(
                        LocalDate.of(2024, 1, 3), "Anders", væskeMængde, fad
                )
        );
    }
}