### **Día 9 \- Cine**

#### **1\. Introducción y Problema**

El problema nos sitúa en el cine de la base del Polo Norte, con un suelo de baldosas rojas y de otros colores. La entrada es una lista de coordenadas de las baldosas rojas. El objetivo es encontrar el **rectángulo más grande** posible usando dos baldosas rojas como esquinas opuestas. El reto tiene dos partes que comparten toda la mecánica (generar rectángulos, ordenarlos por área) y solo cambian la **restricción geométrica**:

* **Parte A:** calcular el área máxima formando un rectángulo con **cualquier par** de baldosas rojas, sin importar qué hay en medio.
* **Parte B:** las baldosas rojas forman el contorno de un **polígono**. El rectángulo debe ser **válido**: estar contenido completamente dentro del polígono (no cruzar ninguna arista y tener su centro dentro). Requiere comprobaciones de intersección y de contención (*ray casting*).

Como lo único que cambia es el criterio que decide qué rectángulo vale, ambas partes se modelan como una abstracción (`AreaSolver`) con dos implementaciones intercambiables, seleccionadas por una fábrica.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **cuatro capas** que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc.day09
├── model         (dominio puro, no depende de nadie)
│   ├── Coordinate
│   ├── Rectangle
│   └── AreaSolver
├── io            (frontera de entrada)
│   └── TileLoader
├── control       (orquesta el caso de uso)
│   ├── RectangleFinder
│   ├── MaxAreaSolver
│   ├── AllowedAreaSolver
│   └── AreaSolverFactory
└── application   (detalles y arranque)
    ├── ResourceTileLoader
    ├── InputLoader
    └── a/Main09A, b/Main09B
```

**Dirección de dependencias:** `application → control → (io + model)` y `io → model`. El dominio (`model`) no importa nada del proyecto.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Coordinate`** *(record)*: un *Value Object* inmutable con las coordenadas (x, y). Encapsula su parseo desde texto (`from("7,1")`), centralizando la transformación de datos.
* **`Rectangle`** *(record)*: un *Value Object* rico que no solo guarda dos esquinas, sino que **encapsula toda la geometría**: ancho, alto, área, si es vertical y sus límites (`minX`, `maxX`, `minY`, `maxY`). Así los cálculos geométricos no se dispersan por el resto del programa → **alta cohesión**.
* **`AreaSolver`** *(interfaz funcional)*: la **abstracción** del caso de uso (`long solve()`). Vive en el dominio porque no depende de ninguna otra capa; es la pieza que permite el DIP y el polimorfismo entre la Parte A y la B.

**Capa `io` (frontera de entrada)**

* **`TileLoader`** *(interfaz)*: define *qué* se necesita de la entrada (`List<String> loadLines()`) sin atarse a *cómo* se lee. El arranque depende de esta abstracción, no del detalle de lectura.

**Capa `control` (orquesta el caso de uso)**

* **`RectangleFinder`** *(motor algorítmico)*: contiene la búsqueda combinatoria. Genera todos los rectángulos posibles ordenados por área (`generateRectangles`), encuentra el mayor (`findLargest`, Parte A) y el mayor válido dentro del polígono (`findLargestAllowed`, Parte B), con sus auxiliares de geometría (aristas del polígono, intersección y contención por *ray casting*).
* **`MaxAreaSolver`** *(implements `AreaSolver`)*: la estrategia de la Parte A; delega en el buscador y devuelve el área del rectángulo mayor.
* **`AllowedAreaSolver`** *(implements `AreaSolver`)*: la estrategia de la Parte B; devuelve el área del mayor rectángulo permitido.
* **`AreaSolverFactory`** *(Builder + Factory)*: configura paso a paso (`from(tiles).type(A|B).build()`) y crea la implementación concreta correcta de forma transparente, validando que no falte nada.

**Capa `application` (detalles y arranque)**

* **`ResourceTileLoader`** *(implements `TileLoader`)*: el detalle de bajo nivel; lee un recurso del classpath y devuelve la lista de líneas. Es intercambiable por otra fuente de datos.
* **`InputLoader`** *(fachada de ensamblaje)*: lee las líneas, las parsea a coordenadas (`Coordinate::from`) y configura el `AreaSolver` correcto vía la fábrica (`loadMaxArea`, `loadAllowedArea`).
* **`Main09A` / `Main09B`** *(composition root)*: el único punto donde se elige la estrategia. El resto del flujo es idéntico: `solver.solve()`.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** los `Main` y la fábrica dependen de la abstracción `AreaSolver`, no de las clases concretas; el arranque depende de la interfaz `TileLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** añadir otra restricción (ej. rectángulos de un tamaño máximo) es crear otro `AreaSolver` e inyectarlo por la fábrica; el motor `RectangleFinder` queda inalterado.
* **Patrón Builder + Factory:** `AreaSolverFactory` configura paso a paso y oculta qué implementación concreta se instancia.
* **Responsabilidad Única (SRP):** `Rectangle` guarda la geometría, `RectangleFinder` solo la búsqueda, cada *Solver* solo su variante, `ResourceTileLoader` solo lee I/O, `AreaSolverFactory` solo ensambla.
* **Segregación de Interfaces (ISP):** `TileLoader` expone un único método cohesivo (`loadLines`).
* **Patrón Factory Method:** `Coordinate.from(...)` convierte texto crudo en un objeto del dominio ya válido.
* **Inmutabilidad:** `Coordinate` y `Rectangle` son records inmutables, lo que da seguridad durante la generación masiva de combinaciones en streams.
* **Inyección de Dependencias (DI) / Strategy:** las baldosas y el tipo se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
