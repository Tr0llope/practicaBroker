import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Broker extends Remote {

    public class Server {
        public class Service {
            private Class<?>[] paramTypes;
            private Class<?> returnType;

        }

        private String serverIP;
        private String serverName;
        private HashMap<String, Service> services;

        Server(String name, String IP) {
            serverIP = IP;
            serverName = name;
        }

        public String getName() {
            return serverName;
        }

        public String getIP() {
            return serverIP;
        }

        public void addService(String serviceName, Class<?> returnType, Class<?>... paramTypes) {
            return serverIP;
        }

        public void removeService(String serviceName) {
            return serverIP;
        }

        public String getServices() {

        }
    }

    // API para los servidores:
    void addServer(String serverName, String hostname) throws RemoteException;

    void alta_servicio(String serverName, String serviceName, Class<?> returnType, Class<?>... paramTypes)
            throws RemoteException;

    void baja_servicio(String serverName, String serviceName) throws RemoteException;

    // API para los clientes:
    String getServices() throws RemoteException;

    Object executeInstruction(String instrName, Object... params) throws RemoteException;
}
