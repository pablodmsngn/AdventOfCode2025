### **Día 12 \- Granja de árboles de Navidad**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una caverna bajo el Polo Norte, una granja de árboles de Navidad donde los elfos colocan regalos bajo los árboles. Los regalos tienen formas irregulares (poliominós) y las zonas son cuadrículas de tamaños concretos (ej. 4x4, 12x5). La entrada tiene dos secciones: un **catálogo de formas** y una **lista de regiones** con la cantidad de cada regalo que debe caber. Es un problema clásico de **backtracking / empaquetado** (*tiling*):

* Los regalos pueden **rotarse** (90º, 180º…) y **voltearse** (espejo), pero no pueden superponerse ni salirse de los límites.
* El objetivo (única parte del reto) es contar **cuántas regiones son solubles**: en cuántas caben todos los regalos asignados sin colisiones.

A diferencia de otros días, el día 12 tiene **una sola parte**. La variación interesante es interna al solucionador: elige automáticamente entre dos representaciones del tablero (máscara `long` o `BitSet`) según el tamaño de la región, una decisión de optimización encapsulada que el cliente no ve.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day12
    ├── model         (dominio puro, no depende de nadie)
    │   ├── Coordinate
    │   ├── Shape
    │   ├── Region
    │   └── ProblemDefinition
    ├── control       (orquesta el caso de uso)
    │   ├── FarmSolver
    │   └── FarmController
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main12A
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Coordinate`** *(record)*: un *Value Object* inmutable (fila, columna) que encapsula sus transformaciones geométricas básicas: `rotate()` y `flip()`.
* **`Shape`** *(record)*: un poliominó. Concentra **toda la geometría**: área, generación de las 8 isometrías (`generateVariations`), rotación, volteo y normalización. Al mantener datos y transformaciones juntos, el solucionador solo pide las variantes en vez de hacer matemática vectorial → **alta cohesión**.
* **`Region`** *(record)*: la zona a rellenar (ancho, alto). Sabe su área y si es "pequeña" (`isSmall`, ≤ 64 celdas), criterio que guía la optimización del solucionador.
* **`ProblemDefinition`** *(record)*: agrupa una región con la lista de piezas que deben encajar en ella. Es el caso concreto a resolver.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (InputLoader parsea las dos secciones (catálogo + problemas)). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`FarmSolver`** *(motor algorítmico)*: la lógica pura de backtracking. Descarta rápido por área, ordena las piezas (mayores primero) y elige dinámicamente la representación del tablero: máscara `long` para regiones pequeñas o `BitSet` para grandes. Precalcula las colocaciones válidas de cada pieza y prueba combinaciones podando colisiones. No sabe de ficheros ni del formato global.
* **`FarmController`** *(caso de uso)*: recorre los `ProblemDefinition` y cuenta cuántos son solubles (`countValidRegions`), delegando cada uno en un `FarmSolver`. Oculta al `Main` si por dentro se usan máscaras de bits o recursión.

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de parseo y ensamblaje)*: encapsula el **complejo parseo** de la entrada de dos secciones (catálogo de formas y definición de problemas) y construye el `FarmController`. Expone `load(file)` para el arranque y `fromLines(lines)` para las pruebas.
* **`Main12A`** *(composition root)*: el único punto donde se conecta el cargador con el controlador y se pide el resultado.

#### **4\. Principios y diseños aplicados**

* **Responsabilidad Única (SRP):** `Shape` guarda la geometría, `Region` su tamaño, `FarmSolver` solo el backtracking, `FarmController` solo recorre y cuenta, `InputLoader` solo parsea y ensambla, `ResourceLineLoader` (compartido) solo lee I/O.
* **Alta Cohesión:** `Shape` concentra todas las transformaciones (rotar, voltear, normalizar, variaciones) en un único sitio.
* **Abstracción / encapsulación:** la elección entre máscara `long` y `BitSet` es un detalle **interno** de `FarmSolver`; el cliente solo llama a `solve(...)`. Es un ejemplo de Strategy elegida internamente por una condición (tamaño de región), no inyectada (no hace falta: solo hay un caso de uso → **YAGNI**).
* **Inversión de Dependencias (DIP) / ISP:** el arranque depende de la interfaz `ProblemLoader`, que expone un único método cohesivo (`loadLines`).
* **Inmutabilidad y Value Objects:** los records (`Coordinate`, `Shape`, `Region`, `ProblemDefinition`) garantizan que las miles de rotaciones y traslaciones generen **nuevas** instancias sin corromper las formas originales del catálogo.
* **Patrón Factory Method:** la creación de formas y problemas se centraliza en `InputLoader`, encapsulando el formato de dos secciones.
* **Rendimiento:** representar el tablero como bits permite comprobar colisiones con operaciones `AND`/`OR` en O(1), y la poda del backtracking evita explorar ramas inválidas.
