import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server866 extends Remote {
    // function provided by the server
    public String getNumberOfStudents() throws RemoteException;

}
