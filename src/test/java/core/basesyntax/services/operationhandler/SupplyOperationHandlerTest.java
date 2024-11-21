package core.basesyntax.services.operationhandler;

import core.basesyntax.db.DB;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static IOperationHandler operationHandler;

    @BeforeAll
    public static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
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
    public void handleOperation_shouldAddQuantity_ifFruitExist() {
        operationHandler.handleOperation("banana", 5);

        Assertions.assertEquals(15, DB.getFruitsDB().get("banana"));
    }

    @Test
    public void handleOperation_shouldCreateFruit_ifFruitNotExist() {
        operationHandler.handleOperation("apple", 5);

        Assertions.assertTrue(DB.getFruitsDB().containsKey("apple"));
        Assertions.assertEquals(5, DB.getFruitsDB().get("apple"));
    }
}
