
### Parte 1: Búsqueda en Espacio de Estados (BFS) e Inmutabilidad

En la primera fase, el problema consistía en encontrar la secuencia mínima de botones para configurar un patrón de luces. Dado que el orden de los botones no importa (conmutatividad) y pulsarlos dos veces anula la acción (comportamiento XOR), el problema se modeló como la búsqueda del camino más corto en un grafo de estados finitos.

**¿Qué hicimos?**

1. Modelamos el hardware con records inmutables: `Indicador` (estado de luces/voltajes) y `Boton` (máscara de cambio), asegurando que cada pulsación genere un nuevo estado sin efectos secundarios.
2. Implementamos un algoritmo **BFS (Búsqueda en Anchura)** en `Maquina`: exploramos nivel por nivel las combinaciones de botones, garantizando que la primera solución encontrada sea la óptima (mínimo número de pulsaciones).
3. Utilizamos un `Set` de estados visitados para podar el árbol de búsqueda y evitar ciclos infinitos o cálculos redundantes.
4. Creamos `ControladorFabrica` como fachada para orquestar la ejecución sobre múltiples máquinas.

**Principios y Fundamentos aplicados:**

* BFS:
  Transformamos un problema de configuración física en un problema de "camino más corto" en un grafo no ponderado. Esto nos permite asegurar matemáticamente la optimalidad de la solución.
* **Inmutabilidad:**
  El uso de `record` para `Indicador` es crucial. Al aplicar un botón, no mutamos el objeto actual, sino que devolvemos uno nuevo (`return new Indicador(...)`). Esto simplifica enormemente el rastreo de estados en la cola del BFS y evita errores de concurrencia o estado compartido.
* **Principio de Responsabilidad Única (SRP):**
  `Boton` solo sabe qué índices afecta; `Indicador` solo sabe mantener datos y compararse; y `Maquina` encapsula la complejidad algorítmica de la búsqueda.

### Parte 2: Recursividad, Memoización y Optimización de Bits

En la segunda fase, el problema cambió de estados binarios (luces) a estados enteros (voltajes). El espacio de búsqueda se volvió infinito para un BFS tradicional. La solución requirió un enfoque de "Divide y Vencerás" basado en bits y una optimización agresiva de rendimiento.

**¿Qué hicimos?**

1. Cambiamos el algoritmo a **Recursividad con Memoización**: Descompusimos el problema de los voltajes en una serie de problemas de paridad (bits). Resolvemos la paridad usando la lógica de la Parte 1, restamos el efecto, dividimos por 2 y recursamos.
2. Implementamos **Caché (Memoización)**: Utilizamos un `Map<Indicador, Integer>` para almacenar los resultados de objetivos de voltaje ya resueltos, evitando recalcular ramas enteras del árbol recursivo.
3. **Optimización con Máscaras de Bits (Bitmasks):** Identificamos que el uso de `Set<Set<Boton>>` era demasiado lento y consumía mucha memoria. Reemplazamos los conjuntos de botones por un solo primitivo `long` (donde cada bit representa si un botón está pulsado o no). Esto redujo el tiempo de ejecución de minutos a milisegundos y el consumo de memoria drásticamente.

**Principios y Fundamentos aplicados:**

* **Divide y Vencerás (Descomposición Binaria):**
  Convertimos un problema complejo de enteros en una secuencia de problemas simples de booleanos (luces). Al resolver bit a bit (paridad), reducimos la complejidad exponencial a logarítmica respecto al valor del voltaje.
* **Programación Dinámica (Memoización):**
  Almacenar los resultados de sub-problemas (`cache.put(objetivo, resultado)`) transforma una complejidad exponencial en lineal respecto al número de sub-estados únicos, un patrón clásico para optimizar recursividad.
* **Optimización de Bajo Nivel (Primitive Obsession justificada):**
  Aunque la programación orientada a objetos prefiere estructuras ricas, en el "hot path" (bucle interior de un algoritmo recursivo), el uso de primitivos (`long`) y operaciones a nivel de bit (`mask | (1L << i)`) es órdenes de magnitud más rápido que la manipulación de objetos `Set` y `Stream`, demostrando cuándo es correcto sacrificar abstracción por rendimiento.