import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class Connecton {

	private String host;
	private int port;
	private Request request;
	private SocketChannel clientSocketChannel;

	public Connecton() {	}
	public Connecton(Request request){


		this.host = request.getHost();
		this.port = request.getPort();
		this.request = request;

		try {
			InetSocketAddress serverAdress = new InetSocketAddress(host,port);
			clientSocketChannel = SocketChannel.open();
			clientSocketChannel.configureBlocking(false);
			clientSocketChannel.connect(serverAdress);
			if(clientSocketChannel.finishConnect()){
				System.out.println("Connect to server successfully...\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(){

		ByteBuffer buffer = ByteBuffer.allocate(1024);

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
	    	buffer.put(requestBuilder.toString().getBytes());
	    	buffer.flip();
	    	clientSocketChannel.write(buffer);
	    	buffer.clear();

	        //OutputStream out = socket.getOutputStream();
	        //out.write(requestBuilder.toString().getBytes());
	    } catch (IOException e) {
	   		e.printStackTrace();
	    }
	}
	
	public void receive() throws IOException{

		String data = "";
		String[] headerAndBody = null;
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long bytesRead;
		while (true){
			bytesRead = clientSocketChannel.read(buffer);
			if (bytesRead > 0){
				buffer.flip();
				while (buffer.hasRemaining()){
					data += (char)buffer.get();
				}
				buffer.clear();
				break;
			}
		}
	        headerAndBody = data.split("\r\n\r\n");
	        if(request.getIsV()){
	        		System.out.println(headerAndBody[0] + "\n");
	        }
	        if(headerAndBody.length == 2)
	        		System.out.println(headerAndBody[1] + "\n");

	        String[] body = headerAndBody[0].split(" ");
	       
	        if(body[1].equals("301") || body[1].equals("302")){
        			clientSocketChannel.close();
	        		int indexOfLocaton = headerAndBody[0].indexOf("Location:");
	        		int pathStart = headerAndBody[0].indexOf(":", indexOfLocaton) + 2;
	        		int pathEnd = headerAndBody[0].indexOf("\r\n",pathStart);
	        		request.setPath(headerAndBody[0].substring(pathStart, pathEnd).trim());
	        		Connecton newConnection = new Connecton(request);
	        		newConnection.send();
	        		newConnection.receive();
	        		//newConnection clientSocketChannel.close();
	        } else if(request.getWriteFile() && !headerAndBody[1].equals("")){
	        		writeFile(request.getFileName(),headerAndBody[1]);
	        		System.out.println("Body of the reponse is written to file: " + request.getFileName() + "\r\n");
	        }
		clientSocketChannel.close();
		}

	public void writeFile(String fileName,String data) throws IOException {
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		writer.write(data);
		writer.close();
	}
}
