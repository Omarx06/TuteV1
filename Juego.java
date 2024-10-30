import java.util.Scanner;
import java.util.ArrayList;

public class Juego {
    private Baraja baraja;
    private Jugador jugador1;
    private Jugador jugador2;
    private Mesa mesa;
    private String figuraTriunfo;
    private Scanner scanner;

    public Juego() {
        scanner = new Scanner(System.in);
        baraja = new Baraja();
        mesa = new Mesa();
        iniciarJugadores();
        repartirCartas();
        figuraTriunfo = baraja.robar().getFigura();
        System.out.println("Figura de triunfo: " + figuraTriunfo);
    }

    private void iniciarJugadores() {
        System.out.println("Introduce el nombre del Jugador 1:");
        String nombre1 = scanner.nextLine();
        System.out.println("Introduce el nombre del Jugador 2:");
        String nombre2 = scanner.nextLine();
        jugador1 = new Jugador(nombre1);
        jugador2 = new Jugador(nombre2);
    }

    private void repartirCartas() {
        for (int i = 0; i < 6; i++) {
            jugador1.recibirCarta(baraja.robar());
            jugador2.recibirCarta(baraja.robar());
        }
    }

    public void jugar() {
        int turno = 1;
        while (jugador1.tieneCartas() && jugador2.tieneCartas()) {
            Jugador jugadorActual = (turno % 2 == 1) ? jugador1 : jugador2;
            Jugador jugadorOponente = (turno % 2 == 1) ? jugador2 : jugador1;

            System.out.println(jugadorActual.getNombre() + ", elige una carta para jugar:");
            mostrarCartas(jugadorActual);
            int index = scanner.nextInt() - 1;
            Carta cartaJugada = jugadorActual.jugarCarta(index);
            mesa.ponerCarta(cartaJugada);

            System.out.println(jugadorActual.getNombre() + " jugó: " + cartaJugada);

            // Turno del otro jugador
            System.out.println(jugadorOponente.getNombre() + ", elige una carta para jugar:");
            mostrarCartas(jugadorOponente);
            index = scanner.nextInt() - 1;
            Carta cartaOponente = jugadorOponente.jugarCarta(index);
            mesa.ponerCarta(cartaOponente);

            System.out.println(jugadorOponente.getNombre() + " jugó: " + cartaOponente);

            // Determinar quién ganó la baza
            Jugador ganador = determinarGanador(cartaJugada, cartaOponente);

            System.out.println("¡" + ganador.getNombre() + " ganó la baza!");

            // El ganador recoge las cartas jugadas en la baza
            ganador.recogerBaza(mesa.getCartasJugadas());

            // Asignar puntos por la baza ganada
            ganador.sumarPuntos(calcularPuntos(cartaJugada, cartaOponente));

            // Comprobar los cantes
            comprobarCante(ganador);

            // Limpiar mesa y robar cartas si quedan en la baraja
            mesa.limpiarMesa();
            if (baraja.tieneCartas()) {
                jugadorActual.recibirCarta(baraja.robar());
                jugadorOponente.recibirCarta(baraja.robar());
            }
            turno++;
        }
        mostrarResultadoFinal();
    }

    private void mostrarCartas(Jugador jugador) {
        for (int i = 0; i < jugador.getMazo().size(); i++) {
            System.out.println((i + 1) + ". " + jugador.getMazo().get(i));
        }
    }

    private Jugador determinarGanador(Carta carta1, Carta carta2) {
        if (carta1.getFigura().equals(carta2.getFigura())) {
            return (carta1.getValor() > carta2.getValor()) ? jugador1 : jugador2;
        } else if (carta2.getFigura().equals(figuraTriunfo)) {
            return jugador2;
        } else {
            return jugador1;
        }
    }

    private int calcularPuntos(Carta carta1, Carta carta2) {
        int puntos = 0;
        puntos += obtenerPuntosCarta(carta1);
        puntos += obtenerPuntosCarta(carta2);
        return puntos;
    }

    private int obtenerPuntosCarta(Carta carta) {
        switch (carta.getValor()) {
            case 1: return 11; // As
            case 3: return 10; // Tres
            case 11: return 2;  // Sota
            case 12: return 4;  // Rey
            default: return 0;
        }
    }

    private void comprobarCante(Jugador jugador) {
        // Verificar si el jugador tiene un cante (rey y caballo de la misma figura)
        int tieneCaballo = 0, tieneRey = 0;
        for (Carta carta : jugador.getMazo()) {
            if (carta.getValor() == 11) { // Caballo
                tieneCaballo++;
            }
            if (carta.getValor() == 12) { // Rey
                tieneRey++;
            }
        }
        if (tieneCaballo > 0 && tieneRey > 0) {
            jugador.sumarPuntos(20); // Sumar puntos por cante
            System.out.println("¡" + jugador.getNombre() + " ha hecho un cante!");
        }
    }

    private void mostrarResultadoFinal() {
        System.out.println("Resultado final:");
        System.out.println(jugador1.getNombre() + ": " + jugador1.getPuntaje() + " puntos");
        System.out.println(jugador2.getNombre() + ": " + jugador2.getPuntaje() + " puntos");

        if (jugador1.getPuntaje() > jugador2.getPuntaje()) {
            System.out.println("¡" + jugador1.getNombre() + " ha ganado el juego!");
        } else if (jugador2.getPuntaje() > jugador1.getPuntaje()) {
            System.out.println("¡" + jugador2.getNombre() + " ha ganado el juego!");
        } else {
            System.out.println("¡El juego terminó en empate!");
        }
    }
}
