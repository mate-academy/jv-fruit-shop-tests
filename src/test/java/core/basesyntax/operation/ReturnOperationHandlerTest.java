package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static final String VALID_FRUIT_NAME = "banana";
    private static final Integer VALID_FRUIT_AMOUNT = 10;
    private static final String OPERATION_CODE = "b";
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void executeOperation_ValidData_ok() {
        Map<String, Integer> expected = Map.of(VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT);
        operationHandler.executeOperation(new FruitTransaction(
                OPERATION_CODE, VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT));
        Map<String, Integer> actual = Storage.STORAGE;
        Assertions.assertEquals(expected, actual);
    }
}
