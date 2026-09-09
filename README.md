# Convertidor de Bases Numericas

Este programa en Java (`convertidor.java`) muestra un **menu** con las 12 conversiones posibles entre los sistemas numericos Decimal, Binario, Octal y Hexadecimal. El usuario elige una opcion, ingresa un numero en la base de origen, el programa lo valida y muestra su equivalente en la base de destino.

## Conversiones disponibles

| Opcion | Conversion |
|---|---|
| 1 | Decimal a Binario |
| 2 | Decimal a Octal |
| 3 | Decimal a Hexadecimal |
| 4 | Binario a Decimal |
| 5 | Binario a Octal |
| 6 | Binario a Hexadecimal |
| 7 | Octal a Decimal |
| 8 | Octal a Binario |
| 9 | Octal a Hexadecimal |
| 10 | Hexadecimal a Decimal |
| 11 | Hexadecimal a Binario |
| 12 | Hexadecimal a Octal |

Solo se aceptan **numeros enteros no negativos** (sin signo ni parte decimal).

## Como funciona internamente

En lugar de escribir 12 funciones distintas (una por cada par de bases), el programa convierte **siempre pasando por el sistema decimal** como paso intermedio:

```
numero en base de origen  --->  decimal (int)  --->  numero en base de destino (String)
```

Por ejemplo, para "Octal a Hexadecimal" primero se convierte el octal a decimal, y luego ese decimal se convierte a hexadecimal. Esto evita repetir la logica de conversion 12 veces: solo existen dos funciones genericas, `aDecimal` (de cualquier base a decimal) y `decimalABase` (de decimal a cualquier base), que reciben la base como parametro.

## Codigo completo

```java
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
```

## Explicacion detallada

### Las tablas `BASE_ORIGEN` y `BASE_DESTINO`

```java
private static final int[] BASE_ORIGEN  = {10, 10, 10, 2, 2, 2, 8, 8, 8, 16, 16, 16};
private static final int[] BASE_DESTINO = { 2,  8, 16, 10, 8, 16, 10, 2, 16, 10, 2, 8};
```

Son dos arreglos paralelos de 12 elementos, uno por cada opcion del menu (indice `0` = opcion 1, indice `1` = opcion 2, etc.). Por ejemplo, la opcion 5 ("Binario a Octal") esta en el indice `4`: `BASE_ORIGEN[4]` vale `2` (binario) y `BASE_DESTINO[4]` vale `8` (octal). Guardar esta correspondencia en dos arreglos evita escribir un `switch` gigante con 12 casos repetidos.

### `main`: leer la opcion del menu

1. `mostrarMenu()` imprime las 12 opciones agrupadas por sistema de origen, tal como las pidio el usuario.
2. `sc.nextLine()` lee la opcion elegida como texto.
3. `Integer.parseInt(opcionTexto)` intenta convertir ese texto a un numero entero. Si el usuario escribe algo que no es un numero (por ejemplo `"hola"`), se lanza una excepcion `NumberFormatException`, que se captura en el bloque `catch` para mostrar un mensaje de error controlado en vez de que el programa se caiga con un *stack trace*.
4. Si `opcion` esta fuera del rango 1-12, se informa el error y se termina con `return`.

### `main`: leer y validar el numero

1. `baseOrigen` y `baseDestino` se obtienen de las tablas usando `opcion - 1` (porque los arreglos empiezan en el indice 0, pero el menu empieza en la opcion 1).
2. Se pide el numero indicando explicitamente la base esperada (`nombreBase(baseOrigen)`), para que el usuario sepa que se espera, por ejemplo, un numero *Hexadecimal*.
3. `.trim().replace(" ", "").toUpperCase()`: limpia espacios al inicio/fin y en medio (igual que en la version anterior del programa), y ademas pasa el texto a mayusculas, para aceptar tanto `"ff"` como `"FF"` como numero hexadecimal valido.
4. `esValidoEnBase(valor, baseOrigen)` verifica que todos los caracteres sean digitos permitidos en esa base. Si no lo son, se muestra un error y el programa termina sin intentar convertir.
5. `aDecimal(valor, baseOrigen)` convierte el texto validado a un entero decimal.
6. `decimalABase(decimal, baseDestino)` convierte ese entero decimal a su representacion en la base pedida.
7. Se imprime el resultado final.

### `nombreBase`: traducir el numero de base a un nombre legible

Un simple `switch` que traduce `2 -> "Binario"`, `8 -> "Octal"`, `10 -> "Decimal"`, `16 -> "Hexadecimal"`. Se usa tanto para los mensajes de entrada como para el mensaje de resultado.

### `valorDigito`: de caracter a valor numerico

Esta funcion generaliza lo que en la version anterior era `digito = binario.charAt(i) - '0'` (que solo servia para `0` y `1`):

- Si el caracter es un digito `'0'`-`'9'`, su valor es `c - '0'` (por ejemplo, `'7' - '0' = 7`).
- Si el caracter es una letra `'A'`-`'F'` (necesaria para hexadecimal), su valor es `10 + (c - 'A')` (por ejemplo, `'A'` vale `10`, `'F'` vale `15`).
- Cualquier otro caracter no es un digito valido en ninguna base y devuelve `-1`.
- Finalmente, se comprueba que ese valor sea menor que la base indicada (`valor < base`). Esto es lo que rechaza, por ejemplo, el digito `'8'` en octal (base 8): aunque `'8'` es un digito valido en general, `8` no es menor que `8`, asi que no es valido *en octal*.

### `caracterDigito`: de valor numerico a caracter

Es la operacion inversa a `valorDigito`: dado un valor entre `0` y `15`, devuelve el caracter que lo representa (`0`-`9` o `A`-`F`). Se usa al construir el resultado en `decimalABase`.

### `esValidoEnBase`: validar la cadena completa

Generaliza el antiguo `esBinarioValido`: rechaza cadenas vacias y recorre cada caracter comprobando con `valorDigito` que sea un digito valido para la base recibida. Si `valorDigito` devuelve `-1` para algun caracter, la cadena completa no es valida.

### `aDecimal`: de cualquier base a decimal

Generaliza el antiguo `binarioADecimal`, que solo funcionaba en base 2. La logica es la misma pero usando `base` como parametro en vez del `2` fijo:

- Se recorre la cadena de derecha a izquierda (el digito mas a la derecha es el de menor peso, `base⁰`).
- Cada digito se multiplica por `base` elevado a la potencia correspondiente (`Math.pow(base, potencia)`) y se acumula en `decimal`.
- `potencia` aumenta en cada vuelta, a medida que se avanza hacia la izquierda (posiciones de mayor peso).

Por ejemplo, para el octal `"17"` (base 8): el digito `'7'` (posicion 0) aporta `7 * 8⁰ = 7`, y el digito `'1'` (posicion 1) aporta `1 * 8¹ = 8`; total `15`.

### `decimalABase`: de decimal a cualquier base

Esta es la funcion nueva que faltaba en la version anterior (que solo iba *hacia* decimal, nunca *desde* decimal). Usa el metodo clasico de **divisiones sucesivas**:

1. Si el numero es `0`, el resultado es directamente `"0"` (si no, el bucle de abajo no se ejecutaria nunca y devolveria una cadena vacia).
2. Mientras `decimal` sea mayor que `0`:
   - `resto = decimal % base` obtiene el digito menos significativo que falta por escribir (el resto de dividir entre la base).
   - `resultado.insert(0, caracterDigito(resto))` inserta ese digito **al principio** del resultado (porque los restos se van generando del menos significativo al mas significativo, en orden inverso al que se necesitan para leerlos).
   - `decimal /= base` (division entera) descarta el digito ya procesado y continua con el resto del numero.
3. Al terminar el bucle, `resultado` contiene la representacion completa en la base pedida.

Por ejemplo, para convertir el decimal `26` a binario (base 2): `26 % 2 = 0`, `26 / 2 = 13` -> `13 % 2 = 1`, `13 / 2 = 6` -> `6 % 2 = 0`, `6 / 2 = 3` -> `3 % 2 = 1`, `3 / 2 = 1` -> `1 % 2 = 1`, `1 / 2 = 0`. Los restos, insertados siempre al inicio, forman `"11010"`.

## Ejemplos de ejecucion

```
===== Convertidor de bases numericas =====
...
Elija una opcion (1-12): 1
Ingrese un numero Decimal: 26
El numero en Binario es: 11010
```

```
===== Convertidor de bases numericas =====
...
Elija una opcion (1-12): 10
Ingrese un numero Hexadecimal: ff
El numero en Decimal es: 255
```

```
===== Convertidor de bases numericas =====
...
Elija una opcion (1-12): 8
Ingrese un numero Octal: 17
El numero en Binario es: 1111
```

## Manejo de errores

- **Opcion invalida**: si se ingresa algo que no es un numero, o un numero fuera del rango 1-12, se muestra un error y el programa termina sin pedir el numero a convertir.
- **Numero invalido para la base elegida**: si el numero ingresado contiene caracteres que no son digitos validos en la base de origen (por ejemplo, `"29"` como binario, o `"G1"` como hexadecimal), se muestra un mensaje indicando que no es un numero valido en esa base, y el programa termina sin intentar la conversion.
