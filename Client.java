import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class Client{
    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "./java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String hostname = "155.210.154.192:32000"; //IP del broker
            Broker broker = (Broker) Naming.lookup("//"+ hostname + "/MyBroker");
            broker.addServer("Server651", "155.210.154.193:32000");

        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}