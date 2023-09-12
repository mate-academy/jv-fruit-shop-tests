package core.basesyntax.verificator;

public class NegativeVerifier {
    public static void isNegative(int number) {
        if (number < 0) {
            throw new RuntimeException("Quantity of fruit can`t be a negative");
        }
    }
}
