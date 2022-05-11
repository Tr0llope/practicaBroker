import java.rmi.Naming;
import java.util.Scanner;

public class ClientC {
    static final String brokerHostName = "155.210.154.191:32001"; // broker IP and Port
    static final String brokerRMIName = "MyBroker441";

    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.policy", "./java.policy");

        Broker broker = (Broker) Naming.lookup("//" + brokerHostName + "/" + brokerRMIName);
        boolean end = false;
        Scanner s = new Scanner(System.in);

        while (!end) {
            for (String service : broker.getServices()) {
                System.out.println(service);
            }
            System.out.print("Escribe el servicio a ejecutar: ");
            String chosenService = s.next();
            try {
                switch (chosenService) {
                    case "prueba_alta_servicio":
                        System.out.println(broker.executeService("prueba_alta_servicio"));
                        break;
                    case "prueba_baja_servicio":
                        System.out.println(broker.executeService("prueba_baja_servicio"));
                        break;
                    case "getTimeString":
                        System.out.println(broker.executeService("getTimeString"));
                        break;
                    case "getNumAlumnos":
                        System.out.println(broker.executeService("getNumAlumnos"));
                        break;
                    case "anyadirAlumnos":
                        broker.executeService("anyadirAlumnos", s.nextInt());
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            if (chosenService.equals("q")) {
                end = true;
            }
            System.out.println();
        }
        s.close();
    }
}
