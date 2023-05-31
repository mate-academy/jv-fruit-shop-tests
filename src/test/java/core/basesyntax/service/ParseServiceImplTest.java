package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.template.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void parseLineNullInputLine_NotOk() {
        String line = null;
        parseService.parseLine(line);
    }

    @Test (expected = RuntimeException.class)
    public void parseLineEmptyInputLine_NotOk() {
        String line = "";
        parseService.parseLine(line);
    }

    @Test (expected = IllegalArgumentException.class)
    public void parseLineIncorrectInputLineOrderStructure_NotOk() {
        String firstInputLine = "banana,b,20";
        FruitTransaction actual = parseService.parseLine(firstInputLine);
        actual.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    public void parseLineFruitTransactionReturnObjectType_Ok() {
        String operation = "b,banana,20";
        FruitTransaction actual = parseService.parseLine(operation);

        assertEquals(FruitTransaction.class, actual.getClass());
    }
}
