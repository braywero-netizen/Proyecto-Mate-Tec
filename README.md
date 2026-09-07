# Convertidor Binario a Decimal

Este programa en Java (`convertidor.java`) le pide al usuario un número binario por consola, valida que sea correcto y muestra su equivalente en decimal.

A continuación se explica **línea por línea** el funcionamiento del código.

## Código completo

```java
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
```

## Explicación detallada, línea por línea

### Línea 1 — `import java.util.Scanner;`
Importa la clase `Scanner`, que forma parte del paquete `java.util` de la biblioteca estándar de Java. `Scanner` es la herramienta que se usa para leer datos que el usuario escribe por teclado (entrada estándar, `System.in`). Sin este `import`, no se podría usar `Scanner` más adelante en el código.

### Línea 3 — `public class convertidor {`
Declara la clase pública `convertidor`. En Java, todo el código debe vivir dentro de una clase, y el nombre del archivo (`convertidor.java`) debe coincidir exactamente con el nombre de la clase pública que contiene (respetando mayúsculas/minúsculas). La llave `{` abre el cuerpo de la clase, que se cierra en la línea 46.

### Línea 5 — `public static void main(String[] args) {`
Este es el **método principal**, el punto de entrada de cualquier programa Java. La JVM (máquina virtual de Java) busca exactamente este método para empezar a ejecutar el programa.
- `public`: puede ser llamado desde fuera de la clase (la JVM necesita acceder a él).
- `static`: pertenece a la clase en sí, no a una instancia/objeto; por eso se puede ejecutar sin crear un `convertidor` primero.
- `void`: no devuelve ningún valor.
- `main`: nombre reservado que la JVM reconoce como punto de arranque.
- `(String[] args)`: parámetro que recibe los argumentos pasados por línea de comandos al ejecutar el programa (en este caso no se usan).

### Línea 6 — `try (Scanner sc = new Scanner(System.in)) {`
Esto es un **try-with-resources**: crea el objeto `Scanner` llamado `sc`, conectado a `System.in` (la entrada estándar, normalmente el teclado), pero declarándolo dentro del paréntesis del `try`. Cualquier objeto declarado ahí debe implementar `AutoCloseable` (como es el caso de `Scanner`), y Java garantiza que se llamará automáticamente a `sc.close()` al salir del bloque `try`, sin importar si se sale de forma normal (por ejemplo con `return`) o por una excepción. Esto evita tener que escribir `sc.close()` manualmente en cada punto de salida del método, y elimina la advertencia de "resource leak" que dan los editores cuando un recurso no se cierra en todos los caminos posibles del código.

### Línea 7 — `System.out.print("Ingrese un numero binario: ");`
Imprime en consola el mensaje `"Ingrese un numero binario: "` **sin salto de línea** (se usa `print`, no `println`), de modo que el cursor del usuario quede justo después del mensaje, en la misma línea, listo para escribir la respuesta.

### Línea 8 — `String binario = sc.nextLine().trim().replace(" ", "");`
Esta línea lee la entrada del usuario y la limpia en tres pasos encadenados:
1. `sc.nextLine()`: lee toda la línea de texto que el usuario escribió y presionó Enter, devolviéndola como `String`.
2. `.trim()`: elimina los espacios en blanco sobrantes al principio y al final del texto (por ejemplo, si el usuario escribió `" 1010 "`, queda `"1010"`).
3. `.replace(" ", "")`: elimina también cualquier espacio en blanco que haya quedado **en medio** del texto (por ejemplo, si el usuario escribió `"10 10"`, queda `"1010"`), ya que `.trim()` solo limpia los extremos.

El resultado final se guarda en la variable `binario`.

### Línea 10 — `if (!esBinarioValido(binario)) {`
Llama al método `esBinarioValido` (definido más abajo, línea 21) pasándole el texto ingresado. El operador `!` niega el resultado: si el método devuelve `false` (es decir, el binario **no** es válido), la condición se cumple y se entra al bloque `if`.

### Línea 11 — `System.out.println("Error: solo se permiten digitos 0 y 1.");`
Si la entrada no es un binario válido, se imprime este mensaje de error informando al usuario que solo se aceptan los dígitos `0` y `1`.

### Línea 12 — `return;`
Termina la ejecución del método `main` de inmediato (sin devolver ningún valor, ya que es `void`). Esto evita que el programa siga intentando convertir un binario inválido. Al ser un `try-with-resources`, salir con `return` desde aquí también cierra automáticamente el `Scanner` antes de que el método termine.

### Línea 13 — `}`
Cierra el bloque `if` que maneja el caso de entrada inválida.

### Línea 15 — `int decimal = binarioADecimal(binario);`
Si el código llegó hasta aquí, significa que el binario **sí es válido**. Se llama al método `binarioADecimal` (definido en la línea 34), pasándole la cadena `binario`, y el resultado (un número entero) se guarda en la variable `decimal`.

### Línea 16 — `System.out.println("El numero decimal es: " + decimal);`
Imprime en consola el resultado de la conversión, concatenando el texto `"El numero decimal es: "` con el valor numérico de `decimal` (Java convierte automáticamente el `int` a `String` al concatenarlo con `+`).

### Línea 17 — `}`
Cierra el bloque `try-with-resources`. Al llegar aquí, Java cierra automáticamente el `Scanner` `sc` (llamando internamente a `sc.close()`), tanto si el bloque terminó de forma normal como si se salió antes por el `return` de la línea 12.

### Línea 18 — `}`
Cierra el método `main`.

---

### Línea 20 — `// Verifica que la cadena solo contenga 0s y 1s`
Comentario de una línea que explica el propósito del método que viene a continuación.

### Línea 21 — `public static boolean esBinarioValido(String binario) {`
Declara el método `esBinarioValido`, que recibe una cadena (`String binario`) y devuelve un valor booleano (`boolean`): `true` si la cadena es un número binario válido, `false` si no lo es.
- `public`: accesible desde cualquier parte.
- `static`: puede llamarse directamente desde `main` sin crear un objeto `convertidor`.

### Línea 22 — `if (binario.isEmpty()) {`
Comprueba si la cadena está vacía (longitud cero), por ejemplo si el usuario no escribió nada y solo presionó Enter.

### Línea 23 — `return false;`
Si la cadena está vacía, no es un binario válido, así que el método devuelve `false` inmediatamente.

### Línea 24 — `}`
Cierra el bloque `if` de la línea 22.

### Línea 25 — `for (char c : binario.toCharArray()) {`
Inicia un bucle "for-each" que recorre **cada carácter** de la cadena `binario`.
- `binario.toCharArray()`: convierte el `String` en un arreglo (`array`) de caracteres (`char[]`).
- `char c`: en cada vuelta del bucle, `c` toma el valor de un carácter distinto del arreglo, en orden, de izquierda a derecha.

### Línea 26 — `if (c != '0' && c != '1') {`
Por cada carácter `c`, comprueba si **no** es igual a `'0'` **y** tampoco es igual a `'1'`. El operador `&&` es "Y lógico": la condición completa solo es verdadera si ambas comparaciones son verdaderas, es decir, si el carácter es distinto tanto de `'0'` como de `'1'` (o sea, cualquier otro carácter no permitido).

### Línea 27 — `return false;`
Si se encuentra un carácter que no es `0` ni `1`, el método termina de inmediato devolviendo `false`, indicando que el binario no es válido.

### Línea 28 — `}`
Cierra el bloque `if` de la línea 26.

### Línea 29 — `}`
Cierra el bucle `for` de la línea 25. Si el bucle termina sin haber encontrado ningún carácter inválido, significa que todos los caracteres son `0` o `1`.

### Línea 30 — `return true;`
Si se llegó hasta aquí (la cadena no estaba vacía y todos sus caracteres son `0` o `1`), el método devuelve `true`, confirmando que el binario es válido.

### Línea 31 — `}`
Cierra el método `esBinarioValido`.

---

### Línea 33 — `// Convierte un numero binario (String) a su equivalente decimal`
Comentario que explica el propósito del siguiente método.

### Línea 34 — `public static int binarioADecimal(String binario) {`
Declara el método `binarioADecimal`, que recibe una cadena binaria válida y devuelve un número entero (`int`): su equivalente en el sistema decimal.

### Línea 35 — `int decimal = 0;`
Declara e inicializa la variable `decimal` en `0`. Esta variable acumulará el resultado final de la conversión a medida que se procesa cada dígito binario.

### Línea 36 — `int potencia = 0;`
Declara e inicializa la variable `potencia` en `0`. Representa el exponente de la potencia de 2 correspondiente a cada dígito binario, empezando desde el dígito menos significativo (el de más a la derecha, que corresponde a `2⁰`).

### Línea 38 — `for (int i = binario.length() - 1; i >= 0; i--) {`
Inicia un bucle `for` que recorre la cadena **de derecha a izquierda**:
- `int i = binario.length() - 1`: `i` empieza en el índice del último carácter de la cadena (recordando que los índices en Java empiezan en 0, por eso se resta 1 a la longitud).
- `i >= 0`: el bucle continúa mientras `i` sea mayor o igual a cero (es decir, hasta llegar al primer carácter, índice 0).
- `i--`: en cada vuelta, `i` disminuye en 1, moviéndose hacia la izquierda.

Se recorre de derecha a izquierda porque el dígito de más a la derecha en un número binario representa las unidades (`2⁰`), y las potencias de 2 van aumentando a medida que se avanza hacia la izquierda.

### Línea 39 — `int digito = binario.charAt(i) - '0';`
Obtiene el carácter en la posición `i` de la cadena (`binario.charAt(i)`), que es `'0'` o `'1'`. Al restarle el carácter `'0'`, se aprovecha que en la tabla de caracteres (ASCII/Unicode) los dígitos son consecutivos, por lo que esta resta convierte el carácter a su valor numérico real: `'0' - '0' = 0` y `'1' - '0' = 1`. El resultado se guarda como un `int` en la variable `digito`.

### Línea 40 — `decimal += digito * (int) Math.pow(2, potencia);`
Actualiza el valor acumulado de `decimal`:
- `Math.pow(2, potencia)`: calcula 2 elevado a la potencia actual (por ejemplo, si `potencia` es 3, calcula `2³ = 8`). Esta función devuelve un `double` (número decimal).
- `(int)`: convierte (hace un *cast*) ese resultado `double` a un número entero `int`, descartando la parte decimal (que en este caso siempre es `.0` porque 2 elevado a un entero da un resultado exacto).
- `digito * ...`: multiplica ese valor por el dígito actual (`0` o `1`). Si el dígito es `0`, la potencia no aporta nada a la suma; si es `1`, se suma el valor completo de esa potencia de 2.
- `decimal += ...`: es una forma abreviada de `decimal = decimal + ...`, es decir, suma el resultado al total acumulado.

### Línea 41 — `potencia++;`
Incrementa la variable `potencia` en 1 (equivalente a `potencia = potencia + 1`), preparando el exponente correcto para el siguiente dígito (que, al recorrer de derecha a izquierda, representa la siguiente potencia de 2 más alta).

### Línea 42 — `}`
Cierra el bucle `for` de la línea 38.

### Línea 44 — `return decimal;`
Una vez procesados todos los dígitos del número binario, el método devuelve el valor final acumulado en `decimal`, que es el equivalente decimal del binario original.

### Línea 45 — `}`
Cierra el método `binarioADecimal`.

### Línea 46 — `}`
Cierra la clase `convertidor`.

## Ejemplo de ejecución

```
Ingrese un numero binario: 1010
El numero decimal es: 10
```

Explicación del cálculo interno para `1010`:
| Posición (derecha a izquierda) | Dígito | Potencia de 2 | Aporte |
|---|---|---|---|
| 0 | 0 | 2⁰ = 1 | 0 |
| 1 | 1 | 2¹ = 2 | 2 |
| 2 | 0 | 2² = 4 | 0 |
| 3 | 1 | 2³ = 8 | 8 |

Suma total: `0 + 2 + 0 + 8 = 10`, que es el resultado mostrado.

## Manejo de errores

Si el usuario ingresa algo que no sea únicamente `0` y `1` (por ejemplo `"102"`, `"abc"` o una cadena vacía), el método `esBinarioValido` detecta el problema y el programa muestra:

```
Error: solo se permiten digitos 0 y 1.
```

y termina sin intentar realizar la conversión.
