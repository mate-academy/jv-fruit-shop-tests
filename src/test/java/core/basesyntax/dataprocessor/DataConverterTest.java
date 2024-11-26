package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {

    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverter();
    }

    @Test
    void convert_validRawData_returnsCorrectTransactions() {
        List<String> rawData = Arrays.asList(
                "b,apple,50",
                "s,banana,20",
                "p,orange,15"
        );

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(3, transactions.size());
        assertEquals(new FruitTransaction("b", "apple", 50), transactions.get(0));
        assertEquals(new FruitTransaction("s", "banana", 20), transactions.get(1));
        assertEquals(new FruitTransaction("p", "orange", 15), transactions.get(2));
    }

    @Test
    void convert_emptyRawData_returnsEmptyList() {
        List<String> rawData = List.of();

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(0, transactions.size());
    }

    @Test
    void convert_invalidQuantity_throwsNumberFormatException() {
        List<String> rawData = List.of("b,apple,invalid");

        assertThrows(NumberFormatException.class, () -> dataConverter.convert(rawData));
    }

    @Test
    void convert_missingFields_throwsArrayIndexOutOfBoundsException() {
        List<String> rawData = List.of("b,apple");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> dataConverter.convert(rawData));
    }
}
