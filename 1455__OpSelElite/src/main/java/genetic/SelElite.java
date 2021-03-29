/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;

/**
 *
 * @author RENE
 */

import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SelElite {
 /**-CONFIG Nombre del archivo con los demás parámetros dados abajo*/
 String   ConfigFile;
 /**-FWORK SubRuta donde se van a crear las generaciones */
 String   FWORK;
 /**-FACTG SubRuta (prefijo) de la generación actual, sin incluir paréntesis*/
 String   FACTG;
 /**-FACTGFA  Ruta donde está la funcion de aptitud*/
 String   FFA;
 /**-FSELECT  Nombre del archivo (incluyendno extención) donde se van a guardar la selección*/
 String   FELITE;
 /**-NDIED Número de individuos que van a morir en esta generación,
 * por lo tanto se van a seleccionar NDIED*2 padres */
 int      NELITE;

 File           IF;
 MyListArgs Param;
 IOBinFile BinFile;
 ManejoArchivos IO;
 StringBuffer   Report;

//-CONFIG | (-FWORK -FACTG -FFA -NELITE -FELITE)

    String OPTIMIZA_A;
SelElite(String [] Args){
 IO       = new ManejoArchivos();
 BinFile  = new IOBinFile();
 //*****lectura de argumentos*******
 Param    = new MyListArgs(Args);
 
  ConfigFile=  Param.ValueArgsAsString  ( "-CONFIG"  ,  ""  );//Ruta donde se van a crear los individuos
 if (! ConfigFile.equals("") )
     Param.AddArgsFromFile(ConfigFile);

 String   Sintaxis = " -FWORK:str -FACTG:str -FFA:str -NELITE:int -FELITE:str [-OPTIMOD:MAX:MIN]";
 MySintaxis Review = new MySintaxis( Sintaxis , Param);

 FWORK      =  Param.ValueArgsAsString  ( "-FWORK"     , ""   );
 FACTG      =  Param.ValueArgsAsString  ( "-FACTG"     , ""   );
 FFA        =  Param.ValueArgsAsString  ( "-FFA"       , ""   );
 NELITE     =  Param.ValueArgsAsInteger ( "-NELITE"    , 0    );
 FELITE     =  Param.ValueArgsAsString  ( "-FELITE"    , ""   );
 OPTIMIZA_A =  Param.ValueArgsAsString  ("-OPTIMOD" , "MAX");
 } //fin de la función

void Run(){
String [] Folders = IO.List_Carpetas(FWORK);
String    Path    = "";
String    Fathers = "";

 for (String string : Folders) {
    if (string.toUpperCase().startsWith(FACTG)){
        Fathers = IO.AddToPath(IO.AddToPath(FWORK, string),FELITE);
        Path    = IO.AddToPath(IO.AddToPath(FWORK, string),FFA);
        break;
        }
    }

String [] Inds = IO.List_Files_Abs( Path, ".bin"); //se extraen todos los archivos
Float  []  Pro = new Float [Inds.length];
PairStringFloat  []  Pros = new PairStringFloat [Inds.length];

if (!IO.Open_Write_File(Fathers)) { //abrir archivo de salida
    System.out.println("SelElite: error al escribir el archivo:" + Fathers);
    System.exit(0);}

for (int i = 0; i < Inds.length; i++) //obtener los valores de la FA
   Pros[i] = new PairStringFloat(BinFile.ReadBinFloatFileIEEE754(Inds[i])[0] , Inds[i]);
   

List < PairStringFloat > lista = Arrays.asList(Pros);
Collections.sort(lista, new ComparatorPairStrFloat());
if(OPTIMIZA_A.equals("MIN"))
    Collections.reverse(lista);
if (NELITE>Inds.length)
    NELITE=Inds.length;

for (int i = 0; i < NELITE; i++){
    File uno = new File(lista.get(i).StringValue);
        IO.Write_in_File(uno.getName());
    }

IO.Close_Write_File();
}//de la función run

}//de la clase
