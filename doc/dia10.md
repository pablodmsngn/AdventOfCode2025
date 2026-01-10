
### **Día 10 \- Fábrica**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una fábrica donde debemos reparar máquinas complejas. Cada máquina tiene un conjunto de botones y un panel de indicadores con luces y voltajes asociados. Al pulsar un botón, se alteran estados específicos (luces encendidas/apagadas) y se incrementan los voltajes en ciertos componentes.  
El reto se divide en dos partes de optimización combinatoria:

* **Parte A:** Encontrar la secuencia mínima de pulsaciones de botones para que las luces coincidan exactamente con una configuración objetivo, ignorando los voltajes (resuelto mediante BFS).
* **Parte B:** El problema se complica; debemos cumplir requisitos estrictos de voltaje acumulado. Esto requiere una búsqueda recursiva más profunda, resolviendo primero la paridad (luces) y luego ajustando los voltajes restantes matemáticamente.

#### **2\. Arquitectura General y principios**

* **Patrón Factory Method: (En lugar de usar directamente el constructor... se llama a un método estático que encapsula la creación del objeto).**  
  He aplicado este patrón en todas las clases de dominio (Boton, Maquina, Indicador, Estado) utilizando métodos estáticos desde() o deCaracter().
  * Por ejemplo, Boton.desde(String str) encapsula la lógica de "parsear" una cadena de texto compleja y **convertirla en un objeto válido. Esto limpia el código cliente y centraliza la lógica de creación.**
* **Principio de Responsabilidad Única (SRP): (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).**  
  He distribuido las responsabilidades para cumplir este principio:
  * **CargadorEntrada (Principio DRY):** (Cada pieza de conocimiento... debería tener una representación única). Su única responsabilidad es la infraestructura de I/O (lectura de ficheros), evitando mezclar lógica de negocio con acceso a disco.
  * **Maquina (Lógica de Negocio):** Centraliza los algoritmos de resolución (BFS para la Parte A y Recursividad para la Parte B). No sabe leer ficheros ni imprimir resultados, solo calcular costes.
  * **ControladorFabrica (Abstracción): (Consiste en ocultar los detalles complejos detrás de una interfaz simple).** Oculta la complejidad de la iteración y la agregación de resultados **(uso de Streams y sumatorios)** detrás de métodos directos como **ejecutarParte1**() y **ejecutarParte2**(). El Main no sabe cómo se resuelven los algoritmos de cada máquina (BFS o recursividad), solo pide el resultado total acumulad
* **Fundamento de Alta Cohesión: (Refiere a la idea de que las partes de un módulo o componente deben estar estrechamente relacionadas y enfocadas en una única tarea).**
  * **Indicador**: Es un ejemplo claro. Agrupa datos estrechamente ligados (estados de luces y voltajes). Sus métodos (reducirVoltajesCon, mitadVoltajes) operan exclusivamente sobre estos datos para generar nuevos estados, sin dependencias externas.
  * **Boton**: Solo contiene el conjunto de índices que afecta. Es un componente atómico enfocado en una sola tarea.
* **Inmutabilidad y Value Objects:**  
  He utilizado records de Java (Boton, Indicador, Maquina) para garantizar la inmutabilidad.  
  En algoritmos de búsqueda (como el BFS en Maquina.java), **es crucial que el estado (Indicador) no cambie inesperadamente**. Métodos como **aplicarBoton** devuelven una nueva instancia de Indicador en lugar de modificar el existente, previniendo efectos secundarios (Side Effects) y facilitando la recursividad segura.
* **Abstracción: (Consiste en ocultar los detalles complejos detrás de una interfaz simple).**  
  A través del Enum **Estado** y su método deCaracter, ocultamos la representación de bajo nivel (\# o .) detrás de conceptos semánticos (ENCENDIDO, APAGADO). El resto del sistema trabaja con estados lógicos, no con caracteres.

#### **3\. Conclusión**

El uso extensivo de Factory Methods facilita la creación de objetos desde texto plano, mientras que la separación de responsabilidades permite que el algoritmo de resolución (Maquina) sea independiente de la entrada de datos (Cargador), resultando en un sistema robusto, mantenible y libre de efectos secundarios gracias a la inmutabilidad.
