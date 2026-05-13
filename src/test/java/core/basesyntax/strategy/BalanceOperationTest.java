package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final BalanceOperation balanceOperation = new BalanceOperation();

    @AfterEach
    void tearDown() {
        Storage.getInstance().getFruitStorage().clear();
    }

    @Test
    void handle_newFruit_setsFruitQuantity() {
        balanceOperation.handle("banana", 20);

        assertEquals(20, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void handle_existingFruit_replacesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 10);

        balanceOperation.handle("banana", 20);

        assertEquals(20, Storage.getInstance().getFruitStorage().get("banana"));
    }
}
