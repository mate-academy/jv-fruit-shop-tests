package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.put("Fruit", 20);
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void testApplySupplyTransactionToStorage_Ok() {
        fruitTransaction.setQuantity(1);
        supplyOperationHandler.applyTransactionToStorage(fruitTransaction);
    }

    @Test
    void testApplySupplyTransactionToStorage_NotOk() {
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}
