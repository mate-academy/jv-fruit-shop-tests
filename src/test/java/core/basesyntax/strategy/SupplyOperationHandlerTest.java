package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static OperationHandler balanceHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new SupplyOperationHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,"Apple", 10);

    }

    @AfterEach
    public void cleanUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handle_positiveQuantity_Ok() {
        fruitTransaction.setQuantity(10);
        Storage.fruitStorage.put("Apple", 10);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Apple", 20);
        balanceHandler.handle(fruitTransaction);
        assertEquals(expectedStorage, Storage.fruitStorage);
    }

    @Test
    void handle_zeroQuantity_notOk() {
        fruitTransaction.setQuantity(0);
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(fruitTransaction));
    }

    @Test
    void handle_negativQuantity_notOk() {
        fruitTransaction.setQuantity(-5);
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(fruitTransaction));
    }
}
