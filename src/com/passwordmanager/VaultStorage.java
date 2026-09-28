package passwordmanager;

import java.io.*;
import java.util.ArrayList;

public class VaultStorage {

    private static final String FILE_NAME = "vault.dat";

    public void save(ArrayList<PasswordEntry> entries) {

        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            output.writeObject(entries);

            output.close();

            System.out.println("Vault saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving vault.");
        }
    }

    public ArrayList<PasswordEntry> load() {

        try {

            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            ArrayList<PasswordEntry> entries =
                    (ArrayList<PasswordEntry>) input.readObject();

            input.close();

            System.out.println("Vault loaded successfully.");

            return entries;

        } catch (FileNotFoundException e) {

            System.out.println("No existing vault found.");
            return new ArrayList<>();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading vault.");
            return new ArrayList<>();
        }
    }
}
