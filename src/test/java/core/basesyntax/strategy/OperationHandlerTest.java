package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    @BeforeEach
    void setUp() {
        Storage.getInstance().getFruitStorage().clear();
    }

    @Test
    void balanceOperationSetsFruitQuantity() {
        new BalanceOperation().handle("banana", 20);

        assertEquals(20, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void supplyOperationIncreasesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 20);

        new SupplyOperation().handle("banana", 15);

        assertEquals(35, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void supplyOperationAddsMissingFruit() {
        new SupplyOperation().handle("apple", 15);

        assertEquals(15, Storage.getInstance().getFruitStorage().get("apple"));
    }

    @Test
    void returnOperationIncreasesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 20);

        new ReturnOperation().handle("banana", 5);

        assertEquals(25, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void purchaseOperationDecreasesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 20);

        new PurchaseOperation().handle("banana", 7);

        assertEquals(13, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void purchaseOperationWithNotEnoughFruitThrowsException() {
        Storage.getInstance().getFruitStorage().put("banana", 5);

        assertThrows(RuntimeException.class, () -> new PurchaseOperation().handle("banana", 7));
    }
}
