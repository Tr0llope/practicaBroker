import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface Broker extends Remote {

    //API para los servidores:
    void addServer(Server serverName, String hostname) throws RemoteException;

    void addServices(Server serverName, List<String> services) throws RemoteException;


    
    //API para los clientes:
    String executeInstruction(String instrName, List<String> parameters) throws RemoteException;



}
    
