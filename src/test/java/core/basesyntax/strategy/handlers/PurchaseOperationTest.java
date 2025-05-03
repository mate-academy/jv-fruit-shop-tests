package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final int QUANTITY = 100;
    private static final String FRUIT = "banana";
    private static OperationHandler purchaseOperation;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(20);
    }

    @AfterEach
    void afterEach() {
        FruitStorage.fruits.clear();
        transaction.setQuantity(20);
    }

    @Test
    void executeOperation_fruitIsEnoughInStorage_Ok() {
        FruitStorage.fruits.put(FRUIT, QUANTITY);
        purchaseOperation.executeOperation(transaction);
        int expected = 80;
        int actual = FruitStorage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void executeOperation_fruitIsNotEnoughInStorage_NotOk() {
        FruitStorage.fruits.put(FRUIT, QUANTITY);
        transaction.setQuantity(200);
        assertThrows(RuntimeException.class, () -> purchaseOperation.executeOperation(transaction));
    }

    @Test
    void executeOperation_fruitIsAbsentInStorage_NotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.executeOperation(transaction));
    }
}
