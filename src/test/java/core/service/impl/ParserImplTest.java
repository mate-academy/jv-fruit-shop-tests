package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.model.TransactionDto;
import core.service.Parser;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parseLine_correctWork_ok() {
        String line = "b,banana,30";
        TransactionDto expected = new TransactionDto("b", "banana", 30);
        TransactionDto actual = parser.parseLine(line);
        assertEquals(expected,actual);
    }

    @Test
    public void parseLine_notValidData_ok() {
        String line = "f,apple,35";
        TransactionDto expected = new TransactionDto("f", "apple", 35);
        TransactionDto actual = parser.parseLine(line);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_emptyLine_notOk() {
        String line = "";
        parser.parseLine(line);
    }

    @Test(expected = NullPointerException.class)
    public void parseLine_null_notOk() {
        parser.parseLine(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_withOutOperation_notOk() {
        String line = "banana,20";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_withOutQuantity_notOk() {
        String line = "b,banana";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_withOutFruit_notOk() {
        String line = "b,20";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_redundantWhiteSpace_notOk() {
        String line = "b,banana,20 ";
        parser.parseLine(line);
    }

}
