

### Parte 1: Abstracción Funcional y Parseo Vertical

En la primera fase, el desafío principal no era la complejidad matemática, sino la **representación estructural de los datos**: una matriz de texto donde las operaciones se definen verticalmente (columnas) pero se leen horizontalmente (filas). El objetivo fue desacoplar la lógica de "cómo leer este formato extraño" de la lógica de "cómo calcular el resultado".

**¿Qué hicimos?**

1. Definimos la interfaz `ConstructorOperaciones` (Builder) como un contrato para transformar texto crudo en objetos de dominio, aislando la complejidad del parseo.
2. Implementamos `AnalizadorVertical`, utilizando **Streams** para "rebanar" el texto verticalmente y detectar operadores dinámicamente.
3. Modelamos el dominio con el record `Operacion` (inmutable) y el enum `Operador` (Estrategia), aplicando un enfoque declarativo y funcional para el cálculo.
4. Utilizamos `ControladorCompactador` como orquestador que recibe un flujo de operaciones ya procesadas, ignorando completamente el origen o formato de los datos.

**Principios y Fundamentos aplicados:**

* Principio de Responsabilidad Única (SRP):
Separamos radicalmente las responsabilidades: `AnalizadorVertical` solo sabe interpretar texto y coordenadas; `Operacion` solo sabe reducir listas de números a un resultado. Esto asegura alta cohesión, ya que cada clase tiene una única tarea.


* Principio de Inversión de Dependencias (DIP):
El `ControladorCompactador` (módulo de alto nivel) no depende de la clase concreta `AnalizadorVertical`, sino de la abstracción `Stream<Operacion>`. El `CargadorEntrada` depende de la interfaz `ConstructorOperaciones`. Esto desacopla el flujo de ejecución de los detalles de implementación.


* Código Expresivo y Funcional:
El uso de `Records` y `Enums` con métodos como `apply` o `identity` permite que la lógica matemática se lea casi como lenguaje natural (e.g., `reduce(operador.identidad(), operador)`), eliminando la complejidad accidental de los bucles imperativos.


* **Patrón Strategy (implícito en Enum):**
  El enum `Operador` encapsula algoritmos variables (suma vs. multiplicación) y sus identidades neutras, permitiendo intercambiar la lógica de cálculo sin usar condicionales complejos (`if/else`) en el modelo.



### Parte 2: Extensibilidad y Matemáticas Cefalópodas

En la segunda fase, las reglas de interpretación del archivo cambiaron drásticamente: la lectura debía realizarse de **derecha a izquierda** y la alineación de los bloques dependía del tipo de operador. El reto fue adaptar el sistema a estas nuevas reglas ("Matemáticas Cefalópodas") sin romper la lógica de cálculo ya verificada.

**¿Qué hicimos?**

1. Creamos una nueva implementación `AnalizadorCefalopodo` que respeta la interfaz `ConstructorOperaciones`, pero cambia internamente la lógica de recorrido de la matriz (iteración inversa de columnas).
2. Añadimos comportamiento al `Operador` para determinar reglas de alineación, sin alterar su contrato básico.
3. Inyectamos esta nueva implementación en el `Main` a través del `CargadorEntrada`, reutilizando el 100% del código del controlador y del modelo de datos.

**Principios y Fundamentos aplicados:**

* Principio Abierto Cerrado (OCP):
El sistema demostró estar abierto a la extensión (soportar "Matemáticas Cefalópodas") pero cerrado a la modificación. No tuvimos que tocar ni una línea del `ControladorCompactador` ni de la clase `Operacion` para soportar el nuevo formato de lectura.


* Principio de Sustitución de Liskov (LSP):
El `AnalizadorCefalopodo` sustituye al `AnalizadorVertical` sin que el `CargadorEntrada` o el `Controlador` noten la diferencia. Ambos cumplen el contrato de `ConstructorOperaciones`, asegurando la intercambiabilidad de los módulos.


* Principio DRY (Don't Repeat Yourself):
Reutilizamos la infraestructura de carga (`CargadorEntrada`) y la lógica matemática (`Operacion`). No duplicamos código para manejar la lectura de archivos o la suma de resultados; simplemente variamos la estrategia de construcción.


* **Patrón Builder (Variación):**
  Aunque usamos una interfaz, las clases `Analizador...` actúan como Builders que acumulan estado (líneas de texto) y finalmente construyen el objeto complejo (el Stream de operaciones) al llamar a `construir()`. Esto separa la construcción de la representación final.