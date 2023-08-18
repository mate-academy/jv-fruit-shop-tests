package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private OperationHandlerService operationHandlerService;

    @BeforeEach
    void setUp() {
        operationHandlerService = new SupplyHandler();
        Storage.fruitsStorage.clear();
    }

    @Test
    void supplyOperationFruitExist_ok() {
        Map<String, Integer> expected = Map.of("banana", 10);
        Storage.fruitsStorage.put("banana", 5);
        operationHandlerService.handle(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5));
        Map<String, Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test
    void supplyOperationEmptyStorage_ok() {
        Map<String, Integer> expected = Map.of("banana", 20);
        operationHandlerService.handle(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20));
        Map<String, Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }
}
