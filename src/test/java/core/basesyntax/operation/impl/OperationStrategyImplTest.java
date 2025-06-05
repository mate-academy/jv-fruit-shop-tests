package core.basesyntax.operation.impl;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void processTransaction() {
        FruitStorage.getStorage().put("apple", 10);
        FruitTransaction transactionPurchase = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 5);
        OperationStrategyImpl strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.PURCHASE, new PurchaseOperation()));
        strategy.processTransaction(transactionPurchase);
    }

    @AfterAll
    static void cleanMeth() {
        FruitStorage.getStorage().clear();
    }
}
