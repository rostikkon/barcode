package Tridy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Abstraktni trida pro generovani carovych kodu.
 */
public abstract class CarovyKod {

    /**
     * Koduje vstupni retezec do formatu caroveho kodu.
     * @param vstup Vstupni retezec k zakodovani.
     * @return Zakodovany retezec jako posloupnost 0 a 1.
     */
    public abstract String zakoduj(String vstup);

    /**
     * Vytvari obrazek caroveho kodu s nastavitelnými parametry.
     * @param data Vstupni data pro kod.
     * @param cestaKSouboru Cesta k vystupnimu PNG souboru.
     * @param vyska Vyska kodu v pixelech.
     * @param barvaPozadi Barva pozadi kodu.
     * @param barvaCar Barva car kodu.
     * @param zobrazitTextPodKodem Zda zobrazit vstupni text pod kodem.
     * @throws IOException Pokud dojde k chybe pri ukladani souboru.
     */
    public void vytvoritObrazek(String data, String cestaKSouboru, int vyska, Color barvaPozadi, Color barvaCar, boolean zobrazitTextPodKodem) throws IOException {
        if (cestaKSouboru == null || cestaKSouboru.isEmpty()) {
            throw new IllegalArgumentException("Cesta k souboru nesmi byt prazdna!");
        }

        String zakodovano = zakoduj(data);
        int tichaZona = 10;
        int sirkaObrazku = zakodovano.length() + 2 * tichaZona;
        int vyskaTextu = 20;
        int celkovaVyska;

        if (zobrazitTextPodKodem) {
            celkovaVyska = vyska + vyskaTextu;
        } else {
            celkovaVyska = vyska;
        }

        BufferedImage obrazek = new BufferedImage(sirkaObrazku, celkovaVyska, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = obrazek.createGraphics();

        g.setColor(barvaPozadi);
        g.fillRect(0, 0, sirkaObrazku, celkovaVyska);

        g.setColor(barvaCar);
        for (int x = 0; x < zakodovano.length(); x++) {
            if (zakodovano.charAt(x) == '1') {
                g.drawLine(x + tichaZona, 0, x + tichaZona, vyska);
            }
        }

        if (zobrazitTextPodKodem) {
            g.setColor(barvaCar);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics metrics = g.getFontMetrics();
            int xTextu = (sirkaObrazku - metrics.stringWidth(data)) / 2;
            int yTextu = vyska + vyskaTextu - 5;
            g.drawString(data, xTextu, yTextu);
        }

        g.dispose();
        ImageIO.write(obrazek, "png", new File(cestaKSouboru));
    }

    /**
     * Vytvari obrazek caroveho kodu s vychozimi parametry.
     * @param data Vstupni data pro kod.
     * @param cestaKSouboru Cesta k vystupnimu PNG souboru.
     * @throws IOException Pokud dojde k chybe pri ukladani souboru.
     */
    public void vytvoritObrazek(String data, String cestaKSouboru) throws IOException {
        vytvoritObrazek(data, cestaKSouboru, 70, Color.WHITE, Color.BLACK, false);
    }

}