package core.basesyntax.converter;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_InvalidData_Ok() {
        assertTrue(converter.convertToTransaction(new ArrayList<>(Collections
                .singleton("first second third"))).isEmpty());
    }

    @Test
    void convertToTransaction_EmptyData_Ok() {
        assertTrue(converter.convertToTransaction(new ArrayList<>()).isEmpty());
    }

    @Test
    void convertToTransaction_ValidMultipleData_Ok() {
        List<String> input = List.of(
                "header",
                "b,apple,10",
                "s,orange,20"
        );
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertEquals(2, result.size());
        assertEquals(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 10), result.get(0));
        assertEquals(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "orange", 20), result.get(1));
        Assertions.assertNotEquals(result.get(0).hashCode(), result.get(1).hashCode());
    }
}
