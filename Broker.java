import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Broker extends Remote {
    static final String brokerHostName = "155.210.154.191:32001"; // broker IP and Port
    static final String brokerRMIName = "MyBroker441";

    // API para los servidores:
    void addServer(String serverName, String hostname) throws RemoteException, MalformedURLException, NotBoundException;

    void alta_servicio(String serverName, String serviceName, Class<?> returnType, Class<?>... paramTypes)
            throws RemoteException;

    void baja_servicio(String serverName, String serviceName) throws RemoteException;

    // API para los clientes:
    ArrayList<String> getServices() throws RemoteException;

    Object executeService(String instrName, Object... params) throws RemoteException, Exception;
}
