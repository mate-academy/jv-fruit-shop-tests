package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_convertValidInput_ok() {
        List<String> inputList = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,banana,30",
                "r,apple,10"
        );
        List<FruitTransaction> expectedList = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );
        List<FruitTransaction> actualList = dataConverter.convertToTransaction(inputList);
        assertEquals(expectedList, actualList);
    }

    @Test
    void convertToTransaction_convertInvalidQuantityInput_notOk() {
        List<String> inputList = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,fifty",
                "p,banana,quantity",
                "r,apple,10"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputList));
    }

    @Test
    void convertToTransaction_convertInvalidInput_notOk() {
        List<String> inputList = Arrays.asList(
                "type,fruit,quantity",
                "apple,100",
                "s,fifty",
                "p,banana"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputList));
    }

    @Test
    void convertToTransaction_convertNullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_convertEmptyInput_notOk() {
        List<String> inputList = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputList));
    }
}
