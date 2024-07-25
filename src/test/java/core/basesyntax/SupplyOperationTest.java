package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_APPLE_BALANCE = 100;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void testSupplyOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, INITIAL_APPLE_BALANCE);
        new SupplyOperation().handle(transaction, storage);
        assertEquals((Integer) INITIAL_APPLE_BALANCE, storage.getFruitQuantities().get(APPLE));
    }
}
