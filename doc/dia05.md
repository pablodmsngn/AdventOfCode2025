### **Día 5 \- Cafetería**

#### **1\. Introducción y Problema**

El problema nos sitúa en la cafetería de los elfos, donde una base de datos de inventario corrupta impide distinguir los ingredientes frescos. La entrada consta de una lista de rangos de frescura (ej. "3-5", "10-20") y una lista de IDs de ingredientes específicos.  
El reto se divide en dos partes que requieren enfoques algorítmicos distintos:

* Parte A: Determinar cuántos de los IDs de la lista específica caen dentro de al menos un rango de frescura.
* Parte B: Ignorar la lista de IDs específica y calcular la cobertura total de los rangos. Es decir, cuántos números enteros únicos están cubiertos por la unión de todos los rangos, teniendo en cuenta que estos pueden solaparse (ej. "10-15" y "12-20" deben fusionarse para no contar dos veces los números compartidos).

#### **2\. Arquitectura General y principios**

* **Inversión de Dependencias (DIP):** (Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones). La clase principal **AuditorInventario** no contiene la lógica de validación.. En su lugar, depende de la abstracción **ProtocoloFrescura** (Interfaz Funcional). Esto permite definir qué significa "ser fresco" (Parte A) o inyectar un comportamiento nulo (Parte B) sin modificar la clase auditora.
* **Principio Abierto/Cerrado (OCP):** (Las clases deben estar abiertas para la extensión, pero cerradas para la modificación). Gracias al diseño anterior, **AuditorInventario** está cerrado a cambios. Si mañana la regla de frescura cambia (ej. excluir los números pares), simplemente inyectamos una nueva lambda en el Main que implemente la nueva regla, sin tocar el núcleo del sistema.
* **Patrón Builder (ConstructorAuditoria):** (Permite crear el objeto paso a paso en lugar de hacerlo todo de golpe en un constructor gigante). He separado la compleja lógica de parseo del archivo de texto (separar rangos de IDs sueltos) de la lógica de negocio. **ConstructorAuditoria** configura paso a paso el Stream, el protocolo y finalmente construye el objeto AuditorInventario en un estado válido y consistente.
* **Principio de Responsabilidad Única (SRP):** (Cada módulo o clase debe tener una sola razón para cambiar).  
  He distribuido las responsabilidades para maximizar la cohesión:
  * **CargadorEntrada (Principio DRY):** (No repetir código). Centraliza la gestión del I/O (lectura de recursos) y el uso del Builder, evitando duplicidad en los Main.
  * **Rango (Alta Cohesión):** (Partes estrechamente relacionadas enfocadas en una tarea). Es un record que encapsula toda la matemática de intervalos. Sabe si contiene un número, si se solapa con otro rango y cómo fusionarse con otro. Al mover esta lógica aquí, el auditor queda limpio.
  * **AuditorInventario:** Orquesta el proceso. Para la Parte B, implementa el algoritmo de **fusión de intervalos** (ordenar y unir rangos superpuestos) para calcular la cobertura total eficientemente.
  * **ProtocoloFrescura (Abstracción)**(Consiste en ocultar los detalles complejos detrás de una interfaz  
    simple.): Define el contrato funcional para verificar la validez de un ID.

#### **3\. Conclusión**

El uso del patrón Builder facilita la carga de datos heterogéneos (rangos mezclados con IDs), mientras que DIP mantiene el sistema desacoplado y flexible ante cambios en las reglas de validación.
