package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20"
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(1, actual.size());
        FruitTransaction transaction = actual.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void convertToTransaction_multipleRows_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "s,apple,50",
                "p,apple,10"
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(2, actual.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, actual.get(1).getOperation());
    }

    @Test
    void convertToTransaction_onlyHeader_emptyResult() {
        List<String> input = List.of("type,fruit,quantity");
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(0, actual.size());
    }

    @Test
    void convertToTransaction_onlyReturn_operation() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "r,apple,50"
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(1, actual.size());
        assertEquals(FruitTransaction.Operation.RETURN, actual.get(0).getOperation());
    }

    @Test
    void convertToTransaction_unknownOperationCode_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,10"
        );
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_emptyList_emptyResult() {
        List<String> input = List.of();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(0, actual.size());
    }

    @Test
    void convertToTransaction_invalidQuantityFormat_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );
        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(input));
    }
}
