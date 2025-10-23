package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitDto;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitAddition;
import core.basesyntax.service.impl.FruitReduction;
import core.basesyntax.service.impl.FruitServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private FruitService fruitService;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Map<FruitTransaction.Operation, FruitOperation> operations = new HashMap<>();
        FruitAddition addition = new FruitAddition();
        operations.put(FruitTransaction.Operation.BALANCE, addition);
        operations.put(FruitTransaction.Operation.SUPPLY, addition);
        operations.put(FruitTransaction.Operation.RETURN, addition);
        operations.put(FruitTransaction.Operation.PURCHASE, new FruitReduction());
        fruitService = new FruitServiceImpl(operations);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void makeOperationsOnFruitsDto_validScenario_updatesStorage() {
        List<FruitDto> transactions = List.of(
                new FruitDto(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitDto(FruitTransaction.Operation.SUPPLY, "banana", 5),
                new FruitDto(FruitTransaction.Operation.PURCHASE, "banana", 3),
                new FruitDto(FruitTransaction.Operation.RETURN, "banana", 2),
                new FruitDto(FruitTransaction.Operation.SUPPLY, "apple", 4)
        );

        fruitService.makeOperationsOnFruitsDto(transactions);

        Map<String, Long> expected = Map.of("banana", 14L, "apple", 4L);
        assertEquals(expected, fruitService.gerFruitReport());
    }

    @Test
    void makeOperationsOnFruitsDto_missingOperationHandler_throwsException() {
        Map<FruitTransaction.Operation, FruitOperation> operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE, new FruitAddition());
        FruitService serviceWithoutReturn = new FruitServiceImpl(operations);
        List<FruitDto> transactions = List.of(new FruitDto(
                FruitTransaction.Operation.RETURN, "banana", 1));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serviceWithoutReturn.makeOperationsOnFruitsDto(transactions));

        assertEquals("Operation handler not found for operation: RETURN", exception.getMessage());
    }
}
