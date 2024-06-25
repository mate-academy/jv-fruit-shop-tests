package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.errors.ErrorMessages;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing DataConverterImpl")
class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_nullReport_notOK() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(null));
        assertEquals(ErrorMessages.INPUT_REPORT_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage(),
                getInfoMessage(ErrorMessages.INPUT_REPORT_CANNOT_BE_NULL_OR_EMPTY,
                        exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_emptyReport_notOK() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(Collections.emptyList()));
        assertEquals(ErrorMessages.INPUT_REPORT_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage(),
                getInfoMessage(ErrorMessages.INPUT_REPORT_CANNOT_BE_NULL_OR_EMPTY,
                        exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReport_ok() {
        List<String> inputReport = List.of("b,banana,20", "s,banana,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100)
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertEquals(expected, actual,
                "Test failed, expected: " + expected + " but actual " + actual);
    }

    @Test
    void convertToTransaction_validReportWithEmptyLine_notOK() {
        List<String> inputReport = List.of("", "s,banana,100");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(ErrorMessages.STRING_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage(),
                getInfoMessage(ErrorMessages.STRING_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidLine_notOK() {
        List<String> inputReport = List.of("b,banana,20", "s,banana,100", "invalid");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(ErrorMessages.INVALID_INPUT, exception.getMessage(),
                getInfoMessage(ErrorMessages.INVALID_INPUT, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidCodeOperation_notOK() {
        String invalidCode = "q";
        String expectedErrorMessage = ErrorMessages.INVALID_OPERATION_CODE + invalidCode;
        List<String> inputReport = List.of(invalidCode + ",banana,20");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(expectedErrorMessage, exception.getMessage(),
                getInfoMessage(expectedErrorMessage, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidFruitName_notOK() {
        List<String> inputReport = List.of("s,,100");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(ErrorMessages.FRUIT_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage(),
                getInfoMessage(ErrorMessages.FRUIT_NAME_CANNOT_BE_NULL_OR_EMPTY,
                        exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidQuantity_notOK() {
        String invalidQuantity = "s";
        String expectedErrorMessage = ErrorMessages.INVALID_QUANTITY + invalidQuantity;
        List<String> inputReport = List.of("s,banana," + invalidQuantity);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(expectedErrorMessage, exception.getMessage(),
                getInfoMessage(expectedErrorMessage, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithNegativeQuantity_notOK() {
        String negativeQuantity = "-1";
        String expectedErrorMessage = ErrorMessages.NEGATIVE_QUANTITY + negativeQuantity;
        List<String> inputReport = List.of("s,banana," + negativeQuantity);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(expectedErrorMessage, exception.getMessage(),
                getInfoMessage(expectedErrorMessage, exception.getMessage())
        );
    }

    private String getInfoMessage(String expectedMessage, String actualMessage) {
        return "Test failed, expected: " + expectedMessage + " but actual " + actualMessage;
    }
}
