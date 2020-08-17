package minecraft_panel_proxy;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class foldlistener {
	 private static ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
	    private WatchService ws;
	    private String listenerPath;
	    private String[] cmd;
	    private String type;
	    private foldlistener(String path,String[] command,String type) {
	        try {
	        	this.cmd = command;
	        	this.type = type;
	            ws = FileSystems.getDefault().newWatchService();
	            this.listenerPath = path;
	            start();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public foldlistener() {
			// TODO Auto-generated constructor stub
		}

		private void start() {
	        fixedThreadPool.execute(new Listner(ws,this.listenerPath,cmd,type));
	    }

	    public static void addListener(String path,String[] command,String type) throws IOException {
	        foldlistener resourceListener = new foldlistener(path,command,type);
	        Path p = Paths.get(path);
	        p.register(resourceListener.ws,
	            StandardWatchEventKinds.ENTRY_MODIFY,
	            StandardWatchEventKinds.ENTRY_DELETE,
	            StandardWatchEventKinds.ENTRY_CREATE);
	    }


}
class Listner implements Runnable {
    private WatchService service;
    private String rootPath;
    private String [] cmd;
    private String type;
    public Listner(WatchService service,String rootPath,String[] command,String type) {
        this.service = service;
        this.rootPath = rootPath;
        this.cmd = command;
        this.type = type;
        
    }

    public void run() {
        try {
            while(true){
                WatchKey watchKey = service.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for(WatchEvent<?> event : watchEvents){
                    if(event.context().toString().endsWith(type))
                    try {
                        
                       // String[] cmd = { "/bin/bash","/home/container/haproxy/run.sh" };
                        Runtime.getRuntime().exec(cmd);
                        System.out.println("检测到"+rootPath+"文件更新，执行"+cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            System.out.println("114514");
            try {
                service.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
}