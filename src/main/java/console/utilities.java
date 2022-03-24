package console;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class utilities {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    public HashMap<String,String> readConfig() throws IOException {
        HashMap<String,String> CmdMap = new HashMap<>();
        Properties config = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("config.properties"));
        config.load(bufferedReader);
        Enumeration en = config.propertyNames();
        while (en.hasMoreElements()){
            String key= (String) en.nextElement();
            String value=config.getProperty(key);
            CmdMap.put(key,value);
        }
        return CmdMap;
    }
    public boolean exec(String cmd,HashMap<String,String> CmdMap) throws IOException, InterruptedException, TimeoutException {
        if (CmdMap.containsKey(cmd)){
            String execCmd = CmdMap.get(cmd);
            cachedThreadPool.execute(() -> {
                try {
                    new ProcessExecutor().command(execCmd).redirectOutput(Slf4jStream.of(cmd).asInfo()).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
            return true;
        }else {
            return false;
        }
    }
    public void permissionFix(String path) throws IOException, InterruptedException {
        this.cachedThreadPool.execute(() -> {
            try {
                (new ProcessExecutor()).command(new String[] { "chmod", "-R", "777", path + "/bin" }).redirectOutput(Slf4jStream.of("Permission Fix").asInfo()).execute().getExitValue();
                (new ProcessExecutor()).command(new String[] { "chmod", "-R", "777", path + "/scripts" }).redirectOutput(Slf4jStream.of("Permission Fix").asInfo()).execute().getExitValue();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });
    }

}
