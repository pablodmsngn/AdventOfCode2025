### **Día 10 \- Fábrica**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una fábrica donde hay que reparar máquinas. Cada máquina tiene un conjunto de botones y un panel de indicadores con luces y voltajes. Al pulsar un botón se alternan estados concretos (luces encendidas/apagadas) y se incrementan los voltajes de ciertos componentes. El reto tiene dos partes que comparten el modelo (máquinas, botones, indicadores) y se diferencian en *qué se le pide resolver* a cada máquina:

* **Parte A:** encontrar la **mínima** secuencia de pulsaciones para que las luces coincidan con una configuración objetivo, ignorando los voltajes (resuelto con BFS sobre subconjuntos de botones).
* **Parte B:** cumplir requisitos estrictos de **voltaje acumulado**. Requiere una búsqueda **recursiva** (con memoización) que resuelve primero la paridad de las luces y luego ajusta matemáticamente los voltajes restantes.

Como ambas partes recorren la misma lista de máquinas y solo cambian la función que resuelve cada una, esa función se modela como una abstracción (`MachineSolver`) inyectable: una estrategia distinta para A y para B sobre el mismo controlador.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day10
    ├── model         (dominio puro, no depende de nadie)
    │   ├── State
    │   ├── Button
    │   ├── Indicator
    │   ├── Machine
    │   └── MachineSolver
    ├── control       (orquesta el caso de uso)
    │   └── FactoryController
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main10A, b/Main10B
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`State`** *(enum)*: las luces (`ON`, `OFF`). Oculta la representación de bajo nivel (`#` / `.`) tras conceptos semánticos mediante `fromChar` y `parse`. El resto del sistema habla de estados lógicos, no de caracteres.
* **`Button`** *(record)*: un *Value Object* atómico; solo guarda el conjunto de índices que afecta y su parseo (`from`).
* **`Indicator`** *(record)*: agrupa datos estrechamente ligados (estados de luces y voltajes) y opera sobre ellos de forma **inmutable**: `reduceVoltagesWith`, `voltageHalf`, `toggleState` y `createInitialState` devuelven **nuevas** instancias en vez de mutar → **alta cohesión**.
* **`Machine`** *(record)*: el motor de resolución, **puro e inmutable**. Centraliza los algoritmos: BFS sobre máscaras de botones (`solveMinPresses`, Parte A) y la búsqueda recursiva con caché (`solveVoltageRequirements`, Parte B). No lee ficheros ni imprime: solo calcula costes. Vive en el dominio porque depende solo de `State`, `Button` e `Indicator`.
* **`MachineSolver`** *(interfaz funcional)*: la **abstracción** de "cómo resolver una máquina" (`int solve(Machine machine)`). Es la pieza que permite el DIP y elegir por polimorfismo la estrategia A o B.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (InputLoader parsea con Machine.from). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`FactoryController`**: el caso de uso. Recibe la lista de máquinas y el `MachineSolver` inyectado, y en `execute()` suma el coste que el solucionador calcula para cada máquina. Ignora si por dentro es BFS (A) o recursividad (B).

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de ensamblaje)*: lee las líneas, las parsea a máquinas (`Machine::from`) y conecta todo con el `MachineSolver` inyectado, devolviendo un `FactoryController` listo.
* **`Main10A` / `Main10B`** *(composition root)*: el único punto donde se elige la estrategia. La Parte A inyecta `Machine::solveMinPresses` y la Parte B `machine -> machine.solveVoltageRequirements(new HashMap<>())`; el resto del flujo es idéntico.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** `FactoryController` depende de la abstracción `MachineSolver`, no de un algoritmo concreto; el arranque depende de la interfaz `MachineLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** pedir otra forma de resolver una máquina es inyectar otra estrategia; `FactoryController` queda cerrado a modificación.
* **Responsabilidad Única (SRP):** `Button` guarda los índices, `Indicator` los datos del panel, `Machine` solo los algoritmos, `FactoryController` solo agrega, `ResourceLineLoader` (compartido) solo lee I/O, `InputLoader` solo ensambla.
* **Inmutabilidad y robustez:** los records (`Button`, `Indicator`, `Machine`) garantizan que el estado no cambie inesperadamente; métodos como `applyButton` devuelven un nuevo `Indicator`, lo que evita efectos colaterales y hace segura la recursividad y el BFS.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **Patrón Factory Method:** `Machine.from`, `Button.from`, `Indicator.from` y `State.fromChar` convierten texto crudo en objetos del dominio ya válidos.
* **Inyección de Dependencias (DI) / Strategy:** las máquinas y la estrategia de resolución se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Abstracción / Tell-Don't-Ask:** el sistema trabaja con `State.ON/OFF` y delega los cálculos en los propios objetos del dominio en vez de manipular caracteres o listas crudas.
