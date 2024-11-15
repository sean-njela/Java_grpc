import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class HelloClient {
    public static void main(String[] argv) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        HelloInterface server = null;
        int retries = 3;  // Number of retries
        int attempt = 0;

        while (attempt < retries) {
            try {
                // Try to connect to the server
                server = (HelloInterface) Naming.lookup("//localhost:5001/Hello");
                System.out.println("Connected to the server successfully!");
                break;  // If connection is successful, exit the loop
            } catch (Exception e) {
                attempt++;
                System.out.println("Failed to connect to the server. Attempt " + attempt + " of " + retries);
                if (attempt >= retries) {
                    System.out.println("Max retries reached. Exiting...");
                    return;  // Exit the client if max retries are reached
                }
                try {
                    Thread.sleep(2000);  // Wait 2 seconds before retrying
                } catch (InterruptedException ex) {
                    System.out.println("Retry interrupted");
                    return;
                }
            }
        }

        // If server connection is established, proceed with the operations
        if (server != null) {
            while (true) {
                System.out.println("1. Add User");
                System.out.println("2. Delete User");
                System.out.println("3. Get User Details");
                System.out.println("4. List Users");
                System.out.println("5. Update User");
                System.out.println("6. Shutdown Server");
                System.out.println("7. Exit");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter firstname: ");
                        String firstname = scanner.nextLine();
                        System.out.print("Enter lastname: ");
                        String lastname = scanner.nextLine();
                        System.out.print("Enter birthdate: ");
                        String birthdate = scanner.nextLine();
                        System.out.print("Enter salary: ");
                        double salary = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter gender (MALE/FEMALE/OTHER): ");
                        User.Gender gender = User.Gender.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Enter division: ");
                        String division = scanner.nextLine();
                        System.out.print("Enter position: ");
                        String position = scanner.nextLine();

                        User user = new User(firstname, lastname, birthdate, salary, gender, division, position);
                        server.addUser(user);
                        System.out.println("User added.");
                    }
                    case 2 -> {
                        System.out.print("Enter firstname: ");
                        String firstname = scanner.nextLine();
                        System.out.print("Enter lastname: ");
                        String lastname = scanner.nextLine();

                        if (server.deleteUser(firstname, lastname)) {
                            System.out.println("User deleted.");
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter firstname: ");
                        String firstname = scanner.nextLine();
                        System.out.print("Enter lastname: ");
                        String lastname = scanner.nextLine();

                        User user = server.getUser(firstname, lastname);
                        if (user != null) {
                            System.out.println(user);
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    case 4 -> {
                        server.listUsers().forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.print("Enter firstname of user to update: ");
                        String firstname = scanner.nextLine();
                        System.out.print("Enter lastname of user to update: ");
                        String lastname = scanner.nextLine();

                        System.out.print("Enter new firstname: ");
                        String newFirstname = scanner.nextLine();
                        System.out.print("Enter new lastname: ");
                        String newLastname = scanner.nextLine();
                        System.out.print("Enter new birthdate: ");
                        String newBirthdate = scanner.nextLine();
                        System.out.print("Enter new salary: ");
                        double newSalary = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new gender (MALE/FEMALE/OTHER): ");
                        User.Gender newGender = User.Gender.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Enter new division: ");
                        String newDivision = scanner.nextLine();
                        System.out.print("Enter new position: ");
                        String newPosition = scanner.nextLine();

                        User updatedUser = new User(newFirstname, newLastname, newBirthdate, newSalary, newGender, newDivision, newPosition);
                        if (server.updateUser(firstname, lastname, updatedUser)) {
                            System.out.println("User updated.");
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    case 6 -> {
                        try {
                            server.shutdown();  // Send shutdown request to the server
                            System.out.println("Server is shutting down...");
                            return;  // Exit client after shutting down server
                        } catch (RemoteException e) {
                            System.out.println("Error shutting down server: " + e.getMessage());
                        }
                    }
                    case 7 -> {
                        System.out.println("Goodbye!");
                        return;  // Exit client
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }
}
