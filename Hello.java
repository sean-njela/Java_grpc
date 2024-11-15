import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Hello extends UnicastRemoteObject implements HelloInterface {
    private final String message;
    private final List<User> userDatabase;

    public Hello(String msg) throws RemoteException {
        this.message = msg;
        this.userDatabase = new ArrayList<>();
    }

    public String say() throws RemoteException {
        return message;
    }

    public int addNumbers(int a, int b) throws RemoteException {
        return a + b;
    }

    public int calculateVolume(MyObject o) throws RemoteException {
        return o.getBreadth() * o.getHeight() * o.getLength();
    }

    // Implement user database methods
    public synchronized void addUser(User user) throws RemoteException {
        userDatabase.add(user);
        System.out.println("User added: " + user);
    }

    public synchronized boolean deleteUser(String firstname, String lastname) throws RemoteException {
        return userDatabase.removeIf(user -> user.getFirstname().equals(firstname) && user.getLastname().equals(lastname));
    }

    public synchronized User getUser(String firstname, String lastname) throws RemoteException {
        return userDatabase.stream()
                .filter(user -> user.getFirstname().equals(firstname) && user.getLastname().equals(lastname))
                .findFirst()
                .orElse(null);
    }

    public synchronized List<User> listUsers() throws RemoteException {
        return new ArrayList<>(userDatabase);
    }

    public synchronized boolean updateUser(String firstname, String lastname, User updatedUser) throws RemoteException {
        for (int i = 0; i < userDatabase.size(); i++) {
            User user = userDatabase.get(i);
            if (user.getFirstname().equals(firstname) && user.getLastname().equals(lastname)) {
                userDatabase.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    // Implement shutdown method
    public synchronized void shutdown() throws RemoteException {
        System.out.println("Server is shutting down...");
        System.exit(0);  // Shutdown the server
    }
}
