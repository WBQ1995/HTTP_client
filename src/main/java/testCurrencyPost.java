import java.io.IOException;

public class testCurrencyPost implements Runnable {

    public void run(){
        String[] args = {"post", "-d", "1234567", "http://localhost:8008/TestConcurrency.txt"};
        ArgsParser parser = new ArgsParser(args);
        Request request = parser.getRequest();
        Connecton connecton = new Connecton(request);
        try {
            while (!connecton.getClientSocketChannel().isConnected()){}
                connecton.send();
                connecton.receive();
        } catch (Exception ex){
            ex.getStackTrace();
        }

    }
}
