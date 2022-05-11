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

    private HashMap<String, Server> servers;
    private HashMap<String, String> services;
    private HashMap<String, Class<?>[]> servicesParams;
    private HashMap<String, Class<?>> servicesReturnType;

    protected BrokerImpl() throws RemoteException {
        super();
        servers = new HashMap<>();
        services = new HashMap<>();
        servicesParams = new HashMap<>();
        servicesReturnType = new HashMap<>();
    }

    public void addServer(String RMIName, String hostname)
            throws RemoteException, MalformedURLException, NotBoundException {
        servers.put(RMIName, (Server) Naming.lookup("//" + hostname + "/" + RMIName));
        System.out.println(hostname + "/" + RMIName);
    }

    @Override
    public Object executeService(String svcName, Object... params) throws RemoteException, Exception {
        Server serviceProvider = servers.get(services.get(svcName));
        if (serviceProvider == null) {
            throw new Exception("No server found that provides such service");
        }
        return serviceProvider.executeService(svcName, params);
    }

    @Override
    public void alta_servicio(String serverName, String serviceName, Class<?> returnType, Class<?>... paramTypes)
            throws RemoteException {
        services.put(serviceName, serverName);
        servicesParams.put(serviceName, paramTypes);
        servicesReturnType.put(serviceName, returnType);
        System.out.println("Registered service: "
                + String.format("%s\t%s\t%s", serverName, serviceName, returnType.getCanonicalName()));
    }

    @Override
    public void baja_servicio(String serverName, String serviceName) throws RemoteException {
        services.remove(serviceName);
        System.out.println("Removed service: " + serviceName);
    }

    @Override
    public ArrayList<String> getServices() throws RemoteException {
        ArrayList<String> allServices = new ArrayList<>();
        for (String serviceName : services.keySet()) {
            allServices
                    .add(String.format("%s\t%s\t%s", servers.get(services.get(serviceName)).getName(), serviceName,
                            servicesReturnType.get(serviceName).getCanonicalName()));
        }
        return allServices;
    }
}
