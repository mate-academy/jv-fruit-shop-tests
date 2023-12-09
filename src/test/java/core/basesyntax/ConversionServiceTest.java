package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.NonExistingOperationException;
import core.basesyntax.exceptions.NonNumericDataException;
import core.basesyntax.service.ConversionService;
import core.basesyntax.service.impl.ConversionServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConversionServiceTest {
    private static ConversionService conversionService;

    @BeforeAll
    public static void setConversionService() {
        conversionService = new ConversionServiceImpl();
    }

    @Test
    public void convert_invalidOperationCode_NotOk() {
        List<String[]> invalidList = List.of(
                new String[]{"bb", "banana", "234"},
                new String[]{"p", "apple", "23"});
        assertThrows(NonExistingOperationException.class,
                () -> conversionService.convert(invalidList),
                "Provided a non-existing operation code!");
    }

    @Test
    public void convert_invalidQuantityDataType_NotOk() {
        List<String[]> invalidList = List.of(
                new String[]{"b", "banana", "qwerty"},
                new String[]{"p", "apple", "23"});
        assertThrows(NonNumericDataException.class,
                () -> conversionService.convert(invalidList),
                "Non-numeric data provided as quantity!");
    }

    @Test
    public void convert_validData_Ok() {
        List<String[]> validList = List.of(
                new String[]{"b", "banana", "23"},
                new String[]{"p", "apple", "234"});
        assertDoesNotThrow(() -> conversionService.convert(validList),
                "Provided a non-existing operation code!");
        assertDoesNotThrow(() -> conversionService.convert(validList),
                "Non-numeric data provided as quantity!");
    }
}
