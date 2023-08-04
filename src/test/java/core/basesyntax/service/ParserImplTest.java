package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserImplTest {
    private static final List<String> VALID_DATA = List.of("b,banana,20",
            "s,apple,100", "p,banana,10");
    private static final List<String> NON_VALID_OPERATION_DATA = List.of("b,banana,20",
            "v,apple,100", "p,banana,10");
    private static final String NON_VALID_OPERATION_CODE = "v";
    private static final List<String> NON_VALID_ZERO_QUANTITY_DATA =
            List.of("b,banana,0");
    private static final List<String> NON_VALID_NEGATIVE_QUANTITY_DATA =
            List.of("b,banana,-1");
    private static final List<String> NON_VALID_MISSING_FIELD_DATA =
            List.of("b,banana");
    private Parser parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserImpl();
    }

    @Test
    void parseDataFromFile_ValidData_ok() {
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction("b", "banana", 20),
                new FruitTransaction("s", "apple", 100),
                new FruitTransaction("p", "banana", 10)
        );

        List<FruitTransaction> actualTransactions = parserService
                .parseDataFromFile(VALID_DATA);

        assertEquals(expectedTransactions, actualTransactions,
                "Transactions should be equal.");
    }

    @Test
    void parseDataFromFile_nonValidOperationData_notOk() {
        NoSuchElementException noSuchElementException = Assertions
                .assertThrows(NoSuchElementException.class,
                        () -> parserService.parseDataFromFile(NON_VALID_OPERATION_DATA));
        assertEquals(noSuchElementException.getMessage(),
                "Invalid operation type. Operation code: "
                        + NON_VALID_OPERATION_CODE);
    }

    @Test
    void parseDataFromFile_zeroQuantity_notOk() {
        String invalidDataLine = "b,banana,0";
        RuntimeException runtimeException = Assertions
                .assertThrows(RuntimeException.class,
                        () -> parserService.parseDataFromFile(NON_VALID_ZERO_QUANTITY_DATA));
        assertEquals(runtimeException.getMessage(),
                "Quantity cannot be equal or below zero. Invalid quantity in line "
                        + invalidDataLine);
    }

    @Test
    void parseDataFromFile_negativeQuantity_notOk() {
        String invalidDataLine = "b,banana,-1";
        RuntimeException runtimeException = Assertions
                .assertThrows(RuntimeException.class,
                        () -> parserService.parseDataFromFile(NON_VALID_NEGATIVE_QUANTITY_DATA));
        assertEquals(runtimeException.getMessage(),
                "Quantity cannot be equal or below zero. Invalid quantity in line "
                        + invalidDataLine);
    }

    @Test
    void parseDataFromFile_nullValue_notOk() {
        RuntimeException runtimeException = Assertions
                .assertThrows(RuntimeException.class,
                        () -> parserService.parseDataFromFile(null));
        assertEquals(runtimeException.getMessage(),
                "Input data cannot be null.");
    }

    @Test
    void parseDataFromFile_missingField_notOk() {
        String invalidDataLine = "b,banana";
        RuntimeException runtimeException = Assertions
                .assertThrows(RuntimeException.class,
                        () -> parserService.parseDataFromFile(NON_VALID_MISSING_FIELD_DATA));
        assertEquals(runtimeException.getMessage(),
                "Invalid CSV line: " + invalidDataLine);
    }
}
