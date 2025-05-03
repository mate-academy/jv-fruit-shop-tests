package core.basesyntax.operations;

public class PurchaseOperation implements Operation {
    @Override
    public int calculateValue(int oldValue, int value) {
        if (value > oldValue) {
            throw new RuntimeException("You cannot buy more than we have " + value);
        }
        return oldValue - value;
    }
}
