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
            switch(instrName){
                case "getTimeString":
                    instruction = server.getTimeString();
                    break;
                default:
                    instruction = "Instruction not found";
                    break;
            }
        } catch (final Exception ex) {
            System.out.println(ex);
        }
        return instruction;
    }

    public static void main(final String args[]) {

        System.setProperty("java.security.policy", "./java.policy");
      
        System.setSecurityManager(new SecurityManager());

        String brokerHostName = "155.210.154.192:32000";

        try {
            // run the broker
            BrokerImpl broker = new BrokerImpl();
            System.out.println("Broker is running...");
            Naming.rebind("//" + brokerHostName +"/MyBroker", broker);
            System.out.println("Broker is ready !");

        }
        catch (final Exception ex){
            System.out.println(ex);
        }
    }
}
