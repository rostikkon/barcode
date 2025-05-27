import java.util.HashMap;

public class Code39 extends CarovyKod {
    private static final HashMap<Character, String> MAPA_CODE39 = new HashMap<>();

    static {
        MAPA_CODE39.put('0', "101001101101");
        MAPA_CODE39.put('1', "110100101011");
        MAPA_CODE39.put('2', "101100101011");
        MAPA_CODE39.put('3', "110110010101");
        MAPA_CODE39.put('4', "101001101011");
        MAPA_CODE39.put('5', "110100110101");
        MAPA_CODE39.put('6', "101100110101");
        MAPA_CODE39.put('7', "101001011011");
        MAPA_CODE39.put('8', "110100101101");
        MAPA_CODE39.put('9', "101100101101");
        MAPA_CODE39.put('A', "110101001011");
        MAPA_CODE39.put('B', "101101001011");
        MAPA_CODE39.put('C', "110110100101");
        MAPA_CODE39.put('D', "101011001011");
        MAPA_CODE39.put('E', "110101100101");
        MAPA_CODE39.put('F', "101101100101");
        MAPA_CODE39.put('G', "101010011011");
        MAPA_CODE39.put('H', "110101001101");
        MAPA_CODE39.put('I', "101101001101");
        MAPA_CODE39.put('J', "101011001101");
        MAPA_CODE39.put('K', "110101010011");
        MAPA_CODE39.put('L', "101101010011");
        MAPA_CODE39.put('M', "110110101001");
        MAPA_CODE39.put('N', "101011010011");
        MAPA_CODE39.put('O', "110101101001");
        MAPA_CODE39.put('P', "101101101001");
        MAPA_CODE39.put('Q', "101010110011");
        MAPA_CODE39.put('R', "110101011001");
        MAPA_CODE39.put('S', "101101011001");
        MAPA_CODE39.put('T', "101011011001");
        MAPA_CODE39.put('U', "110010101011");
        MAPA_CODE39.put('V', "100110101011");
        MAPA_CODE39.put('W', "110011010101");
        MAPA_CODE39.put('X', "100101101011");
        MAPA_CODE39.put('Y', "110010110101");
        MAPA_CODE39.put('Z', "100110110101");
        MAPA_CODE39.put('-', "100101011011");
        MAPA_CODE39.put('.', "110010101101");
        MAPA_CODE39.put(' ', "100110101101");
        MAPA_CODE39.put('$', "100100100101");
        MAPA_CODE39.put('/', "100100101001");
        MAPA_CODE39.put('+', "100101001001");
        MAPA_CODE39.put('%', "101001001001");
        MAPA_CODE39.put('*', "100101101101");
    }

    @Override
    public String zakoduj(String vstup) {
        if (vstup == null || vstup.isEmpty()) {
            throw new IllegalArgumentException("Vstup nesmi byt prazdny!");
        }
        vstup = "*" + vstup.toUpperCase() + "*";
        StringBuilder zakodovano = new StringBuilder();

        for (int i = 0; i < vstup.length(); i++) {
            char c = vstup.charAt(i);
            if (!MAPA_CODE39.containsKey(c)) {
                throw new IllegalArgumentException("Neplatny znak pro Code39: " + c);
            }
            zakodovano.append(MAPA_CODE39.get(c));
            if (i != vstup.length() - 1) {
                zakodovano.append("0");
            }
        }
        return zakodovano.toString();
    }
}