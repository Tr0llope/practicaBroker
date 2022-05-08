import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BrokerImpl extends UnicastRemoteObject implements Broker {
    protected BrokerImpl() throws RemoteException {
        super();
        
    }
    private Server651 server;
    private String serverIP;
    private String serverName;
    
    public void addServer(String name, String IP ){
        try {
            serverIP = IP;
            serverName= name;
            server = (Server651) Naming.lookup("//" + serverIP + "/" + serverName);
        }
        catch(final Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String executeInstruction(String instrName, List<String> parameters) {
        String instruction = null;
        try {
            
        } catch (final Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static void main(final String args[]) {

        System.setProperty("java.security.policy", "./java.policy");
      
        System.setSecurityManager(new SecurityManager());

        String brokerIP = "192.168.56.1";

        try {
            // Crear objeto remoto
            BrokerImpl broker = new BrokerImpl();
            System.out.println("Creado!");

            Naming.rebind("//" + brokerIP +"/MyBroker", broker);

            System.out.println("Estoy registrado!");

        }
        catch (final Exception ex){
            System.out.println(ex);
        }
    }
}
