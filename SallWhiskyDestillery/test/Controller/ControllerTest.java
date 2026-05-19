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
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2),
                1, "Evergreen", 100.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(100.0, 1, null, "Sherry", 20.0, null);
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
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2),
                1, "Evergreen", 50.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(200.0, 1, null, "Sherry", 0.0, null);
        VæskeMængde væskeMængde = new VæskeMængde(60.0, destillering);

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createPåfyldning(
                        LocalDate.of(2024, 1, 3), "Anders", væskeMængde, fad
                )
        );
    }

    @Test
    void createPåfyldningFejlerHvisFadIkkeHarPlads() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2),
                1, "Evergreen", 200.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(100.0, 1, null, "Sherry", 90.0, null);
        VæskeMængde væskeMængde = new VæskeMængde(20.0, destillering);

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createPåfyldning(
                        LocalDate.of(2024, 1, 3), "Anders", væskeMængde, fad
                )
        );
    }

    @Test
    void createWhiskyProduktOpretterProdukt() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 2.0
        );

        assertNotNull(whiskyProdukt);
        assertEquals(1, whiskyProdukt.getId());
        assertEquals("Sall First Edition", whiskyProdukt.getNavn());
        assertEquals(48.0, whiskyProdukt.getAlkoholProcent());
        assertTrue(Controller.getWhiskyProdukter().contains(whiskyProdukt));
    }

    @Test
    void createWhiskyMængdeTilføjerMængdeOgFjernerFraFad() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 0.0
        );

        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2),
                101, "Evergreen", 100.0, 60.0, "", "Test"
        );

        Fad fad = Controller.createFad(100.0, 1, null, "Sherry", 0.0, null);

        Påfyldning påfyldning = Controller.createPåfyldning(
                LocalDate.of(2020, 1, 3),
                "Anders",
                new VæskeMængde(50.0, destillering),
                fad
        );

        WhiskyMængde whiskyMængde = Controller.createWhiskyMængde(
                whiskyProdukt, påfyldning, 20.0
        );

        assertNotNull(whiskyMængde);
        assertEquals(20.0, whiskyMængde.getMængde());
        assertTrue(whiskyProdukt.getWhiskyMængder().contains(whiskyMængde));
        assertEquals(30.0, fad.getNuværendeMængde());
    }

    @Test
    void createWhiskyMængdeFejlerHvisDerBrugesForMegetFraPåfyldning() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 0.0
        );

        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2),
                101, "Evergreen", 100.0, 60.0, "", "Test"
        );

        Fad fad = Controller.createFad(100.0, 1, null, "Sherry", 0.0, null);

        Påfyldning påfyldning = Controller.createPåfyldning(
                LocalDate.of(2020, 1, 3),
                "Anders",
                new VæskeMængde(30.0, destillering),
                fad
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createWhiskyMængde(whiskyProdukt, påfyldning, 40.0)
        );
    }

    @Test
    void createWhiskyMængdeFejlerHvisMængdeErNulEllerNegativ() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 0.0
        );

        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2),
                101, "Evergreen", 100.0, 60.0, "", "Test"
        );

        Fad fad = Controller.createFad(100.0, 1, null, "Sherry", 0.0, null);

        Påfyldning påfyldning = Controller.createPåfyldning(
                LocalDate.of(2020, 1, 3),
                "Anders",
                new VæskeMængde(30.0, destillering),
                fad
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createWhiskyMængde(whiskyProdukt, påfyldning, 0.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createWhiskyMængde(whiskyProdukt, påfyldning, -5.0)
        );
    }

    @Test
    void createFlaskeOpretterFlaskeHvisDerErNokWhisky() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 1.0
        );

        Flaske flaske = Controller.createFlaske(whiskyProdukt, 1, 0.7);

        assertNotNull(flaske);
        assertEquals(1, flaske.getFlaskeNr());
        assertEquals(0.7, flaske.getVolumen());
        assertTrue(whiskyProdukt.getFlasker().contains(flaske));
    }

    @Test
    void createFlaskeFejlerHvisDerTappesForMeget() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 1.0
        );

        Controller.createFlaske(whiskyProdukt, 1, 0.7);

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createFlaske(whiskyProdukt, 2, 0.7)
        );
    }

    @Test
    void createFlaskeFejlerHvisVolumenErNulEllerNegativ() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 1.0
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createFlaske(whiskyProdukt, 1, 0.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createFlaske(whiskyProdukt, 2, -0.7)
        );
    }
}