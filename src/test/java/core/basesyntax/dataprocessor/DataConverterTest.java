package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String SEPARATOR = ",";
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
        List<String> rawData = List.of(
                BALANCE + SEPARATOR + APPLE + ",50",
                SUPPLY + SEPARATOR + BANANA + ",20",
                PURCHASE + SEPARATOR + ORANGE + ",15"
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
    void convert_invalidQuantity_throwsCustomException() {
        List<String> rawData = List.of(BALANCE + SEPARATOR + APPLE + ",invalid");

        DataConversionException exception = assertThrows(
                DataConversionException.class,
                () -> dataConverter.convert(rawData)
        );
        assertEquals("Invalid quantity in record: "
                + BALANCE + SEPARATOR + APPLE + ",invalid", exception.getMessage());
    }

    @Test
    void convert_missingFields_throwsCustomException() {
        List<String> rawData = List.of(BALANCE + SEPARATOR + APPLE);

        DataConversionException exception = assertThrows(
                DataConversionException.class,
                () -> dataConverter.convert(rawData)
        );
        assertEquals("Invalid record format: "
                + BALANCE + SEPARATOR + APPLE, exception.getMessage());
    }
}
