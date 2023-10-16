package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler purchaseOperation;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banane");
    }

    @Test
    void handleTransaction_quantity_notOk() {
        Storage.DB.put("banane",50);
        int [] purshases = {555,51,9000};
        for (int quantity : purshases) {
            fruitTransaction.setQuantity(quantity);
            Assertions.assertThrows(RuntimeException.class,
                    () -> purchaseOperation.handleTransaction(fruitTransaction),
                    "If Result < 0 should be Exception");
        }
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        fruitTransaction = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.handleTransaction(fruitTransaction),
                "If Transaction is null should be Exception");
    }

    @Test
    void handleTransaction_quantityResult_ok() {
        int balanceQuantity = 100;
        int[] purchases = {55, 10, 1};
        for (int quantity : purchases) {
            Storage.DB.put("banane", balanceQuantity);
            fruitTransaction.setQuantity(quantity);
            purchaseOperation.handleTransaction(fruitTransaction);
            int actual = balanceQuantity - quantity;
            Assertions.assertEquals(Storage.DB.get("banane"),actual,
                    "Quantity in DB not equals current Result");
        }
    }
}
