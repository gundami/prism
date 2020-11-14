package minecraft_panel_proxy;

import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;




public class mpp {
	private boolean modeProgressOnly = false;
    private boolean modeQuiet = false;
    private boolean modeServer = false;
    private String serverAddress;
    private int serverPort = -1;
    private String[] ignorePatterns;
    public static ResourceBundle strings;
    public mpp() {
    	
    }
	public static void main(String[] args) throws IOException {	

		ExecutorService trd = Executors.newFixedThreadPool(4);
		String [] haproxy = {"/bin/bash","./scripts/run-haproxy.sh"};
		String [] nginx = {"/bin/bash","./scripts/reload-nginx.sh"};    
		foldlistener.addListener("./config/",haproxy,".cfg");
		foldlistener.addListener("./config/", nginx, ".conf");
		trd.execute(run());
		trd.execute(frps());
		trd.execute(nginx());
		trd.execute(customscript());

		consolelistener cs = new consolelistener(new Scanner(System.in), new consolelistener.Action() {

            public void act(String msg) {
            	System.out.println("Console:"+msg);
            	if(msg.equals(new String("nginx"))){
            		try {
            			System.out.println("Reloading Nginx...");
						trd.execute(reloadnginx());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	if(msg.equals(new String("haproxy"))){
            		try {
            			System.out.println("Reloading Haproxy...");
						trd.execute(run());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
                
            }
        });
		cs.listenInNewThread();
		
	}
	private static Runnable run() throws IOException {
		return new Runnable() {
            @Override
            public void run() {
				try {
					new ProcessExecutor().command("/bin/bash","-c", "./scripts/run-haproxy.sh")
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
	    private static Runnable frps() throws IOException {
			return new Runnable() {
	            @Override
	            public void run() {
					try {
						new ProcessExecutor().command("./bin/frps", "-c","./config/frps.ini")
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
	    private static Runnable nginx() throws IOException {
			return new Runnable() {
	            @Override
	            public void run() {
					try {
						new ProcessExecutor().command("/bin/bash","-c","./scripts/run-nginx.sh")
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
	    private static Runnable customscript() throws IOException {
			return new Runnable() {
	            @Override
	            public void run() {
					try {
						new ProcessExecutor().command("/bin/bash","-c","./scripts/customscript.sh")
						  .redirectOutput(Slf4jStream.of("customscript").asInfo()).execute();
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
	    private static Runnable reloadnginx() throws IOException {
			return new Runnable() {
	            @Override
	            public void run() {
					try {
						new ProcessExecutor().command("/bin/bash","-c","./scripts/reload-nginx.sh")
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

