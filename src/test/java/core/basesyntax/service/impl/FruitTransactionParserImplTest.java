package core.basesyntax.service.impl;

import core.basesyntax.exceptions.InvalidDataTypeException;
import core.basesyntax.service.FruitTransactionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FruitTransactionParserImplTest {
    private static final List<String> INVALID_DATA = Arrays.asList(
            "b,banana",
            "p,apple,5,extra"
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
}
