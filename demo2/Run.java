
package Signature_test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Run{

    public static void main(String[] args) {
        String cmd="/media/yujie/项目/MYJAVA/Signature/src/Signature_test/run.sh";
        Run run=new Run();
        run.shell(cmd);

    }
    public static void shell(String cmd) {
        String[] cmds = {"/bin/sh", "-c", cmd};
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmds);
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "Error");
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "Output");
            errorGobbler.start();
            outputGobbler.start();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class StreamGobbler extends Thread {

    InputStream is;
    String type;

    public StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (type.equals("Error")) {
                    System.out.println("Error   :" + line);
                } else {
                    System.out.println("Success:" + line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}