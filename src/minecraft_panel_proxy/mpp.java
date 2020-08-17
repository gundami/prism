package minecraft_panel_proxy;

import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class mpp {

	public static void main(String[] args) throws IOException {		
		ExecutorService trd = Executors.newFixedThreadPool(4);
		String [] haproxy = {"/bin/bash","/home/container/haproxy/run.sh"};
		String [] nginx = {"/bin/bash","/home/container/nginx/reload.sh"};
		foldlistener.addListener("./haproxy/",haproxy,".cfg");
		foldlistener.addListener("./nginx/", nginx, ".conf");
		trd.execute(run());
		trd.execute(frps());
		trd.execute(nginx());
	}
	public static Runnable run() throws IOException {
		return new Runnable() {
            @Override
            public void run() {
				try {
					new ProcessExecutor().command("/bin/bash", "./haproxy/run.sh")
					  .redirectOutput(Slf4jStream.of("haproxy").asInfo()).execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidExitValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
            }
        };
	}
	    public static Runnable frps() throws IOException {
			return new Runnable() {
	            @Override
	            public void run() {
					try {
						new ProcessExecutor().command("./haproxy/frps", "-c","./haproxy/frps.ini")
						  .redirectOutput(Slf4jStream.of("frps").asInfo()).execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidExitValueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	            }
	        };
		}
	    public static Runnable nginx() throws IOException {
			return new Runnable() {
	            @Override
	            public void run() {
					try {
						new ProcessExecutor().command("/bin/bash","./nginx/run.sh")
						  .redirectOutput(Slf4jStream.of("nginx").asInfo()).execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidExitValueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	            }
	        };
		}
	    
	    
	

}

