/**
 * Hello world!
 *
 */
public class httpc 
{
    public static void main( String[] args )
    {
    		ArgsParser parser = new ArgsParser(args);
    		Request request = parser.getRequest();
    		Connecton connecton = new Connecton(request);
    		connecton.send();
    		connecton.receive();
    }
}
    
