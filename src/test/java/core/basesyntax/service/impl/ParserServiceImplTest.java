package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseLineValidOperation_ok() {
        FruitTransaction testFruitTransaction = new FruitTransaction();
        String lineFromFile = "p,banana,5";
        testFruitTransaction.setOperation(FruitTransaction.OperationType.PURCHASE);
        testFruitTransaction.setFruitName("banana");
        testFruitTransaction.setQuantity(5);
        FruitTransaction actual = parserService.parse(lineFromFile);
        FruitTransaction expected = testFruitTransaction;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseLineInvalidOperation_notOk() {
        parserService.parse("write-off,banana,20");
    }

    @Test(expected = RuntimeException.class)
    public void parseLineEmptyData_notOk() {
        parserService.parse("");
    }
}
