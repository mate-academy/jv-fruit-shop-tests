package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationHandler returnOperation;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
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
                    () -> returnOperation.handleTransaction(fruitTransaction),
                    "If Result < 0 should be Exception");
        }
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        fruitTransaction = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> returnOperation.handleTransaction(fruitTransaction),
                "If Transaction is null should be Exception");
    }

    @Test
    void handleTransaction_quantityResult_ok() {
        int startQuantity = 50;
        int[] returns = {555, 10, 1};
        for (int quantity : returns) {
            Storage.DB.put("banane", startQuantity);
            fruitTransaction.setQuantity(quantity);
            returnOperation.handleTransaction(fruitTransaction);
            int actual = startQuantity + quantity;
            Assertions.assertEquals(Storage.DB.get("banane"),actual,
                    "Quantity in DB not equals current Result");
        }
    }
}
