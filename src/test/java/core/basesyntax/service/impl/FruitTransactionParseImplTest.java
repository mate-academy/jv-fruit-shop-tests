package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

public class FruitTransactionParseImplTest {
    private static final String DEFAULT_VALID_FILE = "src/main/resources/example.csv";
    private static final String NULL_FRUIT_IN_FILE = "src/main/resources/exampleWithNullFruit.csv";
    private static final String NULL_QUANTITY_IN_FILE = "src/main/resources/"
            + "exampleWithNullQuantity.csv";
    private static final String INVALID_QUANTITY_IN_FILE = "src/main/resources/"
            + "exampleInvalidQuantity.csv";
    private FileReaderCsv fileReaderCsv = new FileReaderCsv();
    private FruitTransactionParserImpl fruitTransactionParser = new FruitTransactionParserImpl();

    @Test
    void parse_validData_Ok() {
        int actual = fruitTransactionParser.parse(fileReaderCsv
                .read(DEFAULT_VALID_FILE)).size();
        int expected = fileReaderCsv.read(DEFAULT_VALID_FILE).size();
        assertEquals(expected, actual);
    }

    @Test
    void parse_invalidQuantity_ThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () ->
                        fruitTransactionParser.parse(fileReaderCsv.read(INVALID_QUANTITY_IN_FILE)),
                "Input quantity is not valid");
    }

    @Test
    void parse_NullQuantity_ThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () ->
                        fruitTransactionParser.parse(fileReaderCsv.read(NULL_QUANTITY_IN_FILE)),
                "Input quantity is not valid");
    }

    @Test
    void parse_NullFruit_ThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () ->
                fruitTransactionParser.parse(fileReaderCsv.read(NULL_FRUIT_IN_FILE)),
                "Fruit does not exist");
    }
}
