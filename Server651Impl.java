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
    public static void main(String args[]){
        System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
        String hostName = "155.210.154.191:32000"; //IP del cliente
        try {

            Server651Impl srv = new Server651Impl(hostName);
            System.out.println("Creado!");

            Naming.rebind("//" + hostName + "/MyCollection", srv); 
            System.out.println("Estoy registrado!");
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        }
    }
