package core.basesyntax.operations;

public class BalanceOperation implements Operation {

    @Override
    public int calculateValue(int oldValue, int value) {
        if (value <= 0) {
            throw new RuntimeException("Value cannot be less then zero" + value);
        }
        return oldValue + value;
    }
}
