import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Connecton {

	private String host;
	private int port;
	private Socket socket = null;
	private Request request;
	
	public Connecton() {	}
	public Connecton(Request request){
		this.host = request.getHost();
		this.port = request.getPort();
		this.request = request;
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(){
		String requestLine = request.getMethod().toString() + " " + request.getPath() + " HTTP/1.0\r\n";
		String headerLine1 = "Host: " + request.getHost() + "\r\n";

	    StringBuffer requestBuilder = new StringBuffer(requestLine);
	    requestBuilder.append(headerLine1);
	    for(String key : request.getHeader().keySet()){
	        requestBuilder.append(key).append(" :").append(request.getHeader().get(key)).append("\r\n");
	    } 
	    if(request.getMethod().equals(Method.GET)){
	    		requestBuilder.append("\r\n");
	    } else {
			String contentLength = "Content-Length: " + String.valueOf(request.getData().length())+ "\r\n";
			requestBuilder.append(contentLength);
	    		requestBuilder.append("\r\n");
	    		requestBuilder.append(request.getData() + "\r\n");
		}
	    
		System.out.println(requestBuilder.toString());
	    try{
	        OutputStream out = socket.getOutputStream();
	        out.write(requestBuilder.toString().getBytes());
	    } catch (IOException e) {
	   		e.printStackTrace();
	    }
	}
	
	public void receive(){
		InputStream in;
		String data;
		String[] headerAndBody = null;
		try {
			in = socket.getInputStream();
			ByteArrayOutputStream response = new ByteArrayOutputStream();
	        byte[] block = new byte[1024];
	        while (in.read(block) != -1) {
	        		response.write(block);
	        }
	        data = new String(block);
	        headerAndBody = data.split("\r\n\r\n");
	        if(request.getIsV()){
	        		System.out.println(headerAndBody[0] + "\n");
	        }
	        if(headerAndBody.length == 2)
	        		System.out.println(headerAndBody[1] + "\n");
	        
	        String[] body = headerAndBody[0].split(" ");
	       
	        if(body[1].equals("301") || body[1].equals("302")){
        			socket.close();
	        		int indexOfLocaton = headerAndBody[0].indexOf("Location:");
	        		int pathStart = headerAndBody[0].indexOf(":", indexOfLocaton) + 2;
	        		int pathEnd = headerAndBody[0].indexOf("\r\n",pathStart);
	        		request.setPath(headerAndBody[0].substring(pathStart, pathEnd).trim());
	        		Connecton newConnection = new Connecton(request);
	        		newConnection.send();
	        		newConnection.receive();
	        		//newConnection.socket.close();
	        } else if(request.getWriteFile() && !headerAndBody[1].equals("")){
	        		writeFile(request.getFileName(),headerAndBody[1]);
	        		System.out.println("Body of the reponse is written to file: " + request.getFileName() + "\r\n");
	        }
	        socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeFile(String fileName,String data) throws IOException {
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		writer.write(data);
		writer.close();
	}
}
