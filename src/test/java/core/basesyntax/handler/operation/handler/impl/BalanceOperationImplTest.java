package core.basesyntax.handler.operation.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationImplTest {
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperationImpl();
    }

    @Test
    void testBalanceOperation_ExecutionSuccessful() {
        balanceOperation.operationType(FruitModel.BANANA, 20);
        Integer bananaAmount = Storage.fruitStorage.get(FruitModel.BANANA);
        assertEquals(20, bananaAmount);
    }
}
