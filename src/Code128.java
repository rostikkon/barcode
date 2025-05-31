import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code128 extends CarovyKod {

    private static final HashMap<Integer, String> MAPA_CODE128 = new HashMap<>();
    private static boolean mapaInicializovana = false;
    private static final String STOP_PATTERN = "1100011101011";
    private static final int START_B = 104;
    private static final int START_C = 105;
    private static final int CODE_B = 100;
    private static final int CODE_C = 99;

    private static void inicializujMapu() {
        if (mapaInicializovana) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("Soubory/code128.txt"))) {
            String radek;
            while ((radek = reader.readLine()) != null) {

                String[] casti = radek.split("=");
                int hodnota = Integer.parseInt(casti[0].trim());
                String vzor = casti[1].trim();
                MAPA_CODE128.put(hodnota, vzor);

            }
            mapaInicializovana = true;
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri nacitani souboru code128.txt: " + e.getMessage());
        }
    }

    @Override
    public String zakoduj(String vstup) {
        inicializujMapu();
        if (vstup == null || vstup.isEmpty()) {
            throw new IllegalArgumentException("Vstup nesmi byt prazdny!");
        }

        ArrayList<Integer> hodnoty = new ArrayList<>();
        boolean jeSadaC = false;

        Pattern cisla4plus = Pattern.compile("\\d{4,}");
        Matcher matcher = cisla4plus.matcher(vstup);
        int posledniIndex = 0;

        if (matcher.lookingAt()) {
            hodnoty.add(START_C);
            jeSadaC = true;
        } else {
            hodnoty.add(START_B);
            jeSadaC = false;
        }

        while (posledniIndex < vstup.length()) {
            matcher.reset(vstup.substring(posledniIndex));
            if (matcher.find() && matcher.start() == 0) {
                String blokCislic = matcher.group();
                if (!jeSadaC) {
                    hodnoty.add(CODE_C);
                    jeSadaC = true;
                }
                int pocetParu = blokCislic.length() / 2;
                for (int i = 0; i < pocetParu * 2; i += 2) {
                    String par = blokCislic.substring(i, i + 2);
                    int hodnota = Integer.parseInt(par);
                    hodnoty.add(hodnota);
                }
                if (blokCislic.length() % 2 != 0) {
                    hodnoty.add(CODE_B);
                    jeSadaC = false;
                    char posledniCislice = blokCislic.charAt(blokCislic.length() - 1);
                    int hodnota = posledniCislice - '0' + 16;
                    hodnoty.add(hodnota);
                }
                posledniIndex += blokCislic.length();
            } else {
                if (jeSadaC) {
                    hodnoty.add(CODE_B);
                    jeSadaC = false;
                }
                char c = vstup.charAt(posledniIndex);
                int hodnota = c - 32;
                if (hodnota < 0 || hodnota > 94) {
                    throw new IllegalArgumentException("Neplatny znak pro sadu B: " + c);
                }
                hodnoty.add(hodnota);
                posledniIndex++;
            }
        }

        int soucet = hodnoty.get(0);
        for (int i = 1; i < hodnoty.size(); i++) {
            soucet += i * hodnoty.get(i);
        }
        int kontrolniHodnota = soucet % 103;

        StringBuilder vzor = new StringBuilder();
        for (int hodnota : hodnoty) {
            vzor.append(MAPA_CODE128.get(hodnota));
        }
        vzor.append(MAPA_CODE128.get(kontrolniHodnota));
        vzor.append(STOP_PATTERN);

        return vzor.toString();
    }

    public boolean jePlatnyVstupCode128(String vstup) {
        if (vstup == null || vstup.isEmpty()) {
            return false;
        }
        for (char c : vstup.toCharArray()) {
            if (c < 0 || c > 127) {
                return false;
            }
        }
        return true;
    }

}