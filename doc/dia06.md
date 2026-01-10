### **Día 6 \- Compactador de basura**

#### **1\. Introducción y Problema**

El escenario es un compactador de basura donde unos cefalópodos necesitan ayuda con una hoja de deberes de matemáticas. La entrada es una cuadrícula de caracteres inusual donde los problemas matemáticos están dispuestos visualmente en columnas y filas, con un operador (+ o \*) al final de cada bloque. El reto consiste en interpretar esta misma cuadrícula de dos formas radicalmente distintas para obtener operandos y sumarlos al final:

* **Parte A:** Los números están escritos verticalmente en columnas alineadas. Un problema se compone de una lista de números verticales y su operador asociado al pie de la columna.
* **Parte B:** La interpretación cambia a "Matemáticas Cefalópodas". Ahora, las columnas no son números enteros, sino dígitos posicionales. Los números se leen de derecha a izquierda a través de las columnas, y los problemas se separan por columnas vacías.

#### **2\. Arquitectura General y principios**

* **Patrón Strategy (ConstructorOperaciones):** (Define una familia de algoritmos, encapsula cada uno y los hace intercambiables).  
  He definido la interfaz **ConstructorOperaciones** para abstraer la lógica de parseo.
  * **AnalizadorVertical**: Implementa la estrategia de la Parte A (números por columnas).
  * **AnalizadorCefalopodo**: Implementa la estrategia de la Parte B (dígitos posicionales).  
    Esto permite cambiar radicalmente cómo se interpreta el archivo de texto sin tocar ni una línea del Cargador ni del Controlador.
* **Inversión de Dependencias (DIP):** (Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones). El **CargadorEntrada** no depende de las clases concretas de análisis. Depende de la abstracción **ConstructorOperaciones**. De igual forma, el **ControladorCompactador** solo conoce un Stream\<Operacion\>, ignorando por completo de dónde salieron esos datos o cómo se parsearon.
* **Principio Abierto/Cerrado (OCP):** (Las clases deben estar abiertas para la extensión, pero cerradas para la modificación). Gracias al diseño anterior, el sistema está cerrado a modificaciones. Si descubrimos una "Parte C" donde los números se leen en diagonal, solo necesito crear una nueva clase **AnalizadorDiagonal** e inyectarla. El motor de cálculo (**ControladorCompactador**) y el sistema de carga permanecen inalterados.
* **Principio de Responsabilidad Única (SRP) y Alta Cohesión:** (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).
  * **CargadorEntrada** (Principio DRY): (No repetir código). Centraliza la lectura del fichero y el manejo de excepciones de I/O, delegando el procesamiento de líneas a la estrategia inyectada.
  * **Operador (Alta Cohesión / Enum Funcional):** (Partes estrechamente relacionadas enfocadas en una tarea única). Encapsula la lógica matemática. Al implementar BinaryOperator\<Long\>, el Enum no es solo una etiqueta, es una función ejecutable. Sabe cómo operar (apply) y cuál es su valor identidad (0 para sumar, 1 para multiplicar), centralizando la lógica aritmética.
  * **Operacion (Alta Cohesión / Value Object):** Un record que agrupa operandos y operador. Su única responsabilidad es orquestar el cálculo final mediante una reducción (reduce), delegando la matemática pura al Operador.
  * **ControladorCompactador (Alta Cohesión):** Su única tarea es sumar los resultados de todas las operaciones, actuando como el punto final de coordinación del proceso.

#### **3\. Conclusión**

El uso de Enums Funcionales (Operador) y componentes con Alta Cohesión muestra cómo Java permite integrar lógica y datos de manera elegante, evitando grandes bloques condicionales y manteniendo el código limpio y mantenible.