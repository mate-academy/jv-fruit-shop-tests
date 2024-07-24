package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    public static final String BANANA = "banana";
    public static final int INITIAL_QUANTITY = 50;
    public static final int PURCHASE_QUANTITY = 10;

    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        storage.addFruit(BANANA, INITIAL_QUANTITY);
    }

    @Test
    public void testPurchaseOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, PURCHASE_QUANTITY);
        new PurchaseOperation().handle(transaction, storage);
        assertEquals((Integer) (INITIAL_QUANTITY - PURCHASE_QUANTITY),
                storage.getFruitQuantities().get(BANANA));
    }
}
