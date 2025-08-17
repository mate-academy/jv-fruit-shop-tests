package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = List.of("type,fruit,quantity", "b,apple,100");
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        Assertions.assertEquals("apple", result.get(0).getFruit());
        Assertions.assertEquals(100, result.get(0).getQuantity());
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

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        Assertions.assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
    }

    @Test
    void convertToTransaction_emptyList_returnEmptyList() {
        List<String> data = Collections.emptyList();
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_nullList_returnEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(null);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_invalidNumberOfParts_skipLine() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "invalid,line,extra,part", // Некоректний рядок
                "s,banana", // Некоректний рядок
                "p,apple,20"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void convertToTransaction_invalidOperation_throwRuntimeException() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "x,apple,100"
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(data)
        );
    }

    @Test
    void convertToTransaction_invalidQuantity_throwRuntimeException() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "s,banana,abc"
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(data)
        );
    }
}
