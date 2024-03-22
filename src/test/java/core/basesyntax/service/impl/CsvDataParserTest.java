package core.basesyntax.service.impl;

import static core.basesyntax.model.Operation.BALANCE;
import static core.basesyntax.model.Operation.PURCHASE;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class CsvDataParserTest {
    private static List<String> validInputData = new ArrayList<>();
    private static List<String> testList = new ArrayList<>();
    private static List<FruitTransaction> validResultList = new ArrayList<>();
    private static CsvDataParser csvDataParser = new CsvDataParser();

    static {
        validInputData.add("type,fruit,quantity");
        validInputData.add("b,banana,20");
        validInputData.add("p,apple,100");

        validResultList.add(new FruitTransaction(BALANCE, "banana", 20));
        validResultList.add(new FruitTransaction(PURCHASE, "apple", 100));
    }

    @AfterEach
    void clearTestList() {
        testList.clear();
    }

    @Test
    void map_allValidConditions_Ok() {
        var expected = validResultList;
        var actual = csvDataParser.map(validInputData);
        assertIterableEquals(expected, actual);
    }

    @Test
    void map_nullInput_throwsException() {
        assertThrows(InvalidInputDataException.class,
                () -> csvDataParser.map(null));
    }

    @Test
    void map_incorrectHeaderLinePattern_throwException() {
        testList.add("type,fruit,quantity,condition");
        testList.add("b,fruit,50");
        assertThrows(InvalidInputDataException.class,
                () -> csvDataParser.map(testList));
    }

    @Test
    void map_incorrectDataLinePattern_throwException() {
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("p,apple,apple,100");
        assertThrows(InvalidInputDataException.class,
                () -> csvDataParser.map(testList));
    }

    @Test
    void map_emptyLineInFile_throwException() {
        testList.add("type,fruit,quantity");
        testList.add("");
        testList.add("p,apple,apple,100");
        assertThrows(InvalidInputDataException.class,
                () -> csvDataParser.map(testList));
    }
}
