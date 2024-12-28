package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter converter;

    @BeforeAll
    static void beforeAll() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_Ok() {
        List<String> inputRecords = List.of("type,fruit,quantity", "b,banana,20");
        FruitTransaction transaction = converter.convertToTransaction(inputRecords).get(0);

        FruitTransaction.Operation actualOperation = transaction.getOperation();
        String actualFruit = transaction.getFruit();
        int actualQuantity = transaction.getQuantity();

        assertEquals(FruitTransaction.Operation.BALANCE, actualOperation);
        assertEquals("banana", actualFruit);
        assertEquals(20, actualQuantity);
    }

    @Test
    void convertToTransaction_emptyData_Ok() {
        List<String> emptyInputRecord = List.of();
        List<FruitTransaction> list = converter.convertToTransaction(emptyInputRecord);
        assertTrue(list.isEmpty());
    }

    @Test
    void convertToTransaction_onlyHeadLineData_Ok() {
        List<String> onlyHeadLineRecord = List.of("type,fruit,quantity");
        List<FruitTransaction> resultList = converter.convertToTransaction(onlyHeadLineRecord);
        assertTrue(resultList.isEmpty());
    }

    @Test
    void convertToTransaction_invalidData_NotOk() {
        List<String> invalidOperation = List.of("type,fruit,quantity", "xfruit,20");
        assertThrows(RuntimeException.class, () -> converter
                .convertToTransaction(invalidOperation));
    }
}
