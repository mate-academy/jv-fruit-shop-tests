package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balanceOperation;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handleTransaction_quantity_notOk() {
        int [] quantity = {-55,-1,-9000};
        for (int number : quantity) {
            fruitTransaction.setQuantity(number);
            Assertions.assertThrows(RuntimeException.class,
                    () -> balanceOperation.handleTransaction(fruitTransaction),
                    "If Quantity < 0 should be Exception");
        }
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        fruitTransaction = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceOperation.handleTransaction(fruitTransaction),
                "If Transaction is null should be Exception");
    }

    @Test
    void handleTransaction_quantity_ok() {
        fruitTransaction.setFruit("banane");
        int[] quantity = {55, 0, 9000};
        for (int expected : quantity) {
            fruitTransaction.setQuantity(expected);
            balanceOperation.handleTransaction(fruitTransaction);
            int actual = Storage.DB.get("banane");
            Assertions.assertEquals(expected,actual,
                    "Quantity in DB not equals current Quantity");
        }
    }
}
