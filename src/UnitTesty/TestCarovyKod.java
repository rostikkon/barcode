package UnitTesty;

import Tridy.Code39;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class TestCarovyKod {

    private Code39 kod39;
    private Path docasnySoubor;

    @TempDir
    Path docasnyAdresar;

    @BeforeEach
    void priprav() {
        kod39 = new Code39();
        docasnySoubor = docasnyAdresar.resolve("kod.png");
    }

    @Test
    void testVytvoritObrazek() {
        assertDoesNotThrow(() -> kod39.vytvoritObrazek("ABC", docasnySoubor.toString()),
                "Vytvoreni obrazku by melo probehnout bez chyby");
    }
}