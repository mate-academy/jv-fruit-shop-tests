package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        operationHandler = new PurchaseOperation();
        FruitShop.storage.put("Apple", 10);
    }

    @Test
    public void purchaseExitingFruitEnoughQuantity_reducesStock() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "Apple", 8);
        operationHandler.process(fruitTransaction);
        int actualQuantity = FruitShop.getQuantity("Apple");
        assertEquals(2, actualQuantity);

    }

    @Test
    public void purchaseUnknownFruit_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "Ananas", 12);
        assertThrows(RuntimeException.class, () -> operationHandler.process(fruitTransaction));
    }

    @Test
    public void purchaseExitingFruitNotEnoughQuantity_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "Apple", 12);
        assertThrows(RuntimeException.class, () -> operationHandler.process(fruitTransaction));
    }

    @AfterEach
    public void tearDown() {
        FruitShop.storage.clear();
    }
}
