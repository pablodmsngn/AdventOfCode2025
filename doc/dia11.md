

### Parte 1: Grafos Acíclicos y Programación Dinámica (Memoización)

En la primera fase, el problema consistía en encontrar el número total de caminos distintos desde un nodo de origen ("you" o "svr") hasta un nodo destino ("out") en una red de dispositivos. Dado que los datos "no fluyen en sentido inverso", identificamos la estructura como un **Grafo Acíclico Dirigido (DAG)**. El reto principal era evitar la explosión combinatoria de recalcular las mismas ramas múltiples veces.

**¿Qué hicimos?**

1. Sustituimos la implementación iterativa compleja (basada en Pilas/Stacks manuales) por una solución **Recursiva (DFS)** mucho más limpia y legible dentro de `AnalizadorRutas`.
2. Implementamos la técnica de **Memoización** (Caché): utilizamos un `Map<String, Long>` para recordar cuántos caminos hay desde un nodo intermedio hasta el final. Si el algoritmo vuelve a encontrar ese nodo, devuelve el valor guardado instantáneamente.
3. Desacoplamos la carga de datos del análisis mediante `CargadorEntrada` y `ControladorReactor`, permitiendo que el grafo se construya independientemente de cómo se recorra.

**Principios y Fundamentos aplicados:**

* **Eficiencia Algorítmica (Programación Dinámica):**
  Transformamos un problema de complejidad exponencial () en uno lineal () respecto al tamaño del grafo. Al almacenar los resultados de los subproblemas (caminos desde el nodo ), eliminamos el recálculo redundante.
* **Código Limpio (Clean Code):**
  La refactorización a recursividad simplificó la lógica. En lugar de gestionar manualmente pilas de "visitados" y "expandidos", confiamos en la pila de llamadas del sistema y la inducción matemática: *Caminos(Actual) = Suma(Caminos(Vecinos))*.
* **Principio de Responsabilidad Única (SRP):**
  `AnalizadorRutas` se encarga exclusivamente de la lógica de travesía del grafo, mientras que el controlador orquesta la ejecución. No mezclamos el parseo de strings (`aaa: bbb ccc`) con la lógica de búsqueda.

### Parte 2: Composición de Funciones y Descomposición del Problema

En la segunda fase, se introdujo una restricción de negocio compleja: los caminos debían pasar obligatoriamente por dos nodos intermedios (`dac` y `fft`) en cualquier orden. En lugar de escribir un nuevo algoritmo de búsqueda complejo que rastreara el estado "visitado", optamos por una solución matemática basada en la topología del grafo.

**¿Qué hicimos?**

1. Aplicamos la **Descomposición del Problema**: Entendimos que un camino que pasa por  y  es la concatenación de tres segmentos independientes: ,  y .
2. Implementamos la lógica combinatoria en `contarRutasConIntermedios`: Calculamos las dos permutaciones posibles ( y ) reutilizando el método base de la Parte 1.
3. Utilizamos la **Regla del Producto**: El número total de caminos de una ruta segmentada es el producto de los caminos de cada segmento ().

**Principios y Fundamentos aplicados:**

* **Principio DRY (Don't Repeat Yourself):**
  No duplicamos ni modificamos el algoritmo de búsqueda en profundidad (DFS). Reutilizamos el método `contarRutas` existente para calcular los segmentos individuales, componiendo una solución compleja a partir de piezas simples y probadas.
* **Composición sobre Modificación:**
  En lugar de alterar el núcleo del algoritmo DFS para aceptar banderas de estado ("¿ya visité dac?"), compusimos la solución orquestando múltiples llamadas al algoritmo base. Esto mantiene el núcleo simple y libre de errores.
* **Abstracción Matemática:**
  Modelamos el problema no como una búsqueda física paso a paso, sino como una operación de conjuntos. Si existen  formas de llegar a  y  formas de ir de  a , entonces existen  caminos totales. Esta abstracción permitió resolver la Parte 2 con apenas unas líneas de código extra.