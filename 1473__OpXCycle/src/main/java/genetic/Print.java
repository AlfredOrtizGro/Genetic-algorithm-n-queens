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

    private Cruza op;
    ManejoArchivos ma = new ManejoArchivos();
    String gSSalto = "\r\n";

    public Print(Cruza run)
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
        op.IO.Write_String_File(op.REPORTCX, Out.toString());

    }

    public StringBuilder Resumen()
    {
        StringBuilder Out = new StringBuilder();
        Out.append("Ruta de trabajo (-FWORK): " + op.FWORK + gSSalto);
        Out.append("Numero de individuos generados (-NI: " + op.NumInd + ") - (-NELITE: " + op.NELITE + ") = " + op.NHijos + gSSalto);
        
        Out.append("DETALLE" + gSSalto);
        Out.append(op.Report + gSSalto);
        
        return Out;
    }

}
