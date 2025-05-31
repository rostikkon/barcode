package UnitTesty;

import Tridy.Code128;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCode128 {

    private Code128 kod128;

    @BeforeEach
    void priprav() {
        kod128 = new Code128();
    }

    @Test
    void testPlatnyVstup() {
        boolean vysledek = kod128.jePlatnyVstupCode128("Test123");
        assertTrue(vysledek, "Vstup Test123 by mel byt platny");
    }
}