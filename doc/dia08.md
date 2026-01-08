### Parte 1: Modelado de Espacio 3D y Algoritmos Voraces

En la primera fase, el problema consistía en agrupar elementos (cajas de conexiones) en el espacio tridimensional basándose en su proximidad. El reto técnico no era solo calcular distancias, sino gestionar eficientemente la fusión de conjuntos disjuntos (circuitos) a medida que se realizaban conexiones.

**¿Qué hicimos?**

1. Modelamos el dominio con records inmutables: `Caja` (coordenadas 3D) y `ParCajas` (arista con peso/distancia), facilitando el paso de datos sin efectos secundarios.
2. Implementamos `ConectorCircuitos` (el motor lógico), utilizando **Java Streams en paralelo** para calcular la matriz de distancias () de manera eficiente y ordenarlas por proximidad.
3. Aplicamos un **Algoritmo Voraz (Greedy)**: procesamos siempre la conexión más corta disponible. Si conecta dos circuitos diferentes, los fusionamos en uno nuevo (`Circuit`).
4. Utilizamos `ControladorLuces` para encapsular la complejidad del algoritmo y exponer solo el resultado de negocio (el "factor de seguridad").

**Principios y Fundamentos aplicados:**

* **Principio de Responsabilidad Única (SRP):**
  Separamos las preocupaciones: `Caja` solo calcula distancias entre puntos; `ConectorCircuitos` orquesta la lógica de unión de conjuntos; y `CargadorEntrada` traduce texto a objetos.
* **Paralelismo Declarativo:**
  El cálculo de distancias entre todos los pares de cajas es costoso. Utilizamos `stream().parallel()` para aprovechar los núcleos de la CPU sin complicar el código con hilos manuales, mejorando el rendimiento en la fase de preparación de datos.
* **Alta Cohesión:**
  La lógica de "fusionar circuitos" está encapsulada dentro del conector. El controlador no sabe cómo se unen las cajas, solo pide el resultado final.
* **Objetos de Valor (Value Objects):**
  El uso de `record ParCajas` actúa como un objeto de valor temporal que existe solo para transportar la información de una posible conexión (quién con quién y a qué distancia), simplificando la ordenación.

### Parte 2: Convergencia de Estado y Unificación

En la segunda fase, el objetivo cambió: en lugar de realizar un número fijo de conexiones, debíamos continuar conectando hasta que **todos los componentes formaran un único circuito conexo**. Esto requirió cambiar la condición de parada y capturar el evento exacto que lograba la unificación.

**¿Qué hicimos?**

1. Extendimos la lógica en `ConectorCircuitos` para soportar una ejecución hasta la convergencia (cuando `circuitos.size() == 1`).
2. Reutilizamos el flujo de generación de pares (`flujoConexiones`) de la Parte 1, demostrando que el cálculo de distancias es agnóstico a la condición de parada.
3. Utilizamos `AtomicReference` dentro del stream secuencial para capturar "al vuelo" el par de cajas específico que causó la fusión final, permitiendo calcular el coste requerido ().

**Principios y Fundamentos aplicados:**

* **Principio DRY (Don't Repeat Yourself):**
  La compleja lógica de generar pares  y calcular sus distancias euclidianas se definió una sola vez y se reutilizó tanto para la limitación por número (Parte 1) como para la unificación total (Parte 2).
* **Principio Abierto Cerrado (OCP):**
  El diseño permitió añadir la nueva lógica de unificación (`conectarTotalmente`) sin modificar la estructura de datos `Circuit` o `Box`, y sin romper la lógica existente de la Parte 1.
* **Gestión de Estado Mutable Controlada:**
  Aunque preferimos la inmutabilidad, la fusión de circuitos requiere un estado que evoluciona (la lista de circuitos activos). Esta mutabilidad se confinó estrictamente dentro del método de conexión, siendo invisible para el resto de la aplicación, lo que mantiene el sistema seguro y predecible.