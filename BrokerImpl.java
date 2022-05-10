import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrokerImpl extends UnicastRemoteObject implements Broker {
    public static void main(final String args[]) {

        System.setProperty("java.security.policy", "./java.policy");

        String brokerHostName = "155.210.154.192:32000";

        try {
            // run the broker
            BrokerImpl broker = new BrokerImpl();
            System.out.println("Broker is running...");
            Naming.rebind("//" + brokerHostName + "/MyBroker", broker);
            System.out.println("Broker is ready !");

        } catch (final Exception ex) {
            System.out.println(ex);
        }
    }

    private ArrayList<Server> servers;

    private String serverIP;
    private String serverName;

    protected BrokerImpl() throws RemoteException {
        super();

    }

    public void addServer(String srv, String IP) throws RemoteException {
        try {
            serverName = srv;
            serverIP = IP;
            servers.add(new Server(srv, IP));
        } catch (final Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Object executeInstruction(String instrName, Object... params) throws RemoteException {
        try {
            String serverName = null;
            for (String server : services.keySet()) {
                for (String server_service : services.get(server)) {
                    if (server_service == instrName) {
                        serverName = server;
                    }
                }
            }
            if (serverName == null) {
                throw new Exception("Method not found");
            } else {
                Server server = (Server) Naming.lookup("//" + serverIP + "/" + serverName);
                return server.executeService(instrName, params);
            }
        } catch (final Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void alta_servicio(String serverName, String serviceName, Class<?> returnType, Class<?>... paramTypes)
            throws RemoteException {
        services.get(serverName).add(serviceName);
    }

    @Override
    public void baja_servicio(String serverName, String serviceName) throws RemoteException {
        for (Server server : servers) {
            if (server) {
                services.get(serverName).remove(service);
            }
        }
    }

    @Override
    public String getServices() throws RemoteException {
        ArrayList<String> allServices = new ArrayList<>();
        for (String server : services.keySet()) {
            for (String server_service : services.get(server)) {
                allServices.add(server_service);
            }
        }
        return allServices;
    }

}
