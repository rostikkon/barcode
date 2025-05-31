package Tridy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * Trida pro generovani caroveho kodu Code39.
 */
public class Code39 extends CarovyKod {

    private static final HashMap<Character, String> MAPA_CODE39 = new HashMap<>();
    private static boolean mapaInicializovana = false;

    /**
     * Inicializuje mapu pro kodovani Code39 z externiho souboru.
     * @throws RuntimeException Pokud dojde k chybe pri cteni souboru.
     */
    private static void inicializujMapu() {
        if (mapaInicializovana) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("Soubory/code39.txt"))) {
            String radek;
            while ((radek = reader.readLine()) != null) {

                String[] casti = radek.split("=");
                char znak = casti[0].charAt(0);
                String vzor = casti[1].trim();
                MAPA_CODE39.put(znak, vzor);
            }
            mapaInicializovana = true;
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri nacitani souboru code39.txt: " + e.getMessage());
        }
    }

    /**
     * Koduje vstupni retezec do formatu Code39.
     * @param vstup Vstupni retezec k zakodovani.
     * @return Zakodovany retezec jako posloupnost 0 a 1.
     * @throws IllegalArgumentException Pokud je vstup neplatny nebo obsahuje nepodporovane znaky.
     */
    @Override
    public String zakoduj(String vstup) {
        inicializujMapu();
        if (vstup == null || vstup.isEmpty()) {
            throw new IllegalArgumentException("Vstup nesmi byt prazdny!");
        }
        vstup = "*" + vstup.toUpperCase() + "*";
        StringBuilder zakodovano = new StringBuilder();

        for (int i = 0; i < vstup.length(); i++) {
            char c = vstup.charAt(i);
            if (!MAPA_CODE39.containsKey(c)) {
                throw new IllegalArgumentException("Neplatny znak pro Tridy.Code39: " + c);
            }
            zakodovano.append(MAPA_CODE39.get(c));
            if (i != vstup.length() - 1) {
                zakodovano.append("0");
            }
        }
        return zakodovano.toString();
    }

    /**
     * Kontroluje, zda je vstup platny pro Code39.
     * @param vstup Vstupni retezec k overeni.
     * @return True, pokud je vstup platny, jinak false.
     */
    public boolean jePlatnyVstupCode39(String vstup) {
        if (vstup == null || vstup.isEmpty()) {
            return false;
        }
        inicializujMapu();
        vstup = vstup.toUpperCase();
        for (char c : vstup.toCharArray()) {
            if (!MAPA_CODE39.containsKey(c)) {
                return false;
            }
        }
        return true;
    }

}