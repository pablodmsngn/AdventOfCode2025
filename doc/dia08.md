### **Día 8 \- Patio de juegos**

#### **1\. Introducción y Problema**

El escenario es un patio de juegos subterráneo donde los elfos están instalando luces navideñas. Tenemos una lista de coordenadas 3D (X, Y, Z) que representan cajas de conexiones. El objetivo es conectar estas cajas mediante cables, priorizando siempre las que estén más cerca (menor distancia euclidiana).  
El reto se divide en dos partes que plantean problemas de conectividad de grafos:

* **Parte A**: Realizar exactamente las 1000 conexiones más cortas disponibles. Al final, debemos identificar los circuitos resultantes y multiplicar el tamaño de los tres más grandes.
* **Parte B:** Ignorar el límite de conexiones y continuar uniendo cables hasta que todas las cajas formen un único circuito gigante (unificación total).

#### **2\. Arquitectura General y principios**

* **Principio de Responsabilidad Única (SRP): (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).**  
  He distribuido las responsabilidades para cumplir este principio en cada componente:
  * **Caja**: Su responsabilidad es puramente geométrica y de datos. Es un record inmutable que gestiona sus coordenadas y el cálculo de distancias.
  * **Circuito**: Representa una agrupación lógica de cajas conectadas. Su única razón de existir es mantener el conjunto (Set) de elementos que comparten electricidad.
  * **ParCajas**: Es un objeto de transferencia (DTO) especializado. Su única función es asociar dos cajas y la distancia pre-calculada entre ellas, facilitando la ordenación en el stream sin recalcular fórmulas.
  * **ConectorCircuitos**: Su única razón de cambio es la lógica algorítmica. Gestiona la generación de pares, la ordenación por distancia y la decisión de fusionar circuitos.
  * **ControladorLuces(*Consiste en ocultar los detalles complejos detrás de una interfaz simple*)** oculta la complejidad del algoritmo combinatorio (ConectorCircuitos) detrás de métodos simples como ejecutar() y ejecutarUnificacion(). El Main no sabe cómo se conectan las luces ni cómo se fusionan los circuitos, solo pide el resultado final.
* **Principio de no repetir código (DRY): (Cada pieza de conocimiento en un software debería tener una representación única inequívoca).**  
  La clase **CargadorEntrada** centraliza la lógica de lectura y parseo del fichero. Evita duplicar el manejo de flujos (InputStream) y excepciones entre **Main08A** y **Main08B**, proporcionando un punto único de acceso a los datos.
* **Fundamento de Alta Cohesión: (Refiere a la idea de que las partes de un módulo o componente deben estar estrechamente relacionadas y enfocadas en una única tarea).**  
  La clase **Caja** es el mejor ejemplo. No solo almacena datos (x, y, z), sino que encapsula la operación distanciaA. Al mantener los datos y la lógica geométrica juntos, el resto del sistema trabaja con conceptos de alto nivel en lugar de fórmulas matemáticas dispersas.
* **Patrón Factory Method: (En lugar de usar directamente el constructor... se llama a un método estático que encapsula la creación del objeto).**  
  Implementado en **CargadorEntrada.cargar(String)**. Este método estático encapsula la complejidad de localizar el archivo en el classpath y construir el grafo inicial, entregando al cliente una instancia de **ControladorLuces** lista para usar.
* **Inmutabilidad y Value Objects:**  
  He utilizado Records (Caja, Circuito, ParCajas) para definir objetos inmutables.
  * Esto es crítico para la seguridad del procesamiento paralelo: al no poder cambiar el estado interno de una Caja o un ParCajas una vez creados, eliminamos por completo los errores de concurrencia (race conditions) al procesarlos en Streams.
* **Principio YAGNI (You Aren't Gonna Need It): (Aconseja no añadir funcionalidad hasta que sea necesaria).**  
  He evitado crear interfaces genéricas complejas para cumplir estrictamente con OCP (Abierto/Cerrado), ya que el problema tiene un alcance definido (Parte A y B). He optado por un diseño directo en ControladorLuces que expone los dos métodos necesarios, manteniendo el código simple y fácil de leer.Código Expresivo:

#### **3\. Conclusión**

Prioriza la simplicidad (YAGNI) y la seguridad de tipos. El uso extensivo de Records para modelar el dominio (Caja, Circuito, ParCajas) garantiza inmutabilidad y coherencia de datos. La lógica compleja se aísla en ConectorCircuitos, mientras que CargadorEntrada (Factory) y ControladorLuces (Facade) mantienen limpia la interfaz de uso.