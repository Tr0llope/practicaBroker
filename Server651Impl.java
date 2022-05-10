import java.rmi.server.UnicastRemoteObject;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Server651Impl extends UnicastRemoteObject implements Server {
    private String IP_port; // server's IP and Port
    private String name; // server's name
    private ArrayList<String> services = new ArrayList<>(); // Service list

    protected Server651Impl(String address, String name) throws RemoteException {
        super();
        this.IP_port = address;
        this.name = name;
        services.add("getTimeString");
    }

    public static String getTimeString() throws RemoteException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void main(String args[]) {
        System.setProperty("java.security.policy", "./java.policy");
        String brokerHostName = "155.210.154.192:32000"; // broker IP and Port
        String serverHostName = "155.210.154.193:32000"; // server IP and Port
        try {

            // run the server
            Server651Impl srv = new Server651Impl(serverHostName, "Server651");
            System.out.println("Server 651 is running...");
            Naming.rebind("//" + serverHostName + "/MyServer651", srv);
            System.out.println("Server 651 is ready !");

            // connect to the broker
            Broker broker = (Broker) Naming.lookup("//" + brokerHostName + "/MyBroker");
            broker.addServer(srv, serverHostName);
        } catch (Exception ex) {
            System.out.println(ex);
        }
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
    public String getName() {
        return this.name;
    }

    @Override
    public String getIpPort() {
        return this.IP_port;
    }
}
