package core.basesyntax.service.impl;

import static core.basesyntax.util.FruitTestConstants.APPLE;
import static core.basesyntax.util.FruitTestConstants.BANANA;
import static core.basesyntax.util.FruitTestConstants.MELON;
import static core.basesyntax.util.FruitTestConstants.PEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FileDataParserCsvImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileDataParserCsvImplTest {
    private FileDataParser fileDataParser;

    @BeforeEach
    public void setUp() {
        fileDataParser = new FileDataParserCsvImpl();
    }

    @Test
    public void parseData_validData_ok() {
        List<String> dataFromFile = Arrays.asList("b,apple,10", "s,banana,20",
                "p,pear,30", "r,melon,40");
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, PEAR, 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, MELON, 40)
        );
        List<FruitTransaction> actual = fileDataParser.parseData(dataFromFile);
        assertEquals(expected, actual, "Parsed transactions do not match the expected output.");
    }

    @Test
    public void parseData_nullData_notOk() {
        List<FruitTransaction> result = fileDataParser.parseData(null);
        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "Result should be an empty list when input data is null.");
    }

    @Test
    public void parseData_emptyList_ok() {
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = fileDataParser.parseData(Collections.emptyList());
        assertEquals(expected, actual, "Parsed transactions from an empty list should be empty.");
    }

    @Test
    public void parseData_invalidOperation_notOk() {
        List<String> dataFromFile = Collections.singletonList("x,apple,10");
        assertThrows(IllegalArgumentException.class, () -> fileDataParser.parseData(dataFromFile),
                "IllegalArgumentException should be thrown for unknown operation codes.");
    }

    @Test
    public void parseData_incorrectFormat_notOk() {
        List<String> dataFromFile = Collections.singletonList("b,apple");
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                        fileDataParser.parseData(dataFromFile),
                "ArrayIndexOutOfBoundsException should be thrown if the data format is incorrect.");
    }
}
