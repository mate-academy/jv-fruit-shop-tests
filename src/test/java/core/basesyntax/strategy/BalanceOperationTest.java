package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        balanceOperation = new BalanceOperation(storage);
    }

    @Test
    public void handle_addFruit_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        balanceOperation.handle(fruitTransaction);
        assertTrue(storage.findFruit("banana"));
        assertEquals(20, storage.findQuantity("banana"));
    }
}
