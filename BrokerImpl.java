import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class BrokerImpl extends UnicastRemoteObject implements Broker {
    public static void main(final String args[]) {

        System.setProperty("java.security.policy", "./java.policy");

        try {
            // run the broker
            BrokerImpl broker = new BrokerImpl();
            System.out.println("Broker is running...");
            Naming.rebind("//" + brokerHostName + "/" + brokerRMIName, broker);
            System.out.println("Broker is ready !");

        } catch (final Exception ex) {
            System.out.println(ex);
        }
    }

    private ArrayList<Server> servers;
    private HashMap<String, String> services;

    protected BrokerImpl() throws RemoteException {
        super();
        servers = new ArrayList<>();
        services = new HashMap<>();
    }

    public void addServer(String RMIName, String hostname)
            throws RemoteException, MalformedURLException, NotBoundException {
        servers.add((Server) Naming.lookup("//" + hostname + "/" + RMIName));
        System.out.println(hostname + "/" + RMIName);
    }

    @Override
    public Object executeService(String svcName, Object... params) throws RemoteException, Exception {
        Server serviceProvider = null;
        Server serverTmp = null;
        try {
            for (Server server : servers) {
                serverTmp = server;
                if (server.getServices().contains(svcName)) {
                    serviceProvider = server;
                }
            }
        } catch (RemoteException e) {
            servers.remove(serverTmp);
            services.remove(svcName);
        }
        if (serviceProvider == null) {
            throw new Exception("No server found that provides such service");
        }
        return serviceProvider.executeService(svcName, params);
    }

    @Override
    public void alta_servicio(String serverName, String serviceName, Class<?> returnType, Class<?>... paramTypes)
            throws RemoteException {
        String service = String.format("%s\t%s\t%s", serverName, serviceName, returnType.getCanonicalName());
        System.out.println("Registered service: " + service);
        services.put(serviceName, service);
    }

    @Override
    public void baja_servicio(String serverName, String serviceName) throws RemoteException {
        services.remove(serviceName);
        System.out.println("Removed service: " + serviceName);
    }

    @Override
    public ArrayList<String> getServices() throws RemoteException {
        // ArrayList<String> allServices = new ArrayList<>();
        // for (Server server : servers) {
        // for (String server_service : server.getServices()) {
        // System.out.println(server_service);
        // allServices.add(server_service);
        // }
        // }
        // return allServices;
        ArrayList<String> allServices = new ArrayList<>();
        for (String server_service : services.values()) {
            allServices.add(server_service);
        }
        return allServices;
    }

}
