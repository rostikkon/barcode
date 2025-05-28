import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code128 extends CarovyKod {

    private static final HashMap<Integer, String> MAPA_CODE128 = new HashMap<>();

    private static final String STOP_PATTERN = "1100011101011";
    private static final int START_B = 104;
    private static final int START_C = 105;
    private static final int CODE_B = 100;
    private static final int CODE_C = 99;

    static {
        MAPA_CODE128.put(0, "11011001100");
        MAPA_CODE128.put(1, "11001101100");
        MAPA_CODE128.put(2, "11001100110");
        MAPA_CODE128.put(3, "10010011000");
        MAPA_CODE128.put(4, "10010001100");
        MAPA_CODE128.put(5, "10001001100");
        MAPA_CODE128.put(6, "10011001000");
        MAPA_CODE128.put(7, "10011000100");
        MAPA_CODE128.put(8, "10001100100");
        MAPA_CODE128.put(9, "11001001000");
        MAPA_CODE128.put(10, "11001000100");
        MAPA_CODE128.put(11, "11000100100");
        MAPA_CODE128.put(12, "10110011100");
        MAPA_CODE128.put(13, "10011011100");
        MAPA_CODE128.put(14, "10011001110");
        MAPA_CODE128.put(15, "10111001100");
        MAPA_CODE128.put(16, "10011101100");
        MAPA_CODE128.put(17, "10011100110");
        MAPA_CODE128.put(18, "11001110010");
        MAPA_CODE128.put(19, "11001011100");
        MAPA_CODE128.put(20, "11001001110");
        MAPA_CODE128.put(21, "11011100100");
        MAPA_CODE128.put(22, "11001110100");
        MAPA_CODE128.put(23, "11101101110");
        MAPA_CODE128.put(24, "11101001100");
        MAPA_CODE128.put(25, "11100101100");
        MAPA_CODE128.put(26, "11100100110");
        MAPA_CODE128.put(27, "11101100100");
        MAPA_CODE128.put(28, "11100110100");
        MAPA_CODE128.put(29, "11100110010");
        MAPA_CODE128.put(30, "11011011000");
        MAPA_CODE128.put(31, "11011000110");
        MAPA_CODE128.put(32, "11000110110");
        MAPA_CODE128.put(33, "10100011000");
        MAPA_CODE128.put(34, "10001011000");
        MAPA_CODE128.put(35, "10001000110");
        MAPA_CODE128.put(36, "10110001000");
        MAPA_CODE128.put(37, "10001101000");
        MAPA_CODE128.put(38, "10001100010");
        MAPA_CODE128.put(39, "11010001000");
        MAPA_CODE128.put(40, "11000101000");
        MAPA_CODE128.put(41, "11000100010");
        MAPA_CODE128.put(42, "10110111000");
        MAPA_CODE128.put(43, "10110001110");
        MAPA_CODE128.put(44, "10001101110");
        MAPA_CODE128.put(45, "10111011000");
        MAPA_CODE128.put(46, "10111000110");
        MAPA_CODE128.put(47, "10001110110");
        MAPA_CODE128.put(48, "11101110110");
        MAPA_CODE128.put(49, "11010001110");
        MAPA_CODE128.put(50, "11000101110");
        MAPA_CODE128.put(51, "11011101000");
        MAPA_CODE128.put(52, "11011100010");
        MAPA_CODE128.put(53, "11011101110");
        MAPA_CODE128.put(54, "11101011000");
        MAPA_CODE128.put(55, "11101000110");
        MAPA_CODE128.put(56, "11100010110");
        MAPA_CODE128.put(57, "11101101000");
        MAPA_CODE128.put(58, "11101100010");
        MAPA_CODE128.put(59, "11100011010");
        MAPA_CODE128.put(60, "11101111010");
        MAPA_CODE128.put(61, "11001000010");
        MAPA_CODE128.put(62, "11110001010");
        MAPA_CODE128.put(63, "10100110000");
        MAPA_CODE128.put(64, "10100001100");
        MAPA_CODE128.put(65, "10010110000");
        MAPA_CODE128.put(66, "10010000110");
        MAPA_CODE128.put(67, "10000101100");
        MAPA_CODE128.put(68, "10000100110");
        MAPA_CODE128.put(69, "10110010000");
        MAPA_CODE128.put(70, "10110000100");
        MAPA_CODE128.put(71, "10011010000");
        MAPA_CODE128.put(72, "10011000010");
        MAPA_CODE128.put(73, "10000110100");
        MAPA_CODE128.put(74, "10000110010");
        MAPA_CODE128.put(75, "11000010010");
        MAPA_CODE128.put(76, "11001010000");
        MAPA_CODE128.put(77, "11110111010");
        MAPA_CODE128.put(78, "11000010100");
        MAPA_CODE128.put(79, "10001111010");
        MAPA_CODE128.put(80, "10100111100");
        MAPA_CODE128.put(81, "10010111100");
        MAPA_CODE128.put(82, "10010011110");
        MAPA_CODE128.put(83, "10111100100");
        MAPA_CODE128.put(84, "10011110100");
        MAPA_CODE128.put(85, "10011110010");
        MAPA_CODE128.put(86, "11110100100");
        MAPA_CODE128.put(87, "11110010100");
        MAPA_CODE128.put(88, "11110010010");
        MAPA_CODE128.put(89, "11011011110");
        MAPA_CODE128.put(90, "11011110110");
        MAPA_CODE128.put(91, "11110110110");
        MAPA_CODE128.put(92, "10101111000");
        MAPA_CODE128.put(93, "10100011110");
        MAPA_CODE128.put(94, "10001011110");
        MAPA_CODE128.put(95, "10111101000");
        MAPA_CODE128.put(96, "10111100010");
        MAPA_CODE128.put(97, "11110101000");
        MAPA_CODE128.put(98, "11110100010");
        MAPA_CODE128.put(99, "10111011110");
        MAPA_CODE128.put(100, "10111101110");
        MAPA_CODE128.put(101, "11101011110");
        MAPA_CODE128.put(102, "11110101110");
        MAPA_CODE128.put(103, "11010000100");
        MAPA_CODE128.put(104, "11010010000");
        MAPA_CODE128.put(105, "11010011100");
    }

    @Override
    public String zakoduj(String vstup) {
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
}