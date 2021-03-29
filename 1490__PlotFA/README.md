1490__PlotFA

Este componente genera una imagen con los valores de la función de aptitud obtenidos de cada generación de un algoritmo genético. 

Sintaxis:
 " -FWORK:str [-FSTATE:str] [-TOPGEN:int] [-HEAD:str] [-FIMGFA:str] [-PBESTFA:bool] [-NOTAS:str] [-VIEWIFAZ:bool] [-NOTES:str] [-LABELS:str] [-SAVEBOX:boolean -PARAMBOX:str] [-PLOTMAX:bool [-PMAXTEXT:NO:GEN:VAL:GENVAL]] [-PLOTPROM:bool [-PPROMTEXT:NO:GEN:VAL:GENVAL] ] [-PLOTMIN:bool [-PMINTEXT:NO:GEN:VAL:GENVAL]] "  
  

-FWORK         Se indica el directorio de trabajo del algoritmo genetico
-FSTATE        Nombrel del documento State en el cual se va guardando la información de cada generación 
-TOPGEN        Toma los valores máximos del gen, el valor por defecto es 0
-HEAD          Nombre que se le asigna al frame desplegado   
-FIMGFA        Nombre y ruta de la imagen a guardar
-NOTAS         Notas del programador sobre los experimentos
-VIEWIFAZ      Este parámetro nos indica si se va a visualizar la interfaz gráfica, por defecto esta
               desactivada, guardando automaticamente la imagen
-LABELS        Esta etiqueta indica los parámetros que se van a visualizar en el algoritmo genetico, 
               los cuales pueden ser los siguentes: -NELITE,-KTORNEO,-PMUT,-XPROB,-NI, etc.
-SAVEBOX       Es la ruta del componente encargado de gestionar las imagenes en DrobBox (UploadFilesDpx)
-PARAMBOX      Archivo con los parámetros correspondientes al componente UploadFilesDpx
-PLOTMAX       Etiqueta para visualizar el valor máximo de la FA de un individuo a ser graficados por cada generación
-PMAXTEXT :NO:GEN:VAL:GENVAL  indica si se imprimen las etiquetas de los valores
               NO   no imprime texto
               GEN  coloca etiqueta generación
               VAL  coloca etiqueta del valor
               GENVAL coloca etiqueta de generación y valor
-PLOTPROM      Etiqueta para visualizar el valor promedio de la FA de un individuo a ser graficados por cada generación
-PPROMTEXT :NO:GEN:VAL:GENVAL
-PLOTMIN       Etiqueta para visualizar el valo minímo de la FA de un individuo a ser graficados por cada generación
-PMINTEXT  :NO:GEN:VAL:GENVAL
-PBESTFA       indica si se requiere plotear el valor óptimo de la función de aptitud
-OPTIMOD       indica el modo de optimización



__________________________ >--ö--< ____________________________
Versión Base 1.0: Dr. René Arnulfo García Hernández
renearnulfo@hotmail.com

Versión adaptada: 
Ing. Jonathan Rojas Simón 
   ids_jonathan_rojas@hotmail.com
Mtra. Gabriela Villada Ramírez
   inggaby.vr@gmail.com

---------------------------------------------------------------