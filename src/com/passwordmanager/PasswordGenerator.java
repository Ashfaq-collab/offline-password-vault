package passwordmanager;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String UPPERCASE =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String LOWERCASE =
            "abcdefghijklmnopqrstuvwxyz";

    private static final String NUMBERS =
            "0123456789";

    private static final String SPECIAL =
            "!@#$%^&*";

    private final SecureRandom random = new SecureRandom();

    public String generatePassword(int length) {

        StringBuilder password = new StringBuilder();

            password.append(
                    UPPERCASE.charAt(
                            random.nextInt(UPPERCASE.length())
                    )
            );

            password.append(
                    LOWERCASE.charAt(
                            random.nextInt(LOWERCASE.length())
                    )
            );

            password.append(
                    NUMBERS.charAt(
                            random.nextInt(NUMBERS.length())
                    )
            );

            password.append(
                    SPECIAL.charAt(
                            random.nextInt(SPECIAL.length())
                    )
            );
        for (int i = 4; i < length; i++) {

            String allCharacters =
                    UPPERCASE +
                            LOWERCASE +
                            NUMBERS +
                            SPECIAL;

            int index =
                    random.nextInt(allCharacters.length());

            password.append(
                    allCharacters.charAt(index)
            );
        }

        for (int i = password.length() - 1; i > 0; i--) {

            int j = random.nextInt(i + 1);

            char temp = password.charAt(i);

            password.setCharAt(
                    i,
                    password.charAt(j)
            );

            password.setCharAt(
                    j,
                    temp
            );
        }

        return password.toString();
    }
}
