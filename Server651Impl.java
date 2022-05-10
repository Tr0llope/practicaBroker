import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Server651Impl extends UnicastRemoteObject implements Server651 {
    private String IP_port; // server's IP and Port

    protected Server651Impl(String address) throws RemoteException {
        super();
        this.IP_port = address;
    }

    @Override
    public String getTimeString() throws RemoteException {
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
            Server651Impl srv = new Server651Impl(serverHostName);
            System.out.println("Server 651 is running...");
            Naming.rebind("//" + serverHostName + "/MyServer651", srv);
            System.out.println("Server 651 is ready !");

            // connect to the broker
            Broker broker = (Broker) Naming.lookup("//" + brokerHostName + "/MyBroker");
            broker.addServer("MyServer651", serverHostName);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
