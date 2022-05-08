import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server651 extends Remote {

    // function provided by the server
    String getTimeString() throws RemoteException;
}
