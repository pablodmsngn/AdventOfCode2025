### **Día 12 \- Granja de árboles de Navidad**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una caverna bajo el Polo Norte, una granja de árboles de Navidad donde los elfos colocan regalos bajo los árboles. Los regalos tienen formas irregulares (poliominós) y las zonas son cuadrículas de tamaños concretos (ej. 4x4, 12x5). La entrada tiene dos secciones: un **catálogo de formas** y una **lista de regiones** con la cantidad de cada regalo que debe caber. Es un problema clásico de **backtracking / empaquetado** (*tiling*):

* Los regalos pueden **rotarse** (90º, 180º…) y **voltearse** (espejo), pero no pueden superponerse ni salirse de los límites.
* El objetivo (única parte del reto) es contar **cuántas regiones son solubles**: en cuántas caben todos los regalos asignados sin colisiones.

A diferencia de otros días, el día 12 tiene **una sola parte**. La variación interesante es interna al solucionador: elige automáticamente entre dos representaciones del tablero (máscara `long` o `BitSet`) según el tamaño de la región, una decisión de optimización encapsulada que el cliente no ve.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **cuatro capas** que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc.day12
├── model         (dominio puro, no depende de nadie)
│   ├── Coordinate
│   ├── Shape
│   ├── Region
│   └── ProblemDefinition
├── io            (frontera de entrada)
│   └── ProblemLoader
├── control       (orquesta el caso de uso)
│   ├── FarmSolver
│   └── FarmController
└── application   (detalles y arranque)
    ├── ResourceProblemLoader
    ├── InputLoader
    └── a/Main12A
```

**Dirección de dependencias:** `application → control → (io + model)` y `io → model`. El dominio (`model`) no importa nada del proyecto.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Coordinate`** *(record)*: un *Value Object* inmutable (fila, columna) que encapsula sus transformaciones geométricas básicas: `rotate()` y `flip()`.
* **`Shape`** *(record)*: un poliominó. Concentra **toda la geometría**: área, generación de las 8 isometrías (`generateVariations`), rotación, volteo y normalización. Al mantener datos y transformaciones juntos, el solucionador solo pide las variantes en vez de hacer matemática vectorial → **alta cohesión**.
* **`Region`** *(record)*: la zona a rellenar (ancho, alto). Sabe su área y si es "pequeña" (`isSmall`, ≤ 64 celdas), criterio que guía la optimización del solucionador.
* **`ProblemDefinition`** *(record)*: agrupa una región con la lista de piezas que deben encajar en ella. Es el caso concreto a resolver.

**Capa `io` (frontera de entrada)**

* **`ProblemLoader`** *(interfaz)*: define *qué* se necesita de la entrada (`List<String> loadLines()`) sin atarse a *cómo* se lee. El arranque depende de esta abstracción, no del detalle de lectura.

**Capa `control` (orquesta el caso de uso)**

* **`FarmSolver`** *(motor algorítmico)*: la lógica pura de backtracking. Descarta rápido por área, ordena las piezas (mayores primero) y elige dinámicamente la representación del tablero: máscara `long` para regiones pequeñas o `BitSet` para grandes. Precalcula las colocaciones válidas de cada pieza y prueba combinaciones podando colisiones. No sabe de ficheros ni del formato global.
* **`FarmController`** *(caso de uso)*: recorre los `ProblemDefinition` y cuenta cuántos son solubles (`countValidRegions`), delegando cada uno en un `FarmSolver`. Oculta al `Main` si por dentro se usan máscaras de bits o recursión.

**Capa `application` (detalles y arranque)**

* **`ResourceProblemLoader`** *(implements `ProblemLoader`)*: el detalle de bajo nivel; lee un recurso del classpath y devuelve la lista de líneas.
* **`InputLoader`** *(fachada de parseo y ensamblaje)*: encapsula el **complejo parseo** de la entrada de dos secciones (catálogo de formas y definición de problemas) y construye el `FarmController`. Expone `load(file)` para el arranque y `fromLines(lines)` para las pruebas.
* **`Main12A`** *(composition root)*: el único punto donde se conecta el cargador con el controlador y se pide el resultado.

#### **4\. Principios y diseños aplicados**

* **Responsabilidad Única (SRP):** `Shape` guarda la geometría, `Region` su tamaño, `FarmSolver` solo el backtracking, `FarmController` solo recorre y cuenta, `InputLoader` solo parsea y ensambla, `ResourceProblemLoader` solo lee I/O.
* **Alta Cohesión:** `Shape` concentra todas las transformaciones (rotar, voltear, normalizar, variaciones) en un único sitio.
* **Abstracción / encapsulación:** la elección entre máscara `long` y `BitSet` es un detalle **interno** de `FarmSolver`; el cliente solo llama a `solve(...)`. Es un ejemplo de Strategy elegida internamente por una condición (tamaño de región), no inyectada (no hace falta: solo hay un caso de uso → **YAGNI**).
* **Inversión de Dependencias (DIP) / ISP:** el arranque depende de la interfaz `ProblemLoader`, que expone un único método cohesivo (`loadLines`).
* **Inmutabilidad y Value Objects:** los records (`Coordinate`, `Shape`, `Region`, `ProblemDefinition`) garantizan que las miles de rotaciones y traslaciones generen **nuevas** instancias sin corromper las formas originales del catálogo.
* **Patrón Factory Method:** la creación de formas y problemas se centraliza en `InputLoader`, encapsulando el formato de dos secciones.
* **Rendimiento:** representar el tablero como bits permite comprobar colisiones con operaciones `AND`/`OR` en O(1), y la poda del backtracking evita explorar ramas inválidas.

#### **5\. Conclusión**

El día separa la geometría (`Shape`, con sus isometrías) de la lógica de resolución (`FarmSolver`, backtracking con máscaras de bits), bajo las cuatro capas con el dominio en el centro. La inmutabilidad de los records hace seguras las miles de variantes generadas, y la elección interna entre `long` y `BitSet` ilustra una optimización encapsulada que no contamina el resto del sistema. Al tener una sola parte, no se introduce una estrategia inyectable (YAGNI), pero la abstracción de carga (`ProblemLoader`) y la separación motor/controlador mantienen el bajo acoplamiento. El resultado es fácil de probar: el test construye el `FarmController` con `InputLoader.fromLines`, sin tocar ficheros. Se verificó que el comportamiento se conserva: 2 regiones válidas sobre el ejemplo del enunciado.
