### **Día 4 \- Departamento de Impresión**

#### **1\. Introducción y Problema**

El problema nos sitúa en el almacén de la imprenta, representado por una cuadrícula 2D con rollos de papel (`@`) y espacios vacíos (`.`). El objetivo es optimizar la logística contando cuántos rollos son "accesibles". Un rollo es accesible si tiene **menos de 4 vecinos** rollo (de los 8 posibles: horizontales, verticales y diagonales). El reto tiene dos partes que comparten toda la mecánica (parsear el mapa, mirar vecinos, decidir accesibilidad) y solo cambian *cómo* se usa esa regla:

* **Parte A:** contar cuántos rollos son accesibles en el estado inicial (foto estática).
* **Parte B:** simulación completa. Al retirar los rollos accesibles, los que estaban detrás pueden quedar libres; se repite el proceso en bucle hasta que no se pueda retirar ninguno más.

Como lo único que cambia es la forma de explotar la regla, el "ejecutador" del caso de uso se modela como una abstracción con dos implementaciones intercambiables (estática y dinámica).

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day04
    ├── model         (dominio puro, no depende de nadie)
    │   ├── CellContent
    │   ├── Coordinate
    │   ├── Executor
    │   └── WarehouseGrid
    ├── control       (orquesta el caso de uso)
    │   ├── ForkliftOptimizer
    │   ├── PrintShopSolverA
    │   ├── PrintShopSolverB
    │   └── ExecutorFactory
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main04A, b/Main04B
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`CellContent`** *(enum)*: encapsula la representación de los datos (`PAPER_ROLL`, `EMPTY`) y su parseo desde caracteres (`fromChar`). Centraliza en un solo sitio "qué carácter significa qué cosa": el resto del programa habla en términos de alto nivel (`PAPER_ROLL`) y no de detalles de bajo nivel (`@`). Si mañana cambia el símbolo, se toca un único punto.
* **`Coordinate`** *(record)*: solo sabe calcular sus 8 coordenadas vecinas (`neighbors()`) → **alta cohesión**.
* **`Executor`** *(interfaz funcional)*: la **abstracción** del caso de uso (`long execute()`). Vive en el dominio porque no depende de ninguna otra capa; es la pieza que permite el DIP y el polimorfismo entre la Parte A y la B.
* **`WarehouseGrid`** *(record)*: gestiona la matriz. Su `from(...)` añade un borde de seguridad de puntos (*padding*) alrededor del mapa, lo que elimina las comprobaciones de límites (`IndexOutOfBounds`) al mirar vecinos. Para la Parte B es **inmutable**: `removeRolls(...)` devuelve una **nueva** cuadrícula en vez de mutar el estado.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (InputLoader llama a WarehouseGrid.from). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`ForkliftOptimizer`** *(clase de utilidad)*: contiene exclusivamente la regla de qué es un bloqueo (`>= 4` vecinos rollo) y qué rollos son accesibles. Es estática (función pura: recibe un estado, devuelve un resultado), de modo que el bucle de la Parte B no instancia un objeto en cada vuelta.
* **`PrintShopSolverA`** *(implements `Executor`)*: responsabilidad puramente lógica; recibe el almacén y ejecuta el cálculo único delegando en el optimizador. No gestiona I/O.
* **`PrintShopSolverB`** *(implements `Executor`)*: gestiona el bucle de la simulación; recibe el modelo inicial y, en cada iteración, retira los accesibles y actualiza la referencia hasta que no quede ninguno.
* **`ExecutorFactory`** *(Builder + Factory)*: híbrido que configura paso a paso (`from(warehouse).type(A|B).build()`) y crea la instancia concreta correcta (`PrintShopSolverA` o `PrintShopSolverB`) de forma transparente para el cliente. Ya **no lee ficheros**: recibe el `WarehouseGrid` ya construido y valida que no falte nada antes de crear.

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de ensamblaje, en `application`)*: usa el `ResourceLineLoader` compartido para leer las líneas y las parsea al dominio antes de construir el caso de uso.
* **`Main04A` / `Main04B`** *(composition root)*: el único punto donde se eligen el cargador y el tipo y se conectan con la fábrica. La Parte A y la Parte B se diferencian solo en el `ExecutorType` inyectado.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** el `Main` y la fábrica dependen de la abstracción `Executor`, no de las clases concretas de solución; el arranque depende de la interfaz `GridLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** alternar entre la lógica estática (A) y dinámica (B) es elegir otro `ExecutorType`; el código cliente queda cerrado a modificación.
* **Patrón Builder + Factory:** `ExecutorFactory` configura paso a paso y oculta qué implementación concreta se instancia.
* **Responsabilidad Única (SRP):** `WarehouseGrid` gestiona la matriz, `ForkliftOptimizer` solo guarda la regla de bloqueo, `PrintShopSolverA` hace el cálculo único, `PrintShopSolverB` gestiona el bucle, `ResourceLineLoader` (compartido) solo lee I/O, `ExecutorFactory` solo ensambla.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **Inyección de Dependencias (DI) / Strategy:** el cargador y el tipo se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Inmutabilidad y robustez:** `removeRolls` devuelve una nueva cuadrícula (sin efectos colaterales) y el *padding* simplifica la búsqueda de vecinos eliminando comprobaciones de límites.
* **Bajo Acoplamiento y DRY:** la regla de accesibilidad es independiente de cómo se explota (A o B), y la lectura de entrada está centralizada en una sola implementación.
