package core.basesyntax.strategy;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;

public class PurchaseDataHandler implements DataHandler {
    private static final String EXCEPTION_ARGUMENT_NULL_MESSAGE = "Argument of this method is null";

    @Override
    public void processWithData(FruitTransaction transaction, Map<String, Integer> data) {
        if (transaction == null || data == null) {
            throw new IllegalArgumentException(EXCEPTION_ARGUMENT_NULL_MESSAGE);
        } else {
            String fruit = transaction.getFruit();
            int quantityBefore = data.get(transaction.getFruit());
            int quantityTransaction = transaction.getQuantity();
            int quantityAfter = quantityBefore - quantityTransaction;
            if (quantityBefore > quantityTransaction) {
                data.put(fruit, quantityAfter);
            }
        }
    }
}
