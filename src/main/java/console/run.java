package console;

import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class run {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Terminal terminal = TerminalBuilder.builder()
                .system(false)
                .streams(System.in, System.out)
                .build();
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();
        HashMap<String,String> CmdMap = new utilities().readConfig();
        String prompt = "PrismConsole> ";
        System.out.println("PrismConsole--A Shell Executor");
        System.out.println("Powered by Gundami");
        System.out.println("File Location: " + System.getProperty("user.dir"));
        new utilities().permissionFix(System.getProperty("user.dir"));
        new utilities().exec("AutoStart",CmdMap);
        while (true) {
            String line;
            try {
                line = lineReader.readLine(prompt);
                if(new utilities().exec(line,CmdMap)&&line!="AutoStart"){
                    System.out.println("OK");
                }else {
                    System.out.println("Error");
                }

            } catch (UserInterruptException e) {
                // Do nothing
            } catch (EndOfFileException e) {
                System.out.println("\nBye.");
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
