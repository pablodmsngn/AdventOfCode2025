### **Día 2 \- Tienda de Regalos**

#### **1\. Introducción**

El problema nos sitúa en una tienda de regalos con una base de datos corrupta. Tenemos rangos de IDs de productos (ej. "10-20") y debemos encontrar cuáles son inválidos y sumarlos. El reto tiene dos partes que cambian la definición de "inválido":

* **Parte A:** Un ID es inválido si contiene una secuencia repetida exactamente dos veces (ej: 1212).
* **Parte B:** Un ID es inválido si la secuencia se repite al menos dos veces (ej: 121212 también cuenta).

#### **2\. Arquitectura General y principios**

* **Inversión de Dependencias (DIP):** (Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones ). En lugar de que mi clase Motor dependa de una lógica de validación concreta (como un if dentro del código), la hice depender de una abstracción: la interfaz funcional LongPredicate(Interfaz Funcional nativa de Java, siempre recibe un long y devuelve true si cumple la regla). Esto significa que el Motor no sabe cómo se validan los IDs (si es por regex o matemáticas), solo sabe que tiene un componente externo que le dice si es válido o no.

* **Principio Abierto/Cerrado (OCP):** (Las clases deben estar abiertas para la extensión, pero cerradas para la modificación). Gracias a la abstracción anterior, mi clase **Motor** está cerrada a modificaciones (no necesito tocar su código para cambiar de la Parte A a la B). Está abierta a la extensión porque simplemente inyecto una estrategia diferente (PATRON\_A o PATRON\_B) en el constructor y el sistema cambia de comportamiento sin alterar el procesador central.

* **Principio de Responsabilidad Única (SRP):** (Cada módulo o clase debe tener una sola razón para cambiar ).  
  He dividido el sistema para que cada clase tenga un propósito único:
  * **CargadorEntrada** (Principio DRY): (No repetir código ). Su única responsabilidad es lidiar con el I/O (lectura de ficheros) y orquestar la creación del motor, evitando duplicar esta lógica en los Main.
  * **ConstructorMotor** (Patrón Builder)(Permite crear el objeto paso a paso en lugar de hacerlo todo de golpe en un constructor gigante.): Implementa el Patrón Builder para configurar el objeto Motor paso a paso (fichero y estrategia). Esto ofrece una interfaz fluida y aísla la complejidad de la construcción de la lógica de negocio, asegurando que el objeto nunca se cree incompleto.
  * **RangoID (Alta Cohesión):** (Partes estrechamente relacionadas enfocadas en una tarea). Se centra exclusivamente en el dominio de los rangos numéricos: sabe expandirse a sí mismo en un flujo de números (LongStream). No sabe nada de validaciones.
  * **EstrategiasValidacion** (Clase Utilidad): Agrupa las reglas de negocio (Regex) en constantes estáticas. Su única razón para cambiar es si se modifican las reglas de qué constituye un ID **inválido.**

#### **3\. Conclusión**

Al utilizar Streams y Expresiones Regulares bajo estos principios, he evitado la complejidad de los bucles anidados. El resultado es un sistema con Bajo Acoplamiento, donde la lógica de procesamiento (Motor) es totalmente independiente de las reglas de validación, facilitando el mantenimiento y las pruebas.
