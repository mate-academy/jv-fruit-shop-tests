package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {

    @Test
    public void get_purchaseOperationHandler_Ok() {
        FruitsStorage.fruitsStorage.put("banana", 40);
        FruitsStorage.fruitsStorage.put("apple", 80);
        FruitsStorage.fruitsStorage.put("orange", 30);

        FruitTransaction fruitTransaction7 = new FruitTransaction(OperationType.PURCHASE,
                "banana", 30);
        FruitTransaction fruitTransaction8 = new FruitTransaction(OperationType.PURCHASE,
                "apple", 45);
        FruitTransaction fruitTransaction9 = new FruitTransaction(OperationType.PURCHASE,
                "orange", 20);

        OperationHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        purchaseOperationHandler.handle(fruitTransaction7);
        purchaseOperationHandler.handle(fruitTransaction8);
        purchaseOperationHandler.handle(fruitTransaction9);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 10);
        expectedStorage.put("apple", 35);
        expectedStorage.put("orange", 10);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_purchaseOperationHandlerWithNull_NotOk() {
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> purchaseOperationHandler.handle(null));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
