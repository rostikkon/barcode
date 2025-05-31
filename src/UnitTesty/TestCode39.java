package UnitTesty;

import Tridy.Code39;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCode39 {

    private Tridy.Code39 kod39;

    @BeforeEach
    void priprav() {
        kod39 = new Code39();
    }

    @Test
    void testPlatnyVstup() {
        boolean vysledek = kod39.jePlatnyVstupCode39("ABC");
        assertTrue(vysledek, "Vstup ABC by mel byt platny");
    }
}
