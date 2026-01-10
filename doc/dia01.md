### **Día 1 \- Entrada Secreta**

#### **1\. Introducción y Problema**

El problema plantea simular una caja fuerte con un dial circular (0-99). Se empieza en 50; si vas a la izquierda (L10) restas, y si vas a la derecha (R10) sumas. El reto tiene dos partes que cambian la regla de cómo se obtiene la contraseña:

* **Parte A**: La contraseña depende de dónde termina el dial tras una rotación.
* **Parte B:** La contraseña cuenta cada vez que el dial pasa por el cero durante el giro.

#### **2\. Arquitectura General y principios**

* **Inversión de Dependencias (DIP):** (Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones).En lugar de que mi clase CajaFuerte dependa de la lógica concreta de la Parte A o B, la hice depender de una abstracción: la interfaz funcional ProtocoloSeguridad. Esto significa que la caja fuerte no sabe cómo se calculan los puntos, solo sabe que tiene un componente que lo hace.


* **Principio Abierto/Cerrado (OCP):** (Las clases deben estar abiertas para la extensión, pero cerradas para la modificación).Gracias a la abstracción anterior (ocultar los detalles complejos detrás de una interfaz simple), cumplo este principio. Mi clase CajaFuerte está cerrada a modificaciones (no necesito tocar su código si cambian las reglas), pero abierta a la extensión. Simplemente inyecto la lógica que necesito en el constructor y el sistema funciona por polimorfismo, sin tocar el núcleo de la aplicación.


* **Principio de Responsabilidad Única (SRP):** (Cada módulo o clase debe tener una sola razón para cambiar). Busqué que cada clase tuviera una sola razón para existir y para cambiar:
  * **CargadorEntrada (Principio DRY):** (Cada pieza de conocimiento debe tener una representación única). Su única responsabilidad es lidiar con el I/O, centralizando la lectura para no repetirla.
  * **Dial (Alta Cohesión):** (Partes estrechamente relacionadas enfocadas en una tarea única). Se centra exclusivamente en las matemáticas circulares (normalizar de 0 a 99). No sabe nada de ficheros ni de estrategias.
  * **ProtocolosSeguridad (Clase de Utilidad):** Agrupa las implementaciones de las reglas (PART\_A y PART\_B) como métodos estáticos para funciones comunes y reutilizables, separando la lógica pura del estado.

#### **3\. Conclusión**

Al utilizar todos los principios mencionados, logramos un sistema con Bajo Acoplamiento (pocas interdependencias), fácil de probar y preparado para futuros cambios sin riesgo de romper la funcionalidad existente.