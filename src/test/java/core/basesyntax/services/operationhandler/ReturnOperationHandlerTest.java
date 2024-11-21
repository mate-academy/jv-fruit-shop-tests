package core.basesyntax.services.operationhandler;

import core.basesyntax.db.DB;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static IOperationHandler operationHandler;

    @BeforeAll
    public static void beforeAll() {
        operationHandler = new ReturnOperationHandler();
    }

    @BeforeEach
    public void setUp() {
        DB.getFruitsDB().put("banana", 10);
    }

    @AfterEach
    public void tearDown() {
        DB.getFruitsDB().clear();
    }

    @Test
    public void handleOperation_shouldThrowException_ifQuantityLessThanZero() {
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> operationHandler.handleOperation("banana", -5)
        );
    }

    @Test
    public void handleOperation_shouldThrowException_ifFruitNotExistInDatabase() {
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> operationHandler.handleOperation("apple", 10)
        );
    }

    @Test
    public void handleOperation_shouldIncreaseFruitQuantityInDatabase() {
        operationHandler.handleOperation("banana", 5);

        Assertions.assertEquals(15, DB.getFruitsDB().get("banana"));
    }
}
