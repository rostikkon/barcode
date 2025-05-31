import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class CarovyKod {

    public abstract String zakoduj(String vstup);

    public void vytvoritObrazek(String data, String cestaKSouboru) throws IOException {
        if (cestaKSouboru == null || cestaKSouboru.isEmpty()) {
            throw new IllegalArgumentException("Cesta k souboru nesmi byt prazdna!");
        }

        String zakodovano = zakoduj(data);
        int vyskaCary = 100;
        int tichaZona = 10;
        int sirka = zakodovano.length() + 2 * tichaZona;

        BufferedImage obrazek = new BufferedImage(sirka, vyskaCary, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = obrazek.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, sirka, vyskaCary);

        g.setColor(Color.BLACK);
        for (int x = 0; x < zakodovano.length(); x++) {
            if (zakodovano.charAt(x) == '1') {
                g.drawLine(x + tichaZona, 0, x + tichaZona, vyskaCary);
            }
        }

        g.dispose();
        ImageIO.write(obrazek, "png", new File(cestaKSouboru));
    }
}