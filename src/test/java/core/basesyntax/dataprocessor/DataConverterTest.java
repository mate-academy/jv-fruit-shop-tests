package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {

    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";

    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";

    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverter();
    }

    @Test
    void convert_validRawData_returnsCorrectTransactions() {
        List<String> rawData = Arrays.asList(
                BALANCE + "," + APPLE + ",50",
                SUPPLY + "," + BANANA + ",20",
                PURCHASE + "," + ORANGE + ",15"
        );

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(3, transactions.size());
        assertEquals(new FruitTransaction(BALANCE, APPLE, 50), transactions.get(0));
        assertEquals(new FruitTransaction(SUPPLY, BANANA, 20), transactions.get(1));
        assertEquals(new FruitTransaction(PURCHASE, ORANGE, 15), transactions.get(2));
    }

    @Test
    void convert_emptyRawData_returnsEmptyList() {
        List<String> rawData = List.of();

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(0, transactions.size());
    }

    @Test
    void convert_invalidQuantity_throwsNumberFormatException() {
        List<String> rawData = List.of(BALANCE + "," + APPLE + ",invalid");

        assertThrows(NumberFormatException.class, () -> dataConverter.convert(rawData));
    }

    @Test
    void convert_missingFields_throwsArrayIndexOutOfBoundsException() {
        List<String> rawData = List.of(BALANCE + "," + APPLE);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> dataConverter.convert(rawData));
    }
}
