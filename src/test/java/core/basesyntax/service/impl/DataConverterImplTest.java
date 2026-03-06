package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void convertToTransaction_validData_Ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("s, apple,100");
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(2, actual.size());
        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        assertEquals("banana", actual.get(0).getFruit());
        assertEquals(20, actual.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        assertEquals("apple", actual.get(1).getFruit());
        assertEquals(100, actual.get(1).getQuantity());
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("q,banana,20");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> input = new ArrayList<>();
        input.add("type, fruit, quantity");
        input.add("b,banana, twenty");
        assertThrows(NumberFormatException.class,
                () -> dataConverter.convertToTransaction(input));
    }
}
