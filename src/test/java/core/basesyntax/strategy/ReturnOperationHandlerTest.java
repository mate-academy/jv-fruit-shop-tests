package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
//
public class ReturnOperationHandlerTest {

    @Test
    public void get_returnOperationHandler_Ok() {
        FruitsStorage.fruitsStorage.put("banana", 40);
        FruitsStorage.fruitsStorage.put("apple", 80);
        FruitsStorage.fruitsStorage.put("orange", 30);

        FruitTransaction fruitTransaction10 = new FruitTransaction(OperationType.RETURN,
                "banana", 5);
        FruitTransaction fruitTransaction11 = new FruitTransaction(OperationType.RETURN,
                "apple", 10);
        FruitTransaction fruitTransaction12 = new FruitTransaction(OperationType.RETURN,
                "orange", 15);

        OperationHandler returnOperationHandler = new ReturnOperationHandlerImpl();
        returnOperationHandler.handle(fruitTransaction10);
        returnOperationHandler.handle(fruitTransaction11);
        returnOperationHandler.handle(fruitTransaction12);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 45);
        expectedStorage.put("apple", 90);
        expectedStorage.put("orange", 45);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_returnOperationHandlerWithNull_NotOk() {
        OperationHandler returnOperationHandler = new ReturnOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> returnOperationHandler.handle(null));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
