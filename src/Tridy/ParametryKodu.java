package Tridy;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;
/**
 * Trida pro nastaveni parametru caroveho kodu.
 */
public class ParametryKodu {
    private static Scanner scanner = new Scanner(System.in);
    private Zaznam posledniGenerovanyZaznam;

    /**
     * Vraci posledni vygenerovany zaznam.
     * @return Posledni zaznam nebo null, pokud zadny neexistuje.
     */
    public Zaznam getPosledniGenerovanyZaznam() {
        return posledniGenerovanyZaznam;
    }

    /**
     * Spousti nastaveni parametru a generování kodu.
     * @param kod Instance caroveho kodu.
     * @param vstup Vstupni data pro kod.
     * @param typ Typ kodu (Code39 nebo CODE128).
     */
    public void nastavitParametry(CarovyKod kod, String vstup, TypKodu typ) {
        int vyska = 80;
        Barva barvaPozadi = Barva.BILA;
        Barva barvaCar = Barva.CERNA;
        boolean zobrazitTextPodKodem = false;

        while (true) {
            System.out.println("\n--- Nastaveni parametru ---");
            System.out.println("1. Tridy.Barva pozadi (aktualni: " + getNazevBarvy(barvaPozadi) + ")");
            System.out.println("2. Tridy.Barva car (aktualni: " + getNazevBarvy(barvaCar) + ")");
            System.out.println("3. Zobrazit text pod kodem (aktualni: " + getZobrazeniTextu(zobrazitTextPodKodem) + ")");
            System.out.println("4. Vyska (aktualni: " + vyska + ")");
            System.out.println("5. Vygenerovat kod");
            System.out.println("6. Vygenerovat a ulozit kod");
            System.out.println("7. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-7]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-7.");
                continue;
            }

            switch (volba) {
                case "1":
                    Barva novaBarvaPozadi = vybratBarvu("pozadi");
                    if (novaBarvaPozadi != null) {
                        barvaPozadi = novaBarvaPozadi;
                    }
                    break;
                case "2":
                    Barva novaBarvaCar = vybratBarvu("cary");
                    if (novaBarvaCar != null) {
                        barvaCar = novaBarvaCar;
                    }
                    break;
                case "3":
                    zobrazitTextPodKodem = nastavitZobrazeniTextu(zobrazitTextPodKodem);
                    break;
                case "4":
                    vyska = nastavitVysku();
                    break;
                case "5":
                    vygenerovatKod(kod, vstup, typ, vyska, barvaPozadi, barvaCar, zobrazitTextPodKodem);
                    posledniGenerovanyZaznam = new Zaznam(vstup, typ, null, vyska, barvaPozadi, barvaCar, zobrazitTextPodKodem);
                    return;
                case "6":
                    vygenerovatAUlozitKod(kod, vstup, typ, vyska, barvaPozadi, barvaCar, zobrazitTextPodKodem);
                    posledniGenerovanyZaznam = new Zaznam(vstup, typ, null, vyska, barvaPozadi, barvaCar, zobrazitTextPodKodem);
                    return;
                case "7":
                    posledniGenerovanyZaznam = null;
                    return;
            }
        }
    }

    /**
     * Umoznuje uzivateli vybrat barvu.
     * @param typBarvy Typ barvy (pozadi nebo cary).
     * @return Vybrana barva nebo null pri navratu.
     */
    private Barva vybratBarvu(String typBarvy) {
        while (true) {
        System.out.println("\n--- Vyber barvy pro " + typBarvy + " ---");
        System.out.println("1. Bila");
        System.out.println("2. Cerna");
        System.out.println("3. Seda");
        System.out.println("4. Modra");
        System.out.println("5. Cervena");
        System.out.println("6. Zelena");
        System.out.println("7. Zluta");
        System.out.println("8. Azurova");
        System.out.println("9. Purpurova");
        System.out.println("10. Oranzova");
        System.out.println("11. Ruzova");
        System.out.println("12. Svetle seda");
        System.out.println("13. Tmave seda");
        System.out.println("14. Zpatky");
        System.out.print("Zadejte volbu: ");

        String volba = scanner.nextLine().trim();
        if (!volba.matches("[1-9]|1[0-4]")) {
            System.out.println("Neplatna volba, zadejte cislo 1-14.");
            continue;
        }

        switch (volba) {
            case "1":
                return Barva.BILA;
            case "2":
                return Barva.CERNA;
            case "3":
                return Barva.SEDA;
            case "4":
                return Barva.MODRA;
            case "5":
                return Barva.CERVENA;
            case "6":
                return Barva.ZELENA;
            case "7":
                return Barva.ZLUTA;
            case "8":
                return Barva.AZUROVA;
            case "9":
                return Barva.PURPUROVA;
            case "10":
                return Barva.ORANZOVA;
            case "11":
                return Barva.RUZOVA;
            case "12":
                return Barva.SVETLE_SEDA;
            case "13":
                return Barva.TMAVE_SEDA;
            case "14":
                return null;
        }
        }
    }

    /**
     * Vraci textovy nazev barvy.
     * @param barva Barva k prevedeni na nazev.
     * @return Textovy nazev barvy.
     */
    private String getNazevBarvy(Barva barva) {
        switch (barva) {
            case BILA: return "bila";
            case CERNA: return "cerna";
            case SEDA: return "seda";
            case MODRA: return "modra";
            case CERVENA: return "cervena";
            case ZELENA: return "zelena";
            case ZLUTA: return "zluta";
            case AZUROVA: return "azurova";
            case PURPUROVA: return "purpurova";
            case ORANZOVA: return "oranzova";
            case RUZOVA: return "ruzova";
            case SVETLE_SEDA: return "svetle seda";
            case TMAVE_SEDA: return "tmave seda";
            default: return "neznama";
        }
    }

    /**
     * Prevadi barvu na objekt Color.
     * @param barva Barva k prevedeni.
     * @return Objekt Color odpovidajici barve.
     */
    private Color getColorBarvy(Barva barva) {
        switch (barva) {
            case BILA: return Color.WHITE;
            case CERNA: return Color.BLACK;
            case SEDA: return Color.GRAY;
            case MODRA: return Color.BLUE;
            case CERVENA: return Color.RED;
            case ZELENA: return Color.GREEN;
            case ZLUTA: return Color.YELLOW;
            case AZUROVA: return Color.CYAN;
            case PURPUROVA: return Color.MAGENTA;
            case ORANZOVA: return Color.ORANGE;
            case RUZOVA: return Color.PINK;
            case SVETLE_SEDA: return Color.LIGHT_GRAY;
            case TMAVE_SEDA: return Color.DARK_GRAY;
            default: return Color.BLACK;
        }
    }

    /**
     * Nastavuje zobrazeni textu pod kodem.
     * @param aktualniZobrazeni Aktualni stav zobrazeni.
     * @return Nove nastaveni zobrazeni textu.
     */
    private boolean nastavitZobrazeniTextu(boolean aktualniZobrazeni) {
        while (true) {
        System.out.println("\n--- Nastaveni zobrazeni textu pod kodem ---");
        System.out.println("Aktualni stav: " + getZobrazeniTextu(aktualniZobrazeni));
        System.out.println("1. Zobrazit text");
        System.out.println("2. Skryt text");
        System.out.println("3. Zpatky (bez zmen)");
        System.out.print("Zadejte volbu: ");

        String volba = scanner.nextLine().trim();
        if (!volba.matches("[1-3]")) {
            System.out.println("Neplatna volba, zadejte cislo 1-3.");
            continue;
        }

        switch (volba) {
            case "1":
                return true;
            case "2":
                return false;
            case "3":
                return aktualniZobrazeni;

        }
        }
    }

    /**
     * Vraci textovy popis zobrazeni textu.
     * @param zobrazeni Stav zobrazeni textu.
     * @return Popis jako "zobrazen" nebo "skryt".
     */
    private String getZobrazeniTextu(boolean zobrazeni) {
        if (zobrazeni) {
            return "zobrazen";
        } else {
            return "skryt";
        }
    }

    /**
     * Nastavuje vysku kodu.
     * @return Nova vyska v rozmezi 30-150.
     */
    private int nastavitVysku() {
        while (true) {
            System.out.print("Zadejte vysku (30-150): ");
            String vstup = scanner.nextLine();
            try {
                int novaVyska = Integer.parseInt(vstup);
                if (novaVyska >= 30 && novaVyska <= 150) {
                    return novaVyska;
                } else {
                    System.out.println("Vyska musi byt v rozmezi 30-150. Zkuste to znovu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Neplatna hodnota. Zadejte cislo mezi 30 a 150.");
            }
        }
    }

    /**
     * Generuje kod s nastavenymi parametry.
     * @param kod Instance caroveho kodu.
     * @param vstup Vstupni data pro kod.
     * @param typ kódů Typ kodu.
     * @param vyska Vyska kodu.
     * @param barvaPozadi Barva pozadi.
     * @param barvaCar Barva car.
     * @param zobrazitTextPodKodem Zobrazeni textu pod kodem.
     */
    private void vygenerovatKod(CarovyKod kod, String vstup, TypKodu typ, int vyska, Barva barvaPozadi, Barva barvaCar, boolean zobrazitTextPodKodem) {
        try {
            String soubor;
            if (typ == TypKodu.CODE39) {
                soubor = "code39.png";
            } else {
                soubor = "code128.png";
            }
            kod.vytvoritObrazek(vstup, soubor, vyska, getColorBarvy(barvaPozadi), getColorBarvy(barvaCar), zobrazitTextPodKodem);
            System.out.println("\n== " + typ + " vygenerovan! ==");
            System.out.print("Stisknete klavesu Enter pro navrat do hlavniho menu");
            scanner.nextLine();
        } catch (IOException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }

    /**
     * Generuje a uklada kod s nastavenymi parametry.
     * <p>Poznamka: Implementace ukladani obrazku byla vytvorena s pomoci umele inteligence.</p>
     * @param kod Instance caroveho kodu.
     * @param vstup Vstupni data pro kod.
     * @param typ Typ kodu.
     * @param vyska Vyska kodu.
     * @param barvaPozadi Barva pozadi.
     * @param barvaCar Barva car.
     * @param zobrazitTextPodKodem Zobrazeni textu pod kodem.
     */
    private void vygenerovatAUlozitKod(CarovyKod kod, String vstup, TypKodu typ, int vyska, Barva barvaPozadi, Barva barvaCar, boolean zobrazitTextPodKodem) {
        JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Jmeno\\Barcodes"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG soubory", "png"));
        fileChooser.setDialogTitle("Ulozit carovy kod");

        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int result = fileChooser.showSaveDialog(frame);
        frame.dispose();

        if (result == JFileChooser.APPROVE_OPTION) {
            String cesta = fileChooser.getSelectedFile().getAbsolutePath();
            if (!cesta.endsWith(".png")) {
                cesta += ".png";
            }

            try {
                kod.vytvoritObrazek(vstup, cesta, vyska, getColorBarvy(barvaPozadi), getColorBarvy(barvaCar), zobrazitTextPodKodem);
                System.out.println("\nCesta k souboru: " + cesta);
                System.out.println("== " + typ + " vygenerovan a ulozen! ==");
                System.out.print("Stisknete klavesa Enter pro pokracovani");
                scanner.nextLine();
            } catch (IOException e) {
                System.out.println("Chyba pri ukladani: " + e.getMessage());
            }
        } else {
            System.out.println("Ukladani zruseno.");
        }
    }
}
