package passwordmanager;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PasswordManager manager = new PasswordManager();

        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println("       OFFLINE PASSWORD VAULT");
            System.out.println("================================");

            System.out.println("1. Add Password");
            System.out.println("2. View Passwords");
            System.out.println("3. Search Password");
            System.out.println("4. Delete Password");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("\n--- Add Password ---");

                    System.out.print("Enter website: ");
                    String website = scanner.nextLine();

                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    manager.addPassword(
                            website,
                            username,
                            password
                    );

                    break;

                case 2:
                    System.out.println("\n--- Saved Passwords ---");

                    manager.viewPasswords();

                    break;

                case 3:
                    System.out.println("\n--- Search Password ---");

                    System.out.print("Enter website to search: ");
                    String keyword = scanner.nextLine();

                    manager.searchPassword(keyword);

                    break;

                case 4:
                    System.out.println("\n--- Delete Password ---");

                    System.out.print("Enter ID to delete: ");
                    int id = scanner.nextInt();
                        scanner.nextLine();

                    manager.deletePassword(id);

                    break;

                case 5:
                    System.out.println("\nExiting Password Vault...");
                    running = false;

                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }

        scanner.close();

        System.out.println("Thank you for using Offline Password Vault!");
    }
}
