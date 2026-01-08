
### Parte 1: Abstracción y Filtrado de Inventario

En la primera fase, el objetivo era procesar un inventario de ingredientes separando la lógica de interpretación de datos (parsing de rangos e IDs) de la lógica de negocio (determinar la frescura). La prioridad fue establecer una arquitectura desacoplada que permitiera cambiar las reglas de validación sin afectar la lectura del fichero.

**¿Qué hicimos?**

1. Definimos la interfaz funcional `ProtocoloFrescura` (el contrato) para abstraer la regla de validación, permitiendo diferentes criterios de frescura en el futuro.
2. Implementamos el record `Rango` como un objeto de valor inmutable con **Alta Cohesión**, encapsulando tanto los límites numéricos como la lógica de contención.
3. Creamos `AuditorInventario` como controlador principal para orquestar la validación, delegando la decisión en el protocolo inyectado.
4. Implementamos `ConstructorAuditoria` (Builder) para encapsular la complejidad de leer un archivo con dos secciones distintas (rangos e IDs) separadas por una línea en blanco.

**Principios y Fundamentos aplicados:**

* **Principio de Responsabilidad Única (SRP):**
  El `ConstructorAuditoria` tiene la única razón de cambiar si el formato del archivo varía, mientras que el `AuditorInventario` solo cambia si varía el proceso de conteo. Esto evita que la lógica de negocio se mezcle con detalles de infraestructura.


* **Principio de Inversión de Dependencias (DIP):**
  El módulo de alto nivel (`AuditorInventario`) no depende de una implementación concreta de validación (detalles de bajo nivel), sino de la abstracción `ProtocoloFrescura`. Esto permite inyectar diferentes estrategias desde el `Main`.


* **Alta Cohesión:**
  El record `Rango` agrupa los datos (`min`, `max`) y las operaciones estrechamente relacionadas (`contiene`, `desdeTexto`) en una única unidad enfocada en una sola tarea.


* **Bajo Acoplamiento:**
  Al utilizar interfaces y patrones de creación (Builder/Facade), reducimos las interdependencias entre las clases, facilitando la mantenibilidad y el testeo aislado.



---

### Parte 2: Optimización Algorítmica y Fusión de Intervalos

En la segunda fase, el requerimiento evolucionó de filtrar una lista de IDs a calcular la **cobertura total** de los rangos de frescura. Esto introdujo un desafío matemático: los rangos solapados (ej. `10-14` y `12-18`) no pueden sumarse directamente. Se requirió una evolución algorítmica manteniendo la estructura base.

**¿Qué hicimos?**

1. **Extendimos** la funcionalidad de `Rango` haciéndolo `Comparable` e implementando la lógica de dominio para detectar solapamientos (`solapaCon`) y combinarse (`fusionar`).
2. Implementamos el algoritmo de **Merge Intervals** (Fusión de Intervalos) dentro de `AuditorInventario`. Esto optimiza el cálculo ordenando los rangos y combinándolos en una pasada lineal (), evitando la ineficiencia de la fuerza bruta.
3. Reutilizamos el 100% de la infraestructura de carga (`CargadorEntrada`), demostrando la estabilidad del diseño ante cambios funcionales.

**Principios y Fundamentos aplicados:**

* **Principio Abierto Cerrado (OCP):**
  La clase `AuditorInventario` demostró estar "abierta para la extensión" (añadir `calcularCoberturaTotal`) pero "cerrada para la modificación" (no hubo que alterar la lógica de filtrado de la Parte 1).


* **Inmutabilidad (Diseño Funcional):**
  El método `fusionar` devuelve una **nueva instancia** de `Rango` en lugar de modificar el estado interno de los objetos existentes. Esto elimina efectos secundarios indeseados y mejora la predictibilidad del sistema.


* **Principio DRY (Don't Repeat Yourself):**
  "Cada pieza de conocimiento tiene una representación única". No duplicamos la lógica de lectura ni la definición de `Rango`; simplemente ampliamos sus capacidades sin reescribir código base.


* **Patrón Strategy (Adaptabilidad):**
  Aunque la Parte 2 no requiere filtrado externo, la arquitectura permitió pasar un protocolo nulo o *dummy*, adaptando el sistema existente al nuevo contexto sin necesidad de refactorizar el constructor del Auditor.


