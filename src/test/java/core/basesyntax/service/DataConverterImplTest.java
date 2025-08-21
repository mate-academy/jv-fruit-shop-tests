package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.storage.Storage;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
        dataConverter = null;
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = List.of("type,fruit,quantity", "b,apple,100");
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertEquals(1, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_multipleValidData_ok() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,apple,20"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertEquals(3, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
    }

    @Test
    void convertToTransaction_emptyList_returnEmptyList() {
        List<String> data = Collections.emptyList();
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_nullList_returnEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_invalidNumberOfParts_skipLine() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "invalid,line,extra,part",
                "s,banana",
                "p,apple,20"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertEquals(2, result.size());
    }

    @Test
    void convertToTransaction_invalidOperation_throwRuntimeException() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "x,apple,100"
        );
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(data)
        );
    }

    @Test
    void convertToTransaction_invalidQuantity_throwRuntimeException() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "s,banana,abc"
        );
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(data)
        );
    }
}
