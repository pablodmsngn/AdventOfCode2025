

### Parte 1: El Núcleo y la Estructura Base

En la primera fase, el objetivo era procesar secuencias de dígitos (bancos de baterías) para encontrar la mayor "sacudida" posible seleccionando **exactamente dos** baterías, respetando su orden relativo. La prioridad fue desacoplar el algoritmo de búsqueda de la infraestructura de lectura.

**¿Qué hicimos?**

1. Definimos la interfaz funcional `ProtocoloEnergia` (el contrato) para abstraer la lógica de cálculo.
2. Implementamos el algoritmo en `EstrategiasBusqueda`, aplicando una lógica **Greedy** (codiciosa) para maximizar el resultado.
3. Creamos `ControladorEscalera` para orquestar el proceso (Map-Reduce) sin conocer los detalles matemáticos.
4. Implementamos `ConstructorEscalera` (Builder) para encapsular la complejidad de la lectura del fichero (`InputStream`).

**Principios y Fundamentos aplicados:**

* **Inversión de Dependencias (DIP):**
  El `ControladorEscalera` (alto nivel) no depende de la clase concreta `EstrategiasBusqueda` (bajo nivel). Depende de la abstracción `ProtocoloEnergia`. Como indica la teoría, esto permite cambiar el algoritmo sin tocar el orquestador.
* **Alta Cohesión:**
  `BancoBaterias` es un record inmutable que solo transporta datos. `EstrategiasBusqueda` contiene únicamente la lógica matemática pura, sin mezclarla con lectura de ficheros o impresión por pantalla. Cada módulo está enfocado en una única tarea .
* **Patrón Strategy:**
  Aunque no es un principio SOLID per se, es la implementación directa del DIP y OCP. Nos permite inyectar el "cerebro" del cálculo (`planA`) en el momento de la ejecución, separando el *qué* se hace del *cómo* se hace.
* **Código Expresivo:**
  Gracias al Builder, en el `Main` podemos leer: `.desde(input).usando(planA).construir()`. Esto convierte la configuración técnica en una frase legible, facilitando la comprensión del flujo de datos .



### Parte 2: La Extensión y la Generalización

En la segunda fase, el requerimiento cambió drásticamente en magnitud: ahora debíamos seleccionar **exactamente doce** baterías en lugar de dos. Esto planteó dos retos: cambiar la lógica de búsqueda y solucionar el desbordamiento de memoria (un número de 12 cifras no cabe en un `int`).

**¿Qué hicimos?**

1. **Generalizamos** el algoritmo `Greedy` para aceptar un parámetro  (número de dígitos) en lugar de tener el "2" escrito a fuego.
2. Refactorizamos los tipos de datos de `int` a `long` para soportar números grandes (Robustez).
3. Usamos una **Lambda Adapter** en el `Main` para reutilizar el método genérico adaptándolo a la interfaz existente.
4. Reutilizamos el 100% de la infraestructura de carga y control.

**Principios y Fundamentos aplicados:**

* **Principio Abierto Cerrado (OCP):**
  "Las clases deben estar abiertas para la extensión, pero cerradas para la modificación".
  *Cómo lo logramos:* Para la Fase 2, no tuvimos que crear una clase `Estrategia12Digitos` ni modificar el bucle del `Controlador`. Simplemente configuramos el sistema existente con un nuevo parámetro () a través de la inyección de dependencias en el Main.
* **Generalización (Abstracción Paramétrica):**
  En lugar de escribir un método para 2 dígitos y otro para 12, creamos `Greedy(secuencia, n)`. Esto eleva el nivel de abstracción del algoritmo, haciéndolo capaz de resolver una familia de problemas en lugar de uno solo.
* **Principio DRY (Don't Repeat Yourself):**
  "Cada pieza de conocimiento debería tener una representación única".
  *Cómo lo logramos:* Al usar el método generalizado y la misma clase `CargadorEscalera`, no duplicamos código. La lógica de cómo leer el fichero, cómo parsearlo y cómo sumar los resultados es idéntica para ambas fases.
* **Robustez y Tipado:**
  Al detectar que 12 dígitos causarían un *overflow*, cambiamos el sistema a `long`. Esto demuestra un diseño consciente de las limitaciones de la máquina, asegurando que la extensión funcional (más dígitos) no rompa la integridad técnica (resultados negativos o errores).