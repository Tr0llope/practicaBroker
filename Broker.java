import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface Broker extends Remote {

    //API para los servidores:
    void addServer(String serverName, String hostname) throws RemoteException;
    
    //API para los clientes:
    String executeInstruction(String instrName, List<String> parameters);

}
    
