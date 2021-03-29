_______________________________________________________________

                          >--ö--<
                       1473__OpXCycle
                 Cycle crossover operator (CX)
_______________________________________________________________

Ing. Jonathan Rojas Simón (IDS_Jonathan_Rojas@hotmail.com).   
Dr. René Arnulfo García Hernández (renearnulfo@hotmail.com).       
_______________________________________________________________

El operador de cruza por ciclos realiza el intercambio genético
de un conjunto de individuos mediante el argoritmo de cruza por
ciclos (CX). Esta cruza es realizada mediante una probabilidad 
(-XPROB) determinada por el usuario (opcional). El operador de
cruza por ciclos es una técnica de cruza propuesta por Oliver,
Smith y Holland en 1987 (Oliver, Smith & Holland, 1987).

NOTA: La probabilidad de cruza permite generar cruzas entre 
	  varios pares de individuos padres

Básicamente el operador realiza el intercambio genético mediante 
tres sencillos pasos, los cuales son:

1. Determinar un ciclo mediante las posiciones de los valores
   de los padres.
2. Copiar los valores que están en el ciclo del padre 1 para 
   generar el primer descendiente.
3. Rellenar los genes restantes del descendiente con los valores
   no comprendidos del ciclo del padre 2
4. Repetir los pasos 1, 2 y 3 tomando como referencia el padre 2

Para más detalles sobre la ejecución de este componente y 
consultar sobre el intercambio genético de este operador se
recomienda visualizar el siguiente video: 

https://youtu.be/pWAK_CxxO0M

_______________________________________________________________

Sintaxis   

-CONFIG:str | (-FWORK:str -FACTG:str -FNEXTG:str -FPOP:str -FSELECT:str -NI:int -NELITE:int [-XPROB:int] [-REPORTCX:str])
                                                              
-CONFIG		Ruta y nombre del archivo de texto donde se describen
			los parámetros descritos debajo 
-FWORK		Directorio de trabajo por el cual se realizará el
			enlace con las subcarpetas contenidas en el.
-FACTG      Nombre del directorio que contiene los individuos de la
			actual generación
-FNEXTG     Nombre del directorio de la siguiente generación
-FPOP       Nombre del directorio donde recupera (-FACTG) y almacena 
			(-FNEXTG) la información genética de la población modificada.
-FSELECT    Archivo de texto que enlista todos los individuos padres
-NI         Número de individuos de cada generación
-NELITE     Número de individuos elite de cada generación
-XPROB      Número entero que determina la probabilidad de cruza (en 
			un rango de 0 a 100), si esta etiqueta no se especifica 
			se determinará como 70 por defecto. 
-REPORTCX   Nombre del archivo de texto que almacena las operaciones
            relacionadas al cambio de individuos, este archivo se
            generará en el directorio de -FNEXTG.   

________________________________________________________________

Ejemplo 1

-CONFIG CONFIG.txt

La línea anterior especificaría los parámetros debajo:

-FWORK     @WORK_PATH@\WORK
-FACTG     ACTGEN_3_
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-FSELECT   PARENTS.txt
-NI        10
-NELITE    3
-XPROB	   100
-REPORTCX  RepoOpCX_1.txt


El componente recuperará una lista de padres (determinados del
archivo PARENTS.txt), los cuales están ubicados en el directorio 
POPULATION del directorio ACTGEN_3_ para aplicar la cruza por 
ciclos y generará 7 descendientes (Número obtenido de la diferencia
entre -NI -NELITE). Los cambios de genes en los 7 descendientes serán de 
acuerdo a una probabilidad del 100%.
Por último, los descendientes generados son almacenados en el
subdirectorio POPULATION ubicado en el directorio NEXTGEN_4_, además
el archivo de reporte RepoOpCX_1.txt se almacenará en el directorio
WORK el cual mustra a detalle lo realizado de la cruza en
la población a partir de cada generación.

Uso de @WORK_PATH@

El componente genera la carpeta WORK a partir de la especificación de 
la etiqueta @WORK_PATH@, donde se determinará por automático el directorio 
donde se esté ejecutando este componente. Ejemplo. Si se ejecuta este 
componente en el directorio C:\GENGEN\test1, entonces la etiqueta 
@WORK_PATH@ será sustituida por C:\GENGEN\test1.
________________________________________________________________

Ejemplo 2

-CONFIG CONFIG.txt

La línea anterior especificaría los parámetros debajo:

-FWORK     @WORK_PATH@\WORK
-FACTG     ACTGEN_3_
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-FSELECT   PARENTS.txt     
-NI        100
-NELITE    3 
-XPROB  80
-REPORTCX  RepoOpCX_2.txt

El componente recuperará una lista de padres (determinados del
archivo PARENTS.txt), los cuales están ubicados en el directorio 
POPULATION del directorio ACTGEN_3_ para aplicar la cruza por 
ciclos y generará 97 descendientes (Número obtenido de la diferencia
entre -NI -NELITE. Los cambios de genes en los 97 descendientes 
serán de acuerdo a una probabilidad del 80%.
Por último, los descendientes generados son almacenados en el
subdirectorio POPULATION ubicado en el directorio NEXTGEN_4_, además
el archivo de reporte RepoOpCX_2.txt se almacenará en el directorio
WORK el cual mustra a detalle lo realizado de la cruza en
la población a partir de cada generación.

Uso de @WORK_PATH@

El componente genera la carpeta WORK a partir de la especificación de 
la etiqueta @WORK_PATH@, donde se determinará por automático el directorio 
donde se esté ejecutando este componente. Ejemplo. Si se ejecuta este 
componente en el directorio C:\GENGEN\test1, entonces la etiqueta 
@WORK_PATH@ será sustituida por C:\GENGEN\test1.

________________________________________________________________

Ejemplo 3

-CONFIG CONFIG.txt

La línea anterior especificaría los parámetros debajo:

-FWORK     @WORK_PATH@\WORK
-FACTG     ACTGEN_3_
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-FSELECT   PARENTS.txt                     
-NI        100
-NELITE    1                  
-XPROB  20
-REPORTCX  RepoOpCX_3.txt

El componente recuperará una lista de padres (determinados del
archivo PARENTS.txt), los cuales están ubicados en el directorio 
POPULATION del directorio ACTGEN_3_ para aplicar la cruza por 
ciclos y generará 97 descendientes (Número obtenido de la diferencia
entre -NI -NELITE. Los cambios de genes en los 99 descendientes 
serán de acuerdo a una probabilidad del 20%.
Por último, los descendientes generados son almacenados en el
subdirectorio POPULATION ubicado en el directorio NEXTGEN_4_, además
el archivo de reporte RepoOpCX_3.txt se almacenará en el directorio
WORK el cual mustra a detalle lo realizado de la cruza en
la población a partir de cada generación.

Uso de @WORK_PATH@

El componente genera la carpeta WORK a partir de la especificación de 
la etiqueta @WORK_PATH@, donde se determinará por automático el directorio 
donde se esté ejecutando este componente. Ejemplo. Si se ejecuta este 
componente en el directorio C:\GENGEN\test1, entonces la etiqueta 
@WORK_PATH@ será sustituida por C:\GENGEN\test1.

________________________________________________________________

Ejemplo 4

-CONFIG CONFIG.txt

La línea anterior especificaría los parámetros debajo:

-FWORK     @WORK_PATH@\WORK
-FACTG     ACTGEN_3_
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-FSELECT   PARENTS.txt                     
-NI        100
-NELITE    3
-REPORTCX  RepoOpCX_4.txt

El componente recuperará una lista de padres (determinados del
archivo PARENTS.txt), los cuales están ubicados en el directorio 
POPULATION del directorio ACTGEN_3_ para aplicar la cruza por 
ciclos y generará 97 descendientes (Número obtenido de la diferencia
entre -NI -NELITE. Los cambios de genes en los 97 descendientes 
serán de acuerdo a una probabilidad del 70%, en este caso, este 
valor fue especificado por defecto.
Por último, los descendientes generados son almacenados en el
subdirectorio POPULATION ubicado en el directorio NEXTGEN_4_, además
el archivo de reporte RepoOpCX_4.txt se almacenará en el directorio
WORK el cual mustra a detalle lo realizado de la cruza en
la población a partir de cada generación.

Uso de @WORK_PATH@

El componente genera la carpeta WORK a partir de la especificación de 
la etiqueta @WORK_PATH@, donde se determinará por automático el directorio 
donde se esté ejecutando este componente. Ejemplo. Si se ejecuta este 
componente en el directorio C:\GENGEN\test1, entonces la etiqueta 
@WORK_PATH@ será sustituida por C:\GENGEN\test1.

________________________________________________________________

Ejemplo 5

-CONFIG CONFIG.txt

La línea anterior especificaría los parámetros debajo:

-FWORK     @WORK_PATH@\WORK
-FACTG     ACTGEN_3_
-FNEXTG    NEXTGEN_4_
-FPOP      POPULATION
-FSELECT   PARENTS.txt                     
-NI        100
-NELITE    3                  
-XPROB  90
-REPORTCX  RepoOpCX_5.txt

El componente recuperará una lista de padres (determinados del
archivo PARENTS.txt), los cuales están ubicados en el directorio 
POPULATION del directorio ACTGEN_3_ para aplicar la cruza por 
ciclos y generará 97 descendientes (Número obtenido de la diferencia
entre -NI -NELITE. Los cambios de genes en los 97 descendientes 
serán de acuerdo a una probabilidad del 90%.
Por último, los descendientes generados son almacenados en el
subdirectorio POPULATION ubicado en el directorio NEXTGEN_4_, además
el archivo de reporte RepoOpCX_5.txt se almacenará en el directorio
WORK el cual mustra a detalle lo realizado de la cruza en
la población a partir de cada generación.

Uso de @WORK_PATH@

El componente genera la carpeta WORK a partir de la especificación de 
la etiqueta @WORK_PATH@, donde se determinará por automático el directorio 
donde se esté ejecutando este componente. Ejemplo. Si se ejecuta este 
componente en el directorio C:\GENGEN\test1, entonces la etiqueta 
@WORK_PATH@ será sustituida por C:\GENGEN\test1.

_________________________________________________________________

Referencia

(Oliver, Smith & Holland, 2018) - Oliver, I. M., Smith, D., & Holland, 
	J. R. (1987). Study of permutation crossover operators on the traveling 
	salesman problem. In Genetic algorithms and their applications: 
	proceedings of the second International Conference on Genetic Algorithms: 
	July 28-31, 1987 at the Massachusetts Institute of Technology, Cambridge, 
	MA. Hillsdale, NJ: L. Erlhaum Associates, 1987.
