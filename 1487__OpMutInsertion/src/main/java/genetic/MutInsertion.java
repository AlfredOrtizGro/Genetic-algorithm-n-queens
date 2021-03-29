package genetic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;
import java.util.Random;

/**
 * MutInsertion
 * @author RENE
 * @author Jonathan Rojas Simón
 */
public class MutInsertion
{
    /**-CONFIG Nombre del archivo con los demás parámetros dados abajo*/
    String ConfigFile;
    /**-FWORK SubRuta donde se van a crear las generaciones*/
    String FWORK;
    /**-FNEXTG Ruta donde está la siguiente generación*/
    String FNEXTG;
    /**-FPOP Ruta donde está la población, ya sea de la generacion actual o de la siguiente generacion*/
    String FPOP;
    /**-PMUT Probabilidad de que el gen de un individuo sea mutado*/
    float PMUT;
    /**Nombre del archivo de texto donde se guardarán los individuos mutados**/
    String FMUT;
    /**Nombre del archivo de texto que almacena el reporte del operador de mutación**/
    String REPORTINS;
    /**Cadena que almacena los resultados para imprimirlos al archivo de reporte**/
    StringBuilder Report = new StringBuilder("");
    
    MyListArgs Param;
    IOBinFile BinFile;
    ManejoArchivos IO;
    String gSSalto = "\r\n";
    

    /**
     * Constructor de la función donde se pasan y analizan los parámetros por
     * línea de comandos
     * @param Args Arreglo donde cada celda corresponde a un comando o a su
     * valor introducido
     */
    MutInsertion(String[] Args)
    {
        IO = new ManejoArchivos();
        BinFile = new IOBinFile();
        Param = new MyListArgs(Args);

        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");
        if (!ConfigFile.equals(""))
            Param.AddArgsFromFile(ConfigFile);

        String lSSintaxis = "-FWORK:str -FNEXTG:str -FPOP:str [-PMUT:float] [-REPORTINS:str]";
        MySintaxis objSintaxis = new MySintaxis(lSSintaxis, Param);

        FWORK = Param.ValueArgsAsString("-FWORK", "");
        FNEXTG = Param.ValueArgsAsString("-FNEXTG", "");
        FPOP = Param.ValueArgsAsString("-FPOP", "");
        PMUT = Param.ValueArgsAsFloat("-PMUT", 1);
        REPORTINS = Param.ValueArgsAsString("-REPORTINS", "");
    }

    /**
     * Genera la cruza de los padres para obtener NDIED hijos
     */
    void Run()
    {
        String[] Folders = IO.List_Carpetas(FWORK);
        String FNEXTGPOP = "";
        String PROXGEN;

        for (String string : Folders)
        {
            if (string.toUpperCase().startsWith(FNEXTG))
            {
                PROXGEN = IO.AddToPath(FWORK, string);
                FMUT = IO.AddToPath(PROXGEN, "MUTATED.TXT");
                if (!REPORTINS.equals(""))
                    REPORTINS = IO.AddToPath(PROXGEN, REPORTINS).replace("\\", "/");
                IO.Write_String_File(FMUT, "");
                FNEXTGPOP = IO.AddToPath(PROXGEN, FPOP);
                break;
            }
        }
        
        //-CONFIG | (-FWORK -FNEXTG -FPOP [-PMUT] [-REPORTINS:str])
        
        String[] Inds = IO.List_Files_Abs(FNEXTGPOP, ".bin");
        Random Aleos = new Random();
        float[] P = (Inds.length > 0) ? BinFile.ReadBinFloatFileIEEE754(Inds[0]) : null; //individuo a mutar
        
        Report.append("Validación de padres" + gSSalto);

        float[] PM = BinFile.ReadBinFloatFileIEEE754(Inds[0]);
        float lFSuma = 0.0F;
        
        for (int i = 0; i < PM.length; i++)
            lFSuma += PM[i];
        
        Report.append("Suma de valores -> " + lFSuma + gSSalto);
        
        ArrayList<float[]> objArrayListMut = new ArrayList<>();
        for (int i = 0; i < Inds.length; i++)
        {
            float lFSumC = 0.0F;
            float[] lAFTemp = BinFile.ReadBinFloatFileIEEE754(Inds[i]);
            for (int j = 0; j < lAFTemp.length; j++)
                lFSumC += lAFTemp[j];
            if (lFSumC != lFSuma)
            {
                Report.append("El individuo no es una permutacion" + gSSalto);
                Report.append(Arrays.toString(lAFTemp) + gSSalto);
                Report.append("Las permutaciones recibidas tienen valores repetidos" + gSSalto);
                if (!REPORTINS.equals(""))
                {
                    Print objPrint = new Print(this);
                    objPrint.OutPutHead();
                }
                System.exit(-1);
            } else
            {
                objArrayListMut.add(lAFTemp);
            }
        }
        
        Report.append("------------------------------------------" + gSSalto);
        
        for (int i = 0; i < objArrayListMut.size(); i++)
            BinFile.WriteBinFloatFileIEEE754(Inds[i], getMutatedIndInsertion(objArrayListMut.get(i), Inds[i]));//sobreescribe el archivo
        
        if (!REPORTINS.equals(""))
        {
            Print objPrint = new Print(this);
            objPrint.OutPutHead();
        }

    }//de la función run

    /**
     * getMutatedIndInsertion
     * @param lFIndividuo Codificación genética del individuo
     * @param lSNameInd Nombre del individuo a generar
     * @return un arreglo de números modificado por probabilidades
     */
    public float[] getMutatedIndInsertion(float[] lFIndividuo, String lSNameInd)
    {
        Random objRandom = new Random();
        float lFPivote = 0.0F;
        
        int lEIndiceGenAleatorio; //lEResDado;
        boolean lBMuto = false;;
        Random    Aleos = new Random();
        Report.append("Individuo " + new File(lSNameInd).getName());

        if (PMUT > 0)
        {
            for (int i = 0; i < lFIndividuo.length; i++)
            {   
                
               int exponentes=getExpoB10(PMUT);  //System.out.println("Expo x10^: "+exponentes);
               int segmento= getValueSegment(exponentes,1000); //System.out.println("Valor del Segmento:"+segmento);
               int umbral=(int)getPorcent(PMUT,segmento); //System.out.println("Umbral en segmento:  "+umbral);
                        
               int azarRad=(Aleos.nextInt(segmento+1)); //System.out.println("Azar:"+azarRad);
                
               
                //lEResDado = objRandom.nextInt(1001);
                //if (lEResDado > 0 && lEResDado <= PMUT)
                if ((azarRad)<=umbral)
                {   
                    Report.append(gSSalto + " >>Valor obtenido Exponente" + exponentes);
                    Report.append(gSSalto + " >>Valor obtenido segmento" + segmento);
                    Report.append(gSSalto + " Valor obtenido Umbral" + umbral);
                    Report.append(gSSalto + " Valor obtenido Azar" + azarRad);
                    Report.append(gSSalto + " Gen seleccionado " + i);
                    lBMuto = true;
                    lFPivote = lFIndividuo[i];
                    lEIndiceGenAleatorio = objRandom.nextInt(lFIndividuo.length);
                    Report.append(gSSalto + " Gen a insertar " + lEIndiceGenAleatorio + gSSalto);
                    if (lEIndiceGenAleatorio > i)
                    {
                        for (int j = i; j < lEIndiceGenAleatorio; j++)
                            lFIndividuo[j] = lFIndividuo[j + 1];
                    } else if (lEIndiceGenAleatorio < i)
                    {
                        for (int j = i; j > lEIndiceGenAleatorio; j--)
                            lFIndividuo[j] = lFIndividuo[j - 1];
                    }
                    lFIndividuo[lEIndiceGenAleatorio] = lFPivote;
                }
            }
            if (lBMuto)
            {
                IO.Write_String_File_Add(FMUT, new File(lSNameInd).getName() + "\r\n");
                lBMuto = false;
            }else
            {
                Report.append(" no mutó " + gSSalto);
            }
        }else
        {
            Report.append(" no mutó " + gSSalto);
        }
        Report.append("------------------------------------------" + gSSalto);
        return lFIndividuo;
    }

    /**
     * Imprime la sitaxis correcta y la definición de cada uno de los comandos
     */
    void PrintParametros()
    {
        System.out.println("__________________________ >--ö--< ____________________________");
        System.out.println("_                 renearnulfo@hotmail.com                     _");
        System.out.println("_______________________________________________________________");
        System.out.println("OpMutInsertion Mutes an individual according to insertion of genes                             ");
        System.out.println("                                                               ");
        System.out.println("Sintaxis:                                                      ");
        System.out.println("-CONFIG | -FWORK:str -FNEXTG:str -FPOP:str [-PGENMUT:float]  [-REPORTINS:str]");
        System.out.println("-CONFIG           File where the above parameters are defined         ");
        System.out.println("-FWORK          Path where the work directory is                    ");
        System.out.println("-FNEXTG          Prefix of Next Generation Path                      ");
        System.out.println("-FPOP               Path where the population (of the -FACTG or -FNEXTG) is");
        System.out.println("-PGENMUT      Mutation probability from [1..100], by default 1");
        System.out.println("-REPORTINS  File where a report of insertion algorithm is performed in a population ");
    }
    
    
       private int getExpoB10(double PGenMutaBase) {
       int log=0;
           log=(int)Math.ceil(Math.log10(PGenMutaBase));
       return log;
    }
    
    private int getValueSegment(int exponentes,int segmento) {

        if(exponentes<0){
            for (int i = 0; i < Math.abs(exponentes); i++) {
                segmento*=10;
            }
        }
        
        return segmento;
    }

    
    private float getPorcent(float PGenMutaBase,float segmento) {
        
        float PEnRect=0f;
        if(PGenMutaBase>100) //si existe un aumento del 100%
        {
           PEnRect=segmento;
        }else{
            PEnRect=PGenMutaBase*segmento/100;
        }
        
        return PEnRect;
    }

}//de la clase
