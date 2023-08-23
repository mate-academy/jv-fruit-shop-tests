package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationHandler actualGetResult;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        actualGetResult = (new OperationStrategyImpl()).get(FruitTransaction.Operation.BALANCE);
    }

    @BeforeEach
    void setUp() {
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(1);
    }

    @Test
    void testStrategyImplConstructor_Ok() {
        (new OperationStrategyImpl()).get(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void testStrategyImplGet_Ok() {
        actualGetResult.applyTransactionToStorage(fruitTransaction);
        assertTrue(actualGetResult instanceof BalanceOperationHandler);
    }
}
