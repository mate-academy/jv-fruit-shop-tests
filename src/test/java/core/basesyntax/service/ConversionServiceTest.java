package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.NonExistingOperationException;
import core.basesyntax.exceptions.NonNumericDataException;
import core.basesyntax.service.impl.ConversionServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConversionServiceTest {
    private static ConversionService conversionService;

    @BeforeAll
    public static void init() {
        conversionService = new ConversionServiceImpl();
    }

    @Test
    public void convert_invalidOperationCode_notOk() {
        List<String[]> invalidTransactions = List.of(
                new String[]{"bb", "banana", "234"},
                new String[]{"p", "apple", "23"});
        assertThrows(NonExistingOperationException.class,
                () -> conversionService.convert(invalidTransactions),
                "Provided a non-existing operation code!");
    }

    @Test
    public void convert_invalidQuantityDataType_notOk() {
        List<String[]> invalidTransactions = List.of(
                new String[]{"b", "banana", "qwerty"},
                new String[]{"p", "apple", "23"});
        assertThrows(NonNumericDataException.class,
                () -> conversionService.convert(invalidTransactions),
                "Non-numeric data provided as quantity!");
    }

    @Test
    public void convert_validData_ok() {
        List<String[]> validTransactions = List.of(
                new String[]{"b", "banana", "23"},
                new String[]{"p", "apple", "234"});
        assertDoesNotThrow(() -> conversionService.convert(validTransactions));
    }
}
