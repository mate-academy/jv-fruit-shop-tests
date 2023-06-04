package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
//
public class BalanceOperationHandlerTest {

    @Test
    public void get_balanceOperationHandler_Ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(OperationType.BALANCE,
                "banana", 40);
        FruitTransaction fruitTransaction2 = new FruitTransaction(OperationType.BALANCE,
                "apple", 80);
        FruitTransaction fruitTransaction3 = new FruitTransaction(OperationType.BALANCE,
                "orange", 30);

        OperationHandler balanceOperationHandler = new BalanceOperationHandlerImpl();
        balanceOperationHandler.handle(fruitTransaction1);
        balanceOperationHandler.handle(fruitTransaction2);
        balanceOperationHandler.handle(fruitTransaction3);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 40);
        expectedStorage.put("apple", 80);
        expectedStorage.put("orange", 30);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_balanceOperationHandlerWithNull_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> balanceOperationHandler.handle(null));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
