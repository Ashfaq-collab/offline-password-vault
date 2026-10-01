package passwordmanager;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class MasterPasswordManager {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH = 256;

    private static final String MASTER_FILE = "master.dat";

    private byte[] salt;
    private byte[] passwordHash;

    public MasterPasswordManager() {

        loadMasterData();
    }

    private byte[] generateSalt() {

        byte[] salt = new byte[SALT_LENGTH];

        SecureRandom random = new SecureRandom();

        random.nextBytes(salt);

        return salt;
    }

    private byte[] deriveKey(String password, byte[] salt) {

        try {

            PBEKeySpec spec =
                    new PBEKeySpec(
                            password.toCharArray(),
                            salt,
                            ITERATIONS,
                            KEY_LENGTH
                    );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            return factory.generateSecret(spec).getEncoded();

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to derive key",
                    e
            );
        }
    }

    public void createMasterPassword(String password) {

        salt = generateSalt();

        passwordHash = deriveKey(password, salt);

        saveMasterData();

        System.out.println("Master password created successfully.");
    }

    public boolean verifyMasterPassword(String password) {

        if (salt == null || passwordHash == null) {
            return false;
        }

        byte[] enteredHash =
                deriveKey(password, salt);

        return MessageDigest.isEqual(
                passwordHash,
                enteredHash
        );
    }

    private void saveMasterData() {

        try (DataOutputStream output =
                     new DataOutputStream(
                             new FileOutputStream(MASTER_FILE))) {

            output.writeInt(salt.length);
            output.write(salt);

            output.writeInt(passwordHash.length);
            output.write(passwordHash);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Unable to save master password data",
                    e
            );
        }
    }

    private boolean loadMasterData() {

        try (DataInputStream input =
                     new DataInputStream(
                             new FileInputStream(MASTER_FILE))) {

            int saltLength = input.readInt();

            salt = new byte[saltLength];
            input.readFully(salt);

            int hashLength = input.readInt();

            passwordHash = new byte[hashLength];
            input.readFully(passwordHash);

            return true;

        } catch (FileNotFoundException e) {

            return false;

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Unable to load master password data",
                    e
            );
        }
    }

    public boolean hasMasterPassword() {

        return salt != null && passwordHash != null;
    }

    public byte[] deriveEncryptionKey(String password) {

        if (salt == null) {
            throw new IllegalStateException(
                    "Master password data is not loaded."
            );
        }

        return deriveKey(password, salt);
    }
}
