
### **Día 12 \- Granja de árboles de Navidad**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una caverna bajo el Polo Norte, una granja de árboles de Navidad donde los elfos intentan colocar regalos bajo los árboles. Los regalos tienen formas geométricas irregulares (poliominós) y las zonas bajo los árboles son cuadrículas de tamaños específicos (ej. 4x4, 12x5).  
El reto es un problema clásico de Backtracking y Empaquetado (Tiling):

* Se nos proporciona un catálogo de formas de regalos.
* Se nos da una lista de regiones y qué cantidad de cada regalo debe caber en ella.
* Los regalos pueden rotarse (90º, 180º...) y voltearse (espejo), pero no pueden superponerse ni salirse de los límites.
* El objetivo es determinar cuántas de estas configuraciones son solubles, es decir, en cuántas regiones caben todos los regalos asignados sin colisiones.

#### **2\. Arquitectura General y principios**

* **Patrón Factory Method: (En lugar de usar directamente el constructor de una clase para crear objetos, se llama a un método estático que encapsula la creación del objeto).**
    * He aplicado este patrón en CargadorEntrada. El método estático CargadorEntrada.cargar(String) encapsula la compleja lógica de parseo del archivo de entrada (que tiene dos secciones: definición de formas y definición de problemas), entregando al cliente un ControladorGranja listo para usar.
* **Principio de Responsabilidad Única (SRP): (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).**
    * **SolucionadorGranja**: Su única responsabilidad es algorítmica. Contiene la lógica pura de backtracking para encajar piezas. Decide dinámicamente si usar optimización con long (para regiones pequeñas \< 64 celdas) o BitSet (para grandes), pero no sabe nada de ficheros ni de la estructura del problema global.
    * **CargadorEntrada (Principio DRY):** (Cada pieza de conocimiento en un software debería tener una representación única inequívoca). Centraliza la lectura del fichero y el manejo de excepciones, evitando duplicar esta lógica en el Main o el controlador.
* **Fundamento de Alta Cohesión: (Refiere a la idea de que las partes de un módulo o componente deben estar estrechamente relacionadas y enfocadas en una única tarea).**
    * **Forma**: Es un ejemplo perfecto de alta cohesión. No solo almacena la lista de puntos (List\<Coordenada\>), sino que encapsula toda la lógica geométrica necesaria: rotar(), voltear(), normalizar() y generarVariaciones(). Al mantener los datos y las transformaciones geométricas juntos, el solucionador no tiene que calcular matemáticas vectoriales, solo pedir las variantes.
* **Abstracción: (Consiste en ocultar los detalles complejos detrás de una interfaz simple).**
    * **ControladorGranja**: Actúa como una fachada. Oculta la complejidad de iterar sobre múltiples problemas y la instanciación del **SolucionadorGranja**. El Main simplemente llama a **contarRegionesValidas**(), ignorando si el algoritmo usa máscaras de bits o recursividad.
* **Inmutabilidad y Value Objects:**
    * He utilizado records de Java (Coordenada, Forma, Region, DefinicionProblema) para modelar los datos. Esto es crucial en el algoritmo de backtracking, donde se generan miles de variantes de formas. Al ser inmutables, garantizamos que las rotaciones y traslaciones generen nuevas instancias sin corromper las formas originales del catálogo.

#### **3\. Conclusión**

La separación entre la geometría (Forma) y la lógica de resolución (SolucionadorGranja) permite optimizaciones de bajo nivel (como el uso de máscaras de bits long para acelerar el chequeo de colisiones) sin afectar la legibilidad del resto del sistema. El uso de SRP y Factory Method en la carga de datos mantiene el código organizado y robusto frente al complejo formato de entrada.