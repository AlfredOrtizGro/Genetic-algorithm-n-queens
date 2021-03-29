package genetic;

import libs.ManejoER;

import java.util.TreeMap;

public class BaseIncrementos {
    String Cadena;
    StringBuilder objBuilder = new StringBuilder();
    String gSSalto = "\r\n";
    int tamanioIndividuo;

    /**
     * Base
     * Constructor que valida la secuencia de caracteres de las bases por
     * medio de una expresion regular
     * @param lSCadenaGral Es la cadena donde contiene las codificaciones
     */
    public BaseIncrementos(String lSCadenaGral, int tamanioIndividuo)
    {
        this.Cadena = lSCadenaGral;
        this.tamanioIndividuo = tamanioIndividuo;

        objBuilder.append("La expresión es: "+ gSSalto + lSCadenaGral + gSSalto);
        ManejoER objER_1 = new ManejoER("(?<=INCREMENTS:)\\d+([;]\\d+)*");

        String lASContainer = "";
        int lEBase = 0;

        if (objER_1.ExistER(Cadena))
        {
            lASContainer = objER_1.Grupo(0);
        }
        String[] bases = lASContainer.split(";");

        if(tamanioIndividuo%bases.length!=0){
            int repeticiones = tamanioIndividuo/bases.length;
            objBuilder.append("El tamaño del individuo debe ser igual a "+bases.length*repeticiones + gSSalto);
            System.exit(-1);
        }
        if (!objER_1.ExistER(Cadena))
        {
            objBuilder.append("La expresión introducida no es la correcta, revisar la expresión de bases" + gSSalto);
            System.exit(-1);
        }
    }

    /**
     * getMapBases
     * @return
     */
    public TreeMap<Integer, Integer> getMapBases()
    {
        TreeMap<Integer, Integer> objTreeMapStructure = new TreeMap<>();
        ManejoER objER_1 = new ManejoER("(?<=INCREMENTS:)\\d+([;]\\d+)*");
        String lASContainer = "";
        int lEBase = 0;

        if (objER_1.ExistER(Cadena))
        {
            lASContainer = objER_1.Grupo(0);
        }

        String[] bases = lASContainer.split(";");
        int[] basesInt = new int[bases.length];
        for (int i = 0; i < basesInt.length; i++) {
            basesInt[i] = Integer.parseInt(bases[i]);
        }

        for (int i = 0, indexBase = 0; i < this.tamanioIndividuo; i++, indexBase++) {
            if(indexBase==bases.length)
                indexBase = 0;
            objTreeMapStructure.put(i, basesInt[indexBase]);
        }

        return objTreeMapStructure;
    }
}
