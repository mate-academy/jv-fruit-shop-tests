package service.impl;

import model.TransactionDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.Parser;
import service.Validator;

public class ParserImplTest {
    private static Parser parser;
    private static Validator validator;

    @BeforeClass
    public static void beforeAll() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(validator);
    }

    @Test
    public void parsLine_parseLine_ok() {
        String line = "r,banana,100";
        TransactionDto expected = new TransactionDto("r", "banana",100);
        TransactionDto actual = parser.parseLine(line);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parsLine_notValid_notOk() {
        String line = "r,banana, 100";
        parser.parseLine(line);
    }
}
