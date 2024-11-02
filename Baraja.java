import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    private ArrayList<Carta> cartas;

    public Baraja() {
        cartas = new ArrayList<>();
        String[] figuras = {"BASTO", "COPA", "ESPADA", "ORO"};
        int[] valores = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12}; // Sin 8, 9

        for (String figura : figuras) {
            for (int valor : valores) {
                cartas.add(new Carta(figura, valor));
            }
        }
        Collections.shuffle(cartas);
    }

    public Carta robar() {
        if (!cartas.isEmpty()) {
            return cartas.remove(0);
        }
        return null;
    }

    public boolean tieneCartas() {
        return !cartas.isEmpty();
    }
}
