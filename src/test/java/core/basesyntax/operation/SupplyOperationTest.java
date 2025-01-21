package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static final int INITIAL_QUANTITY = 100;
    private static final FruitTransaction INVALID_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", -10);
    private static final int SUPPLY_QUANTITY = 50;
    private static final String TEST_FRUIT = "banana";
    private static final FruitTransaction TRANSACTION_EXAMPLE =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, TEST_FRUIT, SUPPLY_QUANTITY);
    private OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.put(TEST_FRUIT, INITIAL_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_validSupply_increasesQuantity() {
        supplyOperation.handle(TRANSACTION_EXAMPLE);

        int expectedQuantity = INITIAL_QUANTITY + SUPPLY_QUANTITY;
        assertEquals(expectedQuantity, Storage.getQuantity(TEST_FRUIT));
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.handle(INVALID_TRANSACTION),
                "Should throw IllegalArgumentException when returning negative quantity");
    }
}
