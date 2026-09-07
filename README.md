# Convertidor Binario a Decimal

Este programa en Java (`convertidor.java`) le pide al usuario un número binario por consola, valida que sea correcto y muestra su equivalente en decimal.

A continuación se explica **línea por línea** el funcionamiento del código.

## Código completo

```java
import java.util.Scanner;

public class convertidor {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese un numero binario: ");
        String binario = sc.nextLine().trim().replace("﻿", "");

        if (!esBinarioValido(binario)) {
            System.out.println("Error: solo se permiten digitos 0 y 1.");
            sc.close();
            return;
        }

        int decimal = binarioADecimal(binario);
        System.out.println("El numero decimal es: " + decimal);

        sc.close();
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
Declara la clase pública `convertidor`. En Java, todo el código debe vivir dentro de una clase, y el nombre del archivo (`convertidor.java`) debe coincidir exactamente con el nombre de la clase pública que contiene (respetando mayúsculas/minúsculas). La llave `{` abre el cuerpo de la clase, que se cierra en la línea 49.

### Línea 5 — `public static void main(String[] args) {`
Este es el **método principal**, el punto de entrada de cualquier programa Java. La JVM (máquina virtual de Java) busca exactamente este método para empezar a ejecutar el programa.
- `public`: puede ser llamado desde fuera de la clase (la JVM necesita acceder a él).
- `static`: pertenece a la clase en sí, no a una instancia/objeto; por eso se puede ejecutar sin crear un `convertidor` primero.
- `void`: no devuelve ningún valor.
- `main`: nombre reservado que la JVM reconoce como punto de arranque.
- `(String[] args)`: parámetro que recibe los argumentos pasados por línea de comandos al ejecutar el programa (en este caso no se usan).

### Línea 6 — `Scanner sc = new Scanner(System.in);`
Crea un objeto `Scanner` llamado `sc`, conectado a `System.in` (la entrada estándar, normalmente el teclado). A partir de aquí, `sc` se usa para leer lo que el usuario escribe en la consola.

### Línea 8 — `System.out.print("Ingrese un numero binario: ");`
Imprime en consola el mensaje `"Ingrese un numero binario: "` **sin salto de línea** (se usa `print`, no `println`), de modo que el cursor del usuario quede justo después del mensaje, en la misma línea, listo para escribir la respuesta.

### Línea 9 — `String binario = sc.nextLine().trim().replace("﻿", "");`
Esta línea lee la entrada del usuario y la limpia en tres pasos encadenados:
1. `sc.nextLine()`: lee toda la línea de texto que el usuario escribió y presionó Enter, devolviéndola como `String`.
2. `.trim()`: elimina los espacios en blanco sobrantes al principio y al final del texto (por ejemplo, si el usuario escribió `" 1010 "`, queda `"1010"`).
3. `.replace("﻿", "")`: elimina un carácter invisible llamado **BOM** (Byte Order Mark, `U+FEFF`), que a veces se cuela en la entrada por temas de codificación de caracteres/consola. Se reemplaza por una cadena vacía, es decir, se borra.

El resultado final se guarda en la variable `binario`.

### Línea 11 — `if (!esBinarioValido(binario)) {`
Llama al método `esBinarioValido` (definido más abajo, línea 24) pasándole el texto ingresado. El operador `!` niega el resultado: si el método devuelve `false` (es decir, el binario **no** es válido), la condición se cumple y se entra al bloque `if`.

### Línea 12 — `System.out.println("Error: solo se permiten digitos 0 y 1.");`
Si la entrada no es un binario válido, se imprime este mensaje de error informando al usuario que solo se aceptan los dígitos `0` y `1`.

### Línea 13 — `sc.close();`
Cierra el `Scanner`, liberando el recurso asociado a la entrada estándar. Es una buena práctica cerrar los recursos que ya no se van a usar, especialmente antes de terminar el programa.

### Línea 14 — `return;`
Termina la ejecución del método `main` de inmediato (sin devolver ningún valor, ya que es `void`). Esto evita que el programa siga intentando convertir un binario inválido.

### Línea 15 — `}`
Cierra el bloque `if` que maneja el caso de entrada inválida.

### Línea 17 — `int decimal = binarioADecimal(binario);`
Si el código llegó hasta aquí, significa que el binario **sí es válido**. Se llama al método `binarioADecimal` (definido en la línea 37), pasándole la cadena `binario`, y el resultado (un número entero) se guarda en la variable `decimal`.

### Línea 18 — `System.out.println("El numero decimal es: " + decimal);`
Imprime en consola el resultado de la conversión, concatenando el texto `"El numero decimal es: "` con el valor numérico de `decimal` (Java convierte automáticamente el `int` a `String` al concatenarlo con `+`).

### Línea 20 — `sc.close();`
Vuelve a cerrar el `Scanner` para liberar el recurso, esta vez en el camino "exitoso" del programa (cuando la conversión sí se realizó).

### Línea 21 — `}`
Cierra el método `main`.

---

### Línea 23 — `// Verifica que la cadena solo contenga 0s y 1s`
Comentario de una línea que explica el propósito del método que viene a continuación.

### Línea 24 — `public static boolean esBinarioValido(String binario) {`
Declara el método `esBinarioValido`, que recibe una cadena (`String binario`) y devuelve un valor booleano (`boolean`): `true` si la cadena es un número binario válido, `false` si no lo es.
- `public`: accesible desde cualquier parte.
- `static`: puede llamarse directamente desde `main` sin crear un objeto `convertidor`.

### Línea 25 — `if (binario.isEmpty()) {`
Comprueba si la cadena está vacía (longitud cero), por ejemplo si el usuario no escribió nada y solo presionó Enter.

### Línea 26 — `return false;`
Si la cadena está vacía, no es un binario válido, así que el método devuelve `false` inmediatamente.

### Línea 27 — `}`
Cierra el bloque `if` de la línea 25.

### Línea 28 — `for (char c : binario.toCharArray()) {`
Inicia un bucle "for-each" que recorre **cada carácter** de la cadena `binario`.
- `binario.toCharArray()`: convierte el `String` en un arreglo (`array`) de caracteres (`char[]`).
- `char c`: en cada vuelta del bucle, `c` toma el valor de un carácter distinto del arreglo, en orden, de izquierda a derecha.

### Línea 29 — `if (c != '0' && c != '1') {`
Por cada carácter `c`, comprueba si **no** es igual a `'0'` **y** tampoco es igual a `'1'`. El operador `&&` es "Y lógico": la condición completa solo es verdadera si ambas comparaciones son verdaderas, es decir, si el carácter es distinto tanto de `'0'` como de `'1'` (o sea, cualquier otro carácter no permitido).

### Línea 30 — `return false;`
Si se encuentra un carácter que no es `0` ni `1`, el método termina de inmediato devolviendo `false`, indicando que el binario no es válido.

### Línea 31 — `}`
Cierra el bloque `if` de la línea 29.

### Línea 32 — `}`
Cierra el bucle `for` de la línea 28. Si el bucle termina sin haber encontrado ningún carácter inválido, significa que todos los caracteres son `0` o `1`.

### Línea 33 — `return true;`
Si se llegó hasta aquí (la cadena no estaba vacía y todos sus caracteres son `0` o `1`), el método devuelve `true`, confirmando que el binario es válido.

### Línea 34 — `}`
Cierra el método `esBinarioValido`.

---

### Línea 36 — `// Convierte un numero binario (String) a su equivalente decimal`
Comentario que explica el propósito del siguiente método.

### Línea 37 — `public static int binarioADecimal(String binario) {`
Declara el método `binarioADecimal`, que recibe una cadena binaria válida y devuelve un número entero (`int`): su equivalente en el sistema decimal.

### Línea 38 — `int decimal = 0;`
Declara e inicializa la variable `decimal` en `0`. Esta variable acumulará el resultado final de la conversión a medida que se procesa cada dígito binario.

### Línea 39 — `int potencia = 0;`
Declara e inicializa la variable `potencia` en `0`. Representa el exponente de la potencia de 2 correspondiente a cada dígito binario, empezando desde el dígito menos significativo (el de más a la derecha, que corresponde a `2⁰`).

### Línea 41 — `for (int i = binario.length() - 1; i >= 0; i--) {`
Inicia un bucle `for` que recorre la cadena **de derecha a izquierda**:
- `int i = binario.length() - 1`: `i` empieza en el índice del último carácter de la cadena (recordando que los índices en Java empiezan en 0, por eso se resta 1 a la longitud).
- `i >= 0`: el bucle continúa mientras `i` sea mayor o igual a cero (es decir, hasta llegar al primer carácter, índice 0).
- `i--`: en cada vuelta, `i` disminuye en 1, moviéndose hacia la izquierda.

Se recorre de derecha a izquierda porque el dígito de más a la derecha en un número binario representa las unidades (`2⁰`), y las potencias de 2 van aumentando a medida que se avanza hacia la izquierda.

### Línea 42 — `int digito = binario.charAt(i) - '0';`
Obtiene el carácter en la posición `i` de la cadena (`binario.charAt(i)`), que es `'0'` o `'1'`. Al restarle el carácter `'0'`, se aprovecha que en la tabla de caracteres (ASCII/Unicode) los dígitos son consecutivos, por lo que esta resta convierte el carácter a su valor numérico real: `'0' - '0' = 0` y `'1' - '0' = 1`. El resultado se guarda como un `int` en la variable `digito`.

### Línea 43 — `decimal += digito * (int) Math.pow(2, potencia);`
Actualiza el valor acumulado de `decimal`:
- `Math.pow(2, potencia)`: calcula 2 elevado a la potencia actual (por ejemplo, si `potencia` es 3, calcula `2³ = 8`). Esta función devuelve un `double` (número decimal).
- `(int)`: convierte (hace un *cast*) ese resultado `double` a un número entero `int`, descartando la parte decimal (que en este caso siempre es `.0` porque 2 elevado a un entero da un resultado exacto).
- `digito * ...`: multiplica ese valor por el dígito actual (`0` o `1`). Si el dígito es `0`, la potencia no aporta nada a la suma; si es `1`, se suma el valor completo de esa potencia de 2.
- `decimal += ...`: es una forma abreviada de `decimal = decimal + ...`, es decir, suma el resultado al total acumulado.

### Línea 44 — `potencia++;`
Incrementa la variable `potencia` en 1 (equivalente a `potencia = potencia + 1`), preparando el exponente correcto para el siguiente dígito (que, al recorrer de derecha a izquierda, representa la siguiente potencia de 2 más alta).

### Línea 45 — `}`
Cierra el bucle `for` de la línea 41.

### Línea 47 — `return decimal;`
Una vez procesados todos los dígitos del número binario, el método devuelve el valor final acumulado en `decimal`, que es el equivalente decimal del binario original.

### Línea 48 — `}`
Cierra el método `binarioADecimal`.

### Línea 49 — `}`
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
