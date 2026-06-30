### **Día 11 \- Reactor**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una fábrica donde un gran reactor toroidal tiene problemas de conexión con un rack de servidores. La entrada es una lista de dispositivos y sus conexiones unidireccionales: un **grafo dirigido**. El reto consiste en contar rutas a través de esa red, y comparte toda la mecánica (parsear el grafo, recorrer con DFS + memoización) cambiando solo *qué consulta* se hace:

* **Parte A:** calcular el número total de rutas distintas desde el inicio (`you`) hasta la salida (`out`).
* **Parte B:** contar las rutas desde el servidor (`svr`) hasta la salida (`out`) que pasen obligatoriamente por dos nodos intermedios (`dac` y `fft`), en **cualquier orden**. Se descompone en segmentos y se multiplican sus conteos.

Como lo único que cambia es la consulta sobre el mismo grafo, ambas partes se modelan como una abstracción (`RouteSolver`) con dos implementaciones intercambiables, seleccionadas por una fábrica.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day11
    ├── model         (dominio puro, no depende de nadie)
    │   ├── RouteGraph
    │   └── RouteSolver
    ├── control       (orquesta el caso de uso)
    │   ├── RouteAnalyzer
    │   ├── TotalRoutesSolver
    │   ├── CriticalRoutesSolver
    │   └── RouteSolverFactory
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main11A, b/Main11B
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`RouteGraph`** *(record)*: un *Value Object* que encapsula el grafo (`Map<String, List<String>>`). Ofrece una operación de alto nivel (`neighborsOf`) en vez de exponer el mapa crudo, y centraliza su construcción desde texto (`from(List<String>)`) → **alta cohesión** y **Tell-Don't-Ask**.
* **`RouteSolver`** *(interfaz funcional)*: la **abstracción** del caso de uso (`long solve()`). Vive en el dominio porque no depende de ninguna otra capa; es la pieza que permite el DIP y el polimorfismo entre la Parte A y la B.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (InputLoader parsea con RouteGraph.from). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`RouteAnalyzer`** *(motor algorítmico)*: concentra la algoritmia de grafos. `countRoutes` recorre con **DFS y memoización** (un `Map` de resultados parciales) para no recomputar; `countRoutesWithIntermediates` descompone la Parte B en segmentos y multiplica sus conteos. No gestiona I/O ni estado global.
* **`TotalRoutesSolver`** *(implements `RouteSolver`)*: la estrategia de la Parte A; consulta `you → out`.
* **`CriticalRoutesSolver`** *(implements `RouteSolver`)*: la estrategia de la Parte B; consulta `svr → out` pasando por `dac` y `fft`.
* **`RouteSolverFactory`** *(Builder + Factory)*: configura paso a paso (`from(graph).type(A|B).build()`) y crea la implementación concreta correcta de forma transparente.

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de ensamblaje)*: lee las líneas, construye el `RouteGraph` (`RouteGraph.from`) y configura el `RouteSolver` correcto vía la fábrica (`loadTotalRoutes`, `loadCriticalRoutes`).
* **`Main11A` / `Main11B`** *(composition root)*: el único punto donde se elige la estrategia. El resto del flujo es idéntico: `solver.solve()`.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** los `Main` y la fábrica dependen de la abstracción `RouteSolver`, no de las clases concretas; el arranque depende de la interfaz `GraphLoader`.
* **Abierto/Cerrado (OCP):** añadir otra consulta (ej. rutas que eviten un nodo) es crear otro `RouteSolver` e inyectarlo; el motor `RouteAnalyzer` queda inalterado.
* **Patrón Builder + Factory:** `RouteSolverFactory` configura paso a paso y oculta qué implementación concreta se instancia.
* **Responsabilidad Única (SRP):** `RouteGraph` guarda el grafo, `RouteAnalyzer` solo la algoritmia, cada *Solver* solo su consulta, `ResourceLineLoader` (compartido) solo lee I/O, la fábrica solo ensambla.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **DRY:** la Parte B reutiliza `countRoutes` descomponiendo en segmentos en vez de duplicar el DFS; la lectura está centralizada en una implementación.
* **Patrón Factory Method:** `RouteGraph.from(...)` convierte texto crudo en el grafo del dominio ya válido.
* **Memoización / rendimiento:** guardar resultados parciales en un `Map` evita recomputar subcaminos, clave en un problema combinatorio.
