package uiInterface;

import java.util.Random;

public class BankInterface {
    public static String authorizeCardInfo(String cardNumber, int pin) {
        Random random = new Random();
        if (random.nextInt(2) == 1)
            return "8899";
        return null;
    }
}
