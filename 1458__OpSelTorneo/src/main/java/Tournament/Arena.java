/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tournament;

/**
 *
 * @author Christian
 */
public class Arena implements Runnable {

    private Tournament tournament;

    public Arena(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public void run() {
        tournament.tournament();
    }

}
