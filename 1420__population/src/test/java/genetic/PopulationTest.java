package genetic;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    @org.junit.jupiter.api.Test
    void crearHijoTomandoValoresEnVector() {
        float[] indexes = new float[]{1,4,5,6,8,99,2,33};
        Population.CrearHijoTomandoValoresEnVector(indexes, false);
        Population.CrearHijoTomandoValoresEnVector(indexes, true);
    }
}