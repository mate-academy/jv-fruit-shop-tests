package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.impl.DataProcessorServiceImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataProcessorServiceTest {
    private static DataProcessorService dataProcessorService;

    @BeforeAll
    static void beforeAll() {
        dataProcessorService = new DataProcessorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void processData_validData_ok() {
        List<String> data = List.of("b,banana,10", "b,apple,20", "s,banana,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10)
        );
        List<FruitTransaction> fruitTransactions = dataProcessorService.processInputData(data);
        assertEquals(expected, fruitTransactions);
    }

    @Test
    void processData_invalidQuantity_notOk() {
        List<String> data = List.of("b,banana,-200", "b,apple,20", "s,banana,10");
        assertThrows(RuntimeException.class, () -> dataProcessorService.processInputData(data));
    }

    @Test
    void processData_invalidOperationType_notOk() {
        List<String> data = List.of("buy,banana,-200", "b,apple,20", "s,banana,10");
        assertThrows(RuntimeException.class, () -> dataProcessorService.processInputData(data));
    }

    @Test
    void processData_nonNumericQuantity_notOk() {
        List<String> data = List.of("b,banana,abc", "b,apple,abc", "s,banana,abc");
        assertThrows(RuntimeException.class, () -> dataProcessorService.processInputData(data));
    }
}
