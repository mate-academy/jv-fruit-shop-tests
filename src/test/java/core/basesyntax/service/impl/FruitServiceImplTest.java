package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;
import core.basesyntax.strategy.impl.PurchaseOperationsHandler;
import core.basesyntax.strategy.impl.ReturnOperationsHandler;
import core.basesyntax.strategy.impl.SupplyOperationsHandler;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;
    private static OperationsHandler operationsHandler;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void input_ValidData_Ok() {
        List<String> data = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(ActivityType.BALANCE, "banana", 20),
                new FruitTransaction(ActivityType.BALANCE, "apple", 100),
                new FruitTransaction(ActivityType.SUPPLY, "banana", 100)
        );
        List<FruitTransaction> actual = fruitService.processFruitLines(data);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void input_QuantityLessAmount_notOk() {
        operationsHandler = new PurchaseOperationsHandler();
        Storage.getStorage().put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                ActivityType.PURCHASE, "banana", 70);
        assertThrows(RuntimeException.class,
                () -> operationsHandler.useOperation(fruitTransaction));
    }

    @Test
    void input_ReturnValidAmount_Ok() {
        operationsHandler = new ReturnOperationsHandler();
        Storage.getStorage().put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                ActivityType.RETURN, "banana", 20);
        assertDoesNotThrow(() -> operationsHandler.useOperation(fruitTransaction));
        int actual = Storage.getStorage().get("banana");
        assertEquals(40, actual);
    }

    @Test
    void input_SupplyValidAmount_notOK() {
        operationsHandler = new SupplyOperationsHandler();
        Storage.getStorage().put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                ActivityType.SUPPLY, "banana", 50);
        assertDoesNotThrow(() -> operationsHandler.useOperation(fruitTransaction));

        int actual = Storage.getStorage().get("banana");
        assertEquals(70, actual);
    }

    @Test
    void input_QuantityIsNegative_NotOk() {
        List<String> data = List.of("b,banana,-1", "b,apple,-1", "s,banana,-1");
        assertThrows(RuntimeException.class, () -> fruitService.processFruitLines(data));
    }

    @Test
    void input_InvalidOperation_NotOk() {
        List<String> data = List.of("d,banana,20", "a,apple,100", "s,banana,100");
        assertThrows(RuntimeException.class, () -> fruitService.processFruitLines(data));
    }
}
