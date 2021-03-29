/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;

import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


/**
 * XDominante crosses two parents to have 1 children, but takes more genes of the
 * dominant parent with respect to the FA value
 * @author RENE
 */
public class GenGen {
 /**-CONFIG Nombre del archivo con los demás parámetros dados abajo*/
 String   ConfigFile;//Forzoso
 /**-FWORK SubRuta donde se van a crear las generaciones */
 String   FWORK;    //Forzoso
 /**-OPCRUZA Nombre del programa que realiza la operación de cruza*/
 String   OP1SEL;   //Forzoso
 /**-OPCRUZA Nombre del programa que realiza la operación de cruza*/
 String   OPCRUZA;  //Forzoso
 /**-OPCRUZA Nombre del programa que realiza la operación de cruza*/
 String   OPMUT;    //Forzoso
 /**-OPFA Nombre del programa que realiza la FA*/
 String   OPFA;     //Forzoso
/**-OPFA Nombre del programa que realiza la FA*/
 String   OPPOP;    //Forzoso
 /**-OPFA Nombre del programa que verifica si hay que para el AG*/
 String   OPSTOP;    //Forzoso
 /**-NDIED Número de individuos que van a morir en esta generación,
 * por lo tanto se van a seleccionar NDIED*2 padres */
 int      NDIED;    //Autogenerado
 /**-NI Número de individuos en la poblacion */
 int      NI;       //Autogenerado
 /**-FACTG SubRuta (prefijo) de la generación actual, sin incluir paréntesis*/
 String   FACTG;    //Autogenerado
 /**-FNEXTG  Ruta donde está la siguiente generación*/
 String   FNEXTG;   //Autogenerado
 /**-FPOP  Ruta donde está la población, ya sea de la generacion actual o de la siguiente generacion*/
 String   FPOP;     //Autogenerado
 /**-FFA  Ruta donde está la función de aptitud ya se ade la generacion actual o de la siguiente generacion*/
 String   FFA;      //Autogenerado
 /**-FSELECT  Nombre del archivo (incluyendno extensión) donde se van a guardar la selección*/
 String   FSELECT;  //Autogenerado
 /**-OP2SEL  Nombre del archivo (incluyendno extensión) donde se van a guardar la selección elite*/
 String   OP2SEL;   //Opcional
 int      RIMG;
 /**-NELITE cantidad de mejores individuos que sobreviven a la siguiente generación*/
 int      NELITE;   //Opcional-Autogenerado
 /**-FELITE  Nombre del archivo (incluyendno extensión) donde se va a guardar la selección elite*/
 String   FELITE;   //Opcional-Autogenerado
 /**-TIME cantidad de minutos asignados para procesamiento*/
 int      TIME;   //Opcional

 /**-FSELECT  Nombre del archivo (incluyendno extención) donde se van a guardar la selección*/
 String   FINIPOP;  //Opcional
 /**-FSELECT  Nombre del archivo (incluyendno extención) donde se van a guardar la selección*/
 String   FINIFA;   //Opcional
 /**-HISTORY indica que se desean guardar las generaciones creadas*/
 boolean  HISTORY; //Opcional
 /**-CONSOLE indica si desea mostrar la consola*/
 boolean  SHOW; //Opcional


//-CONFIG -KWORK -OP1SEL -OPCRUZA -OPMUT -OPFA ( -FINIPOP [-FINIFA] | -OPPOP )

 MyListArgs Param;
 IOBinFile BinFile;
 ManejoArchivos IO;
 int            NGEN;       //número de generación
 StringBuffer   Report;
 CONSOLE        Exe;
 String         NEXTGEN ;
 String         NEXTGENPOP;
 String         NEXTGENFA;
 String         ACTGEN ;
 String         ACTGENPOP;
 String         ACTGENFA;


//-CONFIG
/**
 * Constructor de la función donde se pasan y analizan los parámetros por línea de comandos
 * @param Args Arreglo donde cada celda corresponde a un comando o a su valor introducido
 */
GenGen(String [] Args){
 
 IO       = new ManejoArchivos();
 BinFile  = new IOBinFile();
 //*****lectura de argumentos*******
 Param    = new MyListArgs(Args);
 
  ConfigFile=  Param.ValueArgsAsString  ( "-CONFIG"  ,  ""  );//Ruta donde se van a crear los individuos
 if (! ConfigFile.equals("") )
     Param.AddArgsFromFile(ConfigFile);

 String     Sintaxis = " -FWORK:str -OP1SEL:str -OPCRUZA:str -OPMUT:str -OPFA:str -OPSTOP:str (-OPPOP:str | [-FINIFA:str] -FINIPOP:str ) "
                      + " [-CONSOLE:bool] [-HISTORY:bool] [-OP2SEL:str] [-NELITE:int] [-TIME:int]";
 MySintaxis Review   = new MySintaxis( Sintaxis , Param);
 
//Forzosos
 FWORK    =  Param.ValueArgsAsString  ( "-FWORK"  , ""          );
 OP1SEL   =  Param.ValueArgsAsString  ( "-OP1SEL" , ""          );
 OPCRUZA  =  Param.ValueArgsAsString  ( "-OPCRUZA", ""          );
 OPMUT    =  Param.ValueArgsAsString  ( "-OPMUT"  , ""          );
 OPFA     =  Param.ValueArgsAsString  ( "-OPFA"   , ""          );
 OPSTOP   =  Param.ValueArgsAsString  ( "-OPSTOP" , ""          );
//Opcional
 SHOW     =  Param.ValueArgsAsBoolean ( "-CONSOLE", false       );
 OPPOP    =  Param.ValueArgsAsString  ( "-OPPOP"  , ""          );
 FINIPOP  =  Param.ValueArgsAsString  ( "-FINIPOP", ""          );
 FINIFA   =  Param.ValueArgsAsString  ( "-FINIFA" , ""          );
 HISTORY  =  Param.ValueArgsAsBoolean ( "-HISTORY", false       );
 OP2SEL   =  Param.ValueArgsAsString  ( "-OP2SEL" , ""          );   //Opcional
 NELITE   =  Param.ValueArgsAsInteger ( "-NELITE" , 0           );   //Opcional-Autogenerado 10% de la poblacion si no existe
 TIME     =  Param.ValueArgsAsInteger ( "-TIME"   , 0           );   
 RIMG     =  Param.ValueArgsAsInteger ( "-RIMG"   , -1          );   

 
 Exe      = new CONSOLE(SHOW); // se pide que muestre la consola

 if (Param.Exists("-OP2SEL")) //si se pide elite
    FELITE   =  Param.AddIFNOTArgValue   ( "-FELITE" , "ELITE.TXT" );   //Opcional-Autogenerado

//Autogenerados
 
 NDIED    =  Param.ValueArgsAsInteger ( "-NDIED"  , -1          ); //Si no está va a autocalcular como el 90% del numero de individuos si hay elite o 100% si no hay elite
 NI       =  Param.ValueArgsAsInteger ( "-NI"     , -1          ); //Si no está van a generar 100 individuos
 FACTG    =  Param.AddIFNOTArgValue  ( "-FACTG"  , "ACTGEN"     );
 FNEXTG   =  Param.AddIFNOTArgValue  ( "-FNEXTG" , "NEXTGEN"    );
 FPOP     =  Param.AddIFNOTArgValue  ( "-FPOP"   , "POPULATION" );
 FFA      =  Param.AddIFNOTArgValue  ( "-FFA"    , "FA"         );
 FSELECT  =  Param.AddIFNOTArgValue  ( "-FSELECT", "PARENTS.txt");


 if (Param.Exists("-TIME")){ //transformar a DATE
    Calendar Fecha = Calendar.getInstance();

    Fecha.set(Fecha.get(Calendar.YEAR),         Fecha.get(Calendar.MONTH),
              Fecha.get(Calendar.DAY_OF_MONTH), Fecha.get(Calendar.HOUR_OF_DAY),
             (Fecha.get(Calendar.MINUTE)+TIME));
    String DATE = Fecha.get(Calendar.YEAR)        +"/"+(Fecha.get(Calendar.MONTH)+1)+"/"+
                  Fecha.get(Calendar.DAY_OF_MONTH)+"/"+ Fecha.get(Calendar.HOUR_OF_DAY)+"/"+
                  Fecha.get(Calendar.MINUTE);
    FSELECT  =  Param.AddIFNOTArgValue  ( "-DATE", DATE);
 }


//Generar el nuevo CONFIG con los parámetros autogenerados
IO.CrearCarpetas(FWORK);

//Siempre va a empezar con el número de generación en 1
NGEN = 1;

//Crear la estructura inicial de las carpetas

IO.UntilDelCarpeta(FWORK);

NEXTGEN = IO.AddToPath(FWORK, FNEXTG + "_"+ NGEN +"_");
IO.UntilCrearCarpetas(NEXTGEN);
NEXTGENPOP = IO.AddToPath(NEXTGEN,FPOP);
IO.UntilCrearCarpetas(NEXTGENPOP);
NEXTGENFA  = IO.AddToPath(NEXTGEN,FFA);
IO.UntilCrearCarpetas(NEXTGENFA);


//Si NI no fue especificado entonces se van a generar 100 individuos
if (!Param.Exists("-NI") ){
    if (Param.Exists("-OPPOP") )
        NI=Integer.valueOf( Param.AddIFNOTArgValue("-NI", "100"));
    else
        NI=IO.List_Files(FINIPOP, ".bin").length;
  }//del if

//Si se pide elite pero no se especificó NELITE se calcula al 10% de la población
int PorPob = NI;
if (Param.Exists("-OP2SEL") && !Param.Exists("-NELITE")){
   if (!Param.Exists("-NDIED"))
      NELITE = Integer.valueOf( Param.AddIFNOTArgValue("-NELITE", String.valueOf((10*NI)/100) ));
   else
      NELITE = Integer.valueOf( Param.AddIFNOTArgValue("-NELITE", String.valueOf(NI-NDIED)));

  PorPob = NI-NELITE; //el resto se deja para NDIED
 }

if (Param.Exists("-OP2SEL") && Param.Exists("-NELITE") && !Param.Exists("-NDIED"))
  PorPob = NI-NELITE; //el resto se deja para NDIED

//Si NDIED no fue especificado entonces se va a autocalcular al 90% de la población
if (!Param.Exists("-NDIED") ){
    NDIED=Integer.valueOf( Param.AddIFNOTArgValue("-NDIED", String.valueOf(PorPob) ));
  }//del if


ConfigFile = IO.AddToPath(FWORK, "AutoConfig.txt");
Param.SaveToFile(ConfigFile);


if (Param.Exists("-OPPOP")){//entonces se va a crear la población inicial
    Exe.Execute(OPPOP, "-CONFIG "+ConfigFile );
   }else{ //se va a copiar de alguna carpeta

    IO.CopyFiles(IO.List_Files_Abs(FINIPOP, ".bin"), NEXTGENPOP);//copiar la poblacion
    if (!FINIFA.equals(""))
      IO.CopyFiles(IO.List_Files_Abs(FINIFA, ".bin"), NEXTGENFA);//copiar la FA

  }//del else

//cargar nuevamente los parámetros que pudiera haber generado el archivo Autoconfig.txt
 Param.AddArgsFromFile(ConfigFile);

 //Forzosos
 FWORK    =  Param.ValueArgsAsString  ( "-FWORK"  , FWORK       );
 OP1SEL   =  Param.ValueArgsAsString  ( "-OP1SEL" , OP1SEL      );
 OPCRUZA  =  Param.ValueArgsAsString  ( "-OPCRUZA", OPCRUZA     );
 OPMUT    =  Param.ValueArgsAsString  ( "-OPMUT"  , OPMUT       );
 OPFA     =  Param.ValueArgsAsString  ( "-OPFA"   , OPFA        );
 OPSTOP   =  Param.ValueArgsAsString  ( "-OPSTOP" , OPSTOP      );
//Opcional
 SHOW     =  Param.ValueArgsAsBoolean ( "-CONSOLE", SHOW        );
 OPPOP    =  Param.ValueArgsAsString  ( "-OPPOP"  , OPPOP       );
 FINIPOP  =  Param.ValueArgsAsString  ( "-FINIPOP", FINIPOP     );
 FINIFA   =  Param.ValueArgsAsString  ( "-FINIFA" , FINIFA      );
 HISTORY  =  Param.ValueArgsAsBoolean ( "-HISTORY", HISTORY     );
 OP2SEL   =  Param.ValueArgsAsString  ( "-OP2SEL" , OP2SEL      );   //Opcional
 NELITE   =  Param.ValueArgsAsInteger ( "-NELITE" , NELITE      );   //Opcional-Autogenerado 10% de la poblacion si no existe
 TIME     =  Param.ValueArgsAsInteger ( "-TIME"   , TIME        );   
 RIMG     =  Param.ValueArgsAsInteger ( "-RIMG"   , RIMG        );   

 Exe      = new CONSOLE(SHOW); // se pide que muestre la consola

 if (Param.Exists("-OP2SEL")) //si se pide elite
    FELITE  =  Param.AddIFNOTArgValue ( "-FELITE" , FELITE      );   //Opcional-Autogenerado

//Autogenerados
 
 NDIED    =  Param.ValueArgsAsInteger ( "-NDIED"  , NDIED       ); //Si no está va a autocalcular como el 90% del numero de individuos si hay elite o 100% si no hay elite
 NI       =  Param.ValueArgsAsInteger ( "-NI"     , NI          ); //Si no está van a generar 100 individuos
 FACTG    =  Param.AddIFNOTArgValue  ( "-FACTG"   , FACTG       );
 FNEXTG   =  Param.AddIFNOTArgValue  ( "-FNEXTG"  , FNEXTG      );
 FPOP     =  Param.AddIFNOTArgValue  ( "-FPOP"    , FPOP        );
 FFA      =  Param.AddIFNOTArgValue  ( "-FFA"     , FFA         );
 FSELECT  =  Param.AddIFNOTArgValue  ( "-FSELECT" , FSELECT     );

 ///***



if (HISTORY)//si se va a guardar el historial de generaciones
  IO.UntilCrearCarpetas(IO.AddToPath(FWORK, "HISTORY"));


 } //fin de la función

/**
 * Genera la cruza de los padres para obtener NDIED hijos
 */
void Run (){
String    Ruta;
String    []ToCopy=null;
boolean   STOP  = false;
int       iRIMG = RIMG ;
  
  while(true){
      //if(Exe.show)
        System.out.println("Generation:"+NGEN);
  //evaluar la población
     Exe.Execute(OPFA,"-CONFIG "+ConfigFile );

  //generar la estructura de carpetas
     ACTGEN=ACTGENPOP=ACTGENFA="";
     ACTGEN    = IO.AddToPath(FWORK, FACTG+ "_" +(NGEN-1)+ "_");
     ACTGENPOP = IO.AddToPath(ACTGEN,FPOP);
     ACTGENFA  = IO.AddToPath(ACTGEN,FFA);

     NEXTGEN    = IO.AddToPath(FWORK, FNEXTG + "_"+ (NGEN) +"_");
     NEXTGENPOP = IO.AddToPath(NEXTGEN,FPOP);
     NEXTGENFA  = IO.AddToPath(NEXTGEN,FFA);

if(STOP) break;
     
     if(NGEN>1){// si no es la primera generación
         if (HISTORY){//si se va a guardar el historial de generaciones
             Ruta = IO.AddToPath(FWORK, "HISTORY\\Generation_"+ (NGEN-1) +"_");
             IO.RenamePath(ACTGEN,Ruta );
            }
         else{  //no se va a guardar el historial de generaciones, eliminar la carpeta
             IO.UntilDelCarpeta(ACTGEN);
            }
        }//del if
   //Renombrar la carpeta de la generación-siguiente como la carpeta de la generación-actual
     ACTGEN    = IO.AddToPath(FWORK, FACTG+"_"+ (NGEN) +"_");
     ACTGENPOP = IO.AddToPath(ACTGEN,FPOP);
     ACTGENFA  = IO.AddToPath(ACTGEN,FFA);

     boolean state;
     do {
         state = IO.RenamePath(NEXTGEN, ACTGEN);
     }while (!state);

   //Crear la estructura de las carpetas de la siguiente generación
     NEXTGEN = IO.AddToPath(FWORK, FNEXTG + "_"+ (NGEN+1) +"_");
     IO.UntilCrearCarpetas(NEXTGEN);
     NEXTGENPOP = IO.AddToPath(NEXTGEN,FPOP);
     IO.UntilCrearCarpetas(NEXTGENPOP);
     NEXTGENFA  = IO.AddToPath(NEXTGEN,FFA);
     IO.UntilCrearCarpetas(NEXTGENFA);

   //Verificar si se cumplen las condiciones para para el algoritmo genético
     Exe.Execute(OPSTOP,"-CONFIG "+ConfigFile );
     if (IO.ExisteCarpetaArchivo(IO.AddToPath(FWORK, "STOP.txt")))
         STOP = true;


   //Generar la selección de padres
     Exe.Execute(OP1SEL,"-CONFIG "+ConfigFile );

   //Hacer la cruza de los padres
     Exe.Execute(OPCRUZA,"-CONFIG "+ConfigFile );

   //Hacer mutacion
     Exe.Execute(OPMUT,"-CONFIG "+ConfigFile );
     
   //Hacer la selección elite si se especificó
     if (OP2SEL.length()>0){
        Exe.Execute(OP2SEL,"-CONFIG "+ConfigFile );
        //leer la lista de archivos
         try {
             ToCopy = IO.Read_Text_File(IO.AddToPath(ACTGEN, FELITE));

            //copiar población con población
            for (int i = 0; i < ToCopy.length; i++)
                 IO.CopyFile(IO.AddToPath(ACTGENPOP,  ToCopy[i]), NEXTGENPOP);
            //copiar FA con FA
            for (int i = 0; i < ToCopy.length; i++)
                 IO.CopyFile(IO.AddToPath(ACTGENFA, ToCopy[i]),  NEXTGENFA);
         }catch (Exception e){}
       }//del if
    
     
    //Inyectar material genético, se tiene que cumplir que se haya especificado 
    // la Reinyección de Materia Genético RIMG, que se llegue a cuenta, que se 
    //tenga el programa de generación de población inicial
    if (OP2SEL.length()>0 && Param.Exists("-RIMG")&& (iRIMG==0) && Param.Exists("-OPPOP")){
        iRIMG = RIMG;
        IO.DelFilesExcept(NEXTGENPOP, ToCopy); //borrar todos los individuos, menos los mejores
        IO.DelFilesExcept(NEXTGENFA, ToCopy); //borrar todas las evaluaciones, menos las mejores
        Exe.Execute(OPPOP, "-CONFIG "+ ConfigFile ); //reinyectar la población
        System.out.println("Rinyección de material genético");
        }//del if
     
      
  iRIMG--;
  NGEN++;
  }//del while





}//de la función run

}//de la clase
