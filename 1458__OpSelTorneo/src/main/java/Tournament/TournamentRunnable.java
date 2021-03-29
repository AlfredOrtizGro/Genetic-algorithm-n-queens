/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tournament;

import java.io.File;

/**
 *
 * @author Christian
 */
public class TournamentRunnable extends Tournament implements Runnable {

    private int numTournament;
    private String nameWinner;
//    private StringBuilder sb;

    public TournamentRunnable(String fWork, String fActG, String fFA, int ni,
            String fSelect, int kTournament, int pTournament, boolean oneAleo,
            int thSelect, String optiMode, boolean log, String logFile) {
        super(fWork, fActG, fFA, ni, fSelect, kTournament, pTournament, oneAleo,
                thSelect, optiMode, log, logFile);
    }

    public TournamentRunnable(TournamentMain tMain, int i) {
        super(tMain);
        this.numTournament = i;
    }

    /**
     * Se realiza un torneo
     *
     */
    @Override
    public void tournament() {
        sbT.append("Torneo número:\t").append(this.getNumTournament() + 1).append("\n");
        if (oneAleo) {
            noMatch = (this.getNumTournament() % 2 == 1);
        }
        int champion = getBetteChallengerr();
        File trophy = new File(chromosomes[champion]);
        setNameWinner(trophy.getName());
    }

    /**
     * @return the numTournament
     */
    public int getNumTournament() {
        return numTournament;
    }

    /**
     * @param numTournament the numTournament to set
     */
    public void setNumTournament(int numTournament) {
        this.numTournament = numTournament;
    }

    /**
     * @return the nameWinner
     */
    public String getNameWinner() {
        return nameWinner;
    }

    /**
     * @param nameWinner the nameWinner to set
     */
    public void setNameWinner(String nameWinner) {
        this.nameWinner = nameWinner;
    }
}
