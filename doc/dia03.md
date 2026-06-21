### **Día 3 \- Vestíbulo**

#### **1\. Introducción y Problema**

El escenario es el vestíbulo principal, donde las escaleras mecánicas necesitan energía de emergencia. Disponemos de bancos de baterías representados por secuencias de dígitos (ej. "987654321"). El objetivo es seleccionar una sub-secuencia de dígitos, respetando el orden de aparición, que forme el número (sacudida) más alto posible. El reto tiene dos partes que solo cambian cuántos dígitos se eligen de cada banco:

* **Parte A:** seleccionar exactamente **2** dígitos de cada banco para maximizar la energía (ej. de "987654321" sale 98).
* **Parte B:** la fricción estática exige más potencia, así que se seleccionan exactamente **12** dígitos de cada banco con la misma lógica de maximización.

Ambas partes leen la misma entrada y comparten todo: cargar los bancos, aplicar el algoritmo voraz y sumar los resultados. Lo único que cambia es **cuántos dígitos** pide la regla. Por eso esa regla se modela como una estrategia intercambiable: un algoritmo *Greedy* (voraz) que en cada paso toma el dígito más alto posible asegurando que queden suficientes dígitos a la derecha para completar la longitud N.

#### **2\. Arquitectura por capas**

He reorganizado el día en las mismas **cuatro capas** que los días 1 y 2, con las dependencias apuntando siempre hacia el dominio:

```
software.ulpgc.aoc.day03
├── model         (dominio puro, no depende de nadie)
│   ├── BatteryBank
│   └── EnergyProtocol
├── io            (frontera de entrada)
│   └── SequenceLoader
├── control       (orquesta el caso de uso)
│   ├── StaircaseController
│   ├── StaircaseBuilder
│   └── SearchStrategies
└── application   (detalles y arranque)
    ├── ResourceSequenceLoader
    └── a/Main03A, b/Main03B
```

**Dirección de dependencias:** `application → control → (io + model)` y `io → model`. El dominio (`model`) no importa nada del proyecto.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`BatteryBank`** *(record)*: un *Value Object* que solo almacena la secuencia de dígitos de un banco. No sabe de ficheros, ni del algoritmo, ni de cuántos dígitos se eligen → **alta cohesión**.
* **`EnergyProtocol`** *(interfaz funcional)*: la **abstracción** de la regla de energía (`calculateEnergy(String)`). Vive en el dominio porque solo habla el idioma del dominio y no depende de ninguna otra capa. Es la pieza que permite el DIP.

**Capa `io` (frontera de entrada)**

* **`SequenceLoader`** *(interfaz)*: define *qué* se necesita de la entrada (`List<BatteryBank> loadAll()`) sin atarse a *cómo* se lee. El arranque depende de esta abstracción, no del detalle de lectura.

**Capa `control` (orquesta el caso de uso)**

* **`StaircaseController`** *(record)*: el caso de uso. Recibe los bancos ya cargados y el `EnergyProtocol` inyectado, y en `activate()` suma la energía de cada banco. No sabe de dónde vienen los bancos ni cómo se calcula la energía de cada uno.
* **`StaircaseBuilder`** *(Patrón Builder, interfaz fluida)*: construye el `StaircaseController` paso a paso (`from(...).use(...).build()`) y garantiza que nunca se cree incompleto (si falta la fuente o el protocolo, lanza excepción). Ya **no lee ficheros**: recibe los bancos ya cargados, así la construcción queda aislada del I/O.
* **`SearchStrategies`** *(clase de utilidad)*: contiene la lógica matemática del algoritmo Greedy (`Greedy(secuencia, n)`) como método estático, separando el "cómo calcular" del "quién tiene los datos". Su única razón para cambiar es que cambie el algoritmo de selección.

**Capa `application` (detalles y arranque)**

* **`ResourceSequenceLoader`** *(implements `SequenceLoader`)*: el detalle de bajo nivel; lee un recurso del classpath, limpia las líneas y produce la `List<BatteryBank>`. Es intercambiable por otra fuente de datos.
* **`Main03A` / `Main03B`** *(composition root)*: el único punto donde se eligen el cargador y la estrategia y se conectan con el Builder. La Parte A y la Parte B se diferencian solo en la lambda inyectada (`Greedy(s, 2)` frente a `Greedy(s, 12)`).

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** `StaircaseController` depende de la abstracción `EnergyProtocol`, no del algoritmo concreto; el arranque depende de la interfaz `SequenceLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** cambiar de la Parte A a la B (o pedir 50 dígitos) es inyectar otra lambda; `StaircaseController` queda cerrado a modificación y abierto a extensión por polimorfismo.
* **Responsabilidad Única (SRP):** `BatteryBank` solo guarda datos, `StaircaseController` solo suma, `SearchStrategies` solo calcula, `ResourceSequenceLoader` solo lee I/O, `StaircaseBuilder` solo ensambla.
* **Patrón Builder + interfaz fluida:** `StaircaseBuilder` arma el controlador paso a paso y valida que esté completo antes de crearlo.
* **Segregación de Interfaces (ISP):** `SequenceLoader` expone un único método cohesivo (`loadAll`).
* **Inyección de Dependencias (DI) / Strategy:** el cargador y la estrategia se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Bajo Acoplamiento y DRY:** el procesamiento es independiente del algoritmo de selección, y la lectura de entrada está centralizada en una sola implementación reutilizable.

#### **5\. Conclusión**

La separación en capas mantiene el dominio en el centro y empuja los detalles (I/O, arranque) a los bordes. El Builder aísla la construcción del I/O, y el `EnergyProtocol` permite manejar lógica algorítmica (Greedy) de forma limpia: cambiar la configuración de la escalera (Parte A vs Parte B) es solo cambiar la estrategia inyectada, sin tocar el núcleo. El resultado tiene bajo acoplamiento y es fácil de probar (los tests construyen `StaircaseController` y `BatteryBank` directamente y llaman al Greedy sin tocar ficheros).
