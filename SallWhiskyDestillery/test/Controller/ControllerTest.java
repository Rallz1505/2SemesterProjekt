package Controller;

import Controller.Controller;
import Controller.Storage;
import Model.Destillering;
import Model.Fad;
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
    void createFad() {
        Fad fad = Controller.createFad(
                100.0,
                1,
                "Sals",
                "Bourbon",
                50.0
        );

        assertNotNull(fad);
        assertEquals(100.0, fad.getStørrelse());
        assertEquals(1, fad.getId());
        assertEquals("Sals", fad.getLeverandør());
        assertEquals("Bourbon", fad.getTidligereIndhold());
        assertEquals(50.0, fad.getNuværendeMængde());
    }

    @Test
    void createDestillering() {
        LocalDate startDato = LocalDate.of(2024, 1, 1);
        LocalDate slutDato = LocalDate.of(2024, 1, 10);

        Destillering destillering = Controller.createDestillering(
                startDato,
                slutDato,
                1,
                "Byg",
                200.0,
                60.0,
                "Tørv",
                "Test kommentar"
        );

        assertNotNull(destillering);
        assertEquals(startDato, destillering.getStartDato());
        assertEquals(slutDato, destillering.getSlutDato());
        assertEquals(1, destillering.getMaltBatch());
        assertEquals("Byg", destillering.getKornSort());
        assertEquals(200.0, destillering.getMængde());
        assertEquals(60.0, destillering.getAlkoholProcent());
        assertEquals("Tørv", destillering.getRygeMateriale());
        assertEquals("Test kommentar", destillering.getKommentar());
    }
}