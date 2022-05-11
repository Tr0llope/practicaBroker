import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote {

    static final String brokerHostName = "127.0.0.1:32000"; // broker IP and Port
    static final String brokerRMIName = "MyBroker";

    String getName() throws RemoteException;

    String getIpPort() throws RemoteException;

    ArrayList<String> getServices() throws RemoteException;

    Object executeService(String service, Object... params) throws RemoteException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException;
}
