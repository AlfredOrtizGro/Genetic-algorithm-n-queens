package uploadfilesdpx;

import java.io.IOException;

/**
 *
 * @author Jonathan Rojas Simón <ids_jonathan_rojas@hotmail.com>
 */
public class UploadFilesDpx
{

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        UpFilesDpx objUpFilesDpx = new UpFilesDpx(args);
        objUpFilesDpx.run();
    }
    
}
