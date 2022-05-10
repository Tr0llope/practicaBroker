import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server866Impl extends UnicastRemoteObject implements Server {
    private String IP_port; // server's IP and Port
    private static String name; // server's name
    
    protected Server866Impl(String address, String name) throws RemoteException {
        super();
        IP_port = address;
        name=name;
    }
    public String getNumberOfStudents() throws RemoteException {
        return "Number of EINA students: almost 4500";
        
    }



    
}
