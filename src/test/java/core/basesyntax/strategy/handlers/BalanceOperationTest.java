package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final int QUANTITY = 20;
    private static final String FRUIT = "banana";
    private static OperationHandler balanceOperation;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();

        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(QUANTITY);
    }

    @AfterEach
    void afterEach() {
        FruitStorage.fruits.clear();
    }

    @Test
    void executeOperation_validTransaction_Ok() {
        balanceOperation.executeOperation(transaction);
        int expected = 20;
        int actual = FruitStorage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void executeOperation_fruitIsNull_NotOk() {
        transaction.setFruit(null);
        assertThrows(RuntimeException.class, () -> {
            balanceOperation.executeOperation(transaction);
        });
    }

    @Test
    void executeOperation_negativeQuantity_NotOk() {
        transaction.setQuantity(-20);
        assertThrows(RuntimeException.class, () -> {
            balanceOperation.executeOperation(transaction);
        });
    }
}
