package core.basesyntax.services.operationhandler;

import core.basesyntax.db.DB;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static IOperationHandler operationHandler;

    @BeforeAll
    public static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
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
                () -> operationHandler.handleOperation("banana", -5),
                "Should throw exception if fruit quantity less than zero"
        );
    }

    @Test
    public void handleOperation_shouldThrowException_ifFruitNotExistInDatabase() {
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> operationHandler.handleOperation("apple", 10),
                "Should throw exception if fruit does not exist"
        );
    }

    @Test
    public void handleOperation_shouldThrowException_ifDatabaseDontHaveEnoughFruits() {
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> operationHandler.handleOperation("banana", 20),
                "Should throw exception if not enough fruits in database"
        );
    }

    @Test
    public void handleOperation_shouldDecreaseFruitsQuantityInDatabase() {
        operationHandler.handleOperation("banana", 5);

        Assertions.assertEquals(
                5,
                DB.getFruitsDB().get("banana"),
                "Decreased fruit quantity should be equal to provided parameter"
        );
    }
}
