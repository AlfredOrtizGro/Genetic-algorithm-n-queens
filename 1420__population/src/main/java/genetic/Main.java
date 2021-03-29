/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic;

/**
 * Función principal donde se crea la población inicial
 * @author RENE
 */
public class Main {

    /**
     * @param args Argumentos pasados de la línea de comandos
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Population Generacion = new Population(args);
        Generacion.CrearPoblacion();
    }

}
