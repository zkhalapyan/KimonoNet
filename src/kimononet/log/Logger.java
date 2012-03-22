package kimononet.log;

public class Logger {

	private static boolean infoOn = true;
	private static boolean debugOn = false;
	private static boolean errorOn = false;
	
	public static final void log(String message){
		log(message, LogType.INFO);
	}
	
	public static final void info(String message){
		log(message, LogType.INFO);
	}
	
	public static final void debug(String message){
		log(message, LogType.DEBUG);
	}
	
	public static final void error(String message){
		log(message, LogType.ERROR);
	}
	
	public static final void log(String message, LogType type){
		switch(type){
		case INFO:
			
			if(infoOn){
				sysOut(message);
			}
			
			break;
			
		case DEBUG:
			
			if(debugOn){
				sysOut(message);
			}
			
			break;
			
		case ERROR:
			
			if(errorOn){
				sysOut(message);
			}
			
			break;
		}
	}
	
	private static final void sysOut(String message){
		System.out.println(message);
	}
	
	public static final void setInfo(boolean infoOn){
		Logger.infoOn = infoOn;
	}
	
	public static final void setDebug(boolean debugOn){
		Logger.debugOn = debugOn;
	}
	
	public static final void setError(boolean errorOn){
		Logger.errorOn = errorOn;
	}
}
