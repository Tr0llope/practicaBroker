import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class Client{
    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "./java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String hostname = "155.210.154.192:32000"; //broker IP and Port
            Broker broker = (Broker) Naming.lookup("//"+ hostname + "/MyBroker");
            broker.addServer("Server651", "155.210.154.193:32000");
            System.out.println(broker.executeInstruction("getTimeString", null));

        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}