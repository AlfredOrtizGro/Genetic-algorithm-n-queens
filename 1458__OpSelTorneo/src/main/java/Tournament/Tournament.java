/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tournament;

import java.io.File;
import java.util.Random;
import libs.IOBinFile;
import libs.ManejoArchivos;

/**
 *
 * @author Christian
 */
public class Tournament {

    String fWork;
    String fActG;
    String fFA;
    String fSelect;
    int ni;
    int kTournament;
    int pTournament;
    boolean oneAleo;
    boolean noMatch;
    int thSelect;
    String optiMode;
    boolean log;
    String logFile;

    ManejoArchivos iO;
    IOBinFile binFile;
    String[] chromosomes;
    private OptimizationBehavior optimizationBehavior;
    public TournamentMain tMain;
    StringBuilder sbT;

    public Tournament(String fWork, String fActG, String fFA, int ni, String fSelect,
            int kTournament, int pTournament, boolean oneAleo, int thSelect, String optiMode,
            boolean log, String logFile) {
        this.fWork = fWork;
        this.fActG = fActG;
        this.fFA = fFA;
        this.ni = ni;
        this.fSelect = fSelect;
        this.kTournament = kTournament;
        this.oneAleo = oneAleo;
        this.pTournament = pTournament;
        this.thSelect = thSelect;
        this.optiMode = optiMode;
        this.log = log;
        this.logFile = logFile;

        iO = new ManejoArchivos();
        binFile = new IOBinFile();
        sbT = new StringBuilder();
        try {
            chromosomes = iO.List_Files_Abs(this.getEvalutationFathers(), ".bin");
        } catch (Exception e) {
            System.err.println("Ruleta: Error al intentar acceder la carpeta -FA"
                    + " de la generación actual..."
                    + "\n(verificar los paramétros -FWORK -FACTG)");
            System.exit(-1);
        }
    }

    public Tournament(TournamentMain tm) {
        this.tMain = tm;
        this.fWork = tm.fWork;
        this.fActG = tm.fActG;
        this.fFA = tm.fFA;
        this.ni = tm.ni;
        this.fSelect = tm.fSelect;
        this.kTournament = tm.kTournament;
        this.pTournament = tm.pTournament;
        this.oneAleo = tm.oneAleo;
        this.thSelect = tm.thSelect;
        this.optiMode = tm.optiMode;
        this.log = tm.log;
        this.logFile = tm.logFile;

        iO = new ManejoArchivos();
        binFile = new IOBinFile();
        sbT = new StringBuilder();
        try {
            chromosomes = iO.List_Files_Abs(this.getEvalutationFathers(), ".bin");
        } catch (Exception e) {
            System.err.println("Ruleta: Error al intentar acceder la carpeta -FA"
                    + " de la generación actual..."
                    + "\n(verificar los paramétros -FWORK -FACTG)");
            System.exit(-1);
        }
    }

    public String getFathers() {
        String fathers = "";
        String[] Folders = iO.List_Carpetas(fWork);
        for (String subFolders : Folders) {
            if (subFolders.toUpperCase().startsWith(fActG)) {
                fathers = iO.AddToPath(iO.AddToPath(fWork, subFolders), fSelect);
                break;
            }
        }
        return fathers;
    }

    /**
     * Obtiene la ruta de la carptera que contien los valores de la función de
     * aptitud de cada indiviudo
     *
     * @return ruta completa de los valores de aptitud
     */
    private String getEvalutationFathers() {
        String evaluation = "";
        String[] Folders = iO.List_Carpetas(fWork);
        for (String subFolders : Folders) {
            if (subFolders.toUpperCase().startsWith(fActG)) {
                evaluation = iO.AddToPath(iO.AddToPath(fWork, subFolders), fFA);
                break;
            }
        }
        return evaluation;
    }

    /**
     * Genera un número aleatorio para determinar al que indivudo participa
     *
     * @return
     */
    private int getChallengerRandom() {
        Random r = new Random();
        return r.nextInt(chromosomes.length);
    }

    public void getNameChallengers(int[] challengers, int i) {
        File challenger = new File(chromosomes[challengers[i]]);
        this.sbT.append("Competidor #\t").append(i).append("\t").append(challenger.getName());
    }

    /**
     * Se seleccionan k individuos
     *
     * @param k
     * @return lista de participantes
     */
    public int[] getChallengers(int k) {
        int[] challengers = new int[k];
        for (int i = 0; i < k; i++) {
            challengers[i] = this.getChallengerRandom();
        }
        return challengers;
    }

    public int performOptimzation() {
        return optimizationBehavior.getOpti(this);
    }

    /**
     * Se realiza el torneo entre los participantes y se retorna su posición en
     * el archivo chromosomes.
     *
     * @return posición en chromosomes del individuo ganador
     */
    public int getBetteChallengerr() {
        return this.performOptimzation();
    }

    /**
     * Devuelve el número de padres a seleccionar (50% + 1)
     *
     * @return número de individuos a seleccionar
     */
    private int getNumberOfFather() {
//        int sizePopulation = this.chromosomes.length;
        return ((this.ni * 50 / 100) + 1);
    }

    /**
     * Se realizan los torneo y se genera el archivo con los padres
     * seleccionados
     */
    public void tournament() {
        if (!iO.Open_Write_File(this.getFathers())) {
            System.err.println("Torneo: error al escribir el archivo:" + this.getFathers());
            System.exit(-1);
        }
        for (int i = 0; i < this.getNumberOfFather(); i++) {
            this.sbT.append("Torneo número:\t").append(i + 1).append("\n");
            if (oneAleo) {
                noMatch = (i % 2 == 1);
            }
            int champion = getBetteChallengerr();
            File trophy = new File(chromosomes[champion]);
            iO.Write_in_File(trophy.getName());
        }
        iO.Close_Write_File();
    }

    public void run() {
        this.tournament();
    }

    /**
     * @return the optimizationBehavior
     */
    public OptimizationBehavior getOptimizationBehavior() {
        return optimizationBehavior;
    }

    /**
     * @param optimizationBehavior the optimizationBehavior to set
     */
    public void setOptimizationBehavior(OptimizationBehavior optimizationBehavior) {
        this.optimizationBehavior = optimizationBehavior;
    }
}
