
### Parte 1: Combinatoria y Programación Declarativa

En la primera fase, el problema consistía en encontrar el rectángulo de mayor área posible definido por dos "baldosas rojas" que actúan como esquinas opuestas. El reto principal era la generación eficiente de todas las combinaciones posibles de pares de coordenadas.

**¿Qué hicimos?**

1. Modelamos el dominio con records inmutables: `Coordenada` (posición) y `Rectangulo` (área y dimensiones), encapsulando la lógica matemática ().
2. Implementamos `BuscadorRectangulos` (Buscador), utilizando **Java Streams** para generar combinatoriamente todos los pares únicos  de baldosas rojas.
3. Aplicamos un enfoque **Declarativo**: en lugar de bucles anidados imperativos, definimos el flujo de datos: `generar -> aplanar -> ordenar por área -> tomar el primero`.
4. Utilizamos `ControladorCine` (Controlador) para aislar la lógica de búsqueda de la infraestructura de entrada.

**Principios y Fundamentos aplicados:**

* **Principio de Responsabilidad Única (SRP):**
  Separamos las responsabilidades: `Coordenada` solo parsea datos; `Rectangulo` calcula propiedades geométricas; y `BuscadorRectangulos` ejecuta la estrategia de búsqueda.
* **Inmutabilidad (Records):**
  Al usar `records` para las coordenadas y los rectángulos, garantizamos que las posiciones no se modifiquen durante el cálculo masivo de combinaciones, evitando condiciones de carrera y efectos secundarios.
* **Programación Funcional:**
  El uso de `flatMap` y `sorted` permite expresar la intención ("quiero el rectángulo más grande") sin detallar el control de flujo paso a paso, haciendo el código más legible y mantenible.

### Parte 2: Geometría Computacional y Optimización

En la segunda fase, se introdujo una restricción severa: el rectángulo debía ser válido ("permitido"), lo que implicaba estar contenido estrictamente dentro de un polígono definido por la secuencia de baldosas rojas. El enfoque inicial de fuerza bruta (comprobar cada punto) resultó inviable computacionalmente.

**¿Qué hicimos?**

1. Sustituimos la comprobación píxel a píxel por **Geometría Computacional**: definimos los bordes del polígono uniendo las baldosas rojas secuencialmente.
2. Implementamos el algoritmo de **Ray Casting (Punto en Polígono)**: para validar si un rectángulo está dentro, basta con comprobar si su centro está dentro del polígono y si ninguno de los bordes del polígono corta al rectángulo por la mitad.
3. Optimizamos la complejidad algorítmica: pasamos de una complejidad dependiente del área (millones de píxeles) a una dependiente del número de vértices (cientos de bordes), reduciendo el tiempo de ejecución drásticamente.

**Principios y Fundamentos aplicados:**

* **Eficiencia Algorítmica:**
  Reconocimos que iterar sobre el área () era ineficiente. Cambiamos la estrategia a una comprobación geométrica (), demostrando la importancia de elegir la estructura de datos y el algoritmo adecuados para el volumen de datos.
