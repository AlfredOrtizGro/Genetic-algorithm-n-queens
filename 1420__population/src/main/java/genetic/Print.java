package genetic;

import libs.ManejoArchivos;

/**
 * Interfaz que muestra una salida personalizada en un archivo de reporte. Si se
 * desean agregar más etiquetas solo es necesario ir construyendo la condición
 * con la nueva etiqueta e ir agregando con append la salida que se quiere
 * mostrar
 * @author Gabriela Villada R. inggaby.vr@gmail.com
 */
public class Print
{

    private Population op;
    ManejoArchivos ma = new ManejoArchivos();
    String gSSalto = "\r\n";

    public Print(Population run)
    {
        this.op = run;
    }

    public void OutPutHead()
    {
        StringBuilder Out = new StringBuilder();
        Out.append("*****************************  REPORTE  **********************************" + gSSalto);
        Out.append(Resumen());
        Out.append("***************************************************************************" + gSSalto);
        //Escribimos en el archivo de salida
        op.IO.Write_String_File(op.NameReport, Out.toString());

    }

    public StringBuilder Resumen()
    {
        StringBuilder Out = new StringBuilder();
        Out.append("Ruta de trabajo (-FWORK): " + op.FWORK + gSSalto);
        Out.append("Path de individuos: " + gSSalto);
        Out.append("-FWORK + -FNEXT + -FPOP: " + op.IO.AddToPath(op.IO.AddToPath(op.FWORK, op.FNEXTG), op.FPOP) + gSSalto);
        Out.append("Numero de individuos generados (-NI): " + op.NumInd + gSSalto);
        Out.append("Nombres de individuos generados: " + gSSalto);
        Out.append("(-PRE + -INF + -POS): " + op.Prefijo + "(" + op.Infijo + "-" + (op.NumInd+op.Infijo-1) + ")" + op.Postfijo + gSSalto);
        Out.append("Representación (-REPEAT): " + op.Repeat + " -> ");
        if (op.Repeat)
        {
            Out.append("Combinaciones " + gSSalto);
        } else
        {
            Out.append("Permutaciones " + gSSalto);
        }
        if (op.MinGenInt == 0 && op.MaxGenInt == 0)
        {
            Out.append("Población inicial con genes de base N" + gSSalto);
            Out.append(op.objBase.objBuilder + gSSalto);
        } else
        {
            Out.append("Valor mínimo permitido en los genes (-VMININT): " + op.MinGenInt + gSSalto);
            int lEMax = op.MaxGenInt;
            if (!op.Repeat)
                lEMax--;
            Out.append("Valor máximo permitido en los genes (-VMAXINT): " + lEMax + gSSalto);
            if (!(op.NGenMin == -1 && op.NGenMax == -1))
            {
                Out.append("Número de genes mínimo (-NGENMIN): " + op.NGenMin + gSSalto);
                Out.append("Número de genes máximo (-NGENMAX): " + op.NGenMax + gSSalto);
                if (op.Precision > 0)
                {
                    Out.append("Precisión de valores (-PRECISION): " + op.Precision + gSSalto);
                }
            } else
            {
                Out.append("Número de genes (-NGEN): " + op.NumGen + gSSalto);
            }
        }
        return Out;
    }

}
