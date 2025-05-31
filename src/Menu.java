import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private Historie historie = new Historie();

    public void spustit() {
        System.out.println("----------------------------------------\n" +
                "Vitejte v Generatoru carovych kodu!\n" +
                "Tento nastroj podporuje typy Code39 a Code128.\n" +
                "----------------------------------------");
        while (true) {

            zobrazitHlavniMenu();

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-4]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-4.");
                continue;
            }

            switch (volba) {
                case "1":
                    generaceKodu();
                    break;
                case "2":
                    zobrazitInformace();
                    break;
                case "3":
                    new Historie().spustit();
                    break;
                case "4":
                    System.out.println("----------------------------------------\n" +
                            "Dekuji, ze jste pouzili Generator carovych kodu.\n" +
                            "Preji hezky den a mnoho uspesnych skenu!\n" +
                            "----------------------------------------");
                    System.exit(0);
                    break;
            }
        }
    }

    private void zobrazitHlavniMenu() {
        System.out.println("\n--- Hlavni menu ---");
        System.out.println("1. Generace kodu");
        System.out.println("2. Informace o typech kodu");
        System.out.println("3. Historie");
        System.out.println("4. Ukoncit program");
        System.out.print("Zadejte volbu: ");
    }

    private void generaceKodu() {
        while (true) {
            System.out.println("\n--- Generace kodu ---");
             System.out.println("1. Code39");
            System.out.println("2. Code128");
            System.out.println("3. Informace o typech kodu (co podporuji)");
            System.out.println("4. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-4]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-4.");
                continue;
            }

            switch (volba) {
                case "1":
                    generaceCode39();
                    return;
                case "2":
                    generaceCode128();
                    return;
                case "3":
                    zobrazitInformace();
                    break;
                case "4":
                    return;
            }
        }
    }

    private void generaceCode39() {

        Code39 kod = new Code39();
        String vstup;
        while (true) {
            System.out.print("Zadejte text pro Code39: ");
            vstup = scanner.nextLine();
            if (kod.jePlatnyVstupCode39(vstup)) {
                break;
            } else {
                System.out.println("Neplatny vstup! Code39 podporuje pouze velka pismena (A-Z), cislice (0-9), znaky: - . $ / + % a mezeru. Zadejte text znovu.");
            }
        }

        while (true) {
            System.out.println("\n--- Code39 ---");
            System.out.println("1. Vygenerovat klasicky kod");
            System.out.println("2. Vygenerovat a ulozit klasicky kod");
            System.out.println("3. Vybrat parametry kodu");
            System.out.println("4. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-4]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-4.");
                continue;
            }

            switch (volba) {
                case "1":
                    try {
                        kod.vytvoritObrazek(vstup, "code39.png");
                        System.out.println("\n== Code39 vygenerovan! ==");
                        historie.pridatZaznam(vstup, TypKodu.CODE39, 70, Barva.BILA, Barva.CERNA, false);
                    } catch (IOException e) {
                        System.out.println("Chyba: " + e.getMessage());
                    }
                    return;
                case "2":
                    try {
                        String cesta = ulozitObrazek(kod, vstup);
                        if (cesta != null) {
                            System.out.println("\nCesta k souboru: " + cesta);
                            System.out.println("== Code39 vygenerovan a ulozen! ==");
                            historie.pridatZaznam(vstup, TypKodu.CODE39, 70, Barva.BILA, Barva.CERNA, false);
                            System.out.print("Stisknete klavesu Enter pro pokracovani");
                            scanner.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.println("Chyba: " + e.getMessage());
                    }
                    return;
                case "3":
                    ParametryKodu parametryKodu = new ParametryKodu();
                    parametryKodu.nastavitParametry(kod, vstup, TypKodu.CODE39);
                    if (parametryKodu.getPosledniGenerovanyZaznam() != null) {
                        historie.pridatZaznam(
                                parametryKodu.getPosledniGenerovanyZaznam().getVstup(),
                                TypKodu.CODE39,
                                parametryKodu.getPosledniGenerovanyZaznam().getVyska(),
                                parametryKodu.getPosledniGenerovanyZaznam().getBarvaPozadi(),
                                parametryKodu.getPosledniGenerovanyZaznam().getBarvaCar(),
                                parametryKodu.getPosledniGenerovanyZaznam().isZobrazitTextPodKodem()
                        );
                    }
                    return;
                case "4":
                    return;
            }
        }
    }

    private void generaceCode128() {

        Code128 kod = new Code128();
        String vstup;
        while (true) {
            System.out.print("Zadejte text pro Code128: ");
            vstup = scanner.nextLine();
            if (kod.jePlatnyVstupCode128(vstup)) {
                break;
            } else {
                System.out.println("Neplatny vstup! Code128 podporuje znaky ASCII (0-127). Zadejte text znovu.");
            }
        }

        while (true) {
            System.out.println("\n--- Code128 ---");
            System.out.println("1. Vygenerovat klasicky kod");
            System.out.println("2. Vygenerovat a ulozit klasicky kod");
            System.out.println("3. Vybrat parametry kodu");
            System.out.println("4. Zpatky");
            System.out.print("Zadejte volbu: ");

            String volba = scanner.nextLine().trim();
            if (!volba.matches("[1-4]")) {
                System.out.println("Neplatna volba, zadejte cislo 1-4.");
                continue;
            }

            switch (volba) {
                case "1":
                    try {
                        kod.vytvoritObrazek(vstup, "code128.png");
                        System.out.println("\n== Code128 vygenerovan! ==");
                        historie.pridatZaznam(vstup, TypKodu.CODE128, 70, Barva.BILA, Barva.CERNA, false);
                    } catch (IOException e) {
                        System.out.println("Chyba: " + e.getMessage());
                    }
                    return;
                case "2":
                    try {
                        String cesta = ulozitObrazek(kod, vstup);
                        if (cesta != null) {
                            System.out.println("\nCesta k souboru: " + cesta);
                            System.out.println("== Code128 vygenerovan a ulozen! ==");
                            historie.pridatZaznam(vstup, TypKodu.CODE128, 70, Barva.BILA, Barva.CERNA, false);
                            System.out.print("Stisknete klavesu Enter pro pokracovani");
                            scanner.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.println("Chyba: " + e.getMessage());
                    }
                    return;
                case "3":
                    ParametryKodu parametryKodu = new ParametryKodu();
                    parametryKodu.nastavitParametry(kod, vstup, TypKodu.CODE128);
                    if (parametryKodu.getPosledniGenerovanyZaznam() != null) {
                        historie.pridatZaznam(
                                parametryKodu.getPosledniGenerovanyZaznam().getVstup(),
                                TypKodu.CODE128,
                                parametryKodu.getPosledniGenerovanyZaznam().getVyska(),
                                parametryKodu.getPosledniGenerovanyZaznam().getBarvaPozadi(),
                                parametryKodu.getPosledniGenerovanyZaznam().getBarvaCar(),
                                parametryKodu.getPosledniGenerovanyZaznam().isZobrazitTextPodKodem()
                        );
                    }
                    return;
                case "4":
                    return;
            }
        }
    }

    private void zobrazitInformace() {
        System.out.println("\n--- INFORMACE O KODECH ---");

        System.out.println("\nCode39:");
        System.out.println("- Podporuje pouze velka pismena (A-Z), cislice (0-9)");
        System.out.println("- Take umi nektere specialni znaky: - . $ / + % a mezera");
        System.out.println("- Kazdy znak je reprezentovan 9 cary (5 cernych, 4 bile)");
        System.out.println("- Je vhodny pro jednodussi kody, snazsi cteni i bez kontroly");

        System.out.println("\nCode128:");
        System.out.println("- Podporuje velka i mala pismena (A-Z, a-z), cislice (0-9)");
        System.out.println("- Take umi vice nez 100 specialnich znaku (vsechny ASCII hodnoty 0-127)");
        System.out.println("- Efektivnejsi kodovani, mensi fyzicka velikost pri stejne delce textu");
        System.out.println("- Obsahuje kontrolni soucet (checksum) pro vyssi spolehlivost");

        System.out.println("\nDoporuceni:");
        System.out.println("- Pokud potrebujes kratky, spolehlivy a tiskove kompaktní kod (napr. pro obchod, logistiku, sklad) -> Code128");
        System.out.println("- Pokud staci zakladni znaky a ocenis jednoduchost -> Code39");
        System.out.println("- Pro tisk je lepsi Code128, protoze je kratsi a jasne citelny i v mensi velikosti");

        System.out.println("\nZpet se vratite stisknutim klavesy Enter");
        scanner.nextLine();
    }

    private String ulozitObrazek(CarovyKod kod, String vstup) {
        JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Jmeno\\Barcodes"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG soubory", "png"));
        fileChooser.setDialogTitle("Ulozit carovy kod");

        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        int result = fileChooser.showSaveDialog(frame);
        frame.dispose();

        if (result == JFileChooser.APPROVE_OPTION) {
            String cesta = fileChooser.getSelectedFile().getAbsolutePath();
            if (!cesta.endsWith(".png")) cesta += ".png";

            try {
                kod.vytvoritObrazek(vstup, cesta);
                return cesta;
            } catch (IOException e) {
                System.out.println("Chyba pri ukladani: " + e.getMessage());
            }
        } else {
            System.out.println("Ukladani zruseno.");
        }
        return null;
    }

}
