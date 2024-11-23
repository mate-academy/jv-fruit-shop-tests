package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    @Test
    void testConvert_EmptyData() {
        DataConverter dataConverter = new DataConverter();
        List<String> rawData = List.of(); // Empty list

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(0, transactions.size());
    }

    @Test
    void testConvert_InvalidData() {
        DataConverter dataConverter = new DataConverter();
        List<String> rawData = List.of("invalid,line,format");

        assertThrows(IllegalArgumentException.class, () -> dataConverter.convert(rawData));
    }

    @Test
    void testConvert() {
        // Arrange
        DataConverter dataConverter = new DataConverter();
        List<String> rawData = List.of("b,banana,100", "s,apple,50", "p,banana,30");

        // Act
        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        // Assert
        assertEquals(3, transactions.size());
        assertEquals("b", transactions.get(0).getType());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(100, transactions.get(0).getQuantity());
    }
}
