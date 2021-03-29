/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fareinas;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;

/**
 *
 * @author Alfre
 */
public class FuncionAptitudNReinas {

    /**
     * -CONFIG Nombre del archivo con los demás parámetros dados abajo
     */
    String ConfigFile;
    /**
     * -FWORK SubRuta donde se van a crear las generaciones
     */
    String FWORK;
    /**
     * -FNEXTG Ruta donde está la siguiente generación
     */
    String FNEXTG;
    /**
     * -FPOP Ruta donde está la población, ya sea de la generacion actual o de
     * la siguiente generación
     */
    String FPOP;
    /**
     * -FFA Ruta donde está la función de aptitud ya sea de la generacion actual
     * o de la siguiente generacion
     */
    String FFA;
    /**
     * -FDISTANCES Archivo que almacena todas las distancias entre ciudades*
     */

    MyListArgs Param;
    IOBinFile BinFile;
    ManejoArchivos IO;
    StringBuffer Report;

    //-CONFIG | (-FWORK:str -FNEXTG:str -FPOP:str -FFA:str -FDISTANCES:str [-SEP:str])
    /**
     * Constructor de la función donde se pasan y analizan los parámetros por
     * línea de comandos
     *
     * @param Args Arreglo donde cada celda corresponde a un comando o a su
     * valor introducido
     */
    FuncionAptitudNReinas(String[] Args) {
        IO = new ManejoArchivos();
        BinFile = new IOBinFile();
        //*****lectura de argumentos*******
        Param = new MyListArgs(Args);
        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");
        if (!ConfigFile.equals("")) {
            Param.AddArgsFromFile(ConfigFile);
        }

        FWORK = Param.ValueArgsAsString("-FWORK", "").replace("\\", "/").trim();
        FNEXTG = Param.ValueArgsAsString("-FNEXTG", "").replace("\\", "/").trim();
        FPOP = Param.ValueArgsAsString("-FPOP", "").replace("\\", "/").trim();
        FFA = Param.ValueArgsAsString("-FFA", "").replace("\\", "/").trim();
        String Sintaxis = "-FWORK:str -FNEXTG:str -FPOP:str -FFA:str";
        MySintaxis Review = new MySintaxis(Sintaxis, Param);

    }

    /**
     * Run Método que evalúa toda una generación de individuos en ep problema 8
     * REINAS
     */
    void Run() {
        String[] Folders = IO.List_Carpetas(FWORK);
        String FNEXTGEN = "";
        String FNEXTGFA = "";
        String FNEXTGPOP = "";

        for (String string : Folders) {
            if (string.toUpperCase().startsWith(FNEXTG)) {
                FNEXTGEN = IO.AddToPath(FWORK, string);
                FNEXTGFA = IO.AddToPath(FNEXTGEN, FFA);
                FNEXTGPOP = IO.AddToPath(FNEXTGEN, FPOP);
                break;
            }
        }

        String[] Inds = IO.List_Files(FNEXTGPOP, ".bin");
        String[] FAs = IO.List_Files(FNEXTGFA, ".bin");

        Set<String> Conjunto = new HashSet<>(Arrays.asList(FAs));
        final String NEXTPOP = FNEXTGPOP;
        final String NEXTFA = FNEXTGFA;
        IntStream.range(0, Inds.length).parallel().forEach(i -> {
            if (!Conjunto.contains(Inds[i])) {
                float[] P = IOBinFile.ReadBinFloatFileIEEE754(IO.AddToPath(NEXTPOP, Inds[i]));
                float[] Adapt = {Evaluar(P)};
                BinFile.WriteBinFloatFileIEEE754(IO.AddToPath(NEXTFA, Inds[i]), Adapt);
            }
        });
    }//de la función run

    /**
     * Imprime la sintaxis correcta y la definición de cada uno de los comandos
     */
    void PrintParametros() {
        System.out.println("______________________________________________________________________________________________________________");
        System.out.println("_____________________________________FA_N_REINAS______________________________________________________________");
        System.out.println("___________________Función de aptitud del problema del 8 o N reinas___________________________________________");
        System.out.println("___________Autor: Alfredo Ortiz Guerrero__________correo: alfredortiz@gmail.com_______________________________");
        System.out.println("______________________________________________________________________________________________________________");
        System.out.println("Este componente realiza la medición de un conjunto de posiciones del problema de las 8 reinas.");
        System.out.println("______________________________________________________________________________________________________________");
        System.out.println("Sintaxis:                                                      ");
        System.out.println("-CONFIG:str | (-FWORK:str -FACTG:str -FNEXTG:str -FPOP:str -FFA:str -FDISTANCES:str [-SEP:str])");
        System.out.println("                                                               ");
        System.out.println("-CONFIG     Archivo de texto que define los parámetros descritos debajo          ");
        System.out.println("-FWORK     Define la ruta y nombre del directorio de trabajo, esta ruta es la raíz por el cual va a realizar");
        System.out.println("                     operaciones de creación de carpetas y archivos derivados de las carpetas.");
        System.out.println("-FNEXTG     Nombre del directorio de la generación para calcular sus valores de aptitud");
        System.out.println("-FPOP         Nombre del directorio que almacena la codificación genética de individuos, este directorio");
        System.out.println("                     debe ser común para cualquier generación para calcular su FA");
        System.out.println("-FFA             Nombre del directorio donde se almacenarán los resultados de evaluar los individios ");
        System.out.println("                     especificados en -FPOP de -FNEXT");
        System.out.println("______________________________________________________________________________________________________________");
    }

    /**
     * Evaluar Método que evalúa los individuos
     *
     * @param P Es el arreglo que define las posiciones de las reinas en el
     * tablero
     * @return el numero de ataques
     */
    public float Evaluar(float[] P) {
        float ataques = 0.0F;
        int n = P.length;
        for (int i = 0; i < n; i++) {
            int min = (int) Math.min(i, P[i]);
            int x = i - min;
            int y = (int) (P[i] - min);
            while (x < n && y < n) {
                if (x != i && P[x] == y) {
                    ataques++;
                }
                x++;
                y++;
            }
            x = n;
            y = (int) (P[i] - n + i);
            while (x > 0 && y < n - 1) {
                x--;
                y++;
                if (x != i && P[x] == y) {
                    ataques++;
                }
            }
        }
        return ataques / 2;
    }

}
