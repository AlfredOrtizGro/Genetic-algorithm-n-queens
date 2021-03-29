__________________________________________________________________________________________________

								1458__OpSelTorneo
					    	Operador torneo --- Tournament Operator.

__________________________________________________________________________________________________

		 	M.C.C. Christian Eduardo Millán Hernández <ceduardo dot millan at gmail dot com>
			Dr. René Arnulfo García hHernández <renearnulfo at hotmail dot com>

__________________________________________________________________________________________________

El operador torneo[1] selecciona un pequeño grupo de individuos y compiten por el derecho a ser padre.
Elegidos los individuos, el que posee la mejor evalución es seleccionado.
Existen dos versiones: Determinística y Probabilistica

1. Versión determinística 
	1.1. Selecciona k individuos al azar
	1.2. Se comparan con base en su aptitud
	1.3. El ganador del torneo es aquel individuo más apto
	1.4. Realizar los pasos anteriores n veces hasta completar la cantida de padres deseados

2. Versión probabilistica
	La diferencia entre la versión anterior es la implementación de probabilidad.
	La probailidad de termina si en el paso 1.3. se elige el mñas apto o el peor ( este probabilida da 
	oporotunidad a los peores padres de ser seleccionados)

3. Versión modificada
	Se agrega una opción de elegir los padres pares por torneo y los padres impares de manera aleatoria 

Documentación complementaria y ejemplo de uso, visitar: https://www.youtube.com/

__________________________________________________________________________________________________

Sintaxis:

"-FWORK:str -FACTG:str -FFA:str -NI:int -FSELECT:str [-KTOURN:int] [-PTOURN:int] [-ONEALEO:ON:OF]
 [-THSELECT:int] [-OPTIMOD:MIN:MAX] [-LOG:ON:OFF] [-FLOG:str]"

Sintaxis:

-CONFIG		Ruta del archivo que contiene los paramétros siguiente:

-FWORK      Directorio de trabajo donde se ejecuta el algoritmo genético.
-FACTG      Directorio en donde se encuentra la población actual
-FFA        Directorio en donde se encuentra el valor de aptitud de la población actual
-FSELECT    Archivo donde se guarda la lista de padres seleccionados
-KTOURN     Tamaño del torneo, por default 2
-PTOURN     Probabilidad de elegir al mejor adaptado, por defualt 100
-ONEALEO    Se seleciona el padre par por torneo y el padre impar aleatorio, por default OFF
-THSELECT   Número de hilos, por default 1 (1 = secuencial | 0 = auto-calculan los hilos))
-OPTIMOD    Modo de optimización: (MIN|MAX), por default MAX
-LOG        Impresión del log: ON:OFF, por default ON
-FLOG       Archivo donde se guarda el log, por default log.txt



__________________________________________________________________________________________________

Referencias

Coello, C. A. C., & Zacatenco, C. S. P. (2004). Introducción a la computación evolutiva (Notas de curso). CINVESTAV-IPN, Departamento de Ingeniería Eléctrica, Sección de Computación. México, DF.

Brindle, A. (1980). Genetic algorithms for function optimization. https://doi.org/10.7939/R3FB4WS2W



