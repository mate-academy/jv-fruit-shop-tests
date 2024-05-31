package core.basesyntax.handler.operation.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationImplTest {
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperationImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    void testOperationTypeReducesFruitAmount_OK() {
        FruitModel apple = FruitModel.APPLE;
        Storage.fruitStorage.put(apple, 10);
        int amount = 8;
        purchaseOperation.operationType(apple, amount);
        assertEquals(2, Storage.fruitStorage.get(apple));
    }

}
