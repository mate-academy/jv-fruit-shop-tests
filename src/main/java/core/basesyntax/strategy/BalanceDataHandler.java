package core.basesyntax.strategy;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;

public class BalanceDataHandler implements DataHandler {
    private static final String EXCEPTION_ARGUMENT_NULL_MESSAGE = "Argument of this method is null";

    @Override
    public void processWithData(FruitTransaction transaction, Map<String, Integer> data) {
        if (transaction == null || data == null) {
            throw new IllegalArgumentException(EXCEPTION_ARGUMENT_NULL_MESSAGE);
        } else {
            data.put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
