package lib;

public class Utilities {
    public static void clearScreen() {
        System.out.print("\n\n\n\n\n\n\n\n\n");
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
