### **Día 7 \- Laboratorios**

#### **1\. Introducción y Problema**

El escenario es un laboratorio de taquiones donde un haz de luz (`S`) desciende por una cuadrícula y, al chocar con un divisor (`^`), se separa en dos haces que continúan hacia abajo. La entrada es una cuadrícula de caracteres y la simulación avanza **capa a capa** (de arriba abajo) propagando la intensidad de cada haz. El reto tiene dos partes que comparten exactamente la misma simulación y solo cambian *qué se mide* sobre el resultado final:

* **Parte A:** contar el número total de **divisiones** que se producen (cada vez que un haz golpea un divisor).
* **Parte B:** sumar la **intensidad** de todos los haces activos en la **última capa** (las "líneas temporales" resultantes).

Como lo único que cambia es cómo se lee el resultado de una misma simulación, esa medida se modela como una abstracción (`LabProtocol`) inyectable: dos lecturas distintas (contar divisiones vs. sumar intensidades) sobre el mismo `TachyonSimulator` ya resuelto.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **cuatro capas** que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc.day07
├── model         (dominio puro, no depende de nadie)
│   ├── CellType
│   ├── Cell
│   ├── TachyonSimulator
│   └── LabProtocol
├── io            (frontera de entrada)
│   └── GridLoader
├── control       (orquesta el caso de uso)
│   ├── LabController
│   └── GridBuilder
└── application   (detalles y arranque)
    ├── ResourceGridLoader
    ├── InputLoader
    └── a/Main07A, b/Main07B
```

**Dirección de dependencias:** `application → control → (io + model)` y `io → model`. El dominio (`model`) no importa nada del proyecto.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`CellType`** *(enum)*: los tres estados posibles de una celda (`EMPTY`, `DIVISOR`, `BEAM`). Centraliza el vocabulario del dominio.
* **`Cell`** *(record)*: un *Value Object* inmutable que combina el tipo y la intensidad del haz. Encapsula su parseo desde carácter (`fromChar`) y sus factorías (`empty`, `divisor`, `beam`), de modo que el resto del programa habla en alto nivel (`isBeam`, `isDivisor`) y no de símbolos (`^`, `S`) → **alta cohesión**.
* **`TachyonSimulator`** *(record)*: el motor de simulación, **puro e inmutable**. Avanza capa por capa (`solve`) y en cada paso devuelve un **nuevo** simulador con la capa actualizada y el contador de divisiones acumulado, sin mutar estado. Vive en el dominio porque depende solo de `Cell`.
* **`LabProtocol`** *(interfaz funcional)*: la **abstracción** de la medida final (`long measure(TachyonSimulator solved)`). Es la pieza que permite el DIP y elegir por polimorfismo qué se extrae del resultado (divisiones en A, intensidades en B).

**Capa `io` (frontera de entrada)**

* **`GridLoader`** *(interfaz)*: define *qué* se necesita de la entrada (`List<String> loadLines()`) sin atarse a *cómo* se lee. El arranque depende de esta abstracción, no del detalle de lectura.

**Capa `control` (orquesta el caso de uso)**

* **`LabController`** *(record)*: el caso de uso. Recibe la cuadrícula ya parseada y el `LabProtocol` inyectado, y en `run()` lanza la simulación (`new TachyonSimulator(grid, 0, 1).solve()`) y delega la medida en el protocolo. No sabe de dónde vienen los datos ni qué se mide exactamente.
* **`GridBuilder`** *(Patrón Builder, interfaz fluida)*: construye el `LabController` paso a paso (`from(lines).using(protocol).build()`). Se encarga de **parsear** las líneas crudas en una `List<List<Cell>>` y garantiza que nunca se cree un controlador incompleto (si falta la entrada o el protocolo, lanza excepción).

**Capa `application` (detalles y arranque)**

* **`ResourceGridLoader`** *(implements `GridLoader`)*: el detalle de bajo nivel; lee un recurso del classpath y devuelve la lista de líneas. Es intercambiable por otra fuente de datos.
* **`InputLoader`** *(fachada de ensamblaje)*: punto estático que conecta el cargador con el protocolo inyectado (`load(file, protocol)`): lee las líneas y las pasa al `GridBuilder`, devolviendo un `LabController` listo.
* **`Main07A` / `Main07B`** *(composition root)*: el único punto donde se elige la medida. La Parte A inyecta `solved -> solved.accumulatedDivisions()` y la Parte B una lambda que suma la intensidad de los haces de la última capa; el resto del flujo es idéntico.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** `LabController` depende de la abstracción `LabProtocol`, no de una medida concreta; el arranque depende de la interfaz `GridLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** medir otra cosa sobre la simulación (ej. la capa intermedia con más haces) es inyectar otra lambda; el núcleo queda cerrado a modificación.
* **Responsabilidad Única (SRP):** `Cell` guarda el estado de una celda, `TachyonSimulator` solo simula, `LabController` solo orquesta, `GridBuilder` solo parsea y ensambla, `ResourceGridLoader` solo lee I/O.
* **Inmutabilidad y robustez:** la simulación es funcional (`solve` devuelve nuevos simuladores) y `Cell` es un record inmutable, lo que elimina efectos colaterales en la propagación del haz.
* **Patrón Builder + interfaz fluida:** `GridBuilder` arma el controlador paso a paso y valida que esté completo antes de crearlo.
* **Segregación de Interfaces (ISP):** `GridLoader` expone un único método cohesivo (`loadLines`).
* **Inyección de Dependencias (DI) / Strategy:** el cargador y la medida se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Alta Cohesión y DRY:** la simulación está escrita una sola vez y ambas partes la reutilizan; solo cambia la lectura final.
