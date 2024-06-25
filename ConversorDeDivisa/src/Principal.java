import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String HISTORIAL_FILE = "historial.txt";
    private static List<String> historial = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ConversorDeDivisa conversor = new ConversorDeDivisa();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("******** Bienvenido al Conversor de Divisas ********");
                System.out.println("Este conversor utiliza las siglas de cada moneda para hacer la operacion (EU-USD-CLP-ARS)");
                System.out.println("1. Realizar una conversión");
                System.out.println("2. Mostrar monedas disponibles");
                System.out.println("3. Mostrar detalles de las monedas");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea

                if (opcion == 1) {
                    System.out.print("Ingrese la cantidad a convertir: ");
                    double cantidad = scanner.nextDouble();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.print("Ingrese la divisa de origen (por ejemplo, USD): ");
                    String divisaOrigen = scanner.nextLine().toUpperCase();

                    System.out.print("Ingrese la divisa de destino (por ejemplo, EUR): ");
                    String divisaDestino = scanner.nextLine().toUpperCase();

                    double resultado = conversor.convert(divisaOrigen, divisaDestino, cantidad);
                    System.out.printf("Resultado: %.2f %s%n", resultado, divisaDestino);

                    agregarAlHistorial(cantidad, divisaOrigen, divisaDestino, resultado);

                } else if (opcion == 2) {
                    conversor.mostrarMonedasDisponibles();
                } else if (opcion == 3) {
                    conversor.mostrarDetallesMonedas();
                } else if (opcion == 4) {
                    guardarHistorial();
                    break;
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al obtener las tasas de cambio: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void agregarAlHistorial(double cantidad, String divisaOrigen, String divisaDestino, double resultado) {
        String registro = String.format("%.2f %s a %.2f %s", cantidad, divisaOrigen, resultado, divisaDestino);
        historial.add(registro);
    }

    private static void guardarHistorial() {
        try (FileWriter writer = new FileWriter(HISTORIAL_FILE, true)) {
            for (String registro : historial) {
                writer.write(registro + "\n");
            }
            System.out.println("Historial guardado en " + HISTORIAL_FILE);
        } catch (IOException e) {
            System.err.println("Error al guardar el historial: " + e.getMessage());
        }
    }
}
