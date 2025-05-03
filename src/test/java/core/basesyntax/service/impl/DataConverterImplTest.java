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
    private static final String BANANA_FRUIT_DATA_20 = "b,banana,20";
    private static final String BANANA_FRUIT_DATA_100 = "s,banana,100";
    private static final String BANANA_FRUIT = "banana";
    private static final String DATA_WITHOUT_QUANTITY = "s,banana,";
    private static final String DATA_WITHOUT_FRUIT_NAME = "s,,100";
    private static final String DATA_WITHOUT_CODE = ",banana,20";
    private static final String INVALID_DATA = "invalid";
    private static final String INVALID_CODE = "q";
    private static final String INVALID_QUANTITY = "s";
    private static final String NEGATIVE_QUANTITY = "-1";
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_100 = 100;
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
        List<String> inputReport = List.of(BANANA_FRUIT_DATA_20, BANANA_FRUIT_DATA_100);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA_FRUIT, QUANTITY_20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA_FRUIT, QUANTITY_100)
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertEquals(expected, actual,
                "Test failed, expected: " + expected + " but actual " + actual);
    }

    @Test
    void convertToTransaction_validReportWithEmptyLine_notOK() {
        List<String> inputReport = List.of("", BANANA_FRUIT_DATA_100);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(ErrorMessages.STRING_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage(),
                getInfoMessage(ErrorMessages.STRING_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidLine_notOK() {
        List<String> inputReport = List.of(BANANA_FRUIT_DATA_20, BANANA_FRUIT_DATA_100,
                INVALID_DATA);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(ErrorMessages.INVALID_INPUT, exception.getMessage(),
                getInfoMessage(ErrorMessages.INVALID_INPUT, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidCodeOperation_notOK() {
        String expectedErrorMessage = ErrorMessages.INVALID_OPERATION_CODE + INVALID_CODE;
        List<String> inputReport = List.of(INVALID_CODE + DATA_WITHOUT_CODE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(expectedErrorMessage, exception.getMessage(),
                getInfoMessage(expectedErrorMessage, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidFruitName_notOK() {
        List<String> inputReport = List.of(DATA_WITHOUT_FRUIT_NAME);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(ErrorMessages.FRUIT_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage(),
                getInfoMessage(ErrorMessages.FRUIT_NAME_CANNOT_BE_NULL_OR_EMPTY,
                        exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithInvalidQuantity_notOK() {
        String expectedErrorMessage = ErrorMessages.INVALID_QUANTITY + INVALID_QUANTITY;
        List<String> inputReport = List.of(DATA_WITHOUT_QUANTITY + INVALID_QUANTITY);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(inputReport));
        assertEquals(expectedErrorMessage, exception.getMessage(),
                getInfoMessage(expectedErrorMessage, exception.getMessage())
        );
    }

    @Test
    void convertToTransaction_validReportWithNegativeQuantity_notOK() {
        String expectedErrorMessage = ErrorMessages.NEGATIVE_QUANTITY + NEGATIVE_QUANTITY;
        List<String> inputReport = List.of(DATA_WITHOUT_QUANTITY + NEGATIVE_QUANTITY);
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
