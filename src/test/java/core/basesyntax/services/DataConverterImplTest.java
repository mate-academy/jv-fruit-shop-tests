package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.data.DataConverterImpl;
import core.basesyntax.data.FruitTransaction;
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
    void convert_validInput_Ok() {
        List<String> input = List.of(
                "b,apple,10",
                "s,banana,5",
                "r,orange,2",
                "p,orange,2"
        );
        List<FruitTransaction> actual = converter.convert(input);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 2)
        );

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }

    @Test
    void convert_inValidOrderInput_NotOk() {
        List<String> wrongInput = List.of("b,1,banana", "s,1,apple", "r,orange,2");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(wrongInput));
    }

    @Test
    void convert_emptyList_Ok() {
        List<String> input = Collections.emptyList();
        List<FruitTransaction> actual = converter.convert(input);
        assertTrue(actual.isEmpty());
    }

    @Test
    void convert_lessOrMoreThanThreeValuesInALine_NoOk() {
        List<String> inputLess = List.of("b,apple", "s,banana", "r,orange");
        List<String> inputMore = List.of("b,apple,1,1", "s,banana,2,2", "r,orange,4,5");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(inputLess));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(inputMore));
    }
}
