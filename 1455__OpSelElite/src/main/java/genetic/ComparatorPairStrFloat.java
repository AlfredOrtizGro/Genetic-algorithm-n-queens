/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;

import java.util.Comparator;

/**
 *
 * @author RENE
 */

public class ComparatorPairStrFloat implements Comparator <PairStringFloat> {
    
    public int compare(PairStringFloat o1, PairStringFloat o2) {
        if (o1.FloatValue>o2.FloatValue)  return -1;
        if (o1.FloatValue<o2.FloatValue)  return +1;
        return 0;

    }



}
