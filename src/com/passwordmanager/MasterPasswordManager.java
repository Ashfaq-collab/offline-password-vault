package passwordmanager;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class MasterPasswordManager {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH = 256;

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
}
