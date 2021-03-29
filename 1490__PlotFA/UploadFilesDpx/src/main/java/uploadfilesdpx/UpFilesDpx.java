package uploadfilesdpx;

import libs.ManejoArchivos;
import libs.MyListArgs;
import libs.MySintaxis;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.RateLimitException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.GetMetadataErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jonathan Rojas Simón <ids_jonathan_rojas@hotmail.com>
 */
public class UpFilesDpx
{
    String gSDir, gSToken, gSSalidaArchivos, gSExtDir, gSFileURL;
    Calendar gSDate;

    public UpFilesDpx(String[] args)
    {
        MyListArgs objMyListArgsParam = new MyListArgs(args);
        String lSConfigFile = objMyListArgsParam.ValueArgsAsString("-CONFIG", "");//Archivo donde se especifican los parametros necesarios para este componente
        if (!lSConfigFile.equals(""))
        {
            objMyListArgsParam.AddArgsFromFile(lSConfigFile);
        }
        
        String lSSintaxis = "-DIR:str [-EXT:str] -TOKEN:str [-UFDATE:str] [-OUT:str] -FILEURL:str";
        MySintaxis objMySintaxis = new MySintaxis(lSSintaxis, objMyListArgsParam);
        
        gSDir = objMyListArgsParam.ValueArgsAsString("-DIR", "").replace("\\", "/"); 
        gSExtDir = objMyListArgsParam.ValueArgsAsString("-EXT", ".png");
        gSToken = objMyListArgsParam.ValueArgsAsString("-TOKEN", ""); 
        gSDate = objMyListArgsParam.ValueArgsAsDate("-UFDATE",""); 
        gSSalidaArchivos = objMyListArgsParam.ValueArgsAsString("-OUT", "").replace("\\", "/");
        gSFileURL = objMyListArgsParam.ValueArgsAsString("-FILEURL", "");
        
        
       
    }

    public void run() throws IOException
    {
        try
        {
            DbxRequestConfig objDbxRequestConfig = new DbxRequestConfig("");
            DbxClientV2 objDbxClientV2Client = new DbxClientV2(objDbxRequestConfig, gSToken);
            
            RecorreDirectorio objRecorreDirectorio = new RecorreDirectorio();
            objRecorreDirectorio.recorreDirectorios(gSDir, gSExtDir);
            
            for (int i = 0; i < objRecorreDirectorio.objArrayListArchivos.size(); i++)
            {
                InputStream in = new FileInputStream(objRecorreDirectorio.objArrayListArchivos.get(i));
                String lSDpxPath = "/" + gSSalidaArchivos 
                            + "-" + gSDate.get(Calendar.DAY_OF_MONTH) 
                            + "-" + getMes(gSDate)
                            + "-" + gSDate.get(Calendar.YEAR) + "/"
                            + objRecorreDirectorio.objArrayListArchivos.get(i).getName();
                try
                {
                    objDbxClientV2Client.files().getMetadata(lSDpxPath);
                    objDbxClientV2Client.files().deleteV2(lSDpxPath);
                } catch (GetMetadataErrorException | RateLimitException objGMex)
                {
//                    if (objGMex.errorValue.isPath() && objGMex.errorValue.getPathValue().isNotFound())
//                    {
//                        System.out.println("File: " + lSDpxPath + " is not found");
//                    }
                }

                objDbxClientV2Client.files().uploadBuilder(lSDpxPath).withMode(WriteMode.ADD).withClientModified(
                        new Date(objRecorreDirectorio.objArrayListArchivos.get(i).lastModified())).uploadAndFinish(in);

            }
            
            String lSOutputPath = "/" + gSSalidaArchivos
                            + "-" + gSDate.get(Calendar.DAY_OF_MONTH)
                            + "-" + getMes(gSDate)
                            + "-" + gSDate.get(Calendar.YEAR);
            
            String lSUrl = "";

            try
            {
                SharedLinkMetadata objLinkMetadataSlm = objDbxClientV2Client.sharing().createSharedLinkWithSettings(
                        lSOutputPath, SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC).build());
                
                lSUrl = objLinkMetadataSlm.getUrl();

            } catch (CreateSharedLinkWithSettingsErrorException objECSError)
            {
//                System.out.println(objECSError.getMessage());
                ListSharedLinksResult objLinksResult = objDbxClientV2Client.sharing()
                        .listSharedLinksBuilder()
                        .withPath(lSOutputPath)
                        .start();
                lSUrl = objLinksResult.getLinks().get(0).getUrl();
            }

            System.out.println(lSUrl);

            if(!gSSalidaArchivos.equals("")) {
                new ManejoArchivos().CrearCarpetas(gSSalidaArchivos);
                new ManejoArchivos().Write_String_File(gSFileURL, lSUrl);
            }
        } catch (DbxException ex)
        {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * getMes
     * Método que retorna el nombre del mes a partir del número de mes
     * @param objCalendar Contiene el formato de fecha utilizado
     * @return Una cadena que indica el nombre del mes
     */
    public String getMes(Calendar objCalendar)
    {
        switch (objCalendar.get(Calendar.MONTH))
        {
            case Calendar.JANUARY:
                return "Enero";
            case Calendar.FEBRUARY:
                return "Febrero";
            case Calendar.MARCH:
                return "Marzo";
            case Calendar.APRIL:
                return "Abril";
            case Calendar.MAY:
                return "Mayo";
            case Calendar.JUNE:
                return "Junio";
            case Calendar.JULY:
                return "Julio";
            case Calendar.AUGUST:
                return "Agosto";
            case Calendar.SEPTEMBER:
                return "Septiembre";
            case Calendar.OCTOBER:
                return "Octubre";
            case Calendar.NOVEMBER:
                return "Noviembre";
            case Calendar.DECEMBER:
                return "Diciembre";
            default:
                return null;
        }
    }
    

}
