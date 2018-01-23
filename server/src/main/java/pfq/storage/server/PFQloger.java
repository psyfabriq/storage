package pfq.storage.server;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PFQloger {
    public static Logger getLogger(Class clazz, Level level){
     Logger logger = Logger.getLogger(clazz); 
     logger.setLevel(level);
     return logger;
    }
}
