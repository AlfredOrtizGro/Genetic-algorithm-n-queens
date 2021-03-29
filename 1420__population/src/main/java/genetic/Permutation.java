package genetic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Permutation
 * Class to generate several permutations from a specified length
 * @author Jonathan Rojas Simón <ids_jonathan_rojas@hotmail.com>
 * @author Uwe Alex, Ulmenweg 22, 68167 Mannheim, Germany
 */
public class Permutation
{

    /** Array base of permutations */
    public float[] Al;
    /** The size of the int[] Array Al containing the permutation. */
    public int gESize, gEMin;
    
    /**
     * Constructs a Permutation through a specified range (lEMin - lEMax)<br>
     * <pre><b>Example</b>:
     *       Permutation p=new Permutation(4,8); // 4,5,6,7</pre>
     * @param lEMin The minimun number to initialize the permutation
     * @param lEMax The mamimum number to include in each permutation
     */
    public Permutation(int lEMin, int lEMax)
    {
        int n = lEMax+1 - lEMin;
        if (n < 1)
            throw new IllegalArgumentException ("Wrong n(<1): new Permutation(int n)");
        gESize = n;
        gEMin = lEMin;
        Al=new float[gESize];
        first();
    }

    public Permutation(float[] indexes)
    {
        List<Float> indexesShuffle = new ArrayList<>();
        for(float f : indexes)
            indexesShuffle.add(f);

        Collections.shuffle(indexesShuffle);
        Al = new float[indexesShuffle.size()];
        for (int i = 0; i < indexesShuffle.size(); i++) {
            Al[i] = indexesShuffle.get(i);
        }
    }
    
    /**
     * This method initializes the first permutation depending the size of vector Al
     */
    public void first()
    {
        int lEIncrem = gEMin;
        for (int i = 0; i < gESize; i++)
        {
            Al[i] = lEIncrem;
            lEIncrem++;
        }
    }
    
    /**
     * Swaps the elements at the specified positions in this permutation. (If
     * the specified positions are equal, invoking this method leaves the
     * permutation unchanged.)
     * @param i the index of one element to be swapped.
     * @param j the index of the other element to be swapped.
     * @throws IndexOutOfBoundsException if either <tt>i</tt> or <tt>j</tt>
     * is out of range (i &lt; 0 || i &gt;= permutation.size() || j &lt; 0 || j
     * &gt;= permutation.size()).
     */
    public void swap(int i,int j)
    {
        if (i!=j)
        {
            float h=Al[i];
            Al[i]=Al[j];
            Al[j]=h;
        }
    }
    
    /**
     * Randomly permutes the array of this permutation using a default source of
     * randomness. All permutations occur with approximately equal
     * likelihood.<p>
     *
     * The hedge "approximately" is used in the foregoing description because
     * default source of randomenss is only approximately an unbiased source of
     * independently chosen bits. If it were a perfect source of randomly chosen
     * bits, then the algorithm would choose permutations with perfect
     * uniformity.<p>
     *
     * This implementation traverses the permutation array forward, from the
     * first element up to the second last, repeatedly swapping a randomly
     * selected element into the "current position". Elements are randomly
     * selected from the portion of the permutation array that runs from the
     * current position to the last element, inclusive.<p>
     *
     * This method runs in linear time.
     */
    public void shuffle()
    {
        Random r=new Random();
        for (int i=0;i< gESize;i++) {
            swap(i,r.nextInt(gESize));
        }
    }

}
