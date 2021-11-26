package service.impl;

import db.Storage;
import model.TransactionDto;
import org.junit.After;
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
    public void parsLine_ok() {
        String line = "r,banana,100";
        TransactionDto expected = new TransactionDto("r", "banana",100);
        TransactionDto actual = parser.parsLine(line);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parsLine_notOk() {
        String line = "r,banana, 100";
        parser.parsLine(line);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
