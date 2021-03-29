/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RENE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GenGen GenGral = new GenGen(args);
        GenGral.Run();
    }

}
