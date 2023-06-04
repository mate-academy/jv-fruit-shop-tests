package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class SupplyOperationHandlerTest {

    @Test
    public void get_supplyOperationHandler_Ok() {
        FruitsStorage.fruitsStorage.put("banana", 40);
        FruitsStorage.fruitsStorage.put("apple", 80);
        FruitsStorage.fruitsStorage.put("orange", 30);

        FruitTransaction fruitTransaction4 = new FruitTransaction(OperationType.SUPPLY,
                "banana", 10);
        FruitTransaction fruitTransaction5 = new FruitTransaction(OperationType.SUPPLY,
                "apple", 15);
        FruitTransaction fruitTransaction6 = new FruitTransaction(OperationType.SUPPLY,
                "orange", 20);

        OperationHandler supplyOperationHandler = new SupplyOperationHandlerImpl();
        supplyOperationHandler.handle(fruitTransaction4);
        supplyOperationHandler.handle(fruitTransaction5);
        supplyOperationHandler.handle(fruitTransaction6);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 50);
        expectedStorage.put("apple", 95);
        expectedStorage.put("orange", 50);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_supplyOperationHandlerWithNull_NotOk() {
        OperationHandler supplyOperationHandler = new SupplyOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> supplyOperationHandler.handle(null));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
