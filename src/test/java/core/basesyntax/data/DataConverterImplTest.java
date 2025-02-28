package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void validInput_Ok() {
        List<String> input = List.of("b,apple,10", "s,banana,5", "r,orange,2");
        List<FruitTransaction> actual = converter.convert(input);

        assertEquals(3, actual.size());

        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        assertEquals("apple", actual.get(0).getFruit());
        assertEquals(10, actual.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        assertEquals("banana", actual.get(1).getFruit());
        assertEquals(5, actual.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, actual.get(2).getOperation());
        assertEquals("orange", actual.get(2).getFruit());
        assertEquals(2, actual.get(2).getQuantity());
    }

    @Test
    void validOrderInput_Ok() {
        List<String> rightInput = List.of("b,apple,10", "s,banana,5", "r,orange,2");
        List<String> wrongInput = List.of("b,1,banana", "s,1,apple", "r,orange,2");

        assertDoesNotThrow(() -> converter.convert(rightInput));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(wrongInput));
    }

    @Test
    void emptyList_Ok() {
        List<String> input = Collections.emptyList();
        List<FruitTransaction> actual = converter.convert(input);
        assertTrue(actual.isEmpty());
    }

    @Test
    void lessOrMoreThanThreeValuesInALine_NoOk() {
        List<String> inputLess = List.of("b,apple", "s,banana", "r,orange");
        List<String> inputMore = List.of("b,apple,1,1", "s,banana,2,2", "r,orange,4,5");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(inputLess));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(inputMore));
    }
}
