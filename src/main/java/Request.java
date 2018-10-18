import java.util.HashMap;

enum Method{GET,POST,HELP}

public class Request {
	private Method method;
	private Boolean isV;
	private Boolean isH;
	private Boolean writeFile;
	private String fileName;
	private String url;
	private String data = "";
	private int port;
	private String host;
	private String path;
	private HashMap<String, String> header;
	
	public Request(){
		isV = false;
		isH = false;
		writeFile = false;
		port = 80;
		header = new HashMap<String, String>();
		header.put("User-Agent", "Concordia-HTTP/1.0");
	}
	public Method getMethod(){
		return method;
	}
	
	public void setMethod(Method method){
		this.method = method;
	}
	public void setUrl(String url){
		this.url = url;
	}
	
	public int getPort(){
		return port;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public String getHost(){
		return host;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setV(Boolean isV){
		this.isV = isV;
	}
	public void setH(Boolean isH){
		this.isH = isH;
	}
	
	public boolean getIsV(){
		return isV;
	}
	
	public void setWriteFile(Boolean writeFile){
		this.writeFile = writeFile;
	}
	
	public Boolean getWriteFile(){
		return this.writeFile;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	public HashMap<String, String> getHeader(){
		return this.header;
	}
	
	public void setHeader(String key, String value){
		header.put(key, value);
	}
	
	public String getData(){
		return this.data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
}
