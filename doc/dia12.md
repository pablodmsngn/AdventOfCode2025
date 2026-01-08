### Parte 1: Modelado de Dominio y Geometría Discreta

En la primera fase, el problema consistía en determinar si un conjunto de formas geométricas (regalos/poliminós) cabía perfectamente en una cuadrícula (región). El enfoque inicial fue puramente orientado a objetos, modelando la realidad física de las piezas.

**¿Qué hicimos?**

1. **Modelado Rico:** Creamos records inmutables `Present` y `Coordinate` para representar la geometría, y una clase `Region` para el tablero.
2. **Backtracking Recursivo:** Implementamos un algoritmo estándar de "prueba y error" recursivo: colocar una pieza, intentar colocar la siguiente, y si falla, retroceder (backtrack).
3. **Normalización Geométrica:** Implementamos lógica para generar las 8 variaciones (rotaciones y espejos) de cada pieza y normalizarlas al origen `(0,0)` para simplificar su colocación.

**Principios y Fundamentos aplicados:**

* **Abstracción del Dominio:**
  Las clases `Present` y `Region` encapsulan la complejidad geométrica (rotar, validar límites). El algoritmo de resolución no necesita saber matemáticas de coordenadas, solo pregunta `canFit()`.
* **Inmutabilidad:**
  Al usar `record` para las piezas, garantizamos que las rotaciones generen nuevas instancias en lugar de modificar el estado interno, previniendo efectos secundarios difíciles de rastrear en la recursividad.

### Parte 2: Optimización de Bajo Nivel y Máscaras de Bits (Bitmasks)

El verdadero reto surgió con el rendimiento: el enfoque orientado a objetos era demasiado lento ("se quedaba colgado") debido a la explosión combinatoria y la creación masiva de objetos en el Heap. La solución requirió bajar el nivel de abstracción para acercarnos al metal.

**¿Qué hicimos?**

1. **Transformación a Bits (Bitwise Operations):**
   Sustituimos la matriz de booleanos `boolean[][]` y los objetos `Coordinate` por un único primitivo `long` (64 bits). Cada celda del tablero se convirtió en un bit (0 o 1).
2. **Detección de Colisiones en 1 Ciclo de CPU:**
   En lugar de iterar por las coordenadas de una pieza para ver si choca, usamos la operación **AND** (`tablero & mascara`). Si el resultado es distinto de 0, hay colisión. Esto reduce la complejidad de  (tamaño de la pieza) a .
3. **Romper la Simetría (Symmetry Breaking):**
   Implementamos una poda lógica crítica: si tenemos 3 regalos idénticos, el algoritmo ingenuo intenta colocarlos en todas las permutaciones posibles (, , etc.). Nosotros forzamos un orden estricto, eliminando ramas redundantes y dividiendo el tiempo de ejecución por .
4. **Caché de Pre-cálculo:**
   Calculamos todas las máscaras posibles (posiciones válidas) de cada pieza **una sola vez** al inicio, en lugar de recalcularlas en cada paso de la recursión.

**Principios y Fundamentos aplicados:**

* **Eficiencia sobre Abstracción (en el Hot Path):**
  Reconocimos que en el bucle crítico de un algoritmo exponencial, la orientación a objetos es costosa. Sacrificamos la legibilidad de `Coordinate` por la velocidad cruda de `long` y operadores bitwise (`<<`, `|`, `&`), reduciendo el tiempo de minutos a milisegundos.
* **Fail-Fast (Fallo Rápido):**
  Ordenamos las piezas de mayor a menor área. Es estadísticamente más probable detectar un callejón sin salida intentando colocar una pieza grande al principio que una pequeña al final, podando el árbol de decisión mucho antes.
* **Gestión de Memoria (Stack vs Heap):**
  Al usar primitivos (`long`), eliminamos la presión sobre el **Garbage Collector**. Todo el estado del tablero viaja en la pila (Stack) o registros de la CPU, evitando los `OutOfMemoryError` típicos de la recursión profunda con objetos.