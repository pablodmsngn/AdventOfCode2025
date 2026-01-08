
### Parte 1: El Núcleo y la Estructura Base

En la primera fase, establecimos una arquitectura modular para procesar rangos de identificadores y detectar aquellos que cumplen un patrón específico (repetidos exactamente dos veces, ej: `123123`). El objetivo era separar la lectura de datos, la lógica matemática y la ejecución.

**¿Qué hicimos?**

1. Creamos el record `RangoID` para encapsular la generación de secuencias numéricas y el filtrado.
2. Diseñamos el `Motor` para orquestar la ejecución sin conocer los detalles de la regla.
3. Implementamos `ConstructorMotor` (Builder) y `CargadorEntrada` para la creación de objetos y lectura de ficheros.
4. Definimos `EstrategiaValidacion` con el `PATRON_A`.

**Principios y Fundamentos aplicados:**

* **Alta Cohesión:**
  `RangoID` se enfoca únicamente en manejar intervalos numéricos, mientras que `CargadorEntrada` solo se ocupa de transformar texto en objetos. Esto cumple con la idea de que "las partes de un módulo deben estar estrechamente relacionadas y enfocadas en una única tarea".


* **Abstracción:**
  En `Motor`, ocultamos la complejidad de cómo se valida un número detrás de la interfaz funcional `LongPredicate`. Como indica el texto, esto "consiste en ocultar los detalles complejos detrás de una interfaz simple". El `Motor` no sabe de expresiones regulares, solo sabe preguntar "es válido?".


* **Principio de Responsabilidad Única (SRP):**
  "Cada módulo o clase debe tener una sola razón para cambiar". `ConstructorMotor` cambia si modificamos cómo se construye el objeto; `EstrategiaValidacion` cambia solo si cambia la definición matemática de ID inválido.


* **Código Expresivo:**
  El uso del patrón Builder en `ConstructorMotor` (`.from().use().runner()`) hace que la configuración sea legible como lenguaje natural. Esto se alinea con el fundamento de que el código debe ser "claro y comprensible, facilitando la lectura".





### Parte 2: La Extensión y el Cambio de Reglas

En la segunda fase, el criterio de validación cambió: los IDs ahora son inválidos si el patrón se repite *al menos* dos veces (ej: `123123123`), no solo exactamente dos. Debíamos adaptar el sistema sin reescribir el núcleo.

**¿Qué hicimos?**

1. Añadimos una nueva constante `PATRON_B` en `EstrategiaValidacion` con una Regex modificada (`+` en lugar de nada).
2. Creamos `Main02b` para inyectar esta nueva estrategia.
3. **No tocamos** `Motor`, `RangoID` ni `ConstructorMotor`.

**Principios y Fundamentos aplicados:**

* **Principio Abierto Cerrado (OCP):**
  Aplicamos este principio rigurosamente. "Las clases deben estar abiertas para la extensión, pero cerradas para la modificación".
*Cómo lo logramos:* Para la Fase 2, extendimos el comportamiento creando una nueva estrategia (`PATRON_B`) e inyectándola. El `Motor` permaneció cerrado a modificaciones; no tuvimos que entrar en su código para añadir un `if (fase2)`.


* **Principio de Inversión de Dependencias (DIP):**
  En el documento que esta en el campus de los diseños establece que "Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones".
*Cómo lo logramos:* El `Motor` (alto nivel) no depende de la clase concreta `EstrategiaValidacion` ni de una Regex específica. Depende de la abstracción `LongPredicate`. Solo sabe que recibirá algo que devuelve `true` o `false`.


* **Principio DRY (Don't Repeat Yourself):**
  "Cada pieza de conocimiento... debería tener una representación única".
*Cómo lo logramos:* Al refactorizar `CargadorEntrada` para que utilice internamente `ConstructorMotor`, evitamos duplicar la lógica de parseo del fichero (separación por comas, limpieza de strings) en ambos ejercicios. Reutilizamos toda la infraestructura para la Parte B.


* **Bajo Acoplamiento:**
  Diseñamos componentes con "pocas interdependencias". Gracias a esto, pudimos cambiar la regla de negocio crítica (la Regex) en el `Main` sin que el sistema de carga de archivos o el motor de ejecución se vieran afectados o requirieran recompilación.