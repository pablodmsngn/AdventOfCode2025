
### **Día 9 \- Cine**

#### **1\. Introducción y Problema**

El problema nos sitúa en el cine de la base del Polo Norte, que tiene un suelo de baldosas rojas y de otros colores. Se nos proporciona una lista de coordenadas de las baldosas rojas. El objetivo es encontrar el rectángulo más grande posible utilizando dos baldosas rojas como esquinas opuestas.  
El reto se divide en dos partes que varían en las restricciones geométricas:

* **Parte A:** Calcular el área máxima posible formando un rectángulo con cualquier par de baldosas rojas de la lista, sin importar qué hay en medio.
* **Parte B:** Las baldosas rojas forman el contorno de un polígono (conectadas por baldosas verdes). El rectángulo seleccionado debe ser válido, es decir, debe estar contenido completamente dentro de este polígono (solo puede pisar baldosas rojas o verdes). Esto requiere verificar intersecciones y contención geométrica.

#### **2\. Arquitectura General y principios**

* **Principio de Responsabilidad Única (SRP): (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).**  
  He distribuido las responsabilidades para maximizar la organización del código:
  * **CargadorEntrada (Principio DRY): (No repetir código).** Centraliza la gestión del I/O (lectura de recursos) y el manejo de excepciones, evitando duplicar la lógica de carga en los Main de cada parte.
  * **BuscadorRectangulos (Lógica Algorítmica):** Su única responsabilidad es la búsqueda combinatoria. Genera todos los rectángulos posibles y aplica los filtros de validez (geometría de polígonos) para encontrar el óptimo.
  * **ControladorCine (Adstraccion, Consiste en ocultar los detalles complejos detrás de una interfaz simple)**oculta la complejidad del algoritmo combinatorio (BuscadorRectangulos) detrás de un método simple obtenerAreaMaxima(). El Main no sabe cómo se buscan los rectángulos, solo pide el resultado.
* **Fundamento de Alta Cohesión: (Las partes de un módulo deben estar estrechamente relacionadas y enfocadas en una única tarea).**
  * **Rectangulo:** Es un Record que actúa como Value Object. No solo almacena dos coordenadas, sino que encapsula toda la lógica matemática relacionada con su forma: cálculo de área, ancho, alto y obtención de límites (minX, maxY). Esto evita dispersar cálculos geométricos básicos por el resto de la aplicación.
* **Patrón Factory Method: (En lugar de usar directamente el constructor... se llama a un método estático que encapsula la creación del objeto).**
  * Implementado en CargadorEntrada.cargar: Encapsula la complejidad de abrir el InputStream y leer las líneas del fichero.
  * Implementado en Coordenada.desde(String): Encapsula el parseo de la cadena de texto (ej. "7,1") para convertirla en un objeto Coordenada válido, centralizando la lógica de transformación de datos.
* **Inmutabilidad (Records):**  
  He utilizado records para las estructuras de datos fundamentales (Coordenada, Rectangulo).
  * Al ser inmutables, facilitan el procesamiento paralelo y el uso de Streams en la clase BuscadorRectangulos, asegurando que las coordenadas no sean modificadas accidentalmente durante la generación masiva de combinaciones.

#### **3\. Conclusión**

Se utiliza una arquitectura centrada en el dominio geométrico. El uso de Value Objects ricos (Rectangulo) y la separación de la lógica de validación espacial (BuscadorRectangulos) permiten resolver un problema de optimización combinatoria manteniendo un código limpio y legible. La aplicación de Factory Methods simplifica la creación de objetos desde la entrada de datos.
