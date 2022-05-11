import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Broker extends Remote {
    static final String brokerHostName = "127.0.0.1:32000"; // broker IP and Port
    static final String brokerRMIName = "MyBroker";

    public class ServerRep {
        public class Service {
            private Class<?>[] paramTypes;
            private Class<?> returnType;

            Service(Class<?>[] params, Class<?> returnT) {
                paramTypes = params;
                returnType = returnT;
            }

            public Class<?> getReturnType() {
                return returnType;
            }

            public Class<?>[] getParamTypes() {
                return paramTypes;
            }

        }

        private String serverIP;
        private String serverName;
        private HashMap<String, Service> services;

        ServerRep(String name, String IP) {
            serverIP = IP;
            serverName = name;
            services = new HashMap<>();
        }

        public String getName() {
            return serverName;
        }

        public String getIP() {
            return serverIP;
        }

        public void addService(String serviceName, Class<?> returnType, Class<?>... paramTypes) {
            System.out.println(serviceName);
            services.put(serviceName, new Service(paramTypes, returnType));
        }

        public void removeService(String serviceName) {
            System.out.println(serviceName);
            services.remove(serviceName);
        }

        public ArrayList<String> getServices() {
            ArrayList<String> serviceList = new ArrayList<>();
            for (String serviceName : services.keySet()) {
                serviceList.add(serviceName);
            }
            return serviceList;
        }
    }

    // API para los servidores:
    void addServer(String serverName, String hostname) throws RemoteException, MalformedURLException, NotBoundException;

    void alta_servicio(String serverName, String serviceName, Class<?> returnType, Class<?>... paramTypes)
            throws RemoteException;

    void baja_servicio(String serverName, String serviceName) throws RemoteException;

    // API para los clientes:
    ArrayList<String> getServices() throws RemoteException;

    Object executeService(String instrName, Object... params) throws RemoteException, Exception;
}
