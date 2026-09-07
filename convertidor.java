import java.util.Scanner;

public class convertidor {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Ingrese un numero binario: ");
            String binario = sc.nextLine().trim().replace(" ", "");

            if (!esBinarioValido(binario)) {
                System.out.println("Error: solo se permiten digitos 0 y 1.");
                return;
            }

            int decimal = binarioADecimal(binario);
            System.out.println("El numero decimal es: " + decimal);
        }
    }

    // Verifica que la cadena solo contenga 0s y 1s
    public static boolean esBinarioValido(String binario) {
        if (binario.isEmpty()) {
            return false;
        }
        for (char c : binario.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    // Convierte un numero binario (String) a su equivalente decimal
    public static int binarioADecimal(String binario) {
        int decimal = 0;
        int potencia = 0;

        for (int i = binario.length() - 1; i >= 0; i--) {
            int digito = binario.charAt(i) - '0';
            decimal += digito * (int) Math.pow(2, potencia);
            potencia++;
        }

        return decimal;
    }
}
