import java.rmi.Naming;

public class ClientC {
    static final String brokerHostName = "127.0.0.1:32000"; // broker IP and Port
    static final String brokerRMIName = "MyBroker";

    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.policy", "./java.policy");

        Broker broker = (Broker) Naming.lookup("//" + brokerHostName + "/" + brokerRMIName);
        for (String service : broker.getServices()) {
            System.out.println(service);
        }

        System.out.println(broker.executeService("getTimeString"));
        System.out.println(broker.executeService("getNumAlumnos"));
        broker.executeService("anyadirAlumnos", 3);
        System.out.println(broker.executeService("getNumAlumnos"));
    }
}
