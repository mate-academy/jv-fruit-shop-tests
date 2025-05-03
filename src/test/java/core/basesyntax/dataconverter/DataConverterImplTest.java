package core.basesyntax.dataconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
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
        List<String> input = List.of("header", "b,banana,20", "p,apple,10", "s,apple,30");

        List<String> inputWithoutHeader = input.subList(1, input.size());

        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30)
        ));

        List<FruitTransaction> result = new ArrayList<>(
                dataConverter.convertToTransaction(inputWithoutHeader));

        assertEquals(expected, result);
    }

    @Test
    void convertToTransaction_invalidOperation_throwsException() {
        List<String> input = List.of("x,orange,15");
        Exception exception = assertThrows(DataProcessingException.class, () ->
                dataConverter.convertToTransaction(input));

        assertTrue(exception.getMessage().contains("Unknown operation"));
    }

    @Test
    void convertToTransaction_invalidQuantity_throwsException() {
        List<String> input = List.of("p,grape,-10");
        Exception exception = assertThrows(DataProcessingException.class, () ->
                dataConverter.convertToTransaction(input));

        assertTrue(exception.getMessage().contains(
                "Error: negative amount of fruit in the line: "));
    }

    @Test
    void convertToTransaction_missingFruit_throwsException() {
        List<String> input = List.of("b,,20");
        Exception exception = assertThrows(DataProcessingException.class, () ->
                dataConverter.convertToTransaction(input));

        assertTrue(exception.getMessage().contains("Invalid input format"));
    }

    @Test
    void convertToTransaction_emptyInput_returnsEmptyList() {
        List<String> input = List.of();
        List<FruitTransaction> result = dataConverter.convertToTransaction(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_missingFields_throwsException() {
        List<String> input = List.of("b,banana");
        Exception exception = assertThrows(DataProcessingException.class, () ->
                dataConverter.convertToTransaction(input));

        assertTrue(exception.getMessage().contains("does not contain all 3 fields"));
    }

    @Test
    void convertToTransaction_nonNumericQuantity_throwsException() {
        List<String> input = List.of("s,apple,ten");
        Exception exception = assertThrows(DataProcessingException.class, () ->
                dataConverter.convertToTransaction(input));

        assertTrue(exception.getMessage().contains("quantity is not a number"));
    }
}
