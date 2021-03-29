/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tournament;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import libs.IOBinFile;
import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;

/**
 *
 * @author Christian Millán <ceduardo.millan@gmail.com>
 */
public class TournamentMain {

    String fWork;
    String fActG;
    String fFA;
    String fSelect;
    int ni;
    int kTournament;
    int pTournament;
    boolean oneAleo;
    int thSelect;
    String optiMode;
    boolean log;
    String logFile;

    String configFile;
    ManejoArchivos iO;
    IOBinFile binFile;
    MyListArgs param;
    StringBuilder sb;

    public TournamentMain(String[] args) {
        iO = new ManejoArchivos();
        binFile = new IOBinFile();
        param = new MyListArgs(args);
        sb = new StringBuilder();
        configFile = param.ValueArgsAsString("-CONFIG", "");
        if (!configFile.equals("")) {
            param.AddArgsFromFile(configFile);
        }

        String sintaxis = "-FWORK:str -FACTG:str -FFA:str -NI:int -FSELECT:str "
                + "[-KTOURN:int] [-PTOURN:int] [-ONEALEO:ON:OFF] [-THSELECT:int] "
                + "[-OPTIMOD:MIN:MAX] [-LOG:ON:OFF] [-FLOG:str]";
        MySintaxis review = new MySintaxis(sintaxis, param);

        fWork = param.ValueArgsAsString("-FWORK", "");
        fActG = param.ValueArgsAsString("-FACTG", "");
        fFA = param.ValueArgsAsString("-FFA", "");
        ni = param.ValueArgsAsInteger("-NI", 1);
        fSelect = param.ValueArgsAsString("-FSELECT", "Parents.txt");
        kTournament = param.ValueArgsAsInteger("-KTOURN", 2);
        pTournament = param.ValueArgsAsInteger("-PTOURN", 100);
        String oAleo = param.ValueArgsAsString("-ONEALEO", "OFF");
        oneAleo = ("ON".equals(oAleo));
        thSelect = param.ValueArgsAsInteger("-THSELECT", 1);
        optiMode = param.ValueArgsAsString("-OPTIMOD", "MAX");
        logFile = param.ValueArgsAsString("-FLOG", "log.txt");
        String logString = param.ValueArgsAsString("-LOG", "ON");
        log = ("ON".equals(logString));
    }

    public String getFathersLog() {
        String fathers = "";
        String[] Folders = iO.List_Carpetas(fWork);
        for (String subFolders : Folders) {
            if (subFolders.toUpperCase().startsWith(fActG)) {
                fathers = iO.AddToPath(iO.AddToPath(fWork, subFolders), logFile);
                break;
            }
        }
        return fathers;
    }

    public void run() {
        if (thSelect == 0) {
            thSelect = Runtime.getRuntime().availableProcessors() - 1;
        }
        if (thSelect == 1) {
            Tournament tournament = new Tournament(this);
            if ("MAX".equals(optiMode)) {
                tournament.setOptimizationBehavior(new OptiMax());
                if (log) {
                    sb.append("Operador TORNEO en modo de MAXIMIZACION\n\n");
                }
            } else {
                tournament.setOptimizationBehavior(new OptiMin());
                if (log) {
                    sb.append("Operador TORNEO en modo de MINIMIZACION\n\n");
                }
            }
            tournament.tournament();
            sb.append(tournament.sbT);
        } else {
            OptimizationBehavior ob;
            if ("MAX".equals(optiMode)) {
                ob = new OptiMax();
                if (log) {
                    sb.append("Operador TORNEO en modo de MAXIMIZACION\n\n");
                }
            } else {
                ob = new OptiMin();
                if (log) {
                    sb.append("Operador TORNEO en modo de MINIMIZACION\n\n");
                }
            }
            ArrayList<TournamentRunnable> tournamentsR = new ArrayList();
            for (int i = 0; i < (ni / 2 + 1); i++) {
                tournamentsR.add(new TournamentRunnable(this, i));
                tournamentsR.get(i).setOptimizationBehavior(ob);
            }
            if (!iO.Open_Write_File(tournamentsR.get(0).getFathers())) {
                System.err.println("Torneo: error al escribir el archivo:" + tournamentsR.get(0).getFathers());
                System.exit(-1);
            }
            ExecutorService executor = Executors.newFixedThreadPool(thSelect);
            for (int i = 0; i < tournamentsR.size(); i++) {
                Runnable arena = new Arena(tournamentsR.get(i));
                executor.execute(arena);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                //Esperar a que terminen de ejecutarse todos los procesos
                //para pasar a la siguente instrucción
            }
            for (int i = 0; i < tournamentsR.size(); i++) {
                iO.Write_in_File(tournamentsR.get(i).getNameWinner());
                this.sb.append(tournamentsR.get(i).sbT);
            }
            iO.Close_Write_File();
        }
        if (log) {
            iO.Write_String_File(this.getFathersLog(), sb.toString());
        }
    }

    public static void main(String[] args) {
        TournamentMain tm;
        tm = new TournamentMain(args);
        tm.run();
    }
}
