package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler supplyOperation;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banane");
    }

    @Test
    void handleTransaction_quantityResult_notOk() {
        Storage.DB.put("banane",50);
        int [] returns = {-555,-51,-9000};
        for (int quantity : returns) {
            fruitTransaction.setQuantity(quantity);
            Assertions.assertThrows(RuntimeException.class,
                    () -> supplyOperation.handleTransaction(fruitTransaction),
                    "If Result < 0 should be Exception");
        }
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        fruitTransaction = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperation.handleTransaction(fruitTransaction),
                "If Transaction is null should be Exception");
    }

    @Test
    void handleTransaction_quantityResult_ok() {
        int balanceQuantity = 50;
        int[] supplies = {555, 1, 100};
        for (int quantity : supplies) {
            Storage.DB.put("banane", balanceQuantity);
            fruitTransaction.setQuantity(quantity);
            supplyOperation.handleTransaction(fruitTransaction);
            int actual = balanceQuantity + quantity;
            Assertions.assertEquals(Storage.DB.get("banane"),actual,
                    "Quantity in DB not equals current Result");
        }
    }
}
