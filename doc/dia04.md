
### Parte 1: Modelado del Dominio y Lógica Declarativa

En la primera fase, el objetivo fue representar un mapa bidimensional de un almacén y aplicar una regla de negocio espacial: identificar qué rollos de papel son accesibles basándose en la densidad de sus vecinos (menos de 4 obstáculos alrededor). La prioridad fue crear un modelo robusto que evitara los típicos errores de desbordamiento de índices en los bordes del mapa.

**¿Qué hicimos?**

1. Creamos **Objetos de Valor** (`Coordenada`, `ContenidoCasilla`) para dar semántica al problema, evitando el uso de tipos primitivos sueltos (`int`, `char`).
2. Implementamos `CuadriculaAlmacen` con una **Estrategia de Relleno (Padding)**: añadimos automáticamente un borde de seguridad (puntos vacíos) alrededor del mapa real.
3. Desarrollamos `OptimizadorMontacargas` utilizando **Programación Declarativa (Streams)** para filtrar las ubicaciones accesibles sin bucles anidados complejos.
4. Utilizamos una **Fachada** (`CargadorImprenta`) para ocultar la complejidad de la instanciación.

**Principios y Fundamentos aplicados:**

* **Alta Cohesión y Responsabilidad Única (SRP):**
  Separamos claramente las responsabilidades: `CuadriculaAlmacen` solo gestiona la estructura de datos y la seguridad de los accesos; `OptimizadorMontacargas` contiene exclusivamente la regla de negocio ("¿es accesible?"). Ninguno sabe leer ficheros ni imprimir por consola.
* **Programación Defensiva (Null Object / Padding):**
  Al rodear el mapa con un "marco" de casillas vacías en `CuadriculaAlmacen`, eliminamos la necesidad de comprobar si una coordenada está en el borde (límites del array). Esto simplifica drásticamente la lógica de vecindad y previene errores tipo `IndexOutOfBoundsException`.
* **Inmutabilidad:**
  El uso de `record` para `Coordenada` y `CuadriculaAlmacen` garantiza que el estado del almacén no cambie de forma impredecible. Esto facilita el razonamiento sobre el código y lo hace seguro para hilos (thread-safe).
* **Código Expresivo (Clean Code):**
  En lugar de usar `if (grid[x][y] == '@')`, creamos un Enum `ContenidoCasilla`. Además, el uso de Streams permite leer el algoritmo casi en lenguaje natural: `.filter(noEstaBloqueado).count()`.



### Parte 2: Simulación Iterativa y Gestión de Estado

En la segunda fase, el problema pasó de ser un análisis estático a una **simulación dinámica**. Al retirar rollos, se abren nuevos huecos, lo que permite retirar más rollos en cascada. El reto fue gestionar la evolución del estado del almacén sin romper la inmutabilidad ni duplicar la lógica de detección.

**¿Qué hicimos?**

1. Extendemos `CuadriculaAlmacen` con el método `retirarRollos`, que genera una **nueva instancia** del almacén en lugar de modificar la existente.
2. Implementamos `SolucionadorImprentaB` con un bucle `while` que reevalúa el estado hasta que el sistema converge (no hay más cambios).
3. Reutilizamos el 100% de la lógica de detección de `OptimizadorMontacargas` dentro del bucle de simulación.

**Principios y Fundamentos aplicados:**

* **Principio DRY (Don't Repeat Yourself):**
  La regla compleja ("¿tiene menos de 4 vecinos?") no se volvió a escribir para la Parte 2. Simplemente instanciamos el `OptimizadorMontacargas` dentro del bucle de simulación. Si la regla de negocio cambia, solo hay que tocar un sitio.
* **Principio Abierto/Cerrado (OCP):**
  Añadimos la funcionalidad de simulación creando una nueva clase `SolucionadorImprentaB` e inyectándola mediante la Fábrica (`FabricaEjecutador`). No tuvimos que modificar el código de la Parte 1 ni romper la lógica existente para agregar la nueva característica.
* **Programación Funcional (Manejo de Estado):**
  Aunque la simulación implica cambio, mantuvimos la inmutabilidad de los objetos. El método `retirarRollos` devuelve un *nuevo* almacén con los cambios aplicados. Esto evita "efectos secundarios" (side effects) donde modificar el mapa en un paso podría corromper los cálculos del paso siguiente.
* **Polimorfismo:**
  Tanto la Parte A (estática) como la Parte B (dinámica) implementan la interfaz `Ejecutador`. El `Main` es agnóstico a la complejidad de la simulación; solo llama a `.ejecutar()`, confiando en que la implementación inyectada hará el trabajo correcto.