package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void fadTilføjMængdeOgLedigKapacitet() {
        Fad fad = new Fad(100.0, 1, null, "Bourbon", 40.0, null);

        fad.tilføjMængde(30.0);

        assertEquals(70.0, fad.getNuværendeMængde());
        assertEquals(30.0, fad.ledigKapacitet());
        assertFalse(fad.erFuldt());
        assertFalse(fad.erTomt());
    }

    @Test
    void lagerFinderLedigeOgOptagedePladser() {
        Lager lager = new Lager(1, "Container", "Sall", "Bag destilleriet");

        LagerPlads plads1 = lager.createLagerPlads(1, 1, 1, "");
        LagerPlads plads2 = lager.createLagerPlads(1, 1, 2, "");

        Fad fad = new Fad(100.0, 1, null, "Sherry", 50.0, null);
        plads1.placerFad(fad);

        assertEquals(1, lager.getLedigePladser().size());
        assertEquals(1, lager.getOptagedePladser().size());
        assertTrue(lager.getLedigePladser().contains(plads2));
        assertTrue(lager.getOptagedePladser().contains(plads1));
    }

    @Test
    void leverandørTilføjerOgFjernerFad() {
        Leverandør leverandør = new Leverandør(
                1, "Cask Supplier", "Spanien", "Pedro", "12345678", "test@test.dk", "Test"
        );

        Fad fad = new Fad(200.0, 1, null, "Sherry", 0.0, null);

        leverandør.addFad(fad);

        assertEquals(1, leverandør.antalFade());
        assertEquals(leverandør, fad.getLeverandør());

        leverandør.removeFad(fad);

        assertEquals(0, leverandør.antalFade());
        assertNull(fad.getLeverandør());
    }
}