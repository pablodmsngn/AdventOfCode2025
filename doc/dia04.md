### **Día 4 \- Departamento de Impresión**

#### **1\. Introducción y Problema**

El problema nos sitúa en el almacén de la imprenta, representado por una cuadrícula 2D con rollos de papel (@) y espacios vacíos (.). El objetivo es optimizar la logística contando cuántos rollos son "accesibles". Un rollo es accesible si tiene menos de 4 vecinos (de los 8 posibles: horizontales, verticales y diagonales) que también sean rollos.

El reto se divide en dos estrategias distintas:

* **Parte A:** Contar cuántos rollos son accesibles en el estado inicial (foto estática).
* **Parte B:** Simulación completa. Al retirar los rollos accesibles, los que estaban detrás podrían quedar libres. Debemos repetir el proceso en bucle hasta que no se puedan retirar más rollos.

#### **2\. Arquitectura General y principios**

* **Patrón Builder (FabricaEjecutador):** (Permite configurar paso a paso qué solucionador queremos y oculta la instancia concreta). He implementado la clase **FabricaEjecutador** que actúa como híbrido. Funciona como Builder para la configuración (desde, tipo) y como **Factory** para la creación (construir). Esto permite crear la instancia correcta (**SolucionadorImprentaA** o **SolucionadorImprentaB**) de forma transparente para el cliente.

* **Inversión de Dependencias (DIP):** (Módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones). El **Main** y el **cargador** no dependen de las clases concretas de solución. En su lugar, dependen de la interfaz funcional **Ejecutador**. Esto permite que el sistema ejecute la lógica estática (A) o dinámica (B) mediante polimorfismo sin cambiar el código cliente.
* **Técnica de Robustez (Padding / Relleno):** En la clase **CuadriculaAlmacen**, agrego un borde de seguridad de puntos (.) alrededor del mapa original. Esto elimina la complejidad de comprobar los límites del array (IndexOutOfBounds) al buscar vecinos, simplificando la lógica y haciendo el código más limpio.
* **Principio de Responsabilidad Única (SRP):** (Cada módulo o clase debe tener una sola razón para cambiar, reflejando la alta cohesión).
  * **CargadorImprenta (DRY):** Simplifica la llamada desde el Main delegando en la Fábrica.
  * **SolucionadorImprentaA:** Su responsabilidad es puramente lógica: recibe el almacén y ejecuta el cálculo único delegando en el optimizador. Ya no gestiona I/O.
  * **SolucionadorImprentaB:** Su única responsabilidad es gestionar el bucle de la simulación. Recibe el modelo inicial y actualiza la referencia en cada iteración.
  * **OptimizadorMontacargas (Clase de utilidad):** Contiene exclusivamente la regla de qué constituye un bloqueo (\>= 4 vecinos). Se ha convertido en una Clase de Utilidad (Static). Al no instanciarse objetos en cada vuelta del bucle de la Parte B, optimizamos el uso de memoria.
  * **CuadriculaAlmacen:** Gestiona la estructura de la matriz. Para la Parte B, implementa la inmutabilidad: el método retirarRollos devuelve una nueva instancia de la cuadrícula, evitando efectos secundarios en el estado.
  * **Coordenada (Alta Cohesión): (Enfocadas en una única tarea ):** Es un record que solo sabe calcular sus coordenadas vecinas.
  * **ContenidoCasilla (Alta Cohesión):** Enum que encapsula la representación de los datos (ROLLO\_PAPEL, VACIO) y su parseo desde caracteres (@, .). Fíjate en el método desdeCaracter(char simbolo) dentro del Enum. El Problema: Sin este Enum, tendrías if (c \== '@') dispersos por todo tu código (en el parser, en el optimizador, en los tests). Si mañana cambia el símbolo a \#, tendrías que buscar y reemplazar en 20 sitios. La Solución: La lógica de "qué carácter significa qué cosa" está centralizada en un solo sitio. El resto del programa habla en términos de alto nivel (ROLLO\_PAPEL), no de detalles de bajo nivel (@).

#### **3\. Conclusión**

Combina robustez algorítmica (Padding) con patrones de diseño (Factory/Builder). La aplicación estricta de DRY (centralizando el I/O en la fábrica) y la optimización de memoria (Optimizador estático) garantizan un código limpio, eficiente y fácil de testear.
