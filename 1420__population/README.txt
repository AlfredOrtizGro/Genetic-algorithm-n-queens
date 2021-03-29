____________________________________________________________________________

                               >--�--< 
                          1420__population                                      
        Generador de Poblaci�n inicial para el algoritmo gen�tico

____________________________________________________________________________

Ing. Jonathan Rojas Sim�n (IDS_Jonathan_Rojas@hotmail.com) 
Dr. Ren� Arnulfo Garc�a Hern�ndez (renearnulfo@hotmail.com)
____________________________________________________________________________

Este m�dulo presenta las siguientes opciones para generar la poblaci�n inicial 
en un algoritmo gen�tico:

   1.- Combinaciones de una sola base
   2.- Combinaciones con genes de diferentes bases (Codificaci�n base N)
   3.- Permutaciones desde n�meros iniciales (0-N)
   4.- Permutaciones a partir de rangos establecidos (MIN-MAX)

Cada individuo es configurado en un archivo binario (.bin), el cual guarda en 
un vector de n�meros reales (float de 4 bytes formato IEEE754) los genes del 
individuo. Cabe recordar que en un n�mero real cabe un entero, exadecimal, 
binario, caracter ascii, etc.

Para conocer m�s detalles sobre el uso y la generaci�n de variantes se 
recomienda ver el siguiente video:

https://youtu.be/wpafrBUY9gA

____________________________________________________________________________

                                                                            
SINTAXIS:

____________________________________________________________________________


-CONFIG || (-FWORK:str -FNEXTG:str -FPOP:str [-TYPE (INT|REAL)] [-PRE:str] 
[-INF:int] [-POS:str] [-REPEAT (TRUE|FALSE)]) -NI:int (((-NGEN:int ||(-NGENMIN:int -NGENMAX:int)) 
-VMININT:int -VMAXINT:int) || -BASE:str) [-PRECISION:int] [-REPORTPOP:str]

-CONFIG     Archivo donde vienen los dem�s par�metros descritos abajo  

-FWORK      Ruta de trabajo donde se va a crear la primera generaci�n y las 
            dem�s generaciones              
-FNEXTG     SubRuta con la generaci�n siguiente                                
-FPOP       SubRuta donde se van a crear los individuos                        
-TYPE       Tipo de dato a generar del individuo (int o real) 
-PRE        Cadena prefijo del nombre de los individuos a generar (solo letras)
-INF        N�mero secuencial que va despu�s del prefijo y antes del postfijo  
-POST       Cadena postfijo del nombre de los individuos a generar (solo 
            letras)
-REPEAT     TRUE: Si el valor del gen se puede repetir en el individuo, FALSE 
            en caso de que los valores establecidos como rangos no se deben de 
             repetir.
-NI         N�mero de individuos                                               
-NGEN       N�mero de genes de un individuo                                    
-NGENMIN    N�mero m�nimo de cromosomas en individuos de longitud variable,    
            (NumGen=0 o no aparecer)
-NGENMAX    N�mero m�ximo de cromosomas en individuos de longitud variable,    
            (NumGen=0 o no aparecer)
-VMININT    Limite entero del Valor m�nimo del rango del valor de un gen       
-VMAXINT    Limite entero del Valor m�ximo del rango del valor de un gen       
-BASE       Ruta y nombre del archivo de texto donde se definen las bases a 
            trabajar por gen, las bases ser�n de acuerdo a la Siguiente 
            Expresi�n Regular:

                  (\d+)[{](\d+([;]\d+|[-]\d+)*)[}]
                    G1      G2       G3

            Donde G1 representa la base a tratar, G2 representa el gen en 
            espec�fico a aplicar la base de G1 y G3 establece la separaci�n
            de genes a aplicar la Base G1 o los rangos de genes respectivos.
-PRECISION  N�mero de digitos de precision en tipos de datos reales
-REPORTPOP  Nombre del archivo de texto que almacena el reporte generado, si 
            no se especifica no genera el archivo.

____________________________________________________________________________


Ejemplo 1:

-FWORK @WORK_PATH@\WORK
-FNEXTG NEXTGEN_1_
-FPOP POPULATION
-NI 150 
-NGEN 50
-VMININT 0 
-VMAXINT 1 
-TYPE INT 
-PRE IND 
-INF 11 
-POS GEN 
-REPEAT true
-REPORTPOP RepPopulation_T1.txt

El componente genera la carpeta WORK a partir de la especificaci�n de la 
etiqueta @WORK_PATH@, donde se determinar� por autom�tico el directorio donde
se est� ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ ser� sustituida
por C:\GENGEN\test1. A partir de ese directorio se generar� el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generar� 
el directorio POPULATION donde se generar�n 150 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzar�n desde IND11GEN.bin
hasta IND160GEN.bin. Cada individuo tendr� 50 genes (especificado en -NGEN) 
con una codificaci�n binaria desde 0 hasta 1 (codificaci�n binaria) 
considerando que estos valores se pueden repetir (-REPEAT true) para cada gen 
de los individuos. Para la etiqueta -PRE se deben de especificar caracteres de 
letras para evitar confusiones con la definici�n del n�mero infijo (-INF). 
Para el caso del posfijo (-POS) aplica la misma condici�n. Por �ltimo, si se 
desea imprimir un reporte de lo realizado en este operador se coloca en la 
etiqueta -REPORTPOP el nombre del archivo de texto que almacena lo realizado 
en este operador, en este caso el archivo RepPopulation_T1.txt se almacenar� 
en el directorio C:\GENGEN\test1\WORK .
____________________________________________________________________________


Ejemplo 2:

-CONFIG CONFIG.txt

Hace lo mismo que el ejemplo 1 pero manda llamar al archivo ParamPop.txt 
donde estan los siguientes par�metros:

-FWORK @WORK_PATH@\WORK
-FNEXTG NEXTGEN_1_
-FPOP POPULATION
-NI 100 
-NGEN 50
-VMININT 0 
-VMAXINT 15 
-TYPE INT 
-PRE IND 
-INF 11 
-POS GEN 
-REPEAT true
-REPORTPOP RepPopulation_T2.txt

El componente genera la carpeta WORK a partir de la especificaci�n de la 
etiqueta @WORK_PATH@, donde se determinar� por autom�tico el directorio donde
se est� ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ ser� sustituida
por C:\GENGEN\test1. A partir de ese directorio se generar� el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generar� 
el directorio POPULATION donde se generar�n 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzar�n desde IND11GEN.bin
hasta IND110GEN.bin. Cada individuo tendr� 50 genes (especificado en -NGEN) 
con una codificaci�n binaria desde 0 hasta 15 (codificaci�n hexadecimal) 
considerando que estos valores se pueden repetir (-REPEAT true) para cada gen 
de los individuos. Para la etiqueta -PRE se deben de especificar caracteres de 
letras para evitar confusiones con la definici�n del n�mero infijo (-INF). 
Para el caso del posfijo (-POS) aplica la misma condici�n. Por �ltimo, si se 
desea imprimir un reporte de lo realizado en este operador se coloca en la 
etiqueta -REPORTPOP el nombre del archivo de texto que almacena lo realizado 
en este operador, en este caso el archivo RepPopulation_T2.txt se almacenar� 
en el directorio C:\GENGEN\test1\WORK .

____________________________________________________________________________


Ejemplo 3:

-FWORK @WORK_PATH@\WORK
-FNEXTG NEXTGEN_1_
-FPOP POPULATION
-NI 200 
-BASE  Base.txt
-TYPE INT 
-PRE IND 
-INF 11 
-POS GEN
-REPORTPOP RepPopulation_T3.txt

El componente genera la carpeta WORK a partir de la especificaci�n de la 
etiqueta @WORK_PATH@, donde se determinar� por autom�tico el directorio donde
se est� ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ ser� sustituida
por C:\GENGEN\test1. A partir de ese directorio se generar� el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generar� 
el directorio POPULATION donde se generar�n 200 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzar�n desde IND11GEN.bin
hasta IND210GEN.bin. El individuo generado ser� de acuerdo a la generaci�n de 
diferentes bases especificadas en el archivo Base.txt, por ejemplo dentro de
este archivo se encuentra la siguiente definici�n:

4{5}
100{0;6-7},10{1;3}
16{2;4;8;12-14}
7{11-9}

Donde el n�mero que est� afuera de las llaves se trata de la base a 
considerar, los valores de las bases comenzar�n desde el 0 hasta N-1. Los 
n�meros contenidos en las llaves son los genes (o rangos de genes donde se 
aplicar� la base solicitada), en el caso de aplicar una base a genes 
espec�ficos se delimitar�n mediante el caracter ";" y en el caso de rangos de
genes ser� de acuerdo con el caracter "-" donde se especificar� el valor m�nimo
y m�ximo (o viceversa). Cada expresi�n de una base se debe de delimitar con el 
caracter ",".

Para la anterior expresi�n se aplicar�n las base 4 [0-3] en el gen 5, la base
100 [0-99] se aplicar� en los genes 0, 6 y 7, la base 10 [0-9] se aplicar� en
los genes 1 y 3, la base 16 [0-15] se aplicar� en los genes 2, 4, 8, 12, 13 y 
14, por �ltimo la base 7 se aplicar� en los genes 9, 10, y 11. Por lo tanto 
la representaci�n del individuo ser� de 15 genes a partir de la siguiente
representaci�n:
 
  0     1    2     3    4     5    6     7     8     9   10    11   12    13
[0-99][0-9][0-15][0-9][0-15][0-3][0-99][0-99][0-15][0-6][0-6][0-6][0-15][0-15]
  14
[0-15]

Para la etiqueta -PRE se deben de especificar caracteres de letras para evitar 
confusiones con la definici�n del n�mero infijo (-INF). 
Para el caso del posfijo (-POS) aplica la misma condici�n. Por �ltimo, si se 
desea imprimir un reporte de lo realizado en este operador se coloca en la 
etiqueta -REPORTPOP el nombre del archivo de texto que almacena lo realizado 
en este operador, en este caso el archivo RepPopulation_T3.txt se almacenar� 
en el directorio C:\GENGEN\test1\WORK .
____________________________________________________________________________


Ejemplo 4:

-FWORK @WORK_PATH@\WORK
-FNEXTG NEXTGEN_1_
-FPOP POPULATION
-NI 100 
-NGEN 15
-VMININT 0 
-VMAXINT 15 
-TYPE INT 
-PRE IND 
-INF 11 
-POS GEN 
-REPEAT false
-REPORTPOP RepPopulation_T4.txt


El componente genera la carpeta WORK a partir de la especificaci�n de la 
etiqueta @WORK_PATH@, donde se determinar� por autom�tico el directorio donde
se est� ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ ser� sustituida
por C:\GENGEN\test1. A partir de ese directorio se generar� el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generar� 
el directorio POPULATION donde se generar�n 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzar�n desde IND11GEN.bin
hasta IND110GEN.bin. Cada individuo tendr� 15 genes (especificado en -NGEN) 
con una codificaci�n desde 0 hasta 14 considerando que estos valores NO pueden 
repetir (-REPEAT false) para cada gen de los individuos. Para la etiqueta -PRE 
se deben de especificar caracteres de letras para evitar confusiones con la 
definici�n del n�mero infijo (-INF). Para el caso del posfijo (-POS) aplica la 
misma condici�n. 
Por �ltimo, si se desea imprimir un reporte de lo realizado en este operador 
se coloca en la etiqueta -REPORTPOP el nombre del archivo de texto que 
almacena lo realizado en este operador, en este caso el archivo 
RepPopulation_T4.txt se almacenar� en el directorio C:\GENGEN\test1\WORK .
____________________________________________________________________________


Ejemplo 5:

-FWORK @WORK_PATH@\WORK
-FNEXTG NEXTGEN_1_
-FPOP POPULATION
-NI 100 
-NGEN 140
-VMININT 10 
-VMAXINT 150 
-TYPE INT 
-PRE IND 
-INF 11 
-POS GEN 
-REPEAT false
-REPORTPOP RepPopulation_T5.txt


El componente genera la carpeta WORK a partir de la especificaci�n de la 
etiqueta @WORK_PATH@, donde se determinar� por autom�tico el directorio donde
se est� ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ ser� sustituida
por C:\GENGEN\test1. A partir de ese directorio se generar� el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generar� 
el directorio POPULATION donde se generar�n 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzar�n desde IND11GEN.bin
hasta IND110GEN.bin. En caso de que el directorio de los individuos contenga 
un n�mero de individuos, entonces este m�dulo se encargar� de rellenar con
indiviuos aleatorios la poblaci�n de individuos (caso cataclismo), en la 
carpeta Test_5 se incluye este ejemplo con 13 individuos.
Cada individuo tendr� 140 genes (especificado en -NGEN) con una codificaci�n 
desde 10 hasta 149 considerando que estos valores NO pueden repetir (-REPEAT 
false) para cada gen de los individuos. Para la etiqueta -PRE se deben de 
especificar caracteres de letras para evitar confusiones con la definici�n del 
n�mero infijo (-INF). Para el caso del posfijo (-POS) aplica la misma 
condici�n. 
Por �ltimo, si se desea imprimir un reporte de lo realizado en este operador 
se coloca en la etiqueta -REPORTPOP el nombre del archivo de texto que 
almacena lo realizado en este operador, en este caso el archivo 
RepPopulation_T5.txt se almacenar� en el directorio C:\GENGEN\test1\WORK .
____________________________________________________________________________


Ejemplo 6:

-FWORK @WORK_PATH@/Test_6/WORK
-FNEXTG NEXTGEN_1_
-FPOP POPULATION
-NI 100 
-NGEN 12
-TYPE INT 
-PRE IND 
-INF 1 
-POS GEN 
-REPEAT true
-REPORTPOP RepPopulation_T6.txt
-BASE INCREMENTS:3;59;8



El componente genera la carpeta WORK a partir de la especificaci�n de la 
etiqueta @WORK_PATH@, donde se determinar� por autom�tico el directorio donde
se est� ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ ser� sustituida
por C:\GENGEN\test1. A partir de ese directorio se generar� el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generar� 
el directorio POPULATION donde se generar�n 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzar�n desde IND1GEN.bin
hasta IND100GEN.bin. En caso de que el directorio de los individuos contenga 
un n�mero de individuos, entonces este m�dulo se encargar� de rellenar con
indiviuos aleatorios la poblaci�n de individuos (caso cataclismo), en la 
carpeta Test_6 se incluye este ejemplo con 13 individuos.

Cada individuo tendr� 18 genes (especificado en -NGEN).
El individuo generado ser� de acuerdo a la generaci�n de 
diferentes bases especificadas en en la etiqueta -BASE, para esta etiqueta
se tiene la siguiente definici�n:

-BASE INCREMENTS:3;59;8

Para la anterior expresi�n se aplicar�n las bases 3,59 y 8 en orden incremental.
Esta caracter�stica puede utilizarse para generar coordenadas, en este ejemplo
se muestra la generaci�n de un X,Y y un �ngulo.

Los valores de los que genes que representan a X tienen una codificaci�n base3
Los valores de los que genes que representan a Y tienen una codificaci�n base59
Los valores de los que genes que representan a �ngulo tienen una codificaci�n base8
 
  0    1     2    3    4     5    6    7     8    9   10    11      
[0-3][0-59][0-8][0-3][0-59][0-8][0-3][0-59][0-8][0-3][0-59][0-8]


Por �ltimo, si se desea imprimir un reporte de lo realizado en este operador 
se coloca en la etiqueta -REPORTPOP el nombre del archivo de texto que 
almacena lo realizado en este operador, en este caso el archivo 
RepPopulation_T6.txt se almacenar� en el directorio C:\GENGEN\test1\WORK .