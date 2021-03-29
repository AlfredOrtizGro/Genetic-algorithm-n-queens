package uploadfilesdpx;

import libs.ManejoArchivos;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Jonathan Rojas Simón <ids_jonathan_rojas@hotmail.com>
 */
public class RecorreDirectorio
{
    ArrayList<File> objArrayListArchivos;
    ManejoArchivos IO = new ManejoArchivos();

    public RecorreDirectorio()
    {
        this.objArrayListArchivos = new ArrayList<>();
    }
    
    public void recorreDirectorios(String lSDirectorio, String lSExtension)
    {
        File objFileDirectorio = new File(lSDirectorio.replace("\\","/"));
        String[] objFiles = objFileDirectorio.list();
        
        for (int i = 0; i < objFiles.length; i++)
        {
            File objFile = new File(IO.AddToPath(lSDirectorio, objFiles[i]));
            if (objFile.isDirectory())
            {
                this.recorreDirectorios(IO.AddToPath(objFileDirectorio.getAbsolutePath(), objFile.getName()), lSExtension);
            } else
            {
                if (objFile.isFile()
                        && (objFile.getName().endsWith(lSExtension))
                        && (!objFile.getName().startsWith(".") && !objFile.getName().startsWith("_")))
                {
                    objArrayListArchivos.add(objFile);
                }
            }
        }
    }
}
