package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static final int INITIAL_QUANTITY = 100;
    private static final int RETURN_QUANTITY = 20;
    private static final String TEST_FRUIT = "banana";
    private static final FruitTransaction TRANSACTION_EXAMPLE =
            new FruitTransaction(FruitTransaction.Operation.RETURN, TEST_FRUIT, RETURN_QUANTITY);
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        Storage.put(TEST_FRUIT, INITIAL_QUANTITY);
    }

    @Test
    void handle_validReturn_increasesQuantity() {
        returnOperation.handle(TRANSACTION_EXAMPLE);
        int expectedQuantity = INITIAL_QUANTITY + RETURN_QUANTITY;
        assertEquals(expectedQuantity, Storage.getQuantity(TEST_FRUIT));
    }
}
