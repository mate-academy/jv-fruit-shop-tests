package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static Operation purchaseOperation;

    @BeforeAll
    static void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    public void performOperation_enoughFruits_ok() {
        Storage.getFruits().put("apple", 70);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OperationType.PURCHASE, "apple", 50);
        purchaseOperation.performOperation(fruitTransaction);
        assertEquals(20, Storage.getFruits().get("apple"));
    }

    @Test
    public void performOperation_enoughFruits_notOk() {
        Storage.getFruits().put("apple", 70);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OperationType.PURCHASE, "apple", 80);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.performOperation(fruitTransaction));
    }
}
