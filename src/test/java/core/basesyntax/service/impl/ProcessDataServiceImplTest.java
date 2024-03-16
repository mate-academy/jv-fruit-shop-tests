package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ProcessDataService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataServiceImplTest {
    private ProcessDataService processDataService;

    @BeforeEach
    void setUp() {
        processDataService = new ProcessDataServiceImpl();
        Storage.getInstance().setElements(new HashMap<>());
    }

    @AfterEach
    void clear() {
        Storage.getInstance().setElements(new HashMap<>());
    }

    @Test
    void successfulProcessing_processData_ok() {
        List<Fruit> fruits = List.of(
                new Fruit("banana", Operation.BALANCE, 20),
                new Fruit("apple", Operation.BALANCE, 20),
                new Fruit("apple", Operation.PURCHASE, 10)
        );
        processDataService.processData(fruits);
        Map<String, Integer> expected = Map.of(
                "banana", 20,
                "apple", 10
        );
        assertEquals(expected, Storage.getInstance().getElements(),
                "Storage should be updated correctly.");
    }

    @Test
    void negativeQuantityResult_processData_throwsRuntimeException() {
        List<Fruit> fruits = List.of(
                new Fruit("apple", Operation.PURCHASE, 10));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> processDataService.processData(fruits));
        assertEquals("Total amount for apple cannot be negative",
                exception.getMessage(), "Exception message should match.");
    }

    @Test
    void emptyListNoChangeToStorage_processData_ok() {
        processDataService.processData(List.of());
        assertTrue(Storage.getInstance().getElements().isEmpty(),
                "Storage should remain unchanged.");
    }
}
