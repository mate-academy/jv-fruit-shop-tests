package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ShouldSkipHeader_WhenHeaderPresent() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,apple,20"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertEquals(1, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_ShouldConvertValidData() {
        List<String> data = List.of(
                "b,apple,20",
                "s,banana,15",
                "p,orange,5"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertEquals(3, transactions.size());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(15, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("orange", transactions.get(2).getFruit());
        assertEquals(5, transactions.get(2).getQuantity());
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenOperationCodeIsInvalid() {
        List<String> data = List.of("x,apple,10");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data)
        );
        assertEquals("Invalid operation code: x", exception.getMessage());
    }

    @Test
    void convertToTransaction_ShouldReturnEmptyList_WhenInputListIsEmpty() {
        List<String> data = new ArrayList<>();

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenQuantityIsInvalid() {
        List<String> data = List.of("b,apple,abc");

        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(data));
    }

}
