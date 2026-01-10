### **Día 7 \- Laboratorios**

#### **1\. Introducción y Problema**

El problema nos sitúa en un laboratorio donde debemos reparar un teletransportador analizando una rejilla llamada "colector de taquiones". Un haz de partículas entra por un punto inicial (S) y viaja hacia abajo. Si encuentra espacio vacío (.), sigue avanzando. Si encuentra un divisor (^), se detiene y genera nuevos haces hacia la izquierda y la derecha en la siguiente capa.  
El reto se divide en dos interpretaciones físicas que requieren gestionar el estado de la simulación de formas distintas:

* **Parte A:** Simulamos un haz clásico continuo. Se busca contar cuántas veces ocurre el evento de "división" (un haz golpeando un ^).
* **Parte B:** Interpretación cuántica (Multiverso). Cada vez que una partícula golpea un divisor, el tiempo se bifurca. Si dos trayectorias convergen, sus líneas temporales (intensidad) se suman aritméticamente. El objetivo es contar la intensidad total acumulada al final del recorrido.

#### **2\. Arquitectura General y principios**

* **Patrón Builder (ConstructorRejilla):** (Permite crear el objeto paso a paso en lugar de hacerlo todo de golpe en un constructor gigante). He separado la complejidad de parsear el texto de entrada (convertir caracteres ASCII en objetos del dominio) de la lógica de simulación. **ConstructorRejilla** acumula las líneas de texto una a una y finalmente construye la estructura de datos List\<List\<Celda\>\> en un estado válido.
* **Inmutabilidad y Programación Funcional:**  
  El núcleo de la solución, **SimuladorTaquiones**, es un Record inmutable. En lugar de modificar el estado de la rejilla actual, el método **simularSiguienteCapa** genera una nueva instancia del simulador con el estado actualizado de la siguiente fila. Esto evita efectos secundarios y simplifica el rastreo de la simulación capa por capa.
* **Principio de Responsabilidad Única (SRP): (Cada módulo o clase debe tener una sola razón para cambiar).**  
  He distribuido las responsabilidades para maximizar la cohesión:
  * **CargadorEntrada (Principio DRY): (No repetir código)**. Centraliza la gestión del I/O y el manejo de excepciones, delegando la construcción al Builder.
  * **Celda (Alta Cohesión):** (Partes estrechamente relacionadas enfocadas en una tarea). Es un record inteligente que no solo guarda datos, sino que centraliza la lógica de creación mediante Métodos de Factoría (haz(), divisor()) y encapsula su propio comportamiento (esHaz, esDivisor), eliminando la necesidad de comparar caracteres en la lógica principal.
  * **SimuladorTaquiones:** Contiene exclusivamente las reglas de propagación física (cómo se mueve el haz a la izquierda/derecha). Es agnóstico a si estamos en la Parte A o B, ya que simplemente propaga intensidades.
  * **ControladorLaboratorio (Abstraccion):** Actúa como punto de entrada simplificado. Oculta la complejidad del simulador al cliente (Main), exponiendo solo los métodos de negocio (contarDivisiones, contarLineasTemporales).
* **Abstracción (DIP implícito):**  
  A través del método Celda.desdeCaracter, el sistema se desacopla de la representación visual. El resto de la aplicación trabaja con conceptos abstractos (TipoCelda) en lugar de caracteres primitivos, facilitando cambios futuros en el formato de entrada.
* **Algoritmo de Simulación por Capas (BFS \- Breadth First Search):** En lugar de usar recursividad (DFS) que podría desbordar la pila en rejillas grandes, proceso la simulación capa por capa de arriba a abajo. Esto es eficiente porque el estado de la capa N depende exclusivamente de la capa N-1. Para la Parte B: Uso aritmética de long para sumar intensidades. Si un haz con intensidad 5 y otro con 3 convergen, la celda resultante tendrá intensidad 8\. Esto resuelve el problema combinatorio sin necesitar memoria exponencial.

#### **3\. Conclusión**

La solución destaca por transformar un problema de recorrido complejo en una simulación lineal capa por capa (BFS) mediante el uso de Estado Inmutable. La separación entre la estructura de datos (Celda) y el motor de lógica (Simulador) permite resolver tanto el caso clásico como el cuántico reutilizando el mismo motor de propagación, garantizando un código robusto y mantenible.