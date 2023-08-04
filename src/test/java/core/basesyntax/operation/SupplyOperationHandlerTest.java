package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final String VALID_FRUIT_NAME = "banana";
    private static final Integer VALID_FRUIT_AMOUNT = 10;
    private static final String OPERATION_CODE = "b";
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperationHandler();
        Storage.STORAGE.clear();
    }

    @Test
    void executeOperation_ValidDataStorageEmpty_ok() {
        Map<String, Integer> expected = Map.of(VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT);

        operationHandler.executeOperation(new FruitTransaction(
                OPERATION_CODE, VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT));
        Map<String, Integer> actual = Storage.STORAGE;

        assertEquals(expected, actual);
    }

    @Test
    void executeOperation_ValidDataStorageFilled_ok() {
        Map<String, Integer> expected = Map.of(VALID_FRUIT_NAME,
                VALID_FRUIT_AMOUNT + VALID_FRUIT_AMOUNT);
        Storage.STORAGE.put(VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT);

        operationHandler.executeOperation(new FruitTransaction(
                OPERATION_CODE, VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT));
        Map<String, Integer> actual = Storage.STORAGE;

        assertEquals(expected, actual);
    }
}
