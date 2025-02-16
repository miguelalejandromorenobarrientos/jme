## *v.0.6.2.4*&emsp;<small><small>(14/02/2025)</small></small>
- Añadidas funciones para correspondencias y relaciones matemáticas sobre conjuntos finitos
- Ampliada función **set** para vectores multinivel
- Añadida función **tabla** para añadir encabezados a una matriz de datos
- Añadidas funciones sobre polinomios; **evalpoli**, **sumapoli**, **prodpoli**, **divpoli**, **txtpoli**. 
- Añadida función **harshad/niven** para testear números de Harshad/Niven
- Añadida función **cortes** para números corteses (educados)
- Añadida función **pandigital** para testear números pandigitales
- Añadida función **aestrella** para la aplicación del *algoritmo A**
- Añadida función **barajarsem** para barajar vector especificando semilla
- Ampliada función **enterogrande**, para conversión a entero de números en cualquier base
- Función **cbrt** (raíz cúbica), para reales negativos, vuelve a devolver -raiz3(-x), para rama principal, usar nueva función **raiz3**, equivalente a x^(1/3)
- Añadido operador **resto %%** de la división, con valor siempre positivo (el módulo % puede ser negativo)
- Añadida función **tomar**, para la selección de elementos en vector mediante índices
- Corregidos bugs en operadores **conjunto potencia {^}**, **secuencia ;s;**, **permutación de vector ;!;, ;p;** para el conjunto vacío y longitudes no válidas
- Ampliados a diccionarios los operadores **primera ocurrencia ;?;** y **encontrar todas ;*;**
- Añadida función **subfactorial** equivalente al operador subfactorial **¡**
- Añadida función **barajar**, sinónimo de **shuffle**, **mcm** sinónimo de **lcm**, **mcd** sinónimo de **gcd**, **si** sinónimo de **if**, **si_no_si** sinónimo de **elsif**, **escalon** sinónimo de **step**,
 **aplanar** sinónimo de **flatten**, **grados** sinónimo de **deg**, **intercambio** sinónimo de **swap**, **caso** sinónimo de **switch**,
 **mientras** sinónimo de **while** 
- *Thread-Safe* en operadores y funciones definidas por usuario
- Mayor velocidad de ejecución
- JMEScript: delimitadores **inicio:** y **fin** pueden reemplazarse por **{** y **}**
- ALgunos cambios menores

## *v.0.6.2.3*&emsp;<small><small>(08/01/2023)</small></small>
- Mejorada sentencia ***leer*** <small>(salida visible)</small>
- Corregido bug en primitiva ***ctx2d circunferencia*** cuando se dibujaba un arco <small>(orientación errónea)</small>
- Eliminada clase *AffineTransform* en sentencia ***ctx2d gradiente*** para cláusula *matriz* <small>(por motivos de portabilidad)</small>
- Corregido bug en procedimientos *JMEScript* con parámetros en mayúsculas
- Corregido bug en función **sqrt** (raíz cuadrada), para complejos sin parte imaginaria (isomorfos a reales), que devolvía siempre 0.0
- Función **cbrt** (raíz cúbica), para reales negativos, devuelve ahora rama principal, no **-&#x221B;(-x)**
- Operador **potencia** **(^)** mejorado para *RealGrande* y *EnteroGrande*; mejorada precisión para exponentes negativos y reales
- Añadidas funciones para suma y producto de *progresiones geométricas y aritméticas*; **geosum**, **geoprod**, **aritsum** y **aritprod**
- Añadida función **bell** para los coeficientes de Bell (B<sub>n</sub>)
- Comparación de *EnteroGrande* y *RealGrande* con **&plusmn;inf** y **nan** no devuelve ya excepción, sino booleano apropiado
- Mejorado *overflow* en la ***división con denominador complejo*** y ***logaritmo complejo*** para números grandes

## *v.0.6.2.2*&emsp;<small><small>(23/08/2021)</small></small>
- Corregido bug en excepciones dentro de procedimientos

## *v.0.6.2.1*&emsp;<small><small>(22/08/2021)</small></small>
- Eliminada referencia a paquete *java.awt* en interfaz *AbstractPrimitivas2D* para evitar incompatibilidades

## *v.0.6.2*&emsp;<small><small>(18/08/2021)</small></small>
- Pseudotipo *grafo* y funciones sobre grafos/digrafos/pseudografos/multigrafos
- Constante **nulo** de tipo texto con valor *'\_\_null\_\_'* para la representacíon de pseudovalor *nulo*
- Añadidas Constantes **eol**, **eol_split**, **file_separator**, **tab**, **comillas_simples**, **campana**
- Corregido bug en función *rg* (rango matricial)
- Bug en operador = y == corregido para booleanos clonados
- Añadido operador de acceso **><** que varía en [0,n) en lugar de **;#;** que varía en [1,n]
- Añadido producto escalar complejo (afecta a funciones *mod* y *unit* anteriores)
- Añadida función *geometria* para un catálogo amplio de fórmulas geométricas
- Añadidas funciones de carga/guardado de archivos *guardararchivotxt*, *archivobin*, *guardararchivobin*
- Añadidas funciones CSV; *acsv* y *desdecsv*
- Añadidas funciones matemáticas *ln1p*, *cercano*, *vandermonde*, *ruffini*, *coefmultinomial*, *orto*, *w0*
- Añadidas funciones de texto *empiezacon*, *terminacon*
- Añadida función *morse*
- Operadores superfactorial, hiperfactorial y factorial alternado convertidos a funciones, usando una aproximación a su notación típica matemática; *sf*, *\_h\_*, *af*, y eliminados como operadores
- Ampliada función *const* con flag para clonar o no clonar
- Mejora en comparador de función *ordcomp* para permitir resultados reales (convertidos a {-1,0,1})
- Ampliada y modificada función financiera *interes*
- Ampliada función *indefinido*
- Modificada función *format*, para eliminar las comillas en el formateo del tipo *Texto*
- Eliminada función *comprimir* (se puede usar *trasp*)
- JMEScript v0.2.0
- JMEScript: sentencia **accion** para la creación de macros y scripts de control de la aplicación
- JMEScript: sentencias multilínea
- JMEScript: set **in-place**; modificación mutable de vectores y diccionarios
- JMEScript: asignación múltiple de un valor a varias variables a la vez
- JMEScript: comentarios de fin de línea
- JMEScript: procedimientos globales (*global*)
- JMEScript: añadida sentencia de soporte *SQL*
- JMEScript: añadida sentencia de bucle **repetir**
- JMEScript: añadida sentencia selector condicional de casos **seleccionar**
- JMEScript: añadida sentencia bloque try-catch **intentar/capturar**
- JMEScript: añadida sentencia **intercambio** (swap)
- JMEScript: añadida sentencia **asegurar** (assert)
- JMEScript: añadida sentencia **pausa**
- JMEScript: añadida sentencia **varmap**
- JMEScript: ampliado bucle **para cada**
- JMEScript: ampliada sentencia **imprimir**
- JMEScript: valor inicial de bucle **para cada** iniciado ahora a 1 en lugar de 0
- JMEScript: añadida sentencia **ctx2d** para gráficos 2D en un entorno habilitado
- JMEScript: método *#ultimoResultado* renombrado a *#valorDevuelto*
- JMEScriptGUI v0.1.2.0

## *v.0.6.1*&emsp;<small><small>(05/01/2020)</small></small>
- Añadida función **mapafrec**, para creación de un mapa de frecuencias de elementos de un vector, valores de diccionario, caracteres de cadena o dígitos de un entero
- Añadida función **comprimir**, para creación de un vector a partir de varios vectores del mismo tamaño componente a componente
- Añadida función **catalan**, devuelve el enésimo número de Catalan
- Añadidas funciones **comp1** y **comp2**, devuelven el complemento a la 1 y a la 2 respectivamente de una cadena binaria
- Añadidas funciones **partepar** y **parteimpar**, devuelven las componentes pares e impares respectivamente de una función
- Añadida función **hash**, que crea un resumen de un mensaje de texto a partir de un algoritmo de hashing especificado
- Modificadas funciones *escape*, *mandelbrot* y *julia*
- Ampliadas funciones *_throw_* y *indefinido*
- Añadida constante de *Planck* (**_si_h**) y *Planck reducida* (**\_si_h_**)
- Corregido bug en función *alisum* para x=0 y errores en cálculo de la función *Beta regularizada* que afectaba a varias funciones

## *v.0.6.0.2*
- Ampliada función *set* para eliminar una entrada de diccionario
- Añadida constante **dic0**  *(diccionario vacío)*
- JMEScript v0.1.3, corregido bug cuando ocurre *StackOverflow* en llamadas recursivas

## *v.0.6.0.1*
- Eliminada función *beep*, podía causar incompatibilidad con *Android*
- Algunos cambios menores
- JMEScript v0.1.2, ampliada instrucción *leer*
- JMEScriptGUI v0.1.1, entrada de datos en instrucción *leer* mediante diálogo

## *v.0.6.0*
- Evaluación de expresiones puede detenerse interrumpiendo el hilo en que ejecutan
- Añadidos hilos **ExpresionThread**, que permiten la ajecución en hilo separado de una expresión JME y la posibilidad de interrumpir la evaluación mediante temporizador o interrupción síncrona directa
- Evaluación de script puede detenerse interrumpiendo el hilo en que ejecuta
- Añadidos hilos **ScriptThread**, que permiten la ejecución en hilo separado del script y la posibilidad de interrumpir la ejecución mediante temporizador o interrupción síncrona directa
- *REPL* incluye ahora especificación de tiempo máximo de evaluación
- Añadidas funciones **dormir**, **beep** e **imprimir**
- Optimización de algunas funciones y algunos cambios menores.
- Versión **0.1.1** de *JMEScript*
- Lanzamiento de *IDE* **JMEScriptGUI** *v0.1.0*, para ejecución en escritorio de scripts *(jar ejecutable)*

## *v0.5.0*
- Añadido **lenguaje estructurado** para scripts, ***JMEScript***
- Añadida función **script**, para evaluación de scripts en expresiones o en el *REPL*
- Añadida función **indefinido**, para comprobación de inicialización de variables
- Añadida función **archivotxt**, para carga de archivos de texto como cadena o cadenas
- Añadida función **exec**, para ejecutar comandos externos
- Corregido bug en cadenas de texto terminadas en **\**
- Tipos *Texto* y *Diccionario* implementan ahora *Iterable&lt;Terminal&gt;*

## *v0.4.10*
- Añadidas funciones estadísticas **esperanza**, **fvarianza**, **cuantil**, **fmediana**, **entropia** para funciones de distribución de probabilidad
- Añadida función para integral de trayectoria y de línea **intlinea**
- Añadida función **zint** para integración de funciones f:R->C y **zintlinea** para integración de contorno compleja f:C->C
- Añadidas funciones de densidad y distribución **triangular**, **triangulardist**
- Añadida función **eliptica** para funciones especiales K(k),F(φ,k),E(k),E(φ,k) *(integrales elípticas completas e incompletas de primera y segunda especie)*
- Añadida función **geometria** para el cálculo de propiedades de figuras geométricas
- Algunos cambios menores

## *v0.4.9*
- Añadidas funciones de densidad y distribución **fdt**, **tdist**, **laplace**, **laplacedist**, **logistica** y **logisticadist**
- Eliminada función *randm*, puede usarse *genrand* en su lugar
- Presentación vertical de diccionarios en REPL
- Optimizadas algunas funciones
- Algunos cambios menores

## *v0.4.8*
- Añadidas funciones especiales **gammap**, **gammaq**, **lngamma**, **digamma**, **trigamma** y **lnbeta**
- Añadidas funciones de densidad y distribución **chi2**, **chi2dist**, **fdf** y **fdist**
- Añadido método de integración numérica ***Simpson adaptativo***
- Corregido bug en función *fdgamma*, donde el parámetro *escala* se había tomado como *ratio* [1/escala]
- Invertidos operadores '**º**' y '**ª**'. Ahora '**º**' convierte a radianes y 'ª' a grados sexagesimales (30º generaba confusión, por ejemplo)
- Añadidos métodos de álgebra compleja a clase terminal *Complejo*
- Algunos cambios menores

## *v0.4.7*
- Métodos de evaluación con casting;&emsp;*Expresion#evaluarANumero*, *Expresion#evaluarATexto*, *Expresion#evaluarAVector*, ...
- Añadida función **igamma** para implementar la función especial ***gamma incompleta inferior γ(a,b)***
- Añadida función **ibeta** para implementar la función especial ***beta incompleta regularizada I~x~(a,b)***
- Ampliada función *gamma* para implementar la función especial ***gamma incompleta superior Γ(a;x)***
- Ampliada función *beta* para implementar la función especial ***beta incompleta inferior B(x;a,b)***
- Optimizadas funciones *gammadist*, *betadist*, *poissondist*, *binomialdist* y *binomialnegdist* mediante uso de *Apache Commons Math*
- Optimizada función especial de error *erf* y añadidas funciones especiales **erfc** (función de error complemetario) y **ierf** (recíproca de función *erf*)
- Optimizado operador factorial (**!**)
- Añadida clase ***jme.JMEMath*** a acceso de función *java* y eliminada ***jme.Util***
- Corregido bug en comparación estricta (**===**) de *NaN* con *NaN*. Ahora devuelve *verdadero* siempre (**=** y **==** devuelven *falso*)
- Algunos cambios menores

## *v0.4.6*
- Añadidas funciones **autovalores**, **evaldic**, **igamma**, **exponencial**, **exponencialdist**, **binomialneg**, **binomialnegdist**, **fdgamma**, **gammadist**, **fdbeta**, **betadist**, **cauchy** y **cauchydist**
- Ampliada función *log* para incluir la función *~~logb~~*, que ha sido eliminada
- Optimizadas algunas funciones de distribución

## *v0.4.5*
- Leve mejora en la velocidad de evaluación
- Añadidas funciones **genrand**, **hipergeomdist**, **geom** y **geomdist**
- Ampliadas funciones *normaldist*, *uniformedist*, *poissondist* y *binomialdist*
- Modificada función *normal*

## *v0.4.4*
- Añadido tipo de dato **Diccionario**, para la representación de mapas clave/valor
- importación/Exportación Diccionario<->JSON/XML
- Añadidas funciones sobre diccionarios **dic**, **valores**, **claves**, **entradas**, **esdiccionario**, **json**, **xml**
- Ampliadas funciones *propagar*, *enumerar*, *dimension*, *set*, *java* y *revertir* y operadores&emsp;**;#;** ,&emsp;**;;** ,&emsp;**;-;** ,&emsp;**{i}**
- Modificadas funciones *ifrac*, *cpu* y *montecarlo*
- Añadida función **patron**, para el reconocimiento de patrones de texto
- Añadida función **jconst** para el acceso a constantes públicas Java y **return** para métodos *'void'*
- Añadido método Terminal#castToJava
- Añadida función **mc** para la especificación de precisión y redondeo de funciones
- Añadidas funciones de distribución de probabilidad **uniforme**, **uniformedist**, **binomial**, **binomialdist**, **poisson**, **poissondist** y **hipergeom**

## *v0.4.3*
- Añadida función **java**, que permite ejecutar ciertos métodos estáticos Java dentro de JME, ampliando la funcionalidad
- Optimizadas algunas funciones
- Algunos cambios menores

## *v0.4.2*
- Nuevo tipo, **Texto** para representar cadenas de texto
- Nuevos operadores y funciones sobre cadenas
- Añadidas funciones **enumerar**, **esentero**, **ifrac**, **gamma**, **beta**, **fpi**, **sol_bisec**, **escala**, **precision**, **randprimo**
- Optimizadas algunas funciones y operadores
- Algunos cambios menores

## *v0.4.0*
- Operadores definidos por el usuario
- Algunas expresiones malformadas detectadas al evaluar, ahoran son detectadas en analizador sintáctico
- Leve ampliación de la funcionalidad del modo REPL
- Eliminados métodos '**Expresion#nuevaFuncionUsuario**', use '**Expresion#nuevaFuncion**' en su lugar
- Añadidas funciones de cambio de coordenadas esféricas-cilíndricas;  **esf_a_cil**, **cil_a_esf**
- Algunos cambios menores

## *v0.3.7.3*

- Añadidas funciones para matrices de transformación en coordenadas homogéneas **mscale**, **mtras** y adaptada **mrot** a coordenadas homogéneas
- Ampliada la funcionalidad de la función **round**, redondea a número entero, o a número decimal con 'n' cifras decimales, según los 8 modos de ROUNDING_MODE
- Excepciones almacenan la causa previa de error (pila de error)

## *v0.3.7.2*
- Añadidos operadores **igual exacto**(==) e **igual estricto**(===)
- Añadida función aleatoria **elegir**
- Tipos implementan ahora *Cloneable*

## *v0.3.7.1*
- Añadida función **set** para modificar un elemento en vector o fila en matriz
- Algunos cambios menores

## *v0.3.7*
- Modificada precedencia de operadores. Algunos operadores han cambiado su precedencia relativa
- Cambios en funciones **int** (integral), **dif** (derivada) y **frec_lamb** (conversión frecuencia/longitud de onda)
- Añadida función **montecarlo**, que aplica el método de Monte Carlo
- Ampliada funcionalidad del modo REPL
- Exportación de vector/matriz a tabla HTML
- Algunos cambios menores

## *v0.3.6.1*
- Corregido bug en la versión 0.3.6 con el tipo vector
- Ampliada funcionalidad del modo REPL
- Añadidas funciones del operador vectorial nabla; **gradiente, divergencia, rot** (rotacional) y **difdir** (derivada direccional)

## *v0.3.6*
- Constante **e** renombrada a **_e** para evitar confusiones con variables y notación científica
- Función *_error_* renombrada a **_throw_**
- Función *digitos* renombrada a **digfrec**
- Función **cpu** ahora devuelve el tiempo en nanos en lugar de millis
- Mejorado modo REPL
- Añadida importación y exportación CSV (valores separados por comas) hacia/desde vectores y matrices JME
- Añadidas funciones financieras; **interes** (simple, compuesto y continuo), **van**, **tir**
- Añadida función de regresión lineal y parabólica de series de datos, y función de resolución por el método de la secante
- Añadidas funciones de tratamiento de excepciones; **_catch_, _assert_** junto con la renombrada **_throw_**
- Algunos cambios menores

## *v0.3.5*
- Ampliación del número de funciones y operadores
- Añadidas funciones de conversión de unidades
- Optimizadas algunas funciones y operadores
- Añadidas constantes de Física y Astronomía
- Algunos cambios menores

## *v0.3.4*
- Ampliación del número de funciones y operadores
- Optimizadas y corregidas algunas funciones y operadores
- Modo **REPL** en jar ejecutable
- Algunos cambios menores

## *v0.3.3*
- Optimizado uso de memoria
- Solucionado bug en método entrada de la clase Expresion
- Formato de salida de la unidad imaginaria cambia de **i** a **I**
- Añadida función para matriz de rotación 2D y 3D y frecuencia en vector
- Compatible con **Android**

## *v0.3.2.1*
- Agregada función **sol_sis** para resolución de sistemas compatibles determinados y corregido bug en determinante

## *v0.3.2*
- Excepciones heredan ahora de *RuntimeException*
- Ampliación del nº de funciones (determinante, matriz inversa, coordenadas esféricas y cilíndricas, media ponderada, ...)

## *v0.3.1.1*
- Implementada serialización

## *v0.3.1*
- Constantes globales predefinidas y definidas por el usuario
- Ampliación del nº de operadores y funciones

## *v0.3.0*
- Funciones definidas por el usuario
- Breve ampliación del nº de operadores y funciones

## *v0.2.2*
- Operaciones sobre conjuntos
- Breve ampliación del nº de operadores y funciones

## *v0.2.0*
- Optimización del tratamiento de vectores
- Ampliación del nº de operadores y funciones
- Corrección de bugs

## *v0.1.0*
- Versión inicial, Proyecto de Fin de Carrera

[TOC]
