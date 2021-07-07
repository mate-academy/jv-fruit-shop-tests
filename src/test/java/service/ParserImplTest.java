package service;

import static org.junit.Assert.assertEquals;

import dto.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static final int TYPE_OPERATION = 0;
    private static final int NAME_FRUIT = 1;
    private static final int QUANTITY = 2;
    private static final String VALID_LINE = "b,banana,20";
    private static final String NOT_VALID_TYPE = "t,banana,20";
    private static final String NOT_VALID_FRUIT_NAME = "b,@#*,20";
    private static final String NOT_VALID_QUANTITY = "b,banana,%";
    private static final String NOT_VALID_COLUMN = "banana,20";
    private static final String SYMBOL_FOR_SPLIT = ",";
    private static Parser parser;

    @BeforeClass
    public static void operationBeforeTest() {
        parser = new ParserImpl();
    }

    @Test
    public void parseLine_validLine_Ok() {
        Transaction actual = parser.parseLine(VALID_LINE);
        String[] validData = VALID_LINE.split(SYMBOL_FOR_SPLIT);
        Transaction expected = new Transaction(validData[TYPE_OPERATION],
                validData[NAME_FRUIT], Integer.parseInt(validData[QUANTITY]));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_notValidType_NotOk() {
        parser.parseLine(NOT_VALID_TYPE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_notValidFruitName_NotOk() {
        parser.parseLine(NOT_VALID_FRUIT_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_notValidQuantity_NotOk() {
        parser.parseLine(NOT_VALID_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_notValidColumn_NotOk() {
        parser.parseLine(NOT_VALID_COLUMN);
    }
}
