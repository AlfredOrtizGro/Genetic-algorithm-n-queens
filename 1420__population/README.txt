____________________________________________________________________________

                               >--ö--< 
                          1420__population                                      
        Generador de Población inicial para el algoritmo genético

____________________________________________________________________________

Ing. Jonathan Rojas Simón (IDS_Jonathan_Rojas@hotmail.com) 
Dr. René Arnulfo García Hernández (renearnulfo@hotmail.com)
____________________________________________________________________________

Este módulo presenta las siguientes opciones para generar la población inicial 
en un algoritmo genético:

   1.- Combinaciones de una sola base
   2.- Combinaciones con genes de diferentes bases (Codificación base N)
   3.- Permutaciones desde números iniciales (0-N)
   4.- Permutaciones a partir de rangos establecidos (MIN-MAX)

Cada individuo es configurado en un archivo binario (.bin), el cual guarda en 
un vector de números reales (float de 4 bytes formato IEEE754) los genes del 
individuo. Cabe recordar que en un número real cabe un entero, exadecimal, 
binario, caracter ascii, etc.

Para conocer más detalles sobre el uso y la generación de variantes se 
recomienda ver el siguiente video:

https://youtu.be/wpafrBUY9gA

____________________________________________________________________________

                                                                            
SINTAXIS:

____________________________________________________________________________


-CONFIG || (-FWORK:str -FNEXTG:str -FPOP:str [-TYPE (INT|REAL)] [-PRE:str] 
[-INF:int] [-POS:str] [-REPEAT (TRUE|FALSE)]) -NI:int (((-NGEN:int ||(-NGENMIN:int -NGENMAX:int)) 
-VMININT:int -VMAXINT:int) || -BASE:str) [-PRECISION:int] [-REPORTPOP:str]

-CONFIG     Archivo donde vienen los demás parámetros descritos abajo  

-FWORK      Ruta de trabajo donde se va a crear la primera generación y las 
            demás generaciones              
-FNEXTG     SubRuta con la generación siguiente                                
-FPOP       SubRuta donde se van a crear los individuos                        
-TYPE       Tipo de dato a generar del individuo (int o real) 
-PRE        Cadena prefijo del nombre de los individuos a generar (solo letras)
-INF        Número secuencial que va después del prefijo y antes del postfijo  
-POST       Cadena postfijo del nombre de los individuos a generar (solo 
            letras)
-REPEAT     TRUE: Si el valor del gen se puede repetir en el individuo, FALSE 
            en caso de que los valores establecidos como rangos no se deben de 
             repetir.
-NI         Número de individuos                                               
-NGEN       Número de genes de un individuo                                    
-NGENMIN    Número mínimo de cromosomas en individuos de longitud variable,    
            (NumGen=0 o no aparecer)
-NGENMAX    Número máximo de cromosomas en individuos de longitud variable,    
            (NumGen=0 o no aparecer)
-VMININT    Limite entero del Valor mínimo del rango del valor de un gen       
-VMAXINT    Limite entero del Valor máximo del rango del valor de un gen       
-BASE       Ruta y nombre del archivo de texto donde se definen las bases a 
            trabajar por gen, las bases serán de acuerdo a la Siguiente 
            Expresión Regular:

                  (\d+)[{](\d+([;]\d+|[-]\d+)*)[}]
                    G1      G2       G3

            Donde G1 representa la base a tratar, G2 representa el gen en 
            específico a aplicar la base de G1 y G3 establece la separación
            de genes a aplicar la Base G1 o los rangos de genes respectivos.
-PRECISION  Número de digitos de precision en tipos de datos reales
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

El componente genera la carpeta WORK a partir de la especificación de la 
etiqueta @WORK_PATH@, donde se determinará por automático el directorio donde
se esté ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ será sustituida
por C:\GENGEN\test1. A partir de ese directorio se generará el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generará 
el directorio POPULATION donde se generarán 150 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzarán desde IND11GEN.bin
hasta IND160GEN.bin. Cada individuo tendrá 50 genes (especificado en -NGEN) 
con una codificación binaria desde 0 hasta 1 (codificación binaria) 
considerando que estos valores se pueden repetir (-REPEAT true) para cada gen 
de los individuos. Para la etiqueta -PRE se deben de especificar caracteres de 
letras para evitar confusiones con la definición del número infijo (-INF). 
Para el caso del posfijo (-POS) aplica la misma condición. Por último, si se 
desea imprimir un reporte de lo realizado en este operador se coloca en la 
etiqueta -REPORTPOP el nombre del archivo de texto que almacena lo realizado 
en este operador, en este caso el archivo RepPopulation_T1.txt se almacenará 
en el directorio C:\GENGEN\test1\WORK .
____________________________________________________________________________


Ejemplo 2:

-CONFIG CONFIG.txt

Hace lo mismo que el ejemplo 1 pero manda llamar al archivo ParamPop.txt 
donde estan los siguientes parámetros:

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

El componente genera la carpeta WORK a partir de la especificación de la 
etiqueta @WORK_PATH@, donde se determinará por automático el directorio donde
se esté ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ será sustituida
por C:\GENGEN\test1. A partir de ese directorio se generará el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generará 
el directorio POPULATION donde se generarán 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzarán desde IND11GEN.bin
hasta IND110GEN.bin. Cada individuo tendrá 50 genes (especificado en -NGEN) 
con una codificación binaria desde 0 hasta 15 (codificación hexadecimal) 
considerando que estos valores se pueden repetir (-REPEAT true) para cada gen 
de los individuos. Para la etiqueta -PRE se deben de especificar caracteres de 
letras para evitar confusiones con la definición del número infijo (-INF). 
Para el caso del posfijo (-POS) aplica la misma condición. Por último, si se 
desea imprimir un reporte de lo realizado en este operador se coloca en la 
etiqueta -REPORTPOP el nombre del archivo de texto que almacena lo realizado 
en este operador, en este caso el archivo RepPopulation_T2.txt se almacenará 
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

El componente genera la carpeta WORK a partir de la especificación de la 
etiqueta @WORK_PATH@, donde se determinará por automático el directorio donde
se esté ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ será sustituida
por C:\GENGEN\test1. A partir de ese directorio se generará el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generará 
el directorio POPULATION donde se generarán 200 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzarán desde IND11GEN.bin
hasta IND210GEN.bin. El individuo generado será de acuerdo a la generación de 
diferentes bases especificadas en el archivo Base.txt, por ejemplo dentro de
este archivo se encuentra la siguiente definición:

4{5}
100{0;6-7},10{1;3}
16{2;4;8;12-14}
7{11-9}

Donde el número que está afuera de las llaves se trata de la base a 
considerar, los valores de las bases comenzarán desde el 0 hasta N-1. Los 
números contenidos en las llaves son los genes (o rangos de genes donde se 
aplicará la base solicitada), en el caso de aplicar una base a genes 
específicos se delimitarán mediante el caracter ";" y en el caso de rangos de
genes será de acuerdo con el caracter "-" donde se especificará el valor mínimo
y máximo (o viceversa). Cada expresión de una base se debe de delimitar con el 
caracter ",".

Para la anterior expresión se aplicarán las base 4 [0-3] en el gen 5, la base
100 [0-99] se aplicará en los genes 0, 6 y 7, la base 10 [0-9] se aplicará en
los genes 1 y 3, la base 16 [0-15] se aplicará en los genes 2, 4, 8, 12, 13 y 
14, por último la base 7 se aplicará en los genes 9, 10, y 11. Por lo tanto 
la representación del individuo será de 15 genes a partir de la siguiente
representación:
 
  0     1    2     3    4     5    6     7     8     9   10    11   12    13
[0-99][0-9][0-15][0-9][0-15][0-3][0-99][0-99][0-15][0-6][0-6][0-6][0-15][0-15]
  14
[0-15]

Para la etiqueta -PRE se deben de especificar caracteres de letras para evitar 
confusiones con la definición del número infijo (-INF). 
Para el caso del posfijo (-POS) aplica la misma condición. Por último, si se 
desea imprimir un reporte de lo realizado en este operador se coloca en la 
etiqueta -REPORTPOP el nombre del archivo de texto que almacena lo realizado 
en este operador, en este caso el archivo RepPopulation_T3.txt se almacenará 
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


El componente genera la carpeta WORK a partir de la especificación de la 
etiqueta @WORK_PATH@, donde se determinará por automático el directorio donde
se esté ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ será sustituida
por C:\GENGEN\test1. A partir de ese directorio se generará el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generará 
el directorio POPULATION donde se generarán 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzarán desde IND11GEN.bin
hasta IND110GEN.bin. Cada individuo tendrá 15 genes (especificado en -NGEN) 
con una codificación desde 0 hasta 14 considerando que estos valores NO pueden 
repetir (-REPEAT false) para cada gen de los individuos. Para la etiqueta -PRE 
se deben de especificar caracteres de letras para evitar confusiones con la 
definición del número infijo (-INF). Para el caso del posfijo (-POS) aplica la 
misma condición. 
Por último, si se desea imprimir un reporte de lo realizado en este operador 
se coloca en la etiqueta -REPORTPOP el nombre del archivo de texto que 
almacena lo realizado en este operador, en este caso el archivo 
RepPopulation_T4.txt se almacenará en el directorio C:\GENGEN\test1\WORK .
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


El componente genera la carpeta WORK a partir de la especificación de la 
etiqueta @WORK_PATH@, donde se determinará por automático el directorio donde
se esté ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ será sustituida
por C:\GENGEN\test1. A partir de ese directorio se generará el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generará 
el directorio POPULATION donde se generarán 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzarán desde IND11GEN.bin
hasta IND110GEN.bin. En caso de que el directorio de los individuos contenga 
un número de individuos, entonces este módulo se encargará de rellenar con
indiviuos aleatorios la población de individuos (caso cataclismo), en la 
carpeta Test_5 se incluye este ejemplo con 13 individuos.
Cada individuo tendrá 140 genes (especificado en -NGEN) con una codificación 
desde 10 hasta 149 considerando que estos valores NO pueden repetir (-REPEAT 
false) para cada gen de los individuos. Para la etiqueta -PRE se deben de 
especificar caracteres de letras para evitar confusiones con la definición del 
número infijo (-INF). Para el caso del posfijo (-POS) aplica la misma 
condición. 
Por último, si se desea imprimir un reporte de lo realizado en este operador 
se coloca en la etiqueta -REPORTPOP el nombre del archivo de texto que 
almacena lo realizado en este operador, en este caso el archivo 
RepPopulation_T5.txt se almacenará en el directorio C:\GENGEN\test1\WORK .
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



El componente genera la carpeta WORK a partir de la especificación de la 
etiqueta @WORK_PATH@, donde se determinará por automático el directorio donde
se esté ejecutando este componente. Ejemplo. Si se ejecuta este componente en
el directorio C:\GENGEN\test1, entonces la etiqueta @WORK_PATH@ será sustituida
por C:\GENGEN\test1. A partir de ese directorio se generará el directorio WORK,
dentro de WORK se genera el directorio NEXTGEN_1_ y dentro de este se generará 
el directorio POPULATION donde se generarán 100 individuos (especificados por
la etiqueta -NI), los nombres de los individuos comenzarán desde IND1GEN.bin
hasta IND100GEN.bin. En caso de que el directorio de los individuos contenga 
un número de individuos, entonces este módulo se encargará de rellenar con
indiviuos aleatorios la población de individuos (caso cataclismo), en la 
carpeta Test_6 se incluye este ejemplo con 13 individuos.

Cada individuo tendrá 18 genes (especificado en -NGEN).
El individuo generado será de acuerdo a la generación de 
diferentes bases especificadas en en la etiqueta -BASE, para esta etiqueta
se tiene la siguiente definición:

-BASE INCREMENTS:3;59;8

Para la anterior expresión se aplicarán las bases 3,59 y 8 en orden incremental.
Esta característica puede utilizarse para generar coordenadas, en este ejemplo
se muestra la generación de un X,Y y un ángulo.

Los valores de los que genes que representan a X tienen una codificación base3
Los valores de los que genes que representan a Y tienen una codificación base59
Los valores de los que genes que representan a ángulo tienen una codificación base8
 
  0    1     2    3    4     5    6    7     8    9   10    11      
[0-3][0-59][0-8][0-3][0-59][0-8][0-3][0-59][0-8][0-3][0-59][0-8]


Por último, si se desea imprimir un reporte de lo realizado en este operador 
se coloca en la etiqueta -REPORTPOP el nombre del archivo de texto que 
almacena lo realizado en este operador, en este caso el archivo 
RepPopulation_T6.txt se almacenará en el directorio C:\GENGEN\test1\WORK .