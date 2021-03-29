____________________________________________________________________________

                               >--ö--< 
                          1487__OpMutInsertion                                      
        Operador de mutación por inserción del Algoritmo Genético
____________________________________________________________________________

Ing. Jonathan Rojas Simón (IDS_Jonathan_Rojas@hotmail.com) 
Dr. René Arnulfo García Hernández (renearnulfo@hotmail.com)
____________________________________________________________________________

Este módulo realiza la mutación de un conjunto de individuos para modificar 
algunas de sus características tomando en cuenta el algoritmo de mutación por
inserción y también a partir de una probabilidad determinada por el usuario.

Para el correcto funcionamiento del Algoritmo Genético con este operador se 
recomienda utilizar probabilidades de mutación menores a 15, debido a que 
es posible generar una meyor descomposición de la población si se toman valores
de probabilidad mayores a 15.

Para consultar más detalles del cambio de genes en los individuos por inserción
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

-CONFIG     Archivo donde vienen los demás parámetros descritos abajo  

-FWORK      Ruta de trabajo donde se toman en cuenta los descendientes de la
            cruza para aplicar la mutación de los mismos.
-FNEXTG     SubRuta con la generación de individuos a mutar
-FPOP       SubRuta de -FNEXTG que contiene toda la población de descendientes.
-PMUT       Probabilidad de mutación de cada gen del individuo, si no se 
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
especificación de la etiqueta @WORK_PATH@, donde se determinará por automático 
el directorio donde se esté ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ será sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizará una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutación de cada uno de los 
individuos, tomando una probabilidad de 0.8 (de 0 a 100). En caso de que alguno 
de los individuos haya entrado en esta probabilidad se guarda su nombre en el 
archivo MUTATED.TXT, el cual almacena los nombres de los indidivuos mutados. 
Además de este archivo, se genera un reporte de las operaciones de mutación 
realizadas en cada individuo en el archivo "Report_Ins_1.txt". En caso de que 
este archivo no de especifique como parámetro no se generará el reporte.
____________________________________________________________________________


Ejemplo 2:

-CONFIG CONFIG.txt

Hace lo mismo que el ejemplo 1 pero manda llamar al archivo ParamPop.txt 
donde estan los siguientes parámetros:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       0.4
-REPORTINS  Report_Ins_2.txt

El componente realiza la lectura de la carpeta WORK a partir de la
especificación de la etiqueta @WORK_PATH@, donde se determinará por automático 
el directorio donde se esté ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ será sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizará una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutación de cada uno de los 
individuos, tomando una probabilidad de 0.4 (de 0 a 100). En caso de que alguno 
de los individuos haya entrado en esta probabilidad se guarda su nombre en el 
archivo MUTATED.TXT, el cual almacena los nombres de los indidivuos mutados. 
Además de este archivo, se genera un reporte de las operaciones de mutación 
realizadas en cada individuo en el archivo "Report_Ins_2.txt". En caso de que 
este archivo no de especifique como parámetro no se generará el reporte.
____________________________________________________________________________


Ejemplo 3:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       1.2
-REPORTINS  Report_Ins_3.txt

El componente realiza la lectura de la carpeta WORK a partir de la
especificación de la etiqueta @WORK_PATH@, donde se determinará por automático 
el directorio donde se esté ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ será sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizará una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutación de cada uno de los 
individuos, tomando una probabilidad de 1.2 (0 a 100). En caso de que alguno 
de los individuos haya entrado en esta probabilidad se guarda su nombre en el 
archivo MUTATED.TXT, el cual almacena los nombres de los indidivuos mutados. 
Además de este archivo, se genera un reporte de las operaciones de mutación 
realizadas en cada individuo en el archivo "Report_Ins_3.txt". En caso de que 
este archivo no de especifique como parámetro no se generará el reporte.
____________________________________________________________________________


Ejemplo 4:

-FWORK     @WORK_PATH@\WORK
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-REPORTINS  Report_Ins_4.txt


El componente realiza la lectura de la carpeta WORK a partir de la
especificación de la etiqueta @WORK_PATH@, donde se determinará por automático 
el directorio donde se esté ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ será sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizará una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutación de cada uno de los 
individuos, tomando una probabilidad de 1.0 (establecida por defecto en escala
de 0 a 100). 
En caso de que alguno de los individuos haya entrado en esta probabilidad se 
guarda su nombre en el archivo MUTATED.TXT, el cual almacena los nombres de 
los indidivuos mutados. Además de este archivo, se genera un reporte de las 
operaciones de mutación realizadas en cada individuo en el archivo 
"Report_Ins_4.txt". En caso de que este archivo no de especifique como 
parámetro no se generará el reporte.
____________________________________________________________________________


Ejemplo 5:

-FWORK      @WORK_PATH@\WORK
-FNEXTG     NEXTGEN_4_
-FPOP       POPULATION
-PMUT       0.3
-REPORTINS  Report_Ins_5.txt


El componente realiza la lectura de la carpeta WORK a partir de la
especificación de la etiqueta @WORK_PATH@, donde se determinará por automático 
el directorio donde se esté ejecutando este componente. 
Ejemplo. Si se ejecuta este componente en el directorio C:\GENGEN\test1, 
entonces la etiqueta @WORK_PATH@ será sustituida por C:\GENGEN\test1. 

A partir de ese directorio, se realizará una lectura de todos los individuos 
contenidos en POPULATION (el directorio POPULATION debe estar contenido en 
NEXTGEN_4_ y WORK) para realizar el proceso de mutación de cada uno de los 
individuos, tomando una probabilidad de 0.3 (en escala de 0 a 100). En caso de 
que alguno de los individuos haya entrado en esta probabilidad se guarda su 
nombre en el archivo MUTATED.TXT, el cual almacena los nombres de los 
indidivuos mutados. 
Además de este archivo, se genera un reporte de las operaciones de mutación 
realizadas en cada individuo en el archivo "Report_Ins_5.txt". En caso de que 
este archivo no de especifique como parámetro no se generará el reporte.
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
