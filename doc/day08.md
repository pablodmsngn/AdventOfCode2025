### **Día 8 \- Patio de juegos**

#### **1\. Introducción y Problema**

El escenario es un patio de juegos subterráneo donde los elfos instalan luces navideñas. La entrada es una lista de coordenadas 3D (X, Y, Z) que representan cajas de conexiones; cada caja empieza siendo su propio circuito independiente. Las cajas se conectan mediante cables, priorizando siempre las que estén más cerca (menor distancia euclidiana). El reto tiene dos partes que comparten la carga y el cálculo de distancias, y se diferencian en *qué algoritmo de conectividad* se aplica:

* **Parte A:** realizar exactamente las **1000 conexiones más cortas** y, al final, multiplicar el tamaño de los **tres circuitos más grandes** (factor de seguridad).
* **Parte B:** ignorar el límite y seguir uniendo cables hasta que todas las cajas formen **un único circuito**; el resultado es `X1 * X2` del último par que provoca la unificación total.

Como ambas partes son algoritmos distintos sobre el mismo conjunto de cajas, se modelan como una abstracción (`CircuitSolver`) con dos implementaciones intercambiables, seleccionadas por una fábrica.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day08
    ├── model         (dominio puro, no depende de nadie)
    │   ├── Box
    │   ├── BoxPair
    │   ├── Circuit
    │   └── CircuitSolver
    ├── control       (orquesta el caso de uso)
    │   ├── CircuitConnector
    │   ├── SafetyFactorSolver
    │   ├── MergeCostSolver
    │   └── SolverFactory
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main08A, b/Main08B
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Box`** *(record)*: un *Value Object* inmutable con las coordenadas 3D. Encapsula la geometría: sabe calcular la distancia euclidiana a otra caja (`distanceTo`), manteniendo datos y lógica juntos → **alta cohesión**.
* **`BoxPair`** *(record)*: un objeto de transferencia que asocia dos cajas con la distancia **pre-calculada** entre ellas, lo que permite ordenar por distancia sin recalcular la fórmula.
* **`Circuit`** *(record)*: una agrupación lógica de cajas conectadas (un `Set<Box>`). Encapsula además su propio parseo (`fromText`), convirtiendo una línea `"x,y,z"` en un circuito de una sola caja.
* **`CircuitSolver`** *(interfaz funcional)*: la **abstracción** del caso de uso (`long solve()`). Vive en el dominio porque no depende de ninguna otra capa; es la pieza que permite el DIP y el polimorfismo entre la Parte A y la B.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (InputLoader parsea con Circuit.fromText). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`CircuitConnector`** *(motor algorítmico)*: contiene exclusivamente la lógica combinatoria: generar pares, ordenarlos por distancia (en paralelo), fusionar circuitos y calcular tanto el factor de seguridad (`calculateSafetyFactor`) como el coste de unificación (`calculateMergeCost`). Es el "cómo" del algoritmo, separado del "qué" del caso de uso.
* **`SafetyFactorSolver`** *(implements `CircuitSolver`)*: la estrategia de la Parte A; recibe los circuitos y el número de conexiones y delega en el conector.
* **`MergeCostSolver`** *(implements `CircuitSolver`)*: la estrategia de la Parte B; recibe los circuitos y delega el cálculo del coste de unificación.
* **`SolverFactory`** *(Builder + Factory)*: híbrido que configura paso a paso (`from(circuits).type(A|B).connections(n).build()`) y crea la implementación concreta correcta de forma transparente para el cliente, validando que no falte nada antes de construir.

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de ensamblaje)*: punto estático que lee las líneas, las parsea a circuitos (`Circuit::fromText`) y configura el `CircuitSolver` correcto vía la fábrica (`loadSafetyFactor`, `loadMergeCost`).
* **`Main08A` / `Main08B`** *(composition root)*: el único punto donde se elige la estrategia. La Parte A pide el solucionador del factor de seguridad con 1000 conexiones; la Parte B el de unificación. El resto del flujo es idéntico: `solver.solve()`.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** los `Main` y la fábrica dependen de la abstracción `CircuitSolver`, no de las clases concretas; el arranque depende de la interfaz `CircuitLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** añadir una "Parte C" es crear otro `CircuitSolver` e inyectarlo por la fábrica; el motor (`CircuitConnector`) y la carga quedan inalterados.
* **Patrón Builder + Factory:** `SolverFactory` configura paso a paso y oculta qué implementación concreta se instancia.
* **Responsabilidad Única (SRP):** `Box` guarda la geometría, `Circuit` la agrupación y su parseo, `CircuitConnector` solo el algoritmo, cada *Solver* solo su variante, `ResourceLineLoader` (compartido) solo lee I/O, `SolverFactory` solo ensambla.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **Inmutabilidad y seguridad en paralelo:** al ser `Box`, `BoxPair` y `Circuit` records inmutables, el procesamiento paralelo de pares (`parallel()`) queda libre de condiciones de carrera.
* **Inyección de Dependencias (DI) / Strategy:** los circuitos y el tipo se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Alta Cohesión y DRY:** la geometría está en `Box`, el parseo en `Circuit`, y la lectura de entrada centralizada en una sola implementación.
