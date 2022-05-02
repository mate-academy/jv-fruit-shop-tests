package service.impl;

import model.Fruit;
import model.Transaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ParserService;

public class ParserServiceImplTest extends ParserServiceImpl {
    private static final String INPUT_LINE = "s,banana,100";
    private static ParserService parserService;
    private static Transaction expected;

    @BeforeClass
    public static void setUp() {
        parserService = new ParserServiceImpl();
        expected = new Transaction();
        expected.setFruit(new Fruit("banana"));
        expected.setQuantity(100);
        expected.setOperation("s");
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullInput_notOk() {
        parserService.parse(null);
    }

    @Test
    public void parse_correctData_ok() {
        Transaction actual = parserService.parse(INPUT_LINE);
        Assert.assertEquals(expected, actual);
    }
}
