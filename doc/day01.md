### **Día 1 \- Entrada Secreta**

#### **1\. Introducción y Problema**

El problema plantea simular una caja fuerte con un dial circular (0-99). Se empieza en 50; si vas a la izquierda (L10) restas, y si vas a la derecha (R10) sumas. El reto tiene dos partes que cambian la regla de cómo se obtiene la contraseña:

* **Parte A:** La contraseña depende de **dónde termina** el dial tras cada rotación: suma 1 cada vez que un giro acaba exactamente en 0.
* **Parte B:** La contraseña cuenta **cuántas veces el dial pasa por el cero** durante el giro (no solo dónde acaba, sino cada cruce intermedio).

Ambas partes leen la misma entrada y comparten toda la mecánica del dial; lo único que cambia es la regla de puntuación. Esa observación es la que guía el diseño: si lo único que varía es una regla, esa regla debe poder enchufarse desde fuera sin tocar el resto.

#### **2\. Arquitectura por capas**

He reorganizado el día en **tres capas** (más la frontera `common.io` compartida), de modo que las dependencias apuntan siempre hacia el dominio y nunca al revés:

```
software.ulpgc.aoc
├── common.io     (entrada compartida por TODOS los días)
│   ├── LineLoader          (puerto: List<String> loadLines())
│   └── ResourceLineLoader  (adaptador: lee el recurso del classpath)
└── day01
    ├── model         (dominio puro, no depende de nadie)
    │   ├── Dial
    │   ├── Instruction
    │   └── SecurityProtocol
    ├── control       (orquesta el caso de uso)
    │   ├── Safe
    │   └── SecurityProtocols
    └── application   (detalles y arranque)
        ├── InputLoader   (parsea las líneas al dominio)
        └── a/Main01a, b/Main01b
```

**Dirección de dependencias:** `application → control → model` y `application → common.io`. El dominio (`model`) no importa ninguna otra capa; el loader compartido (`common.io`) tampoco depende de nadie.

#### **3\. Explicación clase a clase**

**Capa `model` (dominio puro)**

* **`Dial`** *(record inmutable)*: representa la rueda y su única tarea es la aritmética circular. Su constructor normaliza siempre la posición al rango 0-99, y `rotate(int)` devuelve un **nuevo** `Dial` en vez de mutar el actual. No sabe nada de ficheros ni de reglas de puntuación → **alta cohesión**.
* **`Instruction`** *(record)*: representa una orden de giro ("L10", "R48"). Concentra el parseo y la validación en `of(String)`, que devuelve un `Optional` y descarta entradas inválidas (nulos, vacíos, basura). Expone `movement()` (L resta, R suma). Sacar esto de la caja fuerte es lo que le da a `Safe` una sola responsabilidad.
* **`SecurityProtocol`** *(interfaz funcional)*: la **abstracción** de la regla de puntuación (`calculatePoints(oldDial, movement, newDial)`). Vive en el dominio porque solo habla el idioma del dominio (`Dial`) y no depende de ninguna otra capa. Es la pieza que permite el DIP.

**Frontera de entrada (compartida: `common.io`)**

* **`LineLoader`** *(interfaz, puerto)* y **`ResourceLineLoader`** *(adaptador)*: viven en el paquete común `software.ulpgc.aoc.common.io` y los reutilizan **todos los días**. `loadLines()` devuelve las líneas crudas del recurso (`List<String>`); el parseo al dominio ocurre después, en la capa `application` (Main01a/b parsean con InputLoader). Así se centraliza la lectura (una sola implementación, sin duplicar) y se separa de la interpretación (SRP).

**Capa `control` (orquesta el caso de uso)**

* **`Safe`**: el corazón del caso de uso. Mantiene el estado (dial actual y contador) y, por cada instrucción, gira el dial y delega la puntuación en el `SecurityProtocol` inyectado. No lee ficheros ni parsea texto: recibe `Instruction` ya validadas. `rotate(String)` se conserva por comodidad/robustez y delega en `Instruction.of`.
* **`SecurityProtocols`**: agrupa las dos estrategias concretas (`PART_A`, `PART_B`) como constantes reutilizables. Añadir una regla nueva es crear otra constante, sin tocar `Safe`.

**Capa `application` (detalles y arranque)**

* **`InputLoader`** *(fachada de ensamblaje, en `application`)*: usa el `ResourceLineLoader` compartido para leer las líneas y las parsea al dominio antes de construir el caso de uso.
* **`Main01a` / `Main01b`** *(composition root)*: el único punto donde se eligen las piezas concretas (qué cargador y qué protocolo) y se conectan por inyección. La Parte A y la Parte B se diferencian solo en la estrategia inyectada.

#### **4\. Principios y diseños aplicados**

* **Inversión de Dependencias (DIP):** `Safe` depende de la abstracción `SecurityProtocol`, no de la lógica concreta de A o B; el arranque depende de `InstructionLoader`, no de cómo se leen los datos.
* **Abierto/Cerrado (OCP):** añadir una regla es inyectar otra estrategia; `Safe` queda cerrada a modificación y abierta a extensión por polimorfismo.
* **Responsabilidad Única (SRP):** `Dial` solo hace aritmética, `Instruction` solo parsea/valida, `Safe` solo orquesta, `ResourceLineLoader` (compartido) solo lee I/O.
* **Segregación de Interfaces (ISP):** el puerto compartido `LineLoader` expone un único método cohesivo (`loadLines`).
* **Inyección de Dependencias (DI) / Patrón Strategy:** el protocolo y el cargador se pasan desde fuera; el comportamiento se elige sin tocar el núcleo.
* **Bajo Acoplamiento y Alta Cohesión:** las capas se comunican por abstracciones y cada clase agrupa lo que está estrechamente relacionado.
* **DRY:** la lectura de entrada está centralizada en una sola implementación reutilizable.
* **Tell, Don't Ask / encapsulamiento:** se le ordena a `Safe` que aplique una instrucción (`apply`) en vez de preguntarle su estado para decidir fuera; el `Dial` es inmutable y normaliza en su propio constructor.
