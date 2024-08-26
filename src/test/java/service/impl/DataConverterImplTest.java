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

class DataConverterImplTest {
    private static DataConverter dataConverter;
    private List<String> inputReport;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl(new DataValidatorServiceImpl());
    }

    @BeforeEach
    void setUp() {
        inputReport = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,4");
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
        inputReport = List.of("type,fruit,quantity");
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_incorrectDataLength_notOk() {
        inputReport = List.of("type,fruit,quantity",
                "b,20",
                "b,apple");
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullFruitName_notOk() {
        inputReport = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,null,100",
                "s,banana,100",
                "p,banana,13");
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullOperationName_notOk() {
        inputReport = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "null,banana,100",
                "p,banana,13");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_invalidOperationName_notOk() {
        inputReport = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "BALANCE,banana,13");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_nullQuantity_notOk() {
        inputReport = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,null");
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        inputReport = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,-100",
                "p,banana,13");
        Assertions.assertThrows(ValidationException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_validInput_isOk() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 4)
        );
        Assertions.assertEquals(expected, dataConverter.convertToTransaction(inputReport));
    }

    @AfterEach
    void tearDown() {
        inputReport = List.of();
    }
}
