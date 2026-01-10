### **Día 11 \- Reactor**

#### **1\. Introducción y Problema**

El escenario nos sitúa en una fábrica donde un gran reactor toroidal tiene problemas de conexión con un rack de servidores. Los elfos nos proporcionan una lista de dispositivos y sus conexiones unidireccionales (un grafo dirigido).  
El reto consiste en analizar el flujo de datos a través de esta red de dispositivos:

* **Parte A:** Calcular el número total de rutas distintas desde un punto de inicio (you) hasta la salida del reactor (out).
* **Parte B:** Debido a un problema específico, debemos encontrar el número de rutas desde el servidor (svr) hasta la salida (out) que obligatoriamente pasen por dos nodos intermedios específicos: un convertidor (dac) y una transformada (fft), en cualquier orden.

#### **2\. Arquitectura General y principios**

* **Principio de Responsabilidad Única (SRP): (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).**  
  He distribuido las responsabilidades para cumplir este principio:
  * **CargadorEntrada (Principio DRY): (Cada pieza de conocimiento... debería tener una representación única).** Su única responsabilidad es la infraestructura de I/O y el parseo del grafo, centralizando el manejo de recursos.
  * **AnalizadorRutas:** Se centra únicamente en la algoritmia de grafos (DFS y combinatoria). No almacena el estado global de la aplicación ni gestiona la entrada/salida.
  * **ControladorReactor:** Actúa como coordinador, delegando el cálculo complejo al analizador y manteniendo el estado del grafo (conexiones).
* **ControladorReactor (Abstracción): (Consiste en ocultar los detalles complejos detrás de una interfaz simple).**  
  Oculta la complejidad del algoritmo de Búsqueda en Profundidad (DFS) y la memorización. El Main simplemente llama a métodos de negocio como **contarRutasTotales**(), ignorando cómo se recorre el grafo o cómo se multiplican los segmentos de ruta.
* **Fundamento de Alta Cohesión: (Refiere a la idea de que las partes de un módulo o componente deben estar estrechamente relacionadas y enfocadas en una única tarea).**
  * **AnalizadorRutas**: Es el mejor ejemplo. Todos sus métodos (contarRutas, multiplicarCaminos, dfs) están enfocados en resolver problemas de conectividad matemática. Al aislar esta lógica, evitamos dispersar el algoritmo recursivo por otras clases.
* **Patrón Factory Method: (En lugar de usar directamente el constructor... se llama a un método estático que encapsula la creación del objeto).** Implementado en **CargadorEntrada.cargar(String).** Este método estático encapsula la complejidad de localizar el archivo en el classpath, instanciar el lector y construir el Map de conexiones, entregando al cliente una instancia de **ControladorReactor** totalmente configurada.
* **Principio de no repetir código (DRY): (Cada pieza de conocimiento en un software debería tener una representación única inequívoca).** En la Parte 2, en lugar de duplicar el algoritmo de búsqueda para incluir nodos intermedios, reutilizamos la lógica de la Parte 1\. El método **contarRutasConIntermedios** reutiliza contarRutas descomponiendo el problema en segmentos (A-\>B, B-\>C) y multiplicando sus resultados, optimizando el desarrollo y el mantenimiento.

#### **3\. Conclusión**

La solución aplica una arquitectura modular que separa claramente la persistencia (Cargador), la lógica de control (Controlador) y la algoritmia pura (Analizador). El uso de técnicas como la memorización (guardar resultados parciales en un Map) dentro de AnalizadorRutas optimiza drásticamente el rendimiento, permitiendo resolver problemas combinatorios masivos sin desbordar la memoria ni el tiempo de ejecución.
