package genetic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Executable;

public class CONSOLE {
    boolean show;
    public CONSOLE(boolean show) {
        this.show = show;
    }

    public void Execute(String op, String config) {
        if(show)
        System.out.println("java -jar "+op +" "+config);
        try {
            Process proc = Runtime.getRuntime().exec("java -jar "+op+" "+config);
            proc.waitFor();
            //Thread.sleep(10);
            // Then retreive the process output
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();

            byte b[] = new byte[in.available()];
            in.read(b, 0, b.length);


            byte c[] = new byte[err.available()];
            err.read(c, 0, c.length);
            if(show) {
                String bb = new String(b);
                String cc = new String(c);
                if(!bb.isEmpty())
                    System.out.println(bb);
                if(!cc.isEmpty())
                    System.out.println(cc);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
