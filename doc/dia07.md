

### Parte 1: Simulación Iterativa y Estructuras Inmutables

En la primera fase, el problema consistía en simular un haz de partículas (taquiones) descendiendo por una rejilla y dividiéndose al encontrar obstáculos. En lugar de utilizar una recursividad tradicional o grafos complejos, se optó por una **simulación funcional iterativa por capas**, donde el estado de la fila actual depende exclusivamente de la fila anterior.

**¿Qué hicimos?**

1. Modelamos los datos con el record `Celda` (inmutable) y el enum `TipoCelda`, encapsulando el estado y la representación textual (`toString`).
2. Implementamos `ConstructorRejilla` (Builder) para aislar la complejidad de leer el fichero y convertirlo en una estructura de objetos.
3. Desarrollamos el motor lógico `SimuladorTaquiones`, utilizando **Streams y Reducción**. El sistema no muta la rejilla global, sino que genera un nuevo estado capa por capa.
4. Creamos `ControladorLaboratorio` como fachada para orquestar la simulación y ofrecer una API sencilla.

**Principios y Fundamentos aplicados:**

* **Principio de Responsabilidad Única (SRP):**
  Cada clase tiene un propósito único. `ConstructorRejilla` solo se encarga del parseo; `SimuladorTaquiones` gestiona únicamente la física de los haces (movimiento y división); y `Celda` contiene solo datos. Esto evita que la lógica de negocio se mezcle con la infraestructura de carga.
* **Inmutabilidad y Diseño Funcional:**
  El uso de `records` garantiza que el estado no cambie inesperadamente. La simulación utiliza `IntStream.range(...).reduce(...)` para transformar el estado de manera secuencial y predecible, eliminando los efectos secundarios típicos de los bucles imperativos anidados.
* **Alta Cohesión:**
  Toda la lógica relacionada con la propagación del haz (`agregarCaminoHaz`, `actualizar`) reside estrictamente dentro de `SimuladorTaquiones`. No hay reglas de negocio dispersas en el `Main` o en el controlador.
* **Patrón Builder:**
  Se implementó `ConstructorRejilla` para separar la construcción de la estructura compleja (`List<List<Celda>>`) de su representación final, permitiendo una creación de objetos más limpia y legible.



### Parte 2: Interpretación de Muchos Mundos (Reutilización y Extensión)

En la segunda fase, el requerimiento cambió de contar eventos de división a calcular el total de **líneas temporales** (caminos únicos) resultantes. Gracias al diseño previo que acumulaba intensidades en cada celda, la adaptación fue directa sin alterar el núcleo de la simulación.

**¿Qué hicimos?**

1. Reutilizamos el 100% de la lógica de `SimuladorTaquiones`. El algoritmo ya calculaba la superposición de caminos (`caminosActuales + caminosPrevios`) de forma nativa.
2. Extendimos la funcionalidad en `ControladorLaboratorio` añadiendo un método para consultar el estado final (`obtenerTotalCaminos`), sumando las intensidades de los haces en la última fila.
3. Usamos `CargadorEntrada` para inyectar los datos, demostrando que el sistema de carga es agnóstico a la lógica de negocio.

**Principios y Fundamentos aplicados:**

* **Principio Abierto Cerrado (OCP):**
  La clase `SimuladorTaquiones` demostró estar "cerrada a la modificación" (no hubo que tocar el algoritmo de propagación) pero "abierta a la extensión" (pudimos obtener una nueva métrica de negocio simplemente inspeccionando el resultado final desde el controlador).
* **Principio DRY (Don't Repeat Yourself):**
  "Cada pieza de conocimiento tiene una representación única". No duplicamos la lógica de simulación para la Parte 2. Tanto el conteo de divisiones como el de caminos utilizan la misma invocación `resolver()` del simulador.
* **Patrón Facade (Fachada):**
  `CargadorEntrada` y `ControladorLaboratorio` actúan como fachadas que ocultan la complejidad del sistema (Streams, Builders, InputStream) al cliente final (`Main`), proporcionando una interfaz de uso simple y directa.