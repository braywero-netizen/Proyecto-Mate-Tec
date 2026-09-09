import java.util.Scanner;

public class convertidor {

    // Bases de origen y destino para cada opcion del menu, en el mismo orden en que se muestran
    private static final int[] BASE_ORIGEN  = {10, 10, 10, 2, 2, 2, 8, 8, 8, 16, 16, 16};
    private static final int[] BASE_DESTINO = { 2,  8, 16, 10, 8, 16, 10, 2, 16, 10, 2, 8};

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            mostrarMenu();
            System.out.print("Elija una opcion (1-12): ");
            String opcionTexto = sc.nextLine().trim();

            int opcion;
            try {
                opcion = Integer.parseInt(opcionTexto);
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero de opcion valido.");
                return;
            }

            if (opcion < 1 || opcion > 12) {
                System.out.println("Error: la opcion debe estar entre 1 y 12.");
                return;
            }

            int baseOrigen = BASE_ORIGEN[opcion - 1];
            int baseDestino = BASE_DESTINO[opcion - 1];

            System.out.print("Ingrese un numero " + nombreBase(baseOrigen) + ": ");
            String valor = sc.nextLine().trim().replace(" ", "").toUpperCase();

            if (!esValidoEnBase(valor, baseOrigen)) {
                System.out.println("Error: '" + valor + "' no es un numero " + nombreBase(baseOrigen)
                        + " valido (numero entero no negativo).");
                return;
            }

            int decimal = aDecimal(valor, baseOrigen);
            String resultado = decimalABase(decimal, baseDestino);

            System.out.println("El numero en " + nombreBase(baseDestino) + " es: " + resultado);
        }
    }

    // Muestra las 12 conversiones disponibles
    public static void mostrarMenu() {
        System.out.println("===== Convertidor de bases numericas =====");
        System.out.println("De Decimal a:");
        System.out.println("  1. Decimal a Binario");
        System.out.println("  2. Decimal a Octal");
        System.out.println("  3. Decimal a Hexadecimal");
        System.out.println("De Binario a:");
        System.out.println("  4. Binario a Decimal");
        System.out.println("  5. Binario a Octal");
        System.out.println("  6. Binario a Hexadecimal");
        System.out.println("De Octal a:");
        System.out.println("  7. Octal a Decimal");
        System.out.println("  8. Octal a Binario");
        System.out.println("  9. Octal a Hexadecimal");
        System.out.println("De Hexadecimal a:");
        System.out.println("  10. Hexadecimal a Decimal");
        System.out.println("  11. Hexadecimal a Binario");
        System.out.println("  12. Hexadecimal a Octal");
    }

    // Nombre legible de una base numerica
    public static String nombreBase(int base) {
        switch (base) {
            case 2:  return "Binario";
            case 8:  return "Octal";
            case 10: return "Decimal";
            case 16: return "Hexadecimal";
            default: return "Base " + base;
        }
    }

    // Valor numerico (0-15) de un digito dentro de una base, o -1 si no es valido en esa base
    public static int valorDigito(char c, int base) {
        int valor;
        if (c >= '0' && c <= '9') {
            valor = c - '0';
        } else if (c >= 'A' && c <= 'F') {
            valor = 10 + (c - 'A');
        } else {
            return -1;
        }
        return (valor < base) ? valor : -1;
    }

    // Caracter correspondiente a un valor numerico (0-15) dentro de una base
    public static char caracterDigito(int valor) {
        if (valor < 10) {
            return (char) ('0' + valor);
        }
        return (char) ('A' + (valor - 10));
    }

    // Verifica que la cadena solo contenga digitos validos para la base indicada
    public static boolean esValidoEnBase(String valor, int base) {
        if (valor.isEmpty()) {
            return false;
        }
        for (char c : valor.toCharArray()) {
            if (valorDigito(c, base) == -1) {
                return false;
            }
        }
        return true;
    }

    // Convierte una cadena valida en la base indicada a su equivalente decimal (int)
    public static int aDecimal(String valor, int base) {
        int decimal = 0;
        int potencia = 0;

        for (int i = valor.length() - 1; i >= 0; i--) {
            int digito = valorDigito(valor.charAt(i), base);
            decimal += digito * (int) Math.pow(base, potencia);
            potencia++;
        }

        return decimal;
    }

    // Convierte un numero decimal a su representacion (String) en la base indicada
    public static String decimalABase(int decimal, int base) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder resultado = new StringBuilder();
        while (decimal > 0) {
            int resto = decimal % base;
            resultado.insert(0, caracterDigito(resto));
            decimal /= base;
        }

        return resultado.toString();
    }
}
