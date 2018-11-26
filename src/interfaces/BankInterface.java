/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package interfaces;

import java.util.Random;

public class BankInterface {
    public static String authorizeCardInfo(String cardNumber, int pin) {
        Random random = new Random();
        if (random.nextInt(5) == 1)
            return null;
        return "8899";
    }
}
