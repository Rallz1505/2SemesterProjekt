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

    // ------------------------------------------------------------
    // FORMEL UNITTEST AF MODELKLASSE: FAD
    // ------------------------------------------------------------

    @Test
    void fadBeregnerMængderKorrekt() {
        Fad fad = new Fad(100.0, 1, null, "Sherry", 0.0, null);

        Destillering destillering = new Destillering(
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                101, "Evergreen", 100.0, 60.0, "Tørv", "Test"
        );

        Påfyldning påfyldning = new Påfyldning(
                LocalDate.now().minusYears(4),
                "Anders", new VæskeMængde(50.0, destillering), fad
        );

        fad.addPåfyldning(påfyldning);
        fad.tilføjMængde(50.0);

        assertEquals(50.0, fad.getNuværendeMængde(), 0.001);
        assertEquals(50.0, fad.ledigKapacitet(), 0.001);
        assertEquals(4, fad.getAlder());

        double forventetAngelsShare = 50.0 * 0.02 * 4;
        double forventetDevilsCut = 50.0 * 0.01 * 4;
        double forventetTilgængelig = 50.0 - forventetAngelsShare - forventetDevilsCut;

        assertEquals(forventetAngelsShare, fad.beregnAngelsShare(), 0.001);
        assertEquals(forventetDevilsCut, fad.beregnDevilsCut(), 0.001);
        assertEquals(forventetTilgængelig, fad.beregnTilgængeligMængde(), 0.001);
    }

    @Test
    void createPåfyldningOpdatererFadOgStorage() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2),
                1, "Evergreen", 100.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 20.0, null
        );

        VæskeMængde væskeMængde = new VæskeMængde(30.0, destillering);

        Påfyldning påfyldning = Controller.createPåfyldning(
                LocalDate.of(2024, 1, 3), "Anders", væskeMængde, fad
        );

        assertNotNull(påfyldning);
        assertEquals(50.0, fad.getNuværendeMængde(), 0.001);
        assertTrue(fad.getPåfyldninger().contains(påfyldning));
        assertEquals(30.0, Controller.getFordeltMængde(destillering), 0.001);
    }

    @Test
    void createPåfyldningFejlerHvisDestilleringIkkeHarNokMængde() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2),
                1, "Evergreen", 50.0, 60.0, "Tørv", "Test"
        );

        Fad fad = Controller.createFad(
                200.0, 1, null, "Sherry", 0.0, null
        );

        VæskeMængde væskeMængde = new VæskeMængde(60.0, destillering);

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createPåfyldning(
                        LocalDate.of(2024, 1, 3),
                        "Anders", væskeMængde, fad
                )
        );
    }

    @Test
    void createPåfyldningFejlerHvisFadIkkeHarPlads() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2),
                1, "Evergreen", 200.0, 60.0, "Tørv", "Test"
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

    @Test
    void createWhiskyMængdeTilføjerMængdeOgFjernerFraFad() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 0.0
        );

        Destillering destillering = Controller.createDestillering(
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                101, "Evergreen", 100.0, 60.0, "", "Test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 0.0, null
        );

        Controller.createPåfyldning(
                LocalDate.now().minusYears(4),
                "Anders",
                new VæskeMængde(50.0, destillering),
                fad
        );

        WhiskyMængde whiskyMængde = Controller.createWhiskyMængde(
                whiskyProdukt, fad, 20.0
        );

        assertNotNull(whiskyMængde);
        assertEquals(20.0, whiskyMængde.getMængde(), 0.001);
        assertTrue(whiskyProdukt.getWhiskyMængder().contains(whiskyMængde));
        assertEquals(30.0, fad.getNuværendeMængde(), 0.001);
    }

    @Test
    void createWhiskyMængdeFejlerHvisFadErForUngt() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Ung whisky", "Skal fejle", 48.0, 0.0
        );

        Destillering destillering = Controller.createDestillering(
                LocalDate.now().minusYears(1),
                LocalDate.now().minusYears(1),
                101, "Evergreen", 100.0, 60.0, "", "Test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 0.0, null
        );

        Controller.createPåfyldning(
                LocalDate.now().minusYears(1),
                "Anders",
                new VæskeMængde(50.0, destillering), fad
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createWhiskyMængde(
                        whiskyProdukt, fad, 10.0
                )
        );
    }

    @Test
    void createWhiskyMængdeFejlerHvisDerTappesForMegetFraFad() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 0.0
        );

        Destillering destillering = Controller.createDestillering(
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                101, "Evergreen", 100.0, 60.0, "", "Test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 0.0, null
        );

        Controller.createPåfyldning(
                LocalDate.now().minusYears(4),
                "Anders",
                new VæskeMængde(30.0, destillering),
                fad
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createWhiskyMængde(
                        whiskyProdukt, fad, 100.0
                )
        );
    }

    @Test
    void createFlaskeOpretterFlaskeHvisDerErNokWhisky() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 1.0
        );

        Flaske flaske = Controller.createFlaske(
                whiskyProdukt,
                1,
                0.7
        );

        assertNotNull(flaske);
        assertEquals(1, flaske.getFlaskeNr());
        assertEquals(0.7, flaske.getVolumen(), 0.001);
        assertTrue(whiskyProdukt.getFlasker().contains(flaske));
    }

    @Test
    void createFlaskeFejlerHvisDerTappesForMeget() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 1.0
        );

        Controller.createFlaske(
                whiskyProdukt, 1, 0.7
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createFlaske(
                        whiskyProdukt, 2, 0.7
                )
        );
    }

    @Test
    void createFlaskeFejlerHvisVolumenErNulEllerNegativ() {
        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall First Edition", "Test whisky", 48.0, 1.0
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createFlaske(
                        whiskyProdukt,
                        1,
                        0.0
                )
        );

        assertThrows(IllegalArgumentException.class, () ->
                Controller.createFlaske(
                        whiskyProdukt,
                        2,
                        -0.7
                )
        );
    }

    @Test
    void centralUseCaseOpretWhiskyProduktFraFadOgFlasker() {
        Destillering destillering = Controller.createDestillering(
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                101, "Evergreen", 200.0, 60.0, "Tørv", "Central use case test"
        );

        Fad fad = Controller.createFad(
                100.0, 1, null, "Sherry", 0.0, null
        );

        Controller.createPåfyldning(
                LocalDate.now().minusYears(4),
                "Anders",
                new VæskeMængde(80.0, destillering),
                fad
        );

        WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                1, "Sall Central Test", "Whisky lavet fra fad", 48.0, 2.0
        );

        WhiskyMængde whiskyMængde = Controller.createWhiskyMængde(
                whiskyProdukt,
                fad,
                20.0
        );

        Flaske flaske1 = Controller.createFlaske(
                whiskyProdukt,
                1,
                0.7
        );

        Flaske flaske2 = Controller.createFlaske(
                whiskyProdukt, 2, 0.7
        );

        assertNotNull(whiskyMængde);
        assertNotNull(flaske1);
        assertNotNull(flaske2);

        assertEquals(60.0, fad.getNuværendeMængde(), 0.001);
        assertEquals(22.0, whiskyProdukt.beregnSamletMængde(), 0.001);
        assertEquals(1.4, whiskyProdukt.beregnTappetMængde(), 0.001);
        assertEquals(20.6, whiskyProdukt.beregnResterendeProduktMængde(), 0.001);

        assertTrue(whiskyProdukt.getWhiskyMængder().contains(whiskyMængde));
        assertTrue(whiskyProdukt.getFlasker().contains(flaske1));
        assertTrue(whiskyProdukt.getFlasker().contains(flaske2));
        assertTrue(whiskyProdukt.getHistorik().contains("Sall Central Test"));
        assertTrue(whiskyProdukt.getHistorik().contains("Evergreen"));
    }
}