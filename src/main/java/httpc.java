import java.io.IOException;

/**
 * Hello world!
 *
 */
public class httpc 
{
    public static void main( String[] args ) throws IOException
    {
    		ArgsParser parser = new ArgsParser(args);
    		Request request = parser.getRequest();
    		Connecton connecton = new Connecton(request);
    		connecton.send();
    		connecton.receive();

		//testWrite();
		//testRead();
		//testReadWrite();

    }

    public static void testWrite(){
		testCurrencyPost testCurrencyPost = new testCurrencyPost();
		for (int i = 0; i < 10; i ++) {
			Thread thread1 = new Thread(testCurrencyPost);
			thread1.start();
		}
	}

	public static void testRead(){
		testCurrencyRead testCurrencyRead = new testCurrencyRead();
		for (int i = 0; i < 10; i ++) {
			Thread thread2 = new Thread(testCurrencyRead);
			thread2.start();
		}
	}

	public static void testReadWrite(){
		testCurrencyPost testCurrencyPost = new testCurrencyPost();
		testCurrencyRead testCurrencyRead = new testCurrencyRead();
		for (int i = 0; i < 10; i ++) {
			Thread thread1 = new Thread(testCurrencyPost);
			Thread thread2 = new Thread(testCurrencyRead);
			thread1.start();
			thread2.start();
		}
	}
}
    
