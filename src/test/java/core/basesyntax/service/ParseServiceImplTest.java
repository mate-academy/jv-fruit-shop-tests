package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.template.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parseLineNullInputLine_NotOk() {
        String line = null;

        try {
            parseService.parseLine(line);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is null");
    }

    @Test
    public void parseLineEmptyInputLine_NotOk() {
        String line = null;

        try {
            parseService.parseLine(line);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if value is empty");
    }

    @Test
    public void parseLineIncorectInputLine_1_NotOk() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setFruit("banana");
        expected.setQuantity(20);
        String line = "banana,b,20";
        try {
            FruitTransaction actual = parseService.parseLine(line);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("IllegalArgumentException should be thrown for incorrect operation "
                + line);
    }

    @Test
    public void parseLineIncorectInputLine_2_NotOk() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setFruit("banana");
        expected.setQuantity(20);
        String line = "b,20,banana";
        try {
            FruitTransaction actual = parseService.parseLine(line);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("IllegalArgumentException should be thrown for incorrect operation "
                + line);
    }

    @Test
    public void parseLineFruitTransactionReturnObjectType_Ok() {
        String operation = "b,banana,20";
        FruitTransaction actual = parseService.parseLine(operation);

        assertEquals(FruitTransaction.class, actual.getClass());
    }
}
