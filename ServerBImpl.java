import java.rmi.server.UnicastRemoteObject;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerBImpl extends UnicastRemoteObject implements Server {

    private static final String serverHostName = "127.0.0.1:32002"; // server IP and Port
    private static final String serverRMIName = "ServerB";

    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        System.setProperty("java.security.policy", "./java.policy");

        // run the server and connect to the broker
        System.out.println(serverRMIName + " is running...");
        ServerBImpl server = new ServerBImpl(serverHostName, serverRMIName);
        System.out.println(serverRMIName + " is ready !");
    }

    private String IP_port; // server's IP and Port
    private String name; // server's name
    private ArrayList<String> services; // Service list
    private Broker broker;

    protected ServerBImpl(String address, String name)
            throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.IP_port = address;
        this.name = name;
        this.services = new ArrayList<>();
        this.numAlumnos = 1500;

        // add server to broker
        System.out.println("Registering " + serverRMIName + " in broker");
        this.broker = (Broker) Naming.lookup("//" + brokerHostName + "/" + brokerRMIName);

        Naming.rebind("//" + serverHostName + "/" + serverRMIName, this);
        this.broker.addServer(this.name, this.IP_port);

        // register services in broker
        System.out.println("Registering " + serverRMIName + " services");

        services.add("getNumAlumnos");
        this.broker.alta_servicio(serverRMIName, "getNumAlumnos", Integer.class);

        services.add("anyadirAlumnos");
        this.broker.alta_servicio(serverRMIName, "anyadirAlumnos", Void.class,
                Integer.class);
    }

    private int numAlumnos;

    public int getNumAlumnos() throws RemoteException {
        return numAlumnos;
    }

    public void anyadirAlumnos(Integer alumnos) throws RemoteException {
        this.numAlumnos = this.numAlumnos + alumnos;
    }

    @Override
    public ArrayList<String> getServices() throws RemoteException {
        return this.services;
    }

    @Override
    public Object executeService(String service, Object... params) throws RemoteException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?>[] paramTypes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }
        return this.getClass().getMethod(service, paramTypes).invoke(this, params);
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public String getIpPort() throws RemoteException {
        return this.IP_port;
    }
}
