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
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
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
                new Fruit(FRUIT_BANANA, Operation.BALANCE, 20),
                new Fruit(FRUIT_APPLE, Operation.BALANCE, 20),
                new Fruit(FRUIT_APPLE, Operation.PURCHASE, 10)
        );
        processDataService.processData(fruits);
        Map<String, Integer> expected = Map.of(
                FRUIT_BANANA, 20,
                FRUIT_APPLE, 10
        );
        assertEquals(expected, Storage.getInstance().getElements(),
                "Storage should be updated correctly.");
    }

    @Test
    void negativeQuantityResult_processData_throwsRuntimeException() {
        List<Fruit> fruits = List.of(
                new Fruit(FRUIT_APPLE, Operation.PURCHASE, 10));
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
