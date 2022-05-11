import java.rmi.server.UnicastRemoteObject;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class ServerAImpl extends UnicastRemoteObject implements Server {

    private static final String serverHostName = "155.210.154.192:32002"; // server IP and Port
    private static final String serverRMIName = "ServerA441";

    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        System.setProperty("java.security.policy", "./java.policy");

        // run the server and connect to the broker
        System.out.println(serverRMIName + " is running...");
        ServerAImpl server = new ServerAImpl(serverHostName, serverRMIName);
        System.out.println(serverRMIName + " is ready !");
    }

    private String IP_port; // server's IP and Port
    private String name; // server's name
    private ArrayList<String> services; // Service list
    private Broker broker;

    protected ServerAImpl(String address, String name)
            throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.IP_port = address;
        this.name = name;
        this.services = new ArrayList<>();

        // add server to broker
        System.out.println("Registering " + serverRMIName + " in broker");
        this.broker = (Broker) Naming.lookup("//" + brokerHostName + "/" + brokerRMIName);

        Naming.rebind("//" + serverHostName + "/" + serverRMIName, this);
        this.broker.addServer(this.name, this.IP_port);

        // register services in broker
        System.out.println("Registering " + serverRMIName + " services");
        this.broker.alta_servicio(serverRMIName, "getTimeString", String.class);
        this.broker.alta_servicio(serverRMIName, "prueba_alta_servicio", String.class);
        this.broker.alta_servicio(serverRMIName, "prueba_baja_servicio", String.class);
    }

    public static String getTimeString() throws RemoteException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    public ArrayList<String> getServices() throws RemoteException {
        return this.services;
    }

    @Override
    public Object executeService(String service, Object... params) throws RemoteException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        return this.getClass().getMethod(service).invoke(this, params);
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public String getIpPort() throws RemoteException {
        return this.IP_port;
    }

    public String prueba_alta_servicio() throws RemoteException {
        this.broker.alta_servicio(serverRMIName, "getTimeString", String.class);
        return "Dado de alta getTimeString";
    }

    public String prueba_baja_servicio() throws RemoteException {
        this.broker.baja_servicio(serverRMIName, "getTimeString");
        return "Dado de baja getTimeString";
    }
}
