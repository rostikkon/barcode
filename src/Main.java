import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Code39 code39 = new Code39();
            code39.vytvorObrazek("FDS12345", "code39.png");
            System.out.println("Code39 vygenerovan!");
        } catch (IOException e) {
            System.out.println("Chyba pri ukladani obrazku: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba ve vstupu: " + e.getMessage());
        }
    }
}