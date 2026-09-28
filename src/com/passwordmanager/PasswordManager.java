package passwordmanager;

import java.util.ArrayList;

public class PasswordManager {

    private int nextId;

    private ArrayList<PasswordEntry> entries;

    public PasswordManager() {
        entries = new ArrayList<>();
        nextId = 1;
    }

    public void addPassword(String website, String username, String password) {

        int id = nextId;

        PasswordEntry entry =
                new PasswordEntry(id, website, username, password);

        entries.add(entry);

        nextId++;

        System.out.println("Password added successfully!");
    }

    public void viewPasswords() {

        if (entries.isEmpty()) {
            System.out.println("No passwords saved.");
            return;
        }

        for (PasswordEntry entry : entries) {

            System.out.println("-----------------------------");
            System.out.println("ID       : " + entry.getId());
            System.out.println("Website  : " + entry.getWebsite());
            System.out.println("Username : " + entry.getUsername());
            System.out.println("Password : " + entry.getPassword());
        }

        System.out.println("-----------------------------");
    }

    public void searchPassword(String keyword) {

        boolean found = false;

        for (PasswordEntry entry : entries) {

            if (entry.getWebsite().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.println("-----------------------------");
                System.out.println("ID       : " + entry.getId());
                System.out.println("Website  : " + entry.getWebsite());
                System.out.println("Username : " + entry.getUsername());
                System.out.println("Password : " + entry.getPassword());

                found = true;
            }
        }

        if (!found) {
            System.out.println("No password found.");
        }
    }
    public void deletePassword(int id) {

        for (PasswordEntry entry : entries) {

            if (entry.getId() == id) {

                entries.remove(entry);

                System.out.println("Password deleted successfully!");
                return;
            }
        }

        System.out.println("Password with ID " + id + " not found.");
    }
}
