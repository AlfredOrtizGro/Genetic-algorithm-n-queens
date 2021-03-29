____________________________________________________________________________

                               >--�--< 
                          1487__OpMutInsertion                                      
        Operador de mutaci�n por inserci�n del Algoritmo Gen�tico
____________________________________________________________________________

Ing. Jonathan Rojas Sim�n (IDS_Jonathan_Rojas@hotmail.com) 
Dr. Ren� Arnulfo Garc�a Hern�ndez (renearnulfo@hotmail.com)
____________________________________________________________________________

Este m�dulo realiza la mutaci�n de un conjunto de individuos para modificar 
algunas de sus caracter�sticas tomando en cuenta el algoritmo de mutaci�n por
inserci�n y tambi�n a partir de una probabilidad determinada por el usuario.

Para el correcto funcionamiento del Algoritmo Gen�tico con este operador se 
recomienda utilizar probabilidades de mutaci�n menores a 15, debido a que 
es posible generar una meyor descomposici�n de la poblaci�n si se toman valores
de probabilidad mayores a 15.

Para consultar m�s detalles del cambio de genes en los individuos por inserci�n
se pueden consultar las siguientes referencias: 

 - (Fogel, 1988)                 -> An evolutionary approach to the traveling 
                                    salesman problem.
 - (Larranaga et al., 1999)      -> Genetic algorithms for the travelling 
                                    salesman problem: A review of 
                                    representations and operators. 
 - (Michalewicz & Hartley, 1996) -> Genetic algorithms + data structures = 
                                    evolution programs.
____________________________________________________________________________

                                                                            
SINTAXIS:

____________________________________________________________________________


-CONFIG || (-FWORK:str -FNEXTG:str -FPOP:str [-PMUT:int] [-REPORTINS:str]) 

-CONFIG     Archivo donde vienen los dem�s par�metros descritos abajo  

-FWORK      Ruta de trabajo donde se toman en cuenta los descendientes de la
            cruza para aplicar la mutaci�n de los mismos.
-FNEXTG     SubRuta con la generaci�n de individuos a mutar
-FPOP       SubRuta de -FNEXTG que contiene toda la poblaci�n de descendientes.
-PMUT       Probabilidad de mutaci�n de cada gen del individuo, si no se 
            especifica este valor se establece con el valor de 1.0 por defecto. 
-REPORTINS  Nombre del archivo de texto que almacena el reporte generado, si 
            no se especifica no genera el archivo.

____________________________________________________________________________


Ejemplo 1:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       0.8
-REPORTINS  Report_Ins_1.txt

El componente realiza la lectura de la carpeta WORK a partir de la
especificaci�n de la etiqueta @WORK_PATH@, donde se determinar� por autom�tico 
el directorio donde se est� ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ ser� sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizar� una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutaci�n de cada uno de los 
individuos, tomando una probabilidad de 0.8 (de 0 a 100). En caso de que alguno 
de los individuos haya entrado en esta probabilidad se guarda su nombre en el 
archivo MUTATED.TXT, el cual almacena los nombres de los indidivuos mutados. 
Adem�s de este archivo, se genera un reporte de las operaciones de mutaci�n 
realizadas en cada individuo en el archivo "Report_Ins_1.txt". En caso de que 
este archivo no de especifique como par�metro no se generar� el reporte.
____________________________________________________________________________


Ejemplo 2:

-CONFIG CONFIG.txt

Hace lo mismo que el ejemplo 1 pero manda llamar al archivo ParamPop.txt 
donde estan los siguientes par�metros:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       0.4
-REPORTINS  Report_Ins_2.txt

El componente realiza la lectura de la carpeta WORK a partir de la
especificaci�n de la etiqueta @WORK_PATH@, donde se determinar� por autom�tico 
el directorio donde se est� ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ ser� sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizar� una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutaci�n de cada uno de los 
individuos, tomando una probabilidad de 0.4 (de 0 a 100). En caso de que alguno 
de los individuos haya entrado en esta probabilidad se guarda su nombre en el 
archivo MUTATED.TXT, el cual almacena los nombres de los indidivuos mutados. 
Adem�s de este archivo, se genera un reporte de las operaciones de mutaci�n 
realizadas en cada individuo en el archivo "Report_Ins_2.txt". En caso de que 
este archivo no de especifique como par�metro no se generar� el reporte.
____________________________________________________________________________


Ejemplo 3:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       1.2
-REPORTINS  Report_Ins_3.txt

El componente realiza la lectura de la carpeta WORK a partir de la
especificaci�n de la etiqueta @WORK_PATH@, donde se determinar� por autom�tico 
el directorio donde se est� ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ ser� sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizar� una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutaci�n de cada uno de los 
individuos, tomando una probabilidad de 1.2 (0 a 100). En caso de que alguno 
de los individuos haya entrado en esta probabilidad se guarda su nombre en el 
archivo MUTATED.TXT, el cual almacena los nombres de los indidivuos mutados. 
Adem�s de este archivo, se genera un reporte de las operaciones de mutaci�n 
realizadas en cada individuo en el archivo "Report_Ins_3.txt". En caso de que 
este archivo no de especifique como par�metro no se generar� el reporte.
____________________________________________________________________________


Ejemplo 4:

-FWORK     @WORK_PATH@\WORK
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-REPORTINS  Report_Ins_4.txt


El componente realiza la lectura de la carpeta WORK a partir de la
especificaci�n de la etiqueta @WORK_PATH@, donde se determinar� por autom�tico 
el directorio donde se est� ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ ser� sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizar� una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutaci�n de cada uno de los 
individuos, tomando una probabilidad de 1.0 (establecida por defecto en escala
de 0 a 100). 
En caso de que alguno de los individuos haya entrado en esta probabilidad se 
guarda su nombre en el archivo MUTATED.TXT, el cual almacena los nombres de 
los indidivuos mutados. Adem�s de este archivo, se genera un reporte de las 
operaciones de mutaci�n realizadas en cada individuo en el archivo 
"Report_Ins_4.txt". En caso de que este archivo no de especifique como 
par�metro no se generar� el reporte.
____________________________________________________________________________


Ejemplo 5:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       0.3
-REPORTINS  Report_Ins_5.txt


El componente realiza la lectura de la carpeta WORK a partir de la
especificaci�n de la etiqueta @WORK_PATH@, donde se determinar� por autom�tico 
el directorio donde se est� ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ ser� sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizar� una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutaci�n de cada uno de los 
individuos, tomando una probabilidad de 0.3 (en escala de 0 a 100). En caso de 
que alguno de los individuos haya entrado en esta probabilidad se guarda su 
nombre en el archivo MUTATED.TXT, el cual almacena los nombres de los 
indidivuos mutados. 
Adem�s de este archivo, se genera un reporte de las operaciones de mutaci�n 
realizadas en cada individuo en el archivo "Report_Ins_5.txt". En caso de que 
este archivo no de especifique como par�metro no se generar� el reporte.
____________________________________________________________________________

 REFERENCIAS

 (Fogel, 1988)  Fogel, D. B. (1988). An evolutionary approach to the traveling
                salesman problem. Biological Cybernetics, 60(2), 139-144.

 (Larranaga et al., 1999) - Larranaga, P., Kuijpers, C. M. H., Murga, R. H., 
                Inza, I., & Dizdarevic, S. (1999). Genetic algorithms for the 
                travelling salesman problem: A review of representations and 
                operators. Artificial Intelligence Review, 13(2), 129-170.

 (Michalewicz & Hartley, 1996) - Michalewicz, Z., & Hartley, S. J. (1996). 
                Genetic algorithms+ data structures= evolution programs.
                Mathematical Intelligencer, 18(3), 71.
