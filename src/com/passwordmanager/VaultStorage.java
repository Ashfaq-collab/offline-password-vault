package passwordmanager;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.ArrayList;

public class VaultStorage {

    private static final String FILE_NAME = "vault.dat";
    private final VaultEncryption encryption;
    private final SecretKey key;

    public VaultStorage(SecretKey key) {

        this.key = key;
        this.encryption = new VaultEncryption();
    }

    public void save(ArrayList<PasswordEntry> entries) {

        byte[] iv = new byte[12];

        new java.security.SecureRandom()
                .nextBytes(iv);

        try {

            // 1. Convert ArrayList into bytes
            ByteArrayOutputStream byteStream =
                    new ByteArrayOutputStream();

            ObjectOutputStream objectOutput =
                    new ObjectOutputStream(byteStream);

            objectOutput.writeObject(entries);
            objectOutput.close();

            byte[] plaintext =
                    byteStream.toByteArray();


            // 2. Encrypt the bytes
            byte[] encrypted =
                    encryption.encrypt(
                            plaintext,
                            key,
                            iv
                    );


            // 3. Store IV + encrypted data
            DataOutputStream output =
                    new DataOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            output.writeInt(iv.length);
            output.write(iv);

            output.writeInt(encrypted.length);
            output.write(encrypted);

            output.close();


            System.out.println(
                    "Vault saved successfully."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving vault."
            );
        }
    }

    public ArrayList<PasswordEntry> load() {

        try {

            DataInputStream input =
                    new DataInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            // 1. Read IV
            int ivLength = input.readInt();

            byte[] iv = new byte[ivLength];

            if (ivLength != 12) {

                System.out.println("Invalid vault file.");

                input.close();

                return new ArrayList<>();
            }

            input.readFully(iv);


            // 2. Read encrypted data
            int encryptedLength = input.readInt();

            if (encryptedLength <= 0) {

                System.out.println("Invalid encrypted data.");

                input.close();

                return new ArrayList<>();
            }

            byte[] encrypted =
                    new byte[encryptedLength];

            input.readFully(encrypted);

            input.close();


            // 3. Decrypt
            byte[] plaintext =
                    encryption.decrypt(
                            encrypted,
                            key,
                            iv
                    );


            // 4. Convert bytes back to ArrayList
            ByteArrayInputStream byteStream =
                    new ByteArrayInputStream(
                            plaintext
                    );

            ObjectInputStream objectInput =
                    new ObjectInputStream(byteStream);

            ArrayList<PasswordEntry> entries =
                    (ArrayList<PasswordEntry>)
                            objectInput.readObject();

            objectInput.close();


            System.out.println(
                    "Vault loaded successfully."
            );

            return entries;

        } catch (FileNotFoundException e) {

            System.out.println(
                    "No existing vault found."
            );

            return new ArrayList<>();

        } catch (IOException |
                 ClassNotFoundException e) {

            System.out.println(
                    "Error loading vault."
            );

            return new ArrayList<>();
        }
    }
}
