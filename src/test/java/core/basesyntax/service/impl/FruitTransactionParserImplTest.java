package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataTypeException;
import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {
    private static final List<String> INVALID_DATA = Arrays.asList(
            "b,banana",
            "p,apple,5,extra"
    );
    private static final List<String> VALID_DATA = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100"
    );

    private FruitTransactionParser fruitTransactionParser;

    @BeforeEach
    void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    void parse_InvalidDataFormat_ThrowsException() {
        assertThrows(
                InvalidDataTypeException.class, () -> fruitTransactionParser.parse(INVALID_DATA));
    }

    @Test
    void parse_ValidDataFormat_ReturnsCorrectTransactions() {
        List<FruitsTransaction> expectedTransactions = Arrays.asList(
                new FruitsTransaction("b", "banana", 20),
                new FruitsTransaction("b", "apple", 100)
        );
        List<FruitsTransaction> actualTransactions = fruitTransactionParser.parse(VALID_DATA);
        assertEquals(expectedTransactions, actualTransactions);
    }

}
