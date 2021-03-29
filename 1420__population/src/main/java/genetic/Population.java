/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;

import java.io.File;
import java.util.*;

import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.MyListArgs;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import libs.ManejoER;
import libs.MySintaxis;

/**Clase que crea la población para el algoritmo genético, en donde se puede especificar varios parámetros.
 * @author RENE
 */
public class Population {
    /**(-CONFIG) Archivo con todos los parámetros {@link #Population(String[])}*/
    String       ConfigFile;
    /**(-FWORK) Ruta donde se van a crear los individuos {@link #Population(String[])} */
    String       FWORK;
    /**(-FACTG) Ruta donde se van a crear los individuos {@link #Population(String[])} */
    String       FNEXTG;
    /**(-FACTGPOP) Ruta donde se van a crear los individuos {@link #Population(String[])} */
    String       FPOP;
    /**(-TYPE) Tipo de dato (int o real) {@link #Population(String[])} */
    String       Type;
    /**(-PRE)Cadena prefijo del nombre de los individuos a generar {@link #Population(String[])}*/
    String       Prefijo;
    /**(-INF)Número secuencial que va después del prefijo y antes del postfijo {@link #Population(String[])}*/
    int          Infijo;
    /**(-POST)Cadena postfijo del nombre de los individuos a generar {@link #Population(String[])}*/
    String       Postfijo;
    /**(-REPEAT) TRUE: Si el valor del gen se puede repetir en el individuo {@link #Population(String[])}*/
    boolean      Repeat;
    /**(-NI) Número de individuos {@link #Population(String[])}*/
    int          NumInd;
    /**(-NGEN)Número de genes de un individuo {@link #Population(String[])}*/
    int          NumGen;
    /**(-VMININT)Limite entero del Valor mínimo del rango del valor de un gen {@link #Population(String[])}*/
    int          MinGenInt;
    /**(-VMAXINT)Limite entero del Valor máximo del rango del valor de un gen {@link #Population(String[])}*/
    int          MaxGenInt;
    /**(-NGENMIN)Número mínimo de cromosomas en individuos de longitud variable, ( NumGen=0 o no aparecer) {@link #Population(String[])}*/
    int          NGenMin;
    /**(-NGENMAX)Número máximo de cromosomas en individuos de longitud variable, ( NumGen=0 o no aparecer) {@link #Population(String[])}*/
    int          NGenMax;
    /**(-Precisión) Número de dígitos en decimales en tipo de datos reales {@link #Population(String[])}*/
    int          Precision;
    /**(-BASE) Ruta del archivo de texto que retomará las bases**/
    String BaseFile;
    /**Cadena que almacena las bases**/
    String BaseExpresion = "@FIRST";
    /**Objeto que almacena la información de las bases de cada gen (Caso codificación Base N)**/
    TreeMap<Integer, Integer> objTreeMapBases;
    /**Nombre del archivo de texto que almacena los mensajes del reporte generado**/
    String NameReport;
    
    String Path = "";

    MyListArgs param;
    String configFile;
    String ValidIndexes;
    Random       Aleos;
    IOBinFile BinFile;
    ManejoArchivos IO = new ManejoArchivos();
    Base objBase;

    /**
     * Función constructor que recibe los parametros donde se especifica la población a crear
     * <p>SINTAXIS:<p> -CONFIG | (-FWORK -FACTG -FACTGPOP -NI (-NGEN |(-NGENMIN -NGENMAX ))
     * -VMININT -VMAXINT [-TYPE (INT|REAL)] [-PRE] [-INF] [-POS] [-REPEAT (TRUE|FALSE)])
     * @param args Lista de argumentos donde cada parámetro y cada valor está en una línea diferente
     */
    Population(String[] args)
    {
        param = new MyListArgs(args);
        BinFile = new IOBinFile();
        param = new MyListArgs(args);
        configFile = param.ValueArgsAsString("-CONFIG", "");
        if (!configFile.equals(""))
        {
            param.AddArgsFromFile(configFile);
        }
        String sintaxis = "-FWORK:str -FNEXTG:str -FPOP:str -TYPE:INT:REAL [-PRE:str] [-INF:int] -POS:str [-REPEAT:bool] "
                + "-NI:int ([-NGEN:str|(-NGENMIN:int -NGENMAX:int)] ((-VMININT:int -VMAXINT:int)|-BASE:str) | -VALID_INDEXES:str) [-PRECISION:int] [-REPORTPOP:str] ";


        MySintaxis review = new MySintaxis(sintaxis, param);
        FWORK = param.ValueArgsAsString("-FWORK", "");//Ruta donde se van a crear los individuos
        FNEXTG = param.ValueArgsAsString("-FNEXTG", "");//SubRuta donde se van a crear los individuos
        FPOP = param.ValueArgsAsString("-FPOP", "");//SubRuta donde se van a crear los individuos
        Type = param.ValueArgsAsString("-TYPE", "INT");//Tipo de dato (int o real)
        Prefijo = param.ValueArgsAsString("-PRE", "INDIVIDUO_");//Cadena prefijo del nombre de los individuos a generar
        Infijo = param.ValueArgsAsInteger("-INF", 0);//Número secuencial que va después del prefijo y antes del postfijo
        Postfijo = param.ValueArgsAsString("-POS", "");//Cadena postfijo del nombre de los individuos a generar
        Repeat = param.ValueArgsAsBoolean("-REPEAT", true);//TRUE: Si el valor del gen se puede repetir en el individuo
        NumInd = param.ValueArgsAsInteger("-NI", 0);//Número de individuos
        NumGen = param.ValueArgsAsInteger("-NGEN", 0);//Número de genes de un individuo
        MinGenInt = param.ValueArgsAsInteger("-VMININT", 0);//Limite entero mínimo del rango del valor de un gen
        MaxGenInt = param.ValueArgsAsInteger("-VMAXINT", 0);//Limite entero máximo del rango del valor de un gen
        BaseFile = param.ValueArgsAsString("-BASE", "");
        NGenMin = param.ValueArgsAsInteger("-NGENMIN", -1);//Número mínimo de cromosomas en individuos de longitud variable, ( NumGen=0 o no aparecer)
        NGenMax = param.ValueArgsAsInteger("-NGENMAX", -1);//(-NGENMAX)Número máximo de cromosomas en individuos de longitud variable, ( NumGen=0 o no aparecer)
        Precision = param.ValueArgsAsInteger("-PRECISION", 0);//Número de digitos de precision en tipos de datos reales
        NameReport = param.ValueArgsAsString("-REPORTPOP", ""); //Nombre del archivo de texto donde se almacenaá el reporte
        ValidIndexes = param.ValueArgsAsString("-VALID_INDEXES", "");

        if(!ValidIndexes.equals("")){

        }
        else if (MinGenInt == 0 && MaxGenInt == 0 & BaseFile.endsWith(".txt"))
        {
            String[] lASCadenas = IO.Read_Text_File(BaseFile);

            for (int i = 0; i < lASCadenas.length; i++)
                BaseExpresion += "," + lASCadenas[i];
            BaseExpresion = BaseExpresion.replace("@FIRST,", "");
            objBase = new Base(BaseExpresion);
            objTreeMapBases = objBase.getMapBases();
        }
        //Toma las bases desde el archivo de parámetros (sólo funciona en base que se aplican incrementalmente)
        else if(!BaseFile.equals("") & NumGen>0) {
            BaseIncrementos objBase2 = new BaseIncrementos(BaseFile, NumGen);
            objTreeMapBases = objBase2.getMapBases();
        }
        else
        {
            if (MinGenInt > MaxGenInt)
            {
                System.out.println("-VMININT es mayor a -VMAXINT ");
                System.out.println(MinGenInt + " > " + MaxGenInt + " por lo tanto se invirtieron los valores automáticamente");
                int Temp = MaxGenInt;
                MaxGenInt = MinGenInt;
                MaxGenInt = Temp;
            }
        }
        //Generación de los tres directorios principales para el componente population
        IO.UntilCrearCarpetas(FWORK);
//        IO.UntilCrearCarpetas(FPOP);
//        IO.UntilCrearCarpetas(FNEXTG);
    }//de la función

    /**
     * Imprime en consola la sintaxis y sus parámetros con una descripción de
     * cada uno de ellos
     */
    void PrintParametros()
    {
        System.out.println("____________________________________________________________________________");
        System.out.println("                              >--ö--<                                       ");
        System.out.println("         Generador de Población inicial para el algoritmo genético          ");
        System.out.println("____________________________________________________________________________");
        System.out.println("                                                                            ");
        System.out.println("SINTAXIS:                                                                   ");
        System.out.println("-CONFIG || (-FWORK:str -FNEXTG:str -FPOP:str [-TYPE (INT|REAL)] [-PRE:str] [-INF:int] [-POS:str] [-REPEAT (TRUE|FALSE)]) ");
        System.out.println("-NI:int (((-NGEN:int ||(-NGENMIN:int -NGENMAX:int)) -VMININT:int -VMAXINT:int) || -BASE:str) [-PRECISION:int]  [-REPORTPOP:str]\n");
        System.out.println("-CONFIG  Archivo donde vienen los demás parámetros descritos abajo          ");
        System.out.println("-FWORK   Ruta de trabajo donde se van a crear las generaciones              ");
        System.out.println("-FNEXTG  SubRuta con la generación siguiente                                ");
        System.out.println("-FPOP    SubRuta donde se van a crear los individuos                        ");
        System.out.println("-TYPE    Tipo de dato (int o real)                                          ");
        System.out.println("-PRE     Cadena prefijo del nombre de los individuos a generar (solo letras)");
        System.out.println("-INF     Número secuencial que va después del prefijo y antes del postfijo  ");
        System.out.println("-POST    Cadena postfijo del nombre de los individuos a generar (solo letras)");
        System.out.println("-REPEAT  TRUE: Si el valor del gen se puede repetir en el individuo         ");
        System.out.println("-NI      Número de individuos                                               ");
        System.out.println("-NGEN    Número de genes de un individuo                                    ");
        System.out.println("-NGENMIN Número mínimo de cromosomas en individuos de longitud variable,    ");
        System.out.println("         (NumGen=0 o no aparecer)                                           ");
        System.out.println("-NGENMAX Número máximo de cromosomas en individuos de longitud variable,    ");
        System.out.println("         (NumGen=0 o no aparecer)                                           ");
        System.out.println("-VMININT Limite entero del Valor mínimo del rango del valor de un gen       ");
        System.out.println("-VMAXINT Limite entero del Valor máximo del rango del valor de un gen       ");
        System.out.println("-BASE Ruta y nombre del archivo de texto donde se definen las bases a trabajar por gen       ");
        System.out.println("-REPORTPOP Nombre del archivo de texto que almacena el reporte generado, si no se especifica no genera el archivo");
    }
    
    /**
     * Crea un hijo con el nombre y longitud deseadas, los demás parámetros ya
     * los tiene la clase
     * @param Lenght Longitud del arreglo para el hijo
     * @param Name Nombre con el que se va guardar
     */
    void CrearHijo(int Lenght, String Name)
    {
        float Hijo[] = new float[Lenght];
        float Gen = 0;
        int Dec = (int) ((Precision > 0) ? Math.pow(10, Precision) : 0);
        float ToDec = (Precision > 0) ? (float) 1 / Dec : 0;

        if (Repeat)
        {
            for (int i = 0; i < Lenght; i++)
            { //rellenar cada uno de los genes
                //  int a = Math.abs( MaxGenInt-MinGenInt)+1;
                //
                Gen = (Aleos.nextInt(MaxGenInt - MinGenInt + 1) + MinGenInt) + (Aleos.nextInt(Dec + 1) * ToDec);
                //System.out.println("Valor del gen" + Gen );
                Hijo[i] = Gen;
            }//del for
        }//del if
        else
        {
            Permutation objPermutation = new Permutation(MinGenInt, MaxGenInt);
            objPermutation.shuffle();
            Lenght = objPermutation.gESize;
            Hijo = objPermutation.Al;
        }
        
//        System.out.println(Name + "  longitutd:" + Lenght);
        BinFile.WriteBinFloatFileIEEE754(Name, Hijo);
    }//de la función CrearHijo
    
    /**
     * Crea un hijo con el nombre y longitud deseadas, los demás parámetros ya
     * los tiene la clase
     * @param Name Nombre con el que se va guardar
     */
    void CrearHijoBaseN(TreeMap<Integer, Integer> objTreeMapBases, String Name)
    {
        float Hijo[] = new float[objTreeMapBases.size()];
        float Gen = 0;
        int lEInc = 0;
        for (Map.Entry<Integer, Integer> objEntry : objTreeMapBases.entrySet())
        {
            Gen = Aleos.nextInt(objTreeMapBases.get(objEntry.getKey()));
            Hijo[lEInc] = Gen;
            lEInc++;
        }
//        System.out.println(Name + "  longitutd:" + objTreeMapBases.size());
        BinFile.WriteBinFloatFileIEEE754(Name, Hijo);
    }//de la función CrearHijo

    /**
     * Crea un hijo con el nombre y longitud deseadas, los demás parámetros ya
     * los tiene la clase
     */
    static float[] CrearHijoTomandoValoresEnVector(float[] indexes, boolean Repeat)
    {
        float Hijo[] = new float[indexes.length];
        float Gen = 0;

        //combinaciones
        if (Repeat)
        {
            //List<Integer> indexList = new ArrayList<>(indexes);
            for (int i = 0; i < Hijo.length; i++)
            { //rellenar cada uno de los genes
                Gen = indexes[ThreadLocalRandom.current().nextInt(0, indexes.length)];
                Hijo[i] = Gen;
            }//del for
        }//del if
        else //permutaciones
        {
        //    HashSet<Integer> index = new HashSet<>();
            Permutation objPermutation = new Permutation(indexes);
            Hijo = objPermutation.Al;
        }

//        System.out.println(Name + "  longitutd:" + Lenght);
        //IOBinFile.WriteBinFloatFileIEEE754(Name, Hijo);
        return Hijo;
    }//de la función CrearHijo
    /**
     * Crea la población con los parámetros deseados
     */
    void CrearPoblacion()
    {
        String[] Folders = IO.List_Carpetas(FWORK); //sacar la lista de carpetas
        
        for (String string : Folders)
        {            //buscar la carpeta con el prefijo FNEXTG
            if (string.startsWith(FNEXTG))
            { //cuando se encuentra
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FPOP); //generar la ruta completa FWORK+FACTG+FACTGPOP
                break;
            }
        }
        Path = (Path.compareTo("") == 0) ? IO.AddToPath(IO.AddToPath(FWORK, FNEXTG), FPOP) : Path;
        IO.UntilCrearCarpetas(Path);
        
        Aleos = new Random();
        
        String[] lASFiles =  IO.List_Files(Path, ".bin");
        if (lASFiles.length > 0)
        {
            ManejoER ER = new ManejoER("([a-zA-Z]*)(\\d+)([a-zA-Z]*)\\.bin");
            int[] lAENumbers = new int[lASFiles.length];
            for (int i = 0; i < lAENumbers.length; i++)
            {
                ER.ExistER(lASFiles[i]);
                lAENumbers[i] = Integer.parseInt(ER.Grupo(2).trim());
            }
            Arrays.sort(lAENumbers);
            Infijo = lAENumbers[lAENumbers.length -1];
        }

        NumInd = (lASFiles.length > 0) ?  (NumInd - lASFiles.length): NumInd;
        Infijo = (lASFiles.length > 0) ? ++Infijo: Infijo;

        if(!ValidIndexes.equals("")){
            float[] indexes = IOBinFile.ReadBinFloatFileIEEE754(this.ValidIndexes);
            IntStream.range(0, NumInd+Infijo).forEach(i->{
                float[] ind = CrearHijoTomandoValoresEnVector(indexes, this.Repeat);
                IOBinFile.WriteBinFloatFileIEEE754(IO.AddToPath(Path, Prefijo + Integer.toString(i) + Postfijo + ".bin"), ind);
            });
            /*for (int i = Infijo; i < NumInd + Infijo; i++) {
                float[] ind = CrearHijoTomandoValoresEnVector(indexes, this.Repeat);
                IOBinFile.WriteBinFloatFileIEEE754(IO.AddToPath(Path, Prefijo + Integer.toString(i) + Postfijo + ".bin"), ind);
            }*/
        }
        else if (MinGenInt == 0 && MaxGenInt == 0)
        {
            for (int i = Infijo; i < NumInd + Infijo; i++)
            {
                CrearHijoBaseN(objTreeMapBases, IO.AddToPath(Path, Prefijo + Integer.toString(i) + Postfijo + ".bin"));
            }
        } else
        {
            if (NumGen > 0)
            {//crear individuos de longitud fija
                for (int i = Infijo; i < NumInd + Infijo; i++)
                {
                    CrearHijo(NumGen, IO.AddToPath(Path, Prefijo + Integer.toString(i) + Postfijo + ".bin"));
                    //CrearHijo(NumGen, (Path.endsWith(File.separator)? Path : Path + File.separator) + Prefijo+Integer.toString(i)+Postfijo+".bin");
                }// del for
            }//del if
            else
            { //individuos de longitud variable
                for (int i = Infijo; i < NumInd + Infijo; i++)
                {
                    CrearHijo(Aleos.nextInt(NGenMax - NGenMin + 1) + NGenMin, IO.AddToPath(Path, Prefijo + Integer.toString(i) + Postfijo + ".bin"));
                    //CrearHijo(Aleos.nextInt(NGenMax-NGenMin+1)+NGenMin, (Path.endsWith(File.separator)? Path : Path +File.separator) +  Prefijo+Integer.toString(i)+Postfijo+".bin");
                }// del for
            }//del else
        }//del else
             
        if (!NameReport.equals(""))
        {
            NameReport = IO.AddToPath(FWORK, NameReport);
            Print objPrint = new Print(this);
            objPrint.OutPutHead();
        }
    }//de la función
}
