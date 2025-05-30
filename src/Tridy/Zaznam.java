package Tridy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Trida pro uchovavani informaci o generovanem carovem kodu.
 */
public class Zaznam {
    private String vstup;
    private TypKodu typ;
    private LocalDateTime cas;
    private int vyska;
    private Barva barvaPozadi;
    private Barva barvaCar;
    private boolean zobrazitTextPodKodem;

    /**
     * Vytvari novy zaznam o carovem kodu.
     * @param vstup Vstupni data kodu.
     * @param typ Typ caroveho kodu.
     * @param cas Cas vytvoreni kodu.
     * @param vyska Vyska kodu.
     * @param barvaPozadi Barva pozadi kodu.
     * @param barvaCar Barva car kodu.
     * @param zobrazitTextPodKodem Zobrazeni textu pod kodem.
     */
    public Zaznam(String vstup, TypKodu typ, LocalDateTime cas, int vyska, Barva barvaPozadi, Barva barvaCar, boolean zobrazitTextPodKodem) {
        this.vstup = vstup;
        this.typ = typ;
        setCas(cas);
        this.vyska = vyska;
        this.barvaPozadi = barvaPozadi;
        this.barvaCar = barvaCar;
        this.zobrazitTextPodKodem = zobrazitTextPodKodem;
    }

    public String getVstup() {
        return vstup;
    }

    public TypKodu getTyp() {
        return typ;
    }

    public LocalDateTime getCas() {
        return cas;
    }

    public int getVyska() {
        return vyska;
    }

    public Barva getBarvaPozadi() {
        return barvaPozadi;
    }

    public Barva getBarvaCar() {
        return barvaCar;
    }

    /**
     * Kontroluje, zda se ma zobrazit text pod kodem.
     * @return True, pokud se text zobrazuje, jinak false.
     */
    public boolean isZobrazitTextPodKodem() {
        return zobrazitTextPodKodem;
    }

    private void setCas(LocalDateTime cas) {
        if (cas == null) {
            this.cas = LocalDateTime.now();
        } else {
            this.cas = cas;
        }
    }

    /**
     * Uklada zaznam do formatu pro soubor.
     * @return Retezec s daty zaznamu oddelenymi znakem |.
     */
    public String ulozitDoSouboru() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return String.join("|",
                vstup,
                typ.toString(),
                cas.format(formatter),
                String.valueOf(vyska),
                barvaPozadi.toString(),
                barvaCar.toString(),
                String.valueOf(zobrazitTextPodKodem));
    }

    /**
     * Nacitá zaznam z retezce ulozeneho v souboru.
     * @param radek Retezec s daty zaznamu.
     * @return Novy zaznam.
     * @throws IllegalArgumentException Pokud je format retezce neplatny.
     */
    public static Zaznam nacistZeSouboru(String radek) {
        String[] casti = radek.split("\\|");
        if (casti.length != 7) {
            throw new IllegalArgumentException("Neplatny format radku v souboru historie: " + radek);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime cas = LocalDateTime.parse(casti[2], formatter);
        return new Zaznam(
                casti[0],
                TypKodu.valueOf(casti[1]),
                cas,
                Integer.parseInt(casti[3]),
                Barva.valueOf(casti[4]),
                Barva.valueOf(casti[5]),
                Boolean.parseBoolean(casti[6])
        );
    }
}