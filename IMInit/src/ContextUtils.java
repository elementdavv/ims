  
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ContextUtils {  
    
    public Map<String, String> load(String xmlFileName) {  
        Map<String, String> config = new LinkedHashMap<String, String>();
        
        try {  
            Document doc = Dom4jXmlUtils.getInstance().load(ContextUtils.class.getResource("/").getPath() + xmlFileName);  
            Element root = doc.getRootElement();
             
            @SuppressWarnings("unchecked")
			Iterator<Element> it = root.elementIterator();
            
            while(it.hasNext()) {
            	Element e = (Element)it.next();
            	String pid = e.attributeValue("PID");
            	String id = e.attributeValue("ID");
            	String key = id;
            	
            	if(pid != null) {
            		key = id + "_" + pid;
            	}
            	String xmString = new String(e.getStringValue());  
            	config.put(key, xmString);
            }
            
        } catch(Exception e) {  
            e.printStackTrace();  
        }  
       
        return config;
    }  
    
}  