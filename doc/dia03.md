
### **Día 3 \- Vestíbulo**

#### **1\. Introducción y Problema**

El escenario es el vestíbulo principal donde las escaleras mecánicas necesitan energía de emergencia. Disponemos de bancos de baterías representados por secuencias de dígitos (ej. "98765..."). El objetivo es seleccionar una sub-secuencia de dígitos para formar el número(sacudida) más alto posible, respetando el orden de aparición. El reto se divide en dos partes:

* **Parte A:** Debemos seleccionar exactamente 2 baterías (dígitos) de cada banco para maximizar la energía.
* **Parte B:** La fricción estática requiere más potencia, por lo que debemos seleccionar exactamente 12 baterías de cada banco, siguiendo la misma lógica de maximización.

Para resolver esto, he implementado un algoritmo Greedy (Voraz) que busca el dígito más alto posible en cada paso, asegurándose siempre de que queden suficientes dígitos a la derecha para completar la longitud requerida (N).

#### **2\. Arquitectura General y principios**

* **Inversión de Dependencias (DIP):** (Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones).La clase **ControladorEscalera** (alto nivel) no contiene la lógica del algoritmo voraz. En su lugar, depende de la interfaz funcional **ProtocoloEnergia**. Esto permite que el controlador calcule la energía total sin saber si está resolviendo la Parte A (2 dígitos) o la Parte B (12 dígitos).
* **Principio Abierto/Cerrado (OCP):** (Las clases deben estar abiertas para la extensión, pero cerradas para la modificación).Gracias al diseño anterior, la clase **ControladorEscalera** queda cerrada a modificaciones. Si cambian los requisitos (ej. seleccionar 50 dígitos), solo extiendo la funcionalidad inyectando una nueva lambda en el Main que configure el algoritmo Greedy con el nuevo parámetro, sin tocar el código fuente del controlador.
* **Patrón Builder (Separación de Construcción y Representación)(Permite crear el objeto paso a paso en lugar de hacerlo todo de golpe en un constructor gigante.):**  
  He implementado la clase **ConstructorEscalera** para gestionar la creación del objeto **ControladorEscalera**.
   * Esto permite una configuración paso a paso (from, use, construir) y valida que todos los componentes necesarios (fichero y protocolo) estén presentes antes de instanciar el objeto, garantizando un estado consistente.
* **Principio de Responsabilidad Única (SRP):** (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).  
  Cada componente tiene un foco único:
   * **CargadorEntrada (Principio DRY):** (Cada pieza de conocimiento... debería tener una representación única). Centraliza la lógica de I/O y uso del Builder para no duplicar código en los Mains.
   * **BancoBaterias (Alta Cohesión): (Enfocadas en una única tarea )**. Es un record que solo almacena la secuencia de datos del banco (Value Object).
   * **EstrategiasBusqueda (Algoritmia greedy):** Contiene la lógica matemática del algoritmo Greedy como un método estático, separando el "cómo calcular" del "quién tiene los datos".

#### **3\. Conclusión**

La arquitectura demuestra cómo patrones como el Builder y principios como DIP permiten manejar lógica algorítmica compleja (Greedy) de forma limpia. El sistema resultante tiene un Bajo Acoplamiento, es fácil de testear y permite cambiar la configuración de la escalera (Parte A vs Parte B) simplemente cambiando la estrategia inyectada.