package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final String BANANA = "banana";
    private static final int BALANCE_QUANTITY = 20;

    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void testBalanceOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                BANANA, BALANCE_QUANTITY);
        new BalanceOperation().handle(transaction, storage);
        assertEquals(BALANCE_QUANTITY, storage.getFruitQuantities().get(BANANA));
    }
}
