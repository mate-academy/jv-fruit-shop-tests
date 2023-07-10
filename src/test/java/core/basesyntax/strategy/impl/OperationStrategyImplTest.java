package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void testConstructor_Ok() {
        (new OperationStrategyImpl()).get(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void testGet_Ok() {
        final OperationHandler actualGetResult = (new OperationStrategyImpl())
                .get(FruitTransaction.Operation.BALANCE);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(1);
        actualGetResult.applyTransactionToStorage(fruitTransaction);
        assertTrue(actualGetResult instanceof BalanceOperationHandler);
    }
}

