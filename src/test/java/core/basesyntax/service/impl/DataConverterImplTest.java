package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validInput_success() {
        List<String> inputData = Arrays.asList("b,apple,10", "s,banana,20");

        List<FruitTransaction> result = dataConverter.convertToTransaction(inputData);

        assertEquals(2, result.size(), "Expected size of transaction is 2");

        FruitTransaction firstTransaction = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation());
        assertEquals("apple", firstTransaction.getFruit());
        assertEquals(10, firstTransaction.getQuantity());

        FruitTransaction secondTransaction = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, secondTransaction.getOperation());
        assertEquals("banana", secondTransaction.getFruit());
        assertEquals(20, secondTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_nullOrEmptyLine_throwsException() {
        List<String> inputData = Arrays.asList("b,apple,10", "", null, "s,banana,20");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputData));

        assertTrue(exception.getMessage().contains("Skipping empty or null line"));
    }

    @Test
    void convertToTransaction_notEnoughParts_throwsException() {
        List<String> inputData = Arrays.asList("b,apple,");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputData));

        assertTrue(exception.getMessage().contains("Invalid data format"));
    }

    @Test
    void convertToTransaction_emptyOrNullValues_throwsException() {
        List<String> inputData = Arrays.asList("b,,10");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputData));

        assertTrue(exception.getMessage().contains("Skipping empty or null values found in line"));
    }

    @Test
    void convertToTransaction_InvalidNumberFormat_throwsException() {
        List<String> inputData = Arrays.asList("b,apple,1w0");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputData));

        assertTrue(exception.getMessage().contains("Invalid number format in line"));
    }

    @Test
    void convertToTransaction_errorProcessingLine_throwsException() {
        List<String> inputData = Arrays.asList("b,apple,10,extra");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputData));

        assertTrue(exception.getMessage().contains("Error processing line"));
    }
}

