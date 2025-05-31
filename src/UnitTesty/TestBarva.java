package UnitTesty;

import Tridy.Barva;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBarva {

    @Test
    void testZiskatBila() {
        Barva barva = Barva.BILA;
        assertEquals(Barva.BILA, barva, "Barva by mela byt BILA");
    }
}