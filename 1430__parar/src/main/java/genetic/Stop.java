package genetic;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;

import consumeapi_lib.ConsumeAPI_LIB;
import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.ManejoER;
import libs.MyListArgs;
import libs.MySintaxis;

/**
 * Este programa determina si alguna de las condiciones de paro se cumplió por
 * lo que algoritmo genético debe parar. Las condiciones de paro pueden ser
 * dadas por línea de comandos o en un archivo especificado en -CONFIG: 1.-Por
 * llegar al número máximo de iteraciones [-MAXGEN] 2.-Porque llegó a
 * determinada fecha, [-DATE (AA/MM/DD/HH/MM)] 3.-Porque se obtuvo una
 * determinada diferencia entre el valor menor y mayor de la función de aptitud.
 * [-FADIFF] 4.-Porque los valores en los genes de la población son frecuentes
 * por lo que se considera que la población se estabilizó. [-GEN100 -POP100]
 * 5.-Porque se alcanzó un valor mayor en la función de aptitud [-FAMAX]
 * 6.-Porque se alcanzó un valor menor en la función de aptitud [-FAMIN]
 *
 * Sintaxis:
 *
 *  //-CONFIG | (-FWORK [-DATE (AA/MM/DD/HH/MM)] //[-FACTG (-MAXGEN) |
 * (-FACTGPOP -GEN100 -POP100) | (-FACTGFA [-FADIFF] [-FAMIN|-FAMAX])] )
 *
 * @author RENE
 */
public class Stop {

    /**
     * -CONFIG Nombre del archivo con los demás parámetros dados abajo
     */
    String ConfigFile;
    
    /**
     * -FWORK SubRuta donde se van a crear las generaciones
     */
    String FWORK;
    
    /**
     * -FACTG SubRuta (prefijo) de la generación actual, sin incluir paréntesis
     */
    String FACTG;
    
    /**
     * -FACTGPOP Ruta donde está la población
     */
    String FPOP;
    
    /**
     * -FACTGFA Ruta donde está la funcion de aptitud
     */
    String FFA;
    
    /**
     * -MAXGEN Número máximo de generaciones
     */
    int MAXGEN;
    
    /**
     * -MAXERAS Número máximo de Eras
     */
    int MAXERAS;    
    
    /**
     * -DATE Fecha en que debe terminar el programa (AA/MM/DD/HH/MM) o (AAAA/MM/DD/HH/MM)
     */
    Calendar DATE;

    //Número de generación actual
    int ActGen;
    
    /**
     * -GEN100 Porcentaje por Gen
     */
    int GEN100;
    
    /**
     * -POP100 Porcentaje por Poblacion
     */
    int POP100;
    
    /**
     * (-FAMIN Minimum Threshold Quality to reach by some individuo
     */
    float FAMIN;
    
    /**
     * (-FAMAX Maximum Threshold Quality to reach by some individuo
     */
    float FAMAX;
    
    /**
     * -FADIFF Diference of FA
     */
    float FADIFF;
    
    /**
     * -OPTIMOD Se configura el AG para Maximizar (MAX) o Minimizar (MIN)
     */
    String OPTIMOD;//Autogenerado      
    
    /**
     * -CATACLYSM Reinicia el AG al finalizar cada ejecución (un ciclo completo)
     */    
    boolean CATACLYSM;  //Opcional    

    File IF;
    MyListArgs Param;
    IOBinFile BinFile;
    ManejoArchivos IO;
    StringBuffer Report;

    //Varaibles para el cache
    String HOST;
    String PORT;
    String SERVER_SCRIPT;
    boolean STOP_SERVER_ON_END;
    float  UMBRAL_CACHE;
    
    /**
     * CONSTRUCTOR de la clase stop, donde se pasan los comentarios por línea de
     * comandos o por archivo
     *
     * @param Args
     */
    Stop(String[] Args) {
        IO = new ManejoArchivos();
        BinFile = new IOBinFile();
        //*****lectura de argumentos*******
        Param = new MyListArgs(Args);

        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");//Ruta donde se van a crear los individuos
        if (!ConfigFile.equals("")) {
            Param.AddArgsFromFile(ConfigFile);
        }

        String Sintaxis = " -FWORK:str [-OPTIMOD:MAX:MIN] [-CATACLYSM:bool [-MAXERAS:int]] [-DATE:date]  "
                + "[ -FACTG:str [-MAXGEN:int] ] [ -FACTG:str -FPOP:str [-GEN100:int -POP100:int]] "
                + "[ -FACTG:str -FFA:str [[-FADIFF:float] [-FAMIN:float | -FAMAX:float]]] " +
                "[-HOST:str -PORT:int] [-UMBRAL_CACHE:str]";

        MySintaxis Review = new MySintaxis(Sintaxis, Param);

        FWORK       = Param.ValueArgsAsString("-FWORK", "");
        FACTG       = Param.ValueArgsAsString("-FACTG", "");//como es subdirectorio
        MAXGEN      = Param.ValueArgsAsInteger("-MAXGEN", -1);
        MAXERAS     = Param.ValueArgsAsInteger("-MAXERAS", -1);
        DATE        = Param.ValueArgsAsDate("-DATE", "");
        FPOP        = Param.ValueArgsAsString("-FPOP", "");
        GEN100      = Param.ValueArgsAsInteger("-GEN100", -1);
        POP100      = Param.ValueArgsAsInteger("-POP100", -1);
        FFA         = Param.ValueArgsAsString("-FFA", "");
        FADIFF      = Param.ValueArgsAsFloat("-FADIFF", -1);
        FAMIN       = Param.ValueArgsAsFloat("-FAMIN", -1);
        FAMAX       = Param.ValueArgsAsFloat("-FAMAX", -1);
        OPTIMOD     = Param.ValueArgsAsString("-OPTIMOD", "MAX"); //Opcional-Autogenerado
        CATACLYSM   = Param.ValueArgsAsBoolean("-CATACLYSM", false);  //Opcional-Autogenerado

        this.HOST = Param.ValueArgsAsString("-HOST", "null");
        this.PORT = Param.ValueArgsAsString("-PORT", "null");
        this.SERVER_SCRIPT = Param.ValueArgsAsString("-SERVER_SCRIPT", "null");
        this.UMBRAL_CACHE  = Param.ValueArgsAsFloat("-UMBRAL_CACHE", 2f);
    }//de la función

    
    
    
    void Run() {
        ActGen = 0;
        if (Param.Exists("-FACTG")) {
            String[] Folders = IO.List_Carpetas(FWORK);
            /* Expresion Regular para obtener el número de la GeneraciónActual */
            ManejoER ER = new ManejoER(FACTG.toUpperCase() + "\\_(\\d+)\\_");

            for (String string : Folders) {
                if (ER.ExistER(string.toUpperCase())) {
                    ActGen = Integer.parseInt(ER.Grupo(1));
                    break;
                }
            }
        }//del if

        Report = new StringBuffer("====================Generación:" + ActGen + "====================\r\n");
        Report.append("Reporte\tOK\tUmbral\t\tValor obtenido\r\n");
        boolean Answer = Stop_MaxGen() | Stop_FAMAX() | Stop_FAMIN() | Stop_FADIFF() | Stop_Date() | Stop_GENPOP100();
        boolean AnswerEras = Stop_MaxEras();
        Estatics();
        Report.append("\r\n\r\n");

        if (Answer) {
            //ConsumeAPI_LIB.GET("", HOST, PORT+"/EXIT");//TODO habilitar la opción de detener el servidor de cache
            IO.Write_String_File(IO.AddToPath(FWORK, "STOP.txt"), Report.toString());
        }

        if (CATACLYSM && AnswerEras) {
            IO.Write_String_File(IO.AddToPath(FWORK, "STOP_ERAS.txt"), Report.toString());
        }

        IO.Write_String_File_Add(IO.AddToPath(FWORK, "State.txt"), Report.toString());

        //while (true);
    }//de la función Run

    
    
    /**
     * Obtiene el promedio de la función de aptitud
     */
    void Estatics() {
        if (!Param.Exists("-FACTG") || !Param.Exists("-FFA")) {
            return; // no se puede carcular el promedio
        }

        String[] Folders = IO.List_Carpetas(FWORK);
        String Path = "";
        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FACTG.toUpperCase())) {
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FFA);
                break;
            }
        }
        String[] FAs = IO.List_Files_Abs(Path, ".bin");
        float Minimo = 0, Maximo = 0;
        double PROM = 0;
        String lSFileMax = "", lSFileMin = "";
        float[] FunValue;

        for (int i = 0; i < FAs.length; i++) {
            FunValue = BinFile.ReadBinFloatFileIEEE754(FAs[i]);
            if (i == 0) {
                PROM = Minimo = Maximo = FunValue[0];
                lSFileMax = FAs[0];
                lSFileMin = FAs[0];
            } else
            {
                PROM += FunValue[0];
            }

            if (Minimo > FunValue[0]) {
                Minimo = FunValue[0];
                lSFileMin = FAs[i];
            }
            if (Maximo < FunValue[0]) {
                Maximo = FunValue[0];
                lSFileMax = FAs[i];
            }

        }//del for
        PROM /= FAs.length;
        Report.append("\r\nAdaptación máxima:").append(Maximo).append(" -> ").append(lSFileMax);
        Report.append("\r\nAdaptación promedio:").append(PROM);
        Report.append("\r\nAdaptación mínima:").append(Minimo).append(" -> ").append(lSFileMin);

        if(!HOST.equals("null")){
            float BEST = (OPTIMOD=="MAX")?Minimo:Maximo;
            actualizaCache(BEST, (float)PROM, ActGen);
        }
    }
    public void actualizaCache(float BEST, float PROM, int ActGen){
        System.out.println(String.valueOf(BEST)+","+ this.HOST+","+ this.PORT+"/BEST");
        float IniDif;
        //TODO ejecutar el script para iniciar el servidor REST
        //if(this.ActGen==1){
        //CONSOLE Exe = new CONSOLE(false); // se pide que muestre la consola
        //Exe.Execute("node "+SERVER_SCRIPT, "");

        ConsumeAPI_LIB.POST("BEST="+String.valueOf(BEST), this.HOST, this.PORT+"/BEST");
        if(ActGen==1){
            IniDif = Math.abs(BEST-(float)PROM);
            ConsumeAPI_LIB.POST("IniDif="+String.valueOf(IniDif), this.HOST, this.PORT+"/INIDIF");
            System.out.println("Diferencia incial entre Mejor y Promedio="+IniDif);
        }

        if(!HOST.equals("")&!HOST.equals("null")){
            try{
                float best = Float.parseFloat(ConsumeAPI_LIB.GET("", HOST, PORT + "/BEST"));
                float ActDif = Math.abs(best - (float) PROM);

                //System.out.println("actDif "+ActDif);

                IniDif = Float.parseFloat(ConsumeAPI_LIB.GET("", HOST, PORT + "/INIDIF"));
                //System.out.println("IniDif "+IniDif);
                if ((ActDif * 100) / IniDif <= UMBRAL_CACHE) {
                    ConsumeAPI_LIB.POST("CACHE=true", this.HOST, this.PORT + "/CACHE");
                }
            }catch(Exception e){
                System.out.println("Imposible establecer conexión con el servidor "+HOST+" revisa la dirección se está ejecutando sin caché");
                ConsumeAPI_LIB.POST("CACHE=true", this.HOST, this.PORT + "/CACHE");
            }
        }
    }

    /**
     * Verifica si el programa debe parar porque la población se estabilizó
     *
     * @return true: si se alcanzó la diferencia mínima entre la FA
     */
    boolean Stop_GENPOP100() {
        if (!Param.Exists("-GEN100")) {
            return (false);
        }

        String[] Folders = IO.List_Carpetas(FWORK);
        String Path = "";
        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FACTG.toUpperCase())) {
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FPOP);
                break;
            }
        }
        String[] Inds = IO.List_Files_Abs(Path, ".bin");
        Map<String, Integer> Mapa = new HashMap<String, Integer>();
        float[] FunValue = null;
        int cars = (Inds.length > 0) ? BinFile.ReadBinFloatFileIEEE754(Inds[0]).length : 0;
        int Freq = 0;
        int FreqGens = 0;
        int DGEN = (POP100 * Inds.length) / 100; //Número de individuos en los que debe repetirse un gen para considerarse frecuente
        int DCARS = (GEN100 * cars) / 100;        //Número de genes frecuentes para considerarse una población como estabilizada
        int MaxFrec[] = new int[cars]; //aquí se va a registrar las frecuencias maximas de un valor para un gen
        String Key;

        Mapa.clear();
        for (int j = 0; j < Inds.length; j++) { //hacer esto para todos los archivos
            FunValue = BinFile.ReadBinFloatFileIEEE754(Inds[j]);

            for (int i = 0; i < cars; i++) { // hacer esto para cada una de las características  
                MaxFrec[i] = 0;
                Key = String.valueOf(i) + ":" + String.valueOf(FunValue[i]);
                if (!Mapa.containsKey(Key)) // si no está
                {
                    Mapa.put(Key, 1); //su frecuencia empieza en uno
                } else { //si ya está
                    Freq = Mapa.get(Key); //obtener la frecuencia
                    Mapa.put(Key, ++Freq); //incrementar la frecuencia en uno
                }
            }//del for
        }//del for //en este punto ya se contaron todos los individuos

        Set<String> keys = Mapa.keySet(); //se obtiene todo el conjunto de claves
        String KeyComp[];
        int gen, frec;
        for (String clave : keys) { //hacer esto para cada clave
            KeyComp = clave.split(":");
            gen = Integer.valueOf(KeyComp[0]); //se obtiene el número de gen (el valor KeyComp[1] con el que se alcanzó esa frecuencia no importa ahorita)
            frec = Mapa.get(clave); //se obtiene la frecuencia

            if (frec > MaxFrec[gen]) //si encontró una frecuencia mayor a la que se tiene, hay que registrarla
            {
                MaxFrec[gen] = frec;
            }
        }//del for //en este punto ya se tiene el vector con las máximas frecuencias por gen

        for (int i = 0; i < MaxFrec.length; i++) { //ahora se cuenta por cada gen            
            if (MaxFrec[i] >= DGEN) //si la frecuencia es mayor al umbral, incrementar el porcentaje global
            {
                FreqGens++;
            }
        }//del for

        int Per = (FreqGens * 100) / cars;

        if (FreqGens >= DCARS) {
            Report.append("-GEN100\t").append("TRUE\t").append(GEN100).append("%(").append(DCARS).append(")\t\t").append(Per).append("%(").append(FreqGens).append("_").append("\r\n");
            return (true);
        }

        Report.append("-GEN100\t").append("FALSE\t").append(GEN100).append("%(").append(DCARS).append(")\t\t").append(Per).append("%(").append(FreqGens).append("_").append("\r\n");
        return (false);
    }// fin de la función

    
    boolean Stop_GENPOP100backup() {
        if (!Param.Exists("-GEN100")) {
            return (false);
        }

        String[] Folders = IO.List_Carpetas(FWORK);
        String Path = "";
        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FACTG.toUpperCase())) {
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FPOP);
                break;
            }
        }
        String[] Inds = IO.List_Files_Abs(Path, ".bin");
        Map<Float, Integer> Mapa = new HashMap<Float, Integer>();
        float[] FunValue = null;
        int cars = (Inds.length > 0) ? BinFile.ReadBinFloatFileIEEE754(Inds[0]).length : 0;
        int Freq = 0;
        int FreqGens = 0;
        int NoFreqGens = 0;
        int DGEN = (POP100 * Inds.length) / 100; //Número de individuos en los que debe repetirse un gen para considerarse frecuente
        int DCARS = (GEN100 * cars) / 100;        //Número de genes frecuentes para considerarse una población como estabilizada
        int PosFails = Inds.length - DGEN;       //Número de genes máximos en los que puede no ser frecuente un gen
        Float KeyFreq;

        for (int i = 0; i < cars; i++) { // hacer esto para cada una de las características

            Mapa.clear();
            for (int j = 0; j < Inds.length; j++) { //hacer esto para todos los archivos
                FunValue = BinFile.ReadBinFloatFileIEEE754(Inds[j]);
                if (!Mapa.containsKey(FunValue[i])) // si no está
                {
                    Mapa.put(FunValue[i], 1);
                } else {
                    Freq = Mapa.get(FunValue[i]);
                    Mapa.put(FunValue[i], ++Freq);
                }
            }//del for
            Set<Float> keys = Mapa.keySet();
            Freq = 0;
            KeyFreq = (float) 0.0;
            for (Float clave : keys) {
                if (Freq < Mapa.get(clave)) {
                    Freq = Mapa.get(clave);
                    KeyFreq = clave;
                }
            }

            //  System.out.println("Car["+(i+1)+"]=<"+KeyFreq+","+Freq+">");
            if (Freq >= DGEN) {
                FreqGens++;
            } else {
                NoFreqGens++;
            }

            if (NoFreqGens > PosFails) { //hace más eficiente el código porque no es necesario evaluar todos los genes
                Report.append("-POP100\t").append("FALSE\t").append(POP100).append("_").append(DGEN).append("_").append("\r\n");
                Report.append("-GEN100\t").append("FALSE\t").append(GEN100).append("_").append(DCARS).append("_").append("\t\t<")
                        .append(GEN100).append("\r\n");

                return (false);
            }
        }//del for
        int Per = (FreqGens * 100) / cars;
        if (FreqGens >= DCARS) {
            //System.out.println("FreqGens="+FreqGens+" DCARS="+ DCARS + " cars=" + cars);
            Report.append("-POP100\t").append("TRUE\t").append(POP100).append("_").append(DGEN).append("_").append("\r\n");
            Report.append("-GEN100\t").append("TRUE\t").append(GEN100).append("_").append(DCARS).append("_")
                    .append("\t\t").append(Per).append("_").append(FreqGens).append("_").append("\r\n");
            return (true);
        }

        Report.append("-POP100\t").append("FALSE\t").append(POP100).append("_").append(DGEN).append("_").append("\r\n");
        Report.append("-GEN100\t").append("FALSE\t").append(GEN100).append("_").append(DCARS).append("_")
                .append("\t\t").append(Per).append("_").append(FreqGens).append("_").append("\r\n");
        return (false);
    }// fin de la función

    
    /**
     * Verifica si el programa debe parar porque se alcanzó una diferencia
     * mínima entre el mínimo y máximo valor de la FA
     *
     * @return true: si se alcanzó la diferencia mínima entre la FA
     */
    boolean Stop_FADIFF() {
        if (!Param.Exists("-FADIFF")) {
            return (false);
        }

        String[] Folders = IO.List_Carpetas(FWORK);
        String Path = "";
        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FACTG.toUpperCase())) {
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FFA);
                break;
            }
        }
        String[] FAs = IO.List_Files_Abs(Path, ".bin");
        float Minimo = 0, Maximo = 0;
        float[] FunValue;

        for (int i = 0; i < FAs.length; i++) {
            FunValue = BinFile.ReadBinFloatFileIEEE754(FAs[i]);
            if (i == 0) {
                Minimo = Maximo = FunValue[0];
            }

            if (Minimo > FunValue[0]) {
                Minimo = FunValue[0];
            }
            if (Maximo < FunValue[0]) {
                Maximo = FunValue[0];
            }
        }//del for

        if (Maximo - Minimo <= FADIFF) {
            Report.append("-FADIFF\t").append("TRUE\t").append(FADIFF).append("\t\t").append(Maximo - Minimo).append("\r\n");
            return (true);
        }//del if
        Report.append("-FADIFF\t").append("FALSE\t").append(FADIFF).append("\t\t").append(Maximo - Minimo).append("\r\n");
        return (false);
    }

    
    
    /**
     * Verifica si el programa debe parar porque se alcanzó un umbral mínimo en
     * la FA
     *
     * @return true: si se alcanzó el umbral mínimo en la FA
     */
    boolean Stop_FAMIN() {
        if (!Param.Exists("-FAMIN")) {
            return (false);
        }

        String[] Folders = IO.List_Carpetas(FWORK);
        String Path = "";
        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FACTG.toUpperCase())) {
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FFA);
                break;
            }
        }
        String[] FAs = IO.List_Files_Abs(Path, ".bin");
        float[] FunValue = new float[1];
        for (String Arch : FAs) {

            FunValue = BinFile.ReadBinFloatFileIEEE754(Arch);
            //System.out.println(Arch + "  " + FunValue[0]);
            if (FunValue[0] <= FAMIN) {
                Report.append("-FAMIN\t").append("TRUE\t").append(FAMIN).append("\t\t").append(FunValue[0]).append("\r\n");
                return (true);
            }//del if
        }
        Report.append("-FAMIN\t").append("FALSE\t").append(FAMIN).append("\t\t").append(FunValue[0]).append("\r\n");
        return (false);
    }

    
    
    /**
     * Verifica si el programa debe parar porque se alcanzó un umbral máximo en
     * la FA
     *
     * @return true: si se alcanzó el umbral máximo en la FA
     */
    boolean Stop_FAMAX() {
        if (!Param.Exists("-FAMAX")) {
            return (false);
        }

        String[] Folders = IO.List_Carpetas(FWORK);
        String Path = "";
        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FACTG.toUpperCase())) {
                Path = IO.AddToPath(IO.AddToPath(FWORK, string), FFA);
                break;
            }
        }

        String[] FAs = IO.List_Files_Abs(Path, ".bin");
        float[] FunValue = new float[1];
        float MaxObt = -1;

        for (int i = 0; i < FAs.length; i++) {
            FunValue = BinFile.ReadBinFloatFileIEEE754(FAs[i]);

            if (FunValue[0] >= FAMAX) {
                Report.append("-FAMAX\t").append("TRUE\t").append(FAMAX).append("\t\t").append(FunValue[0]).append("\r\n");
                return (true);
            }//del if

            if (i == 0 || (FunValue[0] > MaxObt)) {
                MaxObt = FunValue[0];
            }

        }
        Report.append("-FAMAX\t").append("FALSE\t").append(FAMAX).append("\t\t").append(MaxObt).append("\r\n");
        return (false);
    }

    
    
    /**
     * Verifica si el programa debe parar porque se alcanzó el máximo de
     * generaciones
     *
     * @return true: si se alcanzó el máximo de generaciones
     */
    boolean Stop_MaxGen() { //Terminar por el máximo de generaciones
        if (MAXGEN < 0) {
            return (false);  //no se activó ésta bandera
        }
        String[] Folders = IO.List_Carpetas(FWORK);
        ManejoER ER = new ManejoER(FACTG.toUpperCase() + "\\_(\\d+)\\_");
        int ActGen = -1;

        for (String string : Folders) {
            if (ER.ExistER(string.toUpperCase())) {
                ActGen = Integer.parseInt(ER.Grupo(1));
                break;
            }
        }

        if (ActGen < 0) {
            System.out.println("Stop_MaxGen : no se encontró la carpeta con prefijo: " + FACTG + "(...");
            System.exit(0);
        }

        if (ActGen >= MAXGEN) {
            Report.append("-MAXGEN\t").append("TRUE\t").append(MAXGEN).append("\t\t").append(ActGen).append("\r\n");
            return (true);
        }
        Report.append("-MAXGEN\t").append("FALSE\t").append(MAXGEN).append("\t\t").append(ActGen).append("\r\n");
        return (false);
    }//de la funcion

    
    
    /**
     * Verifica si el programa debe parar porque se alcanzó la fecha (con horas
     * y minutos) límite de tiempo para procesar.
     *
     * @return true: si se alcanzó la fecha
     */
    boolean Stop_Date() { // Se alcanzó el tiempo de procesamiento
        if (DATE == null) {
            return (false); //no existe la etiqueta -DATE o el formato es incorrecto
        }

        Calendar FechaAct = Calendar.getInstance();
        String DateStr = DATE.get(Calendar.YEAR) + "/"
                + (DATE.get(Calendar.MONTH) + 1) + "/"
                + DATE.get(Calendar.DAY_OF_MONTH) + "/"
                + DATE.get(Calendar.HOUR_OF_DAY) + "/"
                + DATE.get(Calendar.MINUTE);
        String Fecha = FechaAct.get(Calendar.YEAR) + "/"
                + (FechaAct.get(Calendar.MONTH) + 1) + "/"
                + FechaAct.get(Calendar.DAY_OF_MONTH) + "/"
                + FechaAct.get(Calendar.HOUR_OF_DAY) + "/"
                + FechaAct.get(Calendar.MINUTE);

        if (FechaAct.compareTo(DATE) >= 0) { //se alcanzó la fecha límite tiempo de procesamiento, terminar
            Report.append("-DATE\t").append("TRUE\t").append(DateStr).append("\t").append(Fecha).append("\r\n");
            return (true);
        }
        Report.append("-DATE\t").append("FALSE\t").append(DateStr).append("\t").append(Fecha).append("\r\n");
        return (false);
    }

    
    /**
     * Verifica si el programa debe parar porque se alcanzó el máximo de
     * Eras
     *
     * @return true: si se alcanzó el máximo de Eras
     */
    boolean Stop_MaxEras() { //Terminar por el máximo de Eras
        if (!CATACLYSM || (MAXERAS < 0)) {
            return (false);  //no se activó ésta bandera
        }
        ManejoER ER = new ManejoER("ERA" + "\\_(\\d+)\\_");
        int ActEra = -1;
        if (ER.ExistER(FWORK.toUpperCase())) {
            ActEra = Integer.parseInt(ER.Grupo(1));
        }        
       
        if (ActEra < 0) {
            System.out.println("Stop_MaxEras : no se encontró la carpeta con prefijo: " + "ERA" + "(...");
            System.exit(0);
        }

        if (ActEra >= MAXERAS) {
            Report.append("-MAXERAS\t").append("TRUE\t").append(MAXERAS).append("\t\t").append(ActEra).append("\r\n");
            return (true);
        }
        Report.append("-MAXERAS\t").append("FALSE\t").append(MAXERAS).append("\t\t").append(ActEra).append("\r\n");
        return (false);
    }//de la funcion

    
    
    
}//del objeto Stop
