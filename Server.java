import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote {
    public class Service {
        public String name;
        public ArrayList<Object> params;
    }

    String getName();

    String getIpPort();

    ArrayList<String> getServices() throws RemoteException;

    Object executeService(String service, Object... params) throws RemoteException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException;
}
