import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Historie {
    private ArrayList<Zaznam> zaznamy;
    private Scanner scanner;
    private final String cestaKSouboru = "Soubory/historie.txt";

    public Historie() {
        zaznamy = new ArrayList<>();
        scanner = new Scanner(System.in);
        nacistZeSouboru();
    }

    public void spustit() {
        while (true) {
            if (zaznamy.isEmpty()) {
                System.out.println("\nHistorie je prazdna.");
                System.out.print("Stisknete klavesu Enter pro navrat do hlavniho menu: ");
                scanner.nextLine();
                return;
            }

            System.out.println("\n--- Historie ---");
            System.out.println("1. Cela historie");
            System.out.println("2. Tridit historii");
            System.out.println("3. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-3]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-3.");
                continue;
            }

            switch (volba) {
                case "1":
                    zobrazitCelouHistorii();
                    break;
                case "2":
                    tridit();
                    break;
                case "3":
                    return;
            }
        }
    }

    private void zobrazitCelouHistorii() {
        while (true) {
            System.out.println("\n--- Cela historie ---");
            zobrazitZaznamy(zaznamy);
            System.out.println("\n1. Smazat konkretni");
            System.out.println("2. Smazat celou historii");
            System.out.println("3. Generovat z historie");
            System.out.println("4. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-4]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-4:");
                continue;
            }

            switch (volba) {
                case "1":
                    smazatKonkretni(zaznamy);
                    break;
                case "2":
                    smazatCelouHistorii();
                    return;
                case "3":
                    generovatZHistorie(zaznamy);
                    break;
                case "4":
                    return;
            }
        }
    }

    private void tridit() {
        while (true) {
            System.out.println("\n--- Tridit historii ---");
            System.out.println("1. Jenom Code39");
            System.out.println("2. Jenom Code128");
            System.out.println("3. Dnesni historie");
            System.out.println("4. Posledni 24 hodin");
            System.out.println("5. Posledni 7 dnu");
            System.out.println("6. Posledni mesic");
            System.out.println("7. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-7]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-7.");
                continue;
            }

            switch (volba) {
                case "1":
                    filtrovatAProvest("Code39", vytvoritFiltrCode39());
                    break;
                case "2":
                    filtrovatAProvest("Code128", vytvoritFiltrCode128());
                    break;
                case "3":
                    filtrovatAProvest("Dnesni historie", vytvoritFiltrDnesni());
                    break;
                case "4":
                    filtrovatAProvest("Posledni 24 hodin", vytvoritFiltr24Hodin());
                    break;
                case "5":
                    filtrovatAProvest("Posledni 7 dnu", vytvoritFiltr7Dni());
                    break;
                case "6":
                    filtrovatAProvest("Posledni mesic", vytvoritFiltrMesic());
                    break;
                case "7":
                    return;
            }
        }
    }

    private void filtrovatAProvest(String nazev, ArrayList<Zaznam> filtrovane) {
        while (true) {
            if (filtrovane.isEmpty()) {
                System.out.println("\nZadny zaznam v kategorii: " + nazev);
                System.out.print("Stisknete klavesu Enter pro pokracovani: ");
                scanner.nextLine();
                return;
            }

            System.out.println("\n--- " + nazev + " ---");
            zobrazitZaznamy(filtrovane);
            System.out.println("1. Smazat konkretni");
            System.out.println("2. Smazat vsechny v teto kategorii");
            System.out.println("3. Generovat z historie");
            System.out.println("4. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-4]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-4.");
                continue;
            }

            switch (volba) {
                case "1":
                    smazatKonkretni(filtrovane);
                    break;
                case "2":
                    smazatFiltrovane(filtrovane);
                    return;
                case "3":
                    generovatZHistorie(filtrovane);
                    break;
                case "4":
                    return;
            }
        }
    }

    private void smazatKonkretni(ArrayList<Zaznam> seznam) {
        while (true) {
            if (seznam.isEmpty()) {
                System.out.println("\nZadny zaznam k smazani.");
                System.out.print("Stisknete klavesu Enter pro pokracovani: ");
                scanner.nextLine();
                return;
            }

            System.out.print("\nZadejte cislo zaznamu k smazani (1-" + seznam.size() + ") nebo 'zpatky' pro navrat: ");
            String vstup = scanner.nextLine().trim();
            if (vstup.equalsIgnoreCase("zpatky")) {
                return;
            }
            try {
                int index = Integer.parseInt(vstup) - 1;
                if (index >= 0 && index < seznam.size()) {
                    Zaznam smazany = seznam.remove(index);
                    zaznamy.remove(smazany);
                    ulozitDoSouboru();
                    System.out.println("Zaznam smazan: " + smazany.getVstup());

                    System.out.println("Chcete smazat dalsi?");
                    System.out.println("1. Ano");
                    System.out.println("2. Ne");
                    System.out.print("Zadejte volbu: ");
                    String pokracovat = scanner.nextLine().trim();
                    if (!pokracovat.matches("[1-2]")) {
                        System.out.println("Neplatna volba, zadejte 1 nebo 2.");
                        continue;
                    }
                    if (pokracovat.equals("2")) {
                        return;
                    }
                } else {
                    System.out.println("Neplatne cislo zaznamu.");
                }
            } catch (NumberFormatException e) {
                        System.out.println("Neplatny vstup, zadejte cislo nebo 'zpatky'.");
            }
        }
    }


    private void generovatZHistorie(ArrayList<Zaznam> seznam) {
        if (seznam.isEmpty()) {
            System.out.println("\nZadny zaznam k generovani.");
            System.out.print("Stisknete klavesu Enter pro pokracovani: ");
            scanner.nextLine();
            return;
        }

        while (true) {
            System.out.print("\nZadejte cislo zaznamu k generovani (1-" + seznam.size() + ") nebo 'zpatky' pro navrat: ");
            String vstup = scanner.nextLine().trim();
            if (vstup.equalsIgnoreCase("zpatky")) {
                return;
            }
            try {
                int index = Integer.parseInt(vstup) - 1;
                if (index >= 0 && index < seznam.size()) {
                    Zaznam z = seznam.get(index);
                    CarovyKod kod;
                    switch (z.getTyp()) {
                        case CODE39:
                            kod = new Code39();
                            break;
                        case CODE128:
                            kod = new Code128();
                            break;
                        default:
                            throw new IllegalStateException("Neznamy typ kodu: " + z.getTyp());
                    }

                    String soubor;
                    if (z.getTyp() == TypKodu.CODE39) {
                        soubor = "code39.png";
                    } else {
                        soubor = "code128.png";
                    }

                    try {
                        String cesta = ulozitObrazek(kod, z.getVstup(), z.getVyska(), z.getBarvaPozadi(), z.getBarvaCar(), z.isZobrazitTextPodKodem());
                        if (cesta != null) {
                            System.out.println("\nCesta k souboru: " + cesta);
                            System.out.println("== " + z.getTyp() + " vygenerovan a ulozen! ==");
                            pridatZaznam(z.getVstup(), z.getTyp(), z.getVyska(), z.getBarvaPozadi(), z.getBarvaCar(), z.isZobrazitTextPodKodem());
                        }
                    } catch (IOException e) {
                        System.out.println("Chyba pri ukladani: " + e.getMessage());
                    }

                    System.out.println("Chcete ulozit kod?");
                    System.out.println("1. Ano");
                    System.out.println("2. Ne");
                    System.out.print("Zadejte volbu: ");
                    String ulozit = scanner.nextLine().trim();
                    if (!ulozit.matches("[1-2]")) {
                        System.out.println("Neplatna volba, zadejte 1 nebo 2.");
                        continue;
                    }
                    if (ulozit.equals("1")) {
                        try {
                            String cesta = ulozitObrazek(kod, z.getVstup(), z.getVyska(), z.getBarvaPozadi(), z.getBarvaCar(), z.isZobrazitTextPodKodem());
                            if (cesta != null) {
                                System.out.println("\nCesta k souboru: " + cesta);
                                System.out.println("== " + z.getTyp() + " vygenerovan a ulozen! ==");
                                pridatZaznam(z.getVstup(), z.getTyp(), z.getVyska(), z.getBarvaPozadi(), z.getBarvaCar(), z.isZobrazitTextPodKodem());
                            }
                        } catch (IOException e) {
                            System.out.println("Chyba pri ukladani: " + e.getMessage());
                        }
                    }

                    System.out.print("Stisknete klavesu Enter pro pokracovani: ");
                    scanner.nextLine();
                    return;
                } else {
                    System.out.println("Neplatne cislo zaznamu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Neplatny vstup, zadejte cislo nebo 'zpatky'.");
            }
        }
    }


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

    public void pridatZaznam(String vstup, TypKodu typ, int vyska, Barva barvaPozadi, Barva barvaCar, boolean zobrazitTextPodKodem) {
        zaznamy.add(new Zaznam(vstup, typ, null, vyska, barvaPozadi, barvaCar, zobrazitTextPodKodem));
        ulozitDoSouboru();
    }

    private void nacistZeSouboru() {
        File soubor = new File(cestaKSouboru);
        if (!soubor.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(soubor))) {
            String radek;
            while ((radek = reader.readLine()) != null) {
                if (!radek.trim().isEmpty()) {
                    try {
                        zaznamy.add(Zaznam.nacistZeSouboru(radek));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Chyba pri cteni radku: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri cteni souboru historie: " + e.getMessage());
        }
    }

    private void ulozitDoSouboru() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cestaKSouboru))) {
            for (Zaznam zaznam : zaznamy) {
                writer.write(zaznam.ulozitDoSouboru());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba pri ukladani historie: " + e.getMessage());
        }
    }

    private void zobrazitZaznamy(ArrayList<Zaznam> seznam) {
        if (seznam.isEmpty()) {
            System.out.println("Zadny zaznam k zobrazeni.");
            return;
        }

        for (int i = 0; i < seznam.size(); i++) {
            Zaznam zaznam = seznam.get(i);
            String textZobrazeni;
            if (zaznam.isZobrazitTextPodKodem()) {
                textZobrazeni = "zobrazen";
            } else {
                textZobrazeni = "skryt";
            }

            System.out.println((i + 1) + ". " + zaznam.getVstup() + " (" +
                    zaznam.getTyp() + ", " +
                    zaznam.getCas() + ", vyska: " +
                    zaznam.getVyska() + ", pozadi: " +
                    zaznam.getBarvaPozadi() + ", cary: " +
                    zaznam.getBarvaCar() + ", text: " +
                    textZobrazeni + ")");
        }
    }

    private void smazatCelouHistorii() {
        zaznamy.clear();
        ulozitDoSouboru();
        System.out.println("\nCela historie smazana.");
        System.out.print("Stisknete klavesu Enter pro pokracovani: ");
        scanner.nextLine();
    }

    private void smazatFiltrovane(ArrayList<Zaznam> filtrovane) {
        zaznamy.removeAll(filtrovane);
        ulozitDoSouboru();
        System.out.println("\nVsechny zaznamy v teto kategorii smazany.");
        System.out.print("Stisknete klavesu Enter pro pokracovani: ");
        scanner.nextLine();
    }

    private ArrayList<Zaznam> vytvoritFiltrCode39() {
        ArrayList<Zaznam> filtrovane = new ArrayList<>();
        for (Zaznam zaznam : zaznamy) {
            if (zaznam.getTyp() == TypKodu.CODE39) {
                filtrovane.add(zaznam);
            }
        }
        return filtrovane;
    }

    private ArrayList<Zaznam> vytvoritFiltrCode128() {
        ArrayList<Zaznam> filtrovane = new ArrayList<>();
        for (Zaznam zaznam : zaznamy) {
            if (zaznam.getTyp() == TypKodu.CODE128) {
                filtrovane.add(zaznam);
            }
        }
        return filtrovane;
    }

    private ArrayList<Zaznam> vytvoritFiltrDnesni() {
        ArrayList<Zaznam> filtrovane = new ArrayList<>();
        LocalDateTime dnes = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        for (Zaznam zaznam : zaznamy) {
            if (!zaznam.getCas().isBefore(dnes)) {
                filtrovane.add(zaznam);
            }
        }
        return filtrovane;
    }

    private ArrayList<Zaznam> vytvoritFiltr24Hodin() {
        ArrayList<Zaznam> filtrovane = new ArrayList<>();
        LocalDateTime pred24Hodinami = LocalDateTime.now().minusHours(24).withSecond(0);
        for (Zaznam zaznam : zaznamy) {
            if (!zaznam.getCas().isBefore(pred24Hodinami)) {
                filtrovane.add(zaznam);
            }
        }
        return filtrovane;
    }

    private ArrayList<Zaznam> vytvoritFiltr7Dni() {
        ArrayList<Zaznam> filtrovane = new ArrayList<>();
        LocalDateTime pred7Dny = LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0);
        for (Zaznam zaznam : zaznamy) {
            if (!zaznam.getCas().isBefore(pred7Dny)) {
                filtrovane.add(zaznam);
            }
        }
        return filtrovane;
    }

    private ArrayList<Zaznam> vytvoritFiltrMesic() {
        ArrayList<Zaznam> filtrovane = new ArrayList<>();
        LocalDateTime predMesicem = LocalDateTime.now().minusMonths(1).withHour(0).withMinute(0).withSecond(0);
        for (Zaznam zaznam : zaznamy) {
            if (!zaznam.getCas().isBefore(predMesicem)) {
                filtrovane.add(zaznam);
            }
        }
        return filtrovane;
    }

    private String ulozitObrazek(CarovyKod kod, String vstup, int vyska, Barva barvaPozadi, Barva barvaCar, boolean zobrazitTextPodKodem) throws IOException {
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

            kod.vytvoritObrazek(vstup, cesta, vyska, getColorBarvy(barvaPozadi), getColorBarvy(barvaCar), zobrazitTextPodKodem);
            return cesta;
        } else {
            System.out.println("Ukladani zruseno.");
        }
        return null;
    }

}