/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tournament;

import java.util.Random;
import libs.IOBinFile;

/**
 *
 * @author Christian
 */
public class OptiMax implements OptimizationBehavior {

    @Override
    public int getOpti(Tournament t) {
        Random r = new Random();
        int[] challengers;
        if (t.noMatch) {
            challengers = t.getChallengers(1);
        } else {
            challengers = t.getChallengers(t.kTournament);
        }
        int positionBetter = challengers[0];
        int iWin = 0;
        t.getNameChallengers(challengers, 0);
        float better = IOBinFile.ReadBinFloatFileIEEE754(t.chromosomes[challengers[0]])[0];
        t.sbT.append("\t  fitness: ").append(better).append("\n");
        if (r.nextInt(100) >= t.pTournament) { //Torneo probabilistico.
            for (int i = 1; i < challengers.length; i++) {
                t.getNameChallengers(challengers, i);
                float challenger = IOBinFile.ReadBinFloatFileIEEE754(t.chromosomes[challengers[i]])[0];
                t.sbT.append("\t  fitness: ").append(challenger).append("\n");
                if (better > challenger) {
                    better = challenger;
                    iWin = i;
                    positionBetter = challengers[i];
                }
            }
        } else { //Torneo deterministico.
            for (int i = 1; i < challengers.length; i++) {
                t.getNameChallengers(challengers, i);
                float challenger = IOBinFile.ReadBinFloatFileIEEE754(t.chromosomes[challengers[i]])[0];
                t.sbT.append("\t  fitness: ").append(challenger).append("\n");
                if (better < challenger) {
                    better = challenger;
                    iWin = i;
                    positionBetter = challengers[i];
                }
            }
        }
        t.sbT.append("Ganador :\t").append(iWin).append("\n\n");
        return positionBetter;
    }

}
