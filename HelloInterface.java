import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HelloInterface extends Remote {
    String say() throws RemoteException;
    int addNumbers(int a, int b) throws RemoteException;
    int calculateVolume(MyObject o) throws RemoteException;

    // CRUD methods
    void addUser(User user) throws RemoteException;
    boolean deleteUser(String firstname, String lastname) throws RemoteException;
    User getUser(String firstname, String lastname) throws RemoteException;
    List<User> listUsers() throws RemoteException;
    boolean updateUser(String firstname, String lastname, User updatedUser) throws RemoteException;

    // Remote shutdown method
    void shutdown() throws RemoteException;  // Add shutdown method
}
