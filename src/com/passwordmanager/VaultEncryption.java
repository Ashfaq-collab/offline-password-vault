package passwordmanager;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

public class VaultEncryption {

    public SecretKey generateKey() {

        try {

            KeyGenerator keyGenerator =
                    KeyGenerator.getInstance("AES");

            keyGenerator.init(256);

            return keyGenerator.generateKey();

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to generate AES key",
                    e
            );
        }
    }
    private byte[] generateIV() {

        byte[] iv = new byte[12];

        SecureRandom random = new SecureRandom();

        random.nextBytes(iv);

        return iv;
    }

    public byte[] encrypt(
            String plainText,
            SecretKey key,
            byte[] iv) {

        try {

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec spec =
                    new GCMParameterSpec(128, iv);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    spec
            );

            return cipher.doFinal(
                    plainText.getBytes()
            );

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to encrypt data",
                    e
            );
        }
    }

    public String decrypt(
            byte[] encryptedData,
            SecretKey key,
            byte[] iv) {

        try {

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec spec =
                    new GCMParameterSpec(128, iv);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    spec
            );

            byte[] decrypted =
                    cipher.doFinal(encryptedData);

            return new String(decrypted);

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to decrypt data",
                    e
            );
        }
    }
}
