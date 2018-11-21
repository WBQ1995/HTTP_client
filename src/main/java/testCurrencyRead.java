public class testCurrencyRead implements Runnable {

    public void run(){
        String[] args = {"get", "-v", "http://localhost:8008/TestConcurrency.txt"};
        ArgsParser parser = new ArgsParser(args);
        Request request = parser.getRequest();
        Connecton connecton = new Connecton(request);

        try {
            Thread.sleep(200);
            while (!connecton.getClientSocketChannel().isConnected()){}
            connecton.send();
            connecton.receive();
        } catch (Exception ex){
            ex.getStackTrace();
        }

    }
}
