package service.impl;

import exception.ValidationException;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;
import util.TestConstants;

class DataConverterImplTest {
    private static DataConverter dataConverter;
    private List<String> inputReport;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl(new DataValidatorServiceImpl());
    }

    @BeforeEach
    void setUp() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_2,
                TestConstants.REPORT_LINE_3,
                TestConstants.REPORT_LINE_4,
                TestConstants.REPORT_LINE_5);
    }

    @Test
    void convertToTransaction_emptyInput_notOk() {
        inputReport = List.of();
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullInput_notOk() {
        inputReport = null;
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_reportOnlyWithHeaders_notOk() {
        inputReport = List.of(TestConstants.HEADER);
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_incorrectDataLength_notOk() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.INVALID_REPORT_LINE_LENGTH,
                TestConstants.REPORT_LINE_2);
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullFruitName_notOk() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_NULL_FRUIT,
                TestConstants.REPORT_LINE_3,
                TestConstants.REPORT_LINE_4);
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullOperationName_notOk() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_2,
                TestConstants.REPORT_LINE_NULL_OPERATION,
                TestConstants.REPORT_LINE_4);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_invalidOperationName_notOk() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_2,
                TestConstants.REPORT_LINE_3,
                TestConstants.REPORT_LINE_INVALID_OPERATION);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullQuantity_notOk() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_2,
                TestConstants.REPORT_LINE_3,
                TestConstants.REPORT_LINE_NULL_QUANTITY);
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        inputReport = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_2,
                TestConstants.REPORT_LINE_NEGATIVE_QUANTITY,
                TestConstants.REPORT_LINE_4);
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_validInput_isOk() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, TestConstants.BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, TestConstants.APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, TestConstants.BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, TestConstants.BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, TestConstants.APPLE, 4)
        );
        Assertions.assertEquals(expected, dataConverter.convertToTransaction(inputReport));
    }

    @AfterEach
    void tearDown() {
        inputReport = List.of();
    }
}
