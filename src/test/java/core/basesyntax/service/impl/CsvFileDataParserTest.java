package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileDataParser;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileDataParserTest {
    private static final List<String> INVALID_DATA = List.of(
            "type,fruit,quantity",
            "a,banana",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private static final List<String> VALID_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );

    private static final FileDataParser fileDataParser = new CsvFileDataParser();

    @Test
    void parseData_inputListIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> fileDataParser.parseData(null));
    }

    @Test
    void parseData_inputListIsEmpty_notOk() {
        List<FruitTransaction> actual = fileDataParser.parseData(Collections.emptyList());
        assertTrue(actual.isEmpty());
    }

    @Test
    void parseData_inputListContainsInvalidData_notOk() {
        IllegalInputDataException actual = assertThrows(IllegalInputDataException.class,
                () -> fileDataParser.parseData(INVALID_DATA));
        assertEquals("Invalid input line a,banana", actual.getMessage());
    }

    @Test
    void parseData_inputListContainsValidData_Ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        List<FruitTransaction> actual = fileDataParser.parseData(VALID_DATA);
        assertEquals(expected, actual);
    }
}
