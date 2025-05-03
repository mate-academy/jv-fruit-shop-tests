package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 30;
    private static final int RETURN_QUANTITY = 20;
    private static final int EXPECTED_QUANTITY = INITIAL_QUANTITY + RETURN_QUANTITY;

    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        storage.addFruit(FRUIT_NAME, INITIAL_QUANTITY);
    }

    @Test
    public void testReturnOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_NAME, RETURN_QUANTITY);
        new ReturnOperation().handle(transaction, storage);
        assertEquals((Integer) EXPECTED_QUANTITY, storage.getFruitQuantities().get(FRUIT_NAME));
    }
}
