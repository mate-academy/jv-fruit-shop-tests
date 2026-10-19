package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void convertToTransactions_validData_ok() {
        List<String> inputLines = List.of(
                "operation,fruit,quantity",
                "b,banana,10");
        List<FruitTransaction> result = dataConverter.convertToTransaction(inputLines);

        assertNotNull(result, "Result cant be null");
        assertEquals(1, result.size(), "Result shoud be 1");

        FruitTransaction transaction = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void convertToTransactions_emptyList_ok() {
        List<String> inputLines = Collections.EMPTY_LIST;
        List<FruitTransaction> result = dataConverter.convertToTransaction(inputLines);

        assertNotNull(result, "Result cant be null");
        assertTrue(result.isEmpty(), "Result should be empty");
    }
}
