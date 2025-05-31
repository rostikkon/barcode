package UnitTesty;

import Tridy.Barva;
import Tridy.TypKodu;
import Tridy.Zaznam;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestZaznam {

    @Test
    void testZiskatVstup() {
        Zaznam zaznam = new Zaznam("XYZ", TypKodu.CODE128, null, 70, Barva.BILA, Barva.CERNA, false);
        assertEquals("XYZ", zaznam.getVstup(), "Vstup by mel byt XYZ");
    }
}