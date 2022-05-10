import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class Client {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "./java.policy");
        try {
            String hostname = "155.210.154.192:32000"; // broker IP and Port
            Broker broker = (Broker) Naming.lookup("//" + hostname + "/MyBroker");
            System.out.println(broker.executeInstruction("getTimeString", null));

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
