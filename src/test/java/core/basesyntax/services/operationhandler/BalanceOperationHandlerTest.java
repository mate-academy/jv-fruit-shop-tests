package core.basesyntax.services.operationhandler;

import core.basesyntax.db.DB;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static IOperationHandler operationHandler;

    @BeforeAll
    public static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
    }

    @AfterEach
    public void tearDown() {
        DB.getFruitsDB().clear();
    }

    @Test
    public void handleOperation_shouldThrowException_ifFruitQuantityIsLessThanZero() {
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> operationHandler.handleOperation("apple", -5),
                "Should throw InvalidParameterException if fruit quantity less than zero"
        );
    }

    @Test
    public void handleOperation_shouldSetFruitToDB() {
        operationHandler.handleOperation("banana", 5);

        Assertions.assertTrue(
                DB.getFruitsDB().containsKey("banana"),
                "Should create fruit in database"
        );
        Assertions.assertEquals(
                5,
                DB.getFruitsDB().get("banana"),
                "Created fruit quantity should be equal to provided parameter"
        );
    }
}
