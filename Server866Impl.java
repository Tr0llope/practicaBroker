import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server866Impl extends UnicastRemoteObject implements Server866 {
    private String IP_port; // server's IP and Port -> "IP:Port"

    protected Server866Impl(String address, String port) throws RemoteException {
        super();
        this.IP_port = address;
    }
    @Override
    public String getNumberOfStudents() throws RemoteException {
        return "Number of EINA students: almost 4500";
        
    }


    
}
