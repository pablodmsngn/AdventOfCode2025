### **Día 5 \- Cafetería**

#### **1\. Introducción y Problema**

El problema nos sitúa en la cafetería de los elfos, donde una base de datos de inventario corrupta impide distinguir los ingredientes frescos. La entrada consta de una lista de **rangos de frescura** (ej. `3-5`, `10-20`) y, tras una línea en blanco, una lista de **IDs de ingredientes** concretos. El reto tiene dos partes que comparten la entrada (cargar el fichero, separar rangos de IDs, encapsular la matemática de intervalos) y solo cambian *cómo* se explotan los rangos:

* **Parte A:** contar cuántos de los IDs de la lista caen dentro de **al menos un** rango de frescura.
* **Parte B:** ignorar la lista de IDs y calcular la **cobertura total**: cuántos enteros únicos cubre la unión de todos los rangos, fusionando los que se solapan (ej. `10-15` y `12-20` no deben contar dos veces los números compartidos).

Como lo único que cambia es la forma de explotar los rangos, la regla se modela como una abstracción (`FreshnessProtocol`) con implementaciones intercambiables: la política estándar (A) y un protocolo nulo (B), que delega toda la potencia en el algoritmo de fusión de intervalos.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **tres capas** (más la frontera `common.io` compartida) que los días anteriores, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day05
    ├── model         (dominio puro, no depende de nadie)
    │   ├── Range
    │   └── FreshnessProtocol
    ├── control       (orquesta el caso de uso)
    │   ├── InventoryAuditor
    │   └── AuditBuilder
    └── application   (detalles y arranque)
        └── a/Main05A, b/Main05B
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Range`** *(record)*: un *Value Object* que encapsula toda la matemática de intervalos. Sabe si contiene un número (`contains`), su longitud (`length`), si se solapa con otro rango (`overlapsWith`) y cómo fusionarse con otro (`merge`), además de su orden natural (`Comparable`). Al concentrar aquí esta lógica → **alta cohesión** y el auditor queda limpio.
* **`FreshnessProtocol`** *(interfaz funcional)*: la **abstracción** de la regla de frescura (`boolean isFresh(long ingredientId, List<Range> ranges)`). Vive en el dominio porque no depende de ninguna otra capa; es la pieza que permite el DIP y elegir el comportamiento (A vs B) por polimorfismo.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (AuditBuilder parsea las dos secciones). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`InventoryAuditor`**: el caso de uso. Recibe los rangos, los IDs y el `FreshnessProtocol` inyectado. `audit()` cuenta los IDs frescos delegando en el protocolo; `calculateTotalCoverage()` implementa el algoritmo de **fusión de intervalos** (ordenar y unir solapados) para la cobertura total de la Parte B.
* **`AuditBuilder`** *(Patrón Builder, interfaz fluida)*: construye el `InventoryAuditor` paso a paso (`from(lines).using(protocol).build()`). Ya **no lee ficheros**: recibe las líneas ya cargadas y se encarga de **parsear** las dos secciones (rangos antes de la línea en blanco, IDs después), garantizando que nunca se cree un auditor incompleto.

**Capa `application` (detalles y arranque)**

* **`Main05A` / `Main05B`** *(composition root)*: el único punto donde se eligen el cargador y el protocolo y se conectan con el Builder. La Parte A inyecta `standardPolicy` (`(id, ranges) -> ranges.stream().anyMatch(r -> r.contains(id))`) y llama a `audit()`; la Parte B inyecta un protocolo nulo y llama a `calculateTotalCoverage()`.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** `InventoryAuditor` depende de la abstracción `FreshnessProtocol`, no de una regla concreta; el arranque depende de la interfaz `AuditLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** cambiar la regla de frescura (ej. excluir pares) es inyectar otra lambda; el núcleo queda cerrado a modificación y abierto a extensión por polimorfismo.
* **Responsabilidad Única (SRP):** `Range` guarda la matemática de intervalos, `InventoryAuditor` orquesta el cálculo, `AuditBuilder` solo parsea y ensambla, `ResourceLineLoader` (compartido) solo lee I/O.
* **Patrón Builder + interfaz fluida:** `AuditBuilder` arma el auditor paso a paso y valida que esté completo antes de crearlo.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **Inyección de Dependencias (DI) / Strategy:** el cargador y el protocolo se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Alta Cohesión y DRY:** la matemática de intervalos está centralizada en `Range`, y la lectura de entrada en una única implementación reutilizable.
