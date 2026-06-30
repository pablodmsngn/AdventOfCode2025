### **Día 6 \- Compactador de basura**

#### **1\. Introducción y Problema**

El escenario es un compactador de basura donde unos cefalópodos necesitan ayuda con una hoja de deberes de matemáticas. La entrada es una cuadrícula de caracteres donde los problemas están dispuestos visualmente en columnas y filas, con un operador (`+` o `*`) al pie de cada bloque. El reto consiste en **interpretar la misma cuadrícula de dos formas distintas** para extraer operandos y sumar el resultado de todas las operaciones:

* **Parte A:** los números están escritos en filas alineadas por columnas. Cada problema es una lista de números y su operador asociado.
* **Parte B:** "Matemáticas Cefalópodas". Las columnas dejan de ser números enteros y pasan a ser dígitos posicionales; los números se leen verticalmente a través de las columnas y los problemas se separan por columnas vacías.

Como lo único que cambia es *cómo* se parsea la misma cuadrícula, esa lógica se modela como una **estrategia intercambiable** (`OperationBuilder`) con dos implementaciones: el analizador vertical (A) y el cefalópodo (B). El motor de cálculo es idéntico para ambas.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day06
    ├── model         (dominio puro, no depende de nadie)
    │   ├── Operator
    │   ├── Operation
    │   └── OperationBuilder
    ├── control       (orquesta el caso de uso)
    │   ├── CompactorController
    │   ├── VerticalAnalyzer
    │   └── CephalopodAnalyzer
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main06A, b/Main06B
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Operator`** *(enum funcional)*: encapsula la aritmética. Al implementar `BinaryOperator<Long>`, el enum no es solo una etiqueta sino una **función ejecutable**: sabe operar (`apply`), cuál es su valor identidad (`0` para sumar, `1` para multiplicar) y parsearse desde carácter (`from`, `isOperator`). Centraliza la lógica aritmética en un único punto.
* **`Operation`** *(record)*: un *Value Object* que agrupa los operandos y su operador. Su única responsabilidad es orquestar el cálculo final mediante una reducción (`calculate()`), delegando la matemática pura al `Operator` → **alta cohesión**.
* **`OperationBuilder`** *(interfaz)*: la **abstracción** de la estrategia de parseo (`addLine(String)` + `Stream<Operation> build()`). Vive en el dominio porque solo habla el idioma del dominio; es la pieza que permite intercambiar cómo se interpreta la cuadrícula sin tocar el resto.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (el OperationBuilder inyectado parsea). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`CompactorController`**: el caso de uso. Recibe un `Stream<Operation>` ya parseado y en `execute()` suma el resultado de cada `calculate()`. Ignora por completo de dónde salieron los datos o cómo se parsearon → bajo acoplamiento.
* **`VerticalAnalyzer`** *(implements `OperationBuilder`)*: la estrategia de la Parte A (números por columnas alineadas).
* **`CephalopodAnalyzer`** *(implements `OperationBuilder`)*: la estrategia de la Parte B (dígitos posicionales leídos en vertical, problemas separados por columnas vacías).

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de ensamblaje)*: punto estático que conecta el cargador con la estrategia inyectada (`load(filename, builder)`): lee las líneas, las alimenta al `OperationBuilder` y devuelve un `CompactorController` listo. Aísla el cableado del I/O.
* **`Main06A` / `Main06B`** *(composition root)*: el único punto donde se elige la estrategia. La Parte A inyecta `new VerticalAnalyzer()` y la Parte B `new CephalopodAnalyzer()`; el resto del flujo es idéntico.

#### **4\. Principios y diseños aplicados**

* **Patrón Strategy:** `OperationBuilder` define la familia de algoritmos de parseo (`VerticalAnalyzer`, `CephalopodAnalyzer`) y los hace intercambiables sin tocar el cargador ni el controlador.
* **Inversión de Dependencias (DIP):** `CompactorController` solo conoce un `Stream<Operation>` y el ensamblaje depende de la abstracción `OperationBuilder`, no de las clases concretas de análisis; el arranque depende de la interfaz `LineLoader`.
* **Abierto/Cerrado (OCP):** si apareciera una "Parte C" (ej. lectura en diagonal), basta crear un nuevo `OperationBuilder` e inyectarlo; el motor de cálculo y la carga permanecen inalterados.
* **Responsabilidad Única (SRP):** `Operator` guarda la aritmética, `Operation` orquesta su cálculo, los analizadores solo parsean, `CompactorController` solo suma, `ResourceLineLoader` solo lee I/O, `InputLoader` solo ensambla.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **Inyección de Dependencias (DI):** la estrategia de parseo se pasa desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Alta Cohesión y DRY:** la aritmética está centralizada en el enum funcional `Operator` (evitando bloques condicionales), y la lectura de entrada en una única implementación.
