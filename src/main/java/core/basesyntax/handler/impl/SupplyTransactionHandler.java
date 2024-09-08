package core.basesyntax.handler.impl;

import core.basesyntax.handler.TransactionHandler;
import java.util.Map;

public class SupplyTransactionHandler implements TransactionHandler {
    @Override
    public void handleTransaction(Map<String, Integer> mapFruitQuantity, String key, int value) {
        mapFruitQuantity.merge(key, value, Integer::sum);
    }
}
