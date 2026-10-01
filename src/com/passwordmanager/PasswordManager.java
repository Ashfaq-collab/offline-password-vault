package passwordmanager;

import javax.crypto.SecretKey;
import java.util.ArrayList;

public class PasswordManager {

    private int nextId;

    private ArrayList<PasswordEntry> entries;

    private VaultStorage storage;

    public PasswordManager(SecretKey key) {

        storage = new VaultStorage(key);

        entries = storage.load();

        nextId = calculateNextId();
    }

    public void addPassword(String website, String username, String password) {

        int id = nextId;

        PasswordEntry entry =
                new PasswordEntry(id, website, username, password);

        entries.add(entry);

        nextId++;

        storage.save(entries);

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

                storage.save(entries);

                System.out.println("Password deleted successfully!");
                return;
            }
        }

        System.out.println("Password with ID " + id + " not found.");
    }

    public void editPassword(
            int id,
            String website,
            String username,
            String password) {

        for (PasswordEntry entry : entries) {
            if (entry.getId() == id) {
                entry.setWebsite(website);
                entry.setUsername(username);
                entry.setPassword(password);

                storage.save(entries);

                System.out.println(
                        "Password updated successfully!"
                );

                return;
            }
        }

        System.out.println(
                "Password with ID " + id + " not found."
        );
    }

    private int calculateNextId() {

        int maxId = 0;

        for (PasswordEntry entry : entries) {

            if (entry.getId() > maxId) {
                maxId = entry.getId();
            }
        }

        return maxId + 1;
    }
}
