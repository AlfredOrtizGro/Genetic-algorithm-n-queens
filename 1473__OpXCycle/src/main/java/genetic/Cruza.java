/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;


import java.util.ArrayList;
import java.util.Arrays;
import libs.*;

import java.util.Random;

/**
 * Operador que genera la cruza en N puntos para crear NDIED hijoss
 * @author RENE
 */
public class Cruza {
 /**-CONFIG Nombre del archivo con los demás parámetros dados abajo*/
 String   ConfigFile;
 /**-FWORK SubRuta donde se van a crear las generaciones */
 String   FWORK;
 /**-FACTG SubRuta (prefijo) de la generación actual, sin incluir paréntesis*/
 String   FACTG;
 /**-FNEXTG  Ruta donde está la siguiente generación*/
 String   FNEXTG;
 /**-FPOP  Ruta donde está la población, ya sea de la generacion actual o de la siguiente generacion*/
 String   FPOP;
 /**-FSELECT  Nombre del archivo (incluyendno extención) donde se van a guardar la selección*/
 String   FSELECT;
/**-XPROB Probabilidad de cruce para cada gen */
 int      XPROB;
 /**-NI Numero de individuos de la generacion**/
 int NumInd;
 /**-NELITE Numero de individuos elite de la generacion**/
 int NELITE;
/**N�mero de individuos generados a partir de -NI - -NELITE**/
 int NHijos;
 /**-REPORTCX Nombre del archivo de texto que almacena las operaciones de este operador**/
 String REPORTCX;

 MyListArgs Param;
 IOBinFile BinFile;
 ManejoArchivos IO;
 StringBuilder   Report = new StringBuilder("");
 String gSSalto = "\r\n";

//-CONFIG | (-FWORK:str -FACTG:str -FNEXTG:str -FPOP:str -FSELECT:str -NI:int -NELITE:int [-XPROB:int] -REPORTCX:str)
/**
 * Constructor de la función donde se pasan y analizan los parámetros por línea de comandos
 * @param Args Arreglo donde cada celda corresponde a un comando o a su valor introducido
 */
Cruza(String [] Args) {
    IO = new ManejoArchivos();
    BinFile = new IOBinFile();
    //*****lectura de argumentos*******
    Param = new MyListArgs(Args);

    ConfigFile = Param.ValueArgsAsString("-CONFIG", "");
    if (!ConfigFile.equals(""))
        Param.AddArgsFromFile(ConfigFile);

    String Sintaxis = "-FWORK:str -FACTG:str -FNEXTG:str -FPOP:str -FSELECT:str -NI:int [-NELITE:int] [-XPROB:int] [-REPORTCX:str]";
    MySintaxis Review = new MySintaxis(Sintaxis, Param);

    FWORK = Param.ValueArgsAsString("-FWORK", "");
    FACTG = Param.ValueArgsAsString("-FACTG", "");
    FNEXTG = Param.ValueArgsAsString("-FNEXTG", "");
    FPOP = Param.ValueArgsAsString("-FPOP", "");
    FSELECT = Param.ValueArgsAsString("-FSELECT", "");
    XPROB = Param.ValueArgsAsInteger("-XPROB", 70);
    NumInd = Param.ValueArgsAsInteger("-NI", 0);
    NELITE = Param.ValueArgsAsInteger("-NELITE", 0);
    REPORTCX = Param.ValueArgsAsString("-REPORTCX", "");
    
    NHijos = NumInd - NELITE;
}

    /**
     * Genera la cruza de los padres para obtener (NI - NELITE) hijos
     */
    void Run()
    {
        Report.append("Operador CX" + gSSalto);
        int lEGeneration = 0;
        String[] Folders = IO.List_Carpetas(FWORK);
        String FACTGEN = "";
        String FACTGPOP = "";
        String FNEXTGPOP = "";

        for (String string : Folders)
        {
            if (string.toUpperCase().startsWith(FACTG))
            {
                ManejoER objER = new ManejoER("([a-zA-Z_]+)(\\d+)([a-zA-Z_]+)");
                objER.ExistER(string);
                lEGeneration = Integer.parseInt(objER.Grupo(2).trim());
                Report.append("Generación -> " + lEGeneration + gSSalto);
                FACTGEN = IO.AddToPath(FWORK, string);
                FACTGPOP = IO.AddToPath(FACTGEN, FPOP);
                break;
            }
        }
        for (String string : Folders)
        {
            if (string.toUpperCase().startsWith(FNEXTG))
            {
                FNEXTGPOP = IO.AddToPath(IO.AddToPath(FWORK, string), FPOP);
                if (!REPORTCX.equals(""))
                {
                    ManejoER ERFile = new ManejoER("(.+)\\.(.+)");
                    ERFile.ExistER(REPORTCX);
                    REPORTCX = IO.AddToPath(IO.AddToPath(FWORK, string), ERFile.Grupo(1) + "_" + lEGeneration + "_." + ERFile.Grupo(2));
                }
                IO.UntilCrearCarpetas(FNEXTGPOP);
                break;
            }
        }
        
        ManejoER ER = new ManejoER("([a-zA-Z]*)(\\d+)([a-zA-Z]*)\\.bin");
        String[] Inds = IO.List_Files(FACTGPOP, ".bin");
        
        Report.append("-XPROB: " + XPROB + gSSalto);
        Report.append("Poblacion de padres: " + FACTGPOP + gSSalto);
        String Pre = "";
        int Inf = 0;
        String Post = "";
        int Max = 0;

        //Obtener la sintaxis de los nombres de archivos, prefijo, infijo y postfijo.
        //En el infijo viene el número secuencial, el cual se a utlizar para generar a los hijos
        for (int i = 0; i < Inds.length; i++)
        {
            if (ER.ExistER(Inds[i]))
            {
                Pre = ER.Grupo(1);
                Inf = Integer.valueOf(ER.Grupo(2));
                Post = ER.Grupo(3);
            }
            if (i == 0)
                Max = Inf;
            if (Inf > Max)
                Max = Inf;
        }//del for

        Report.append("Prefijo (-PRE) -> " + Pre + gSSalto);
        Report.append("Infijo (-INF) -> " + Max + gSSalto);
        Report.append("Posfijo (-POS) -> " + Post + gSSalto);

        //**********
        Random Aleos = new Random();
        String[] Parents = IO.Read_Text_File(IO.AddToPath(FACTGEN, FSELECT));

        Report.append("Indices de padres -> " + IO.AddToPath(FACTGEN, FSELECT) + gSSalto);

        for (int i = 0; i < Parents.length; i++)  //se le agrega a cada archivo la ruta completa
            Parents[i] = IO.AddToPath(FACTGPOP, Parents[i]);

        Report.append("Validacion de padres" + gSSalto);

        float[] PA = (Parents.length > 0) ? BinFile.ReadBinFloatFileIEEE754(Parents[0]) : null; //Padre A
        float[] PB = null; //Padre B

        float lFSuma = 0.0F;
        
        for (int i = 0; i < PA.length; i++)
            lFSuma += PA[i];
        

        Report.append("Suma de valores -> " + lFSuma + gSSalto);
        
        ArrayList<float[]> objArrayListParents = new ArrayList<>();
        for (int i = 0; i < Parents.length; i++)
        {
            float lFSumC = 0.0F;
            float[] lAFTemp = BinFile.ReadBinFloatFileIEEE754(Parents[i]);
            for (int j = 0; j < lAFTemp.length; j++)
                lFSumC += lAFTemp[j];
            if (lFSumC != lFSuma)
            {
                Report.append("El individuo no es una permutacion" + gSSalto);
                Report.append(Arrays.toString(lAFTemp) + gSSalto);
                Report.append("Las permutaciones recibidas tienen valores repetidos" + gSSalto);
                if (!REPORTCX.equals(""))
                {
                    Print objPrint = new Print(this);
                    objPrint.OutPutHead();
                }
                System.exit(-1);
            } else
            {
                objArrayListParents.add(lAFTemp);
            }
        }
        Report.append("Todos los padres fueron validados" + gSSalto);
        Report.append("------------------------------------------------------------" + gSSalto);
                
        for (int i = 0, p = 0; i < NHijos;)
        {  //crear NHijos hijos
            if (p >= Parents.length - 1)
                p = 0;
            
            int P1 = p;
            int P2 = p++;

            PA = new float[PA.length];
            PB = new float[PA.length];

            System.arraycopy(objArrayListParents.get(P1), 0, PA, 0, PA.length);
            System.arraycopy(objArrayListParents.get(P2), 0, PB, 0, PA.length);
            
            Report.append("PA -> " + Parents[P1] + gSSalto);
            Report.append("PB -> " + Parents[P2] + gSSalto);

            if ((Aleos.nextFloat() * 100) <= XPROB)
            {
                float[] Temp = new float[PA.length];
                System.arraycopy(PA, 0, Temp, 0, PA.length);
                
                Report.append("PA -> PB ");
                PA = obtenerHijoCruzaCiclosDin(PA, PB);
                Report.append("PB -> PA ");
                PB = obtenerHijoCruzaCiclosDin(PB, Temp);
            } else
            {
                Report.append("No se cruzaron, pasan a la sig generacion " + gSSalto);
            }

            String lSDesc_1 = Pre + (++Max) + Post + ".bin";
            Report.append("DA -> " + lSDesc_1 + gSSalto);
            BinFile.WriteBinFloatFileIEEE754(IO.AddToPath(FNEXTGPOP, lSDesc_1), PA);
            i++;
            if (i < NHijos)
            {
                String lSDesc_2 = Pre + (++Max) + Post + ".bin";
                Report.append("DB -> " + lSDesc_2 + gSSalto);
                BinFile.WriteBinFloatFileIEEE754(IO.AddToPath(FNEXTGPOP, lSDesc_2), PB);
                i++;
            }
            Report.append("------------------------------------------------------------" + gSSalto);
        }//del for

        if (!REPORTCX.equals(""))
        {
            Print objPrint = new Print(this);
            objPrint.OutPutHead();
        }
    }//de la función run

    /**
     * obtenerHijoCruzaCiclos Método que realiza la cruza por ciclos para
     * determinar al descendiente (hijo)
     * @param lAFParent_1 Es la lista que maneja la codificación genética del padre 1
     * @param lAFParent_2 Es la lista que maneja la codificación genética del padre 2
     * @return AL -> Integer
     */
    public float[] obtenerHijoCruzaCiclosDin(float[] lAFParent_1, float[] lAFParent_2)
    {
        Random objRandom = new Random();
        ArrayList<Integer> objArrayListNumerosCiclo = new ArrayList<>();

        float[] lAEHijoResultante = new float[lAFParent_1.length];
        for (int i = 0; i < lAEHijoResultante.length; i++)
            lAEHijoResultante[i] = -1.0F;
        
        int lENumR = objRandom.nextInt(lAEHijoResultante.length);
        
        Report.append("Punto de inicio del ciclo -> " + lENumR + gSSalto);
//        System.out.println("1sr Pt: " + lENumR);
        
        int lEN = lENumR;
        do
        {
            objArrayListNumerosCiclo.add(lEN);
            for (int i = 0; i < lAFParent_1.length; i++)
            {
                if (lAFParent_1[i] == lAFParent_2[lEN])
                {
                    lEN = i;
                    break;
                }
            }
            if (lAFParent_2[lEN] == lAFParent_1[lENumR])
            {
                objArrayListNumerosCiclo.add(lEN);
            }
        } while (lAFParent_2[lEN] != lAFParent_1[lENumR]);

        for (int i = 0; i < objArrayListNumerosCiclo.size(); i++)
        {
            lAEHijoResultante[objArrayListNumerosCiclo.get(i)] = lAFParent_1[objArrayListNumerosCiclo.get(i)];
        }

        for (int i = 0; i < lAEHijoResultante.length; i++)
        {
            if (lAEHijoResultante[i] == -1.0F)
            {
                lAEHijoResultante[i] = lAFParent_2[i];
            }
        }

        return lAEHijoResultante;
    }

    /**
     * Imprime la sitaxis correcta y la definición de cada uno de los comandos
     */
    void PrintParametros()
    {
        System.out.println("_______________________________________________________________");
        System.out.println("_____                                   Cycle crossover operator (CX)                                   ____");
        System.out.println("____________            IDS_Jonathan_Rojas@hotmail.com         ______________");
        System.out.println("____________                 renearnulfo@hotmail.com                     ______________");
        System.out.println("_______________________________________________________________");
        System.out.println(" This operator crosses a set of individuals to generate a set of new offsprings ");
        System.out.println("through the cycle crossover algorithm and a crossover (-XPROB) probability.   ");
        System.out.println("NOTE: The crossover probability allows generate crosses between several      ");
        System.out.println("pairs of individuals fathers                                                                                                 ");
        System.out.println("                                                                                                                                                   ");
        System.out.println("Sintaxis:                                                                                                                                   ");
        System.out.println("-CONFIG | (-FWORK -FACTG -FNEXTG -FPOP -FSELECT -NI:int -NELITE:int [-XPROB]) [-REPORTCX:str]");
        System.out.println("                                                                                                                                                   ");
        System.out.println("-CONFIG      File where the above parameters are defined                                          ");
        System.out.println("-FWORK      Path where the work directory is                                                                ");
        System.out.println("-FACTG        Prefix of Actual Generation Path                                                                 ");
        System.out.println("-FNEXTG      Prefix of Next Generation Path                                                                    ");
        System.out.println("-FPOP          Path where the population (of the -FACTG or -FNEXTG) is                     ");
        System.out.println("-FSELECT    File name where the parents are                                                                ");
        System.out.println("-NI                 Number of individuals                                                                                    ");
        System.out.println("-NELITE        Number of elite individuals                                                                           ");
        System.out.println("-XPROB       Is the crossover probability (0-100) , by default is 70                            ");
        System.out.println("-REPORTCX Is the name of file to generate a report of generated individuals      ");
    }

}//de la clase
